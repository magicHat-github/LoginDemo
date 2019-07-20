package com.bosssoft.hr.train.chp2.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONWriter;
import com.bosssoft.hr.train.chp2.dao.UserDao;
import com.bosssoft.hr.train.chp2.pojo.User;
import com.bosssoft.hr.train.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务层
 * @author likang
 */
public class UserService {

    private static Logger logger = Logger.getLogger(UserService.class);
    /**
     * 循环的次数
     */
    private static final Integer CYCLE_NUMBER = 1;
    /**
     * 时间的格式转换
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 添加一千条数据至User表
     * @param insertLength 集合长度
     * @return Boolean
     */
    public Boolean insertUserData(Integer insertLength){
        boolean success = false;
        SqlSession sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
        try{
            List<User> userList = getUserList(insertLength);
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            Integer number = userDao.addUserBatch(userList);
            logger.info("需插入数据大小："+userList.size()+" --实际插入数据大小："+number);
            if (number.equals(userList.size())) {
                sqlSession.commit();
                success = true;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            sqlSession.close();
        }
        return success;
    }

    /**
     * 自动生成length条数据
     * @param length 集合长度
     * @return 用户集合
     */
    private List<User> getUserList(Integer length) {
        List<User> userList = new ArrayList<User>();
        try{
            Date date = new Date();
            for (int i=0;i<length;i++){
                User user = new User();
                user.setCode("code" + i);
                user.setName("李四" + i);
                user.setBirthday(date);
                user.setPassword("132" + i);
                user.setSex(i%2==0?"男":"女");
                userList.add(user);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return userList;
    }

    /**
     * 大数据分批次查询
     * @param number 总共查询的数据大小
     * @param size 每次查询的数据大小
     * @return 用户集合
     */
    public List<User> queryUserByPage(Integer number,Integer size){
        if (number>=0 && size>=0) {
            SqlSession sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
            List<User> list = new ArrayList<User>();
            try {
                UserDao userDao = sqlSession.getMapper(UserDao.class);
                Integer start = 0;
                int length = number % size == 0 ? (number / size) : (number / size + 1);
                for (int i = 0; i < length; i++) {
                    list.addAll(userDao.getUserByPage(start, size));
                    start += size;
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }finally {
                if (sqlSession!=null) {
                    sqlSession.close();
                }
            }
            return list;
        }else {
            return null;
        }
    }

    /**
     * 通过Dom4J生成Document
     * @param users 用户集合
     * @return Document
     */
    public Document createDom4jXMLFUser(List<User> users){
        if (users.size()>0) {
            Document document = DocumentHelper.createDocument();
            try {
                Element root = document.addElement("Users");
                root.addAttribute("size", String.valueOf(users.size()));
                for (int i = 0; i < CYCLE_NUMBER; i++) {
                    for (User user : users) {
                        Element element = root.addElement("User");
                        element.addAttribute("id", user.getId().toString());
                        element.addElement("code").setText(user.getCode());
                        element.addElement("name").setText(user.getName());
                        element.addElement("password").setText(user.getPassword());
                        element.addElement("sex").setText(user.getSex());
                        element.addElement("birthday").setText(simpleDateFormat.format(user.getBirthday()));
                    }
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }
            return document;
        }else {
            return null;
        }
    }

    /**
     * 保存XML文档
     * @param document 文档
     * @param outputXml 文件
     */
    public Boolean saveDocument(Document document, File outputXml){
        boolean success = false;
        FileOutputStream fileOutputStream = null;
        XMLWriter xmlWriter = null;
        try {
            if (document!=null && outputXml!=null) {
                fileOutputStream = new FileOutputStream(outputXml);
                OutputFormat outputFormat = OutputFormat.createPrettyPrint();
                outputFormat.setEncoding("UTF-8");
                xmlWriter = new XMLWriter(fileOutputStream, outputFormat);
                xmlWriter.write(document);
                success = true;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (xmlWriter != null) {
                    xmlWriter.close();
                }
            }catch (IOException e){
                logger.error(e.getMessage(),e);
            }
        }
        return success;
    }

    /**
     * 从XML读取数据
     * @param fileName 文件名
     * @return 用户集合
     */
    public List<User> getUserList4XMLDom(String fileName){
        SAXReader saxReader = new SAXReader();
        List<User> users = new ArrayList<User>();
        try {
            if (!"".equals(fileName)) {
                Document document = saxReader.read(fileName);
                Element root = document.getRootElement();
                List<Element> elements = root.elements("User");
                for (Element element : elements) {
                    User user = new User();
                    user.setId(Integer.valueOf(element.attributeValue("id")));
                    user.setCode(element.elementText("code"));
                    user.setName(element.elementText("name"));
                    user.setPassword(element.elementText("password"));
                    user.setSex(element.elementText("sex"));
                    user.setBirthday(simpleDateFormat.parse(element.elementText("birthday")));
                    users.add(user);
                }
            }
        } catch (DocumentException | ParseException e) {
            logger.error(e.getMessage());
        }
        return users;
    }

    /**
     * 批量添加用户至数据表 t_user_bak
     * @param users 用户集合
     * @return Boolean
     */
    public Boolean insertUserToBak(List<User> users){
        boolean success = false;
        SqlSession sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
        try{
            if(users.size()>0) {
                UserDao userDao = sqlSession.getMapper(UserDao.class);
                Integer number = userDao.addUserBakBatch(users);
                logger.info("需插入数据大小："+users.size()+" --实际插入数据大小："+number);
                sqlSession.commit();
                success = true;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            if (sqlSession!=null) {
                sqlSession.close();
            }
        }
        return success;
    }

    /**
     * 将用户集合写入JSON文件中
     * @param users 用户集合
     * @return Boolean
     */
    public Boolean insertUsersToJSONFile(List<User> users){
        boolean success = false;
        FileWriter fileWriter = null;
        JSONWriter jsonWriter = null;
        try{
            if (users.size()>0) {
                fileWriter = new FileWriter("student.json");
                jsonWriter = new JSONWriter(fileWriter);
                jsonWriter.writeObject(users);
                success = true;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            try {
                if (jsonWriter!=null){
                    jsonWriter.close();
                }
                if (fileWriter!=null){
                    fileWriter.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }
        return success;
    }

    /**
     * 验证登录
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    public User checkUserLogin(String username,String password){
        User user =null;
        if (StrUtil.isEmpty(username)||StrUtil.isEmpty(password)){
            return null;
        }
        SqlSession sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            List<User> users = userDao.queryUserByNamePassword(username,password);
            if (users!=null && users.size()>0) {
                user = users.get(0);
                user.setPassword("");
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            if (sqlSession!=null) {
                sqlSession.close();
            }
        }
        return user;
    }
}
