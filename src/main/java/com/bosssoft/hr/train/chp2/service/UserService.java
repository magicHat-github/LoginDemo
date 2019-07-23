package com.bosssoft.hr.train.chp2.service;

import com.bosssoft.hr.train.chp2.pojo.User;
import org.dom4j.Document;

import java.io.File;
import java.util.List;

/**
 * 用户相关的业务层接口
 * @author likang
 * @date 2019/7/23 20:01
 */
public interface UserService {

    /**
     * 添加一千条数据至User表
     * @param insertLength 集合长度
     * @return Boolean
     */
    Boolean insertUserData(Integer insertLength);

    /**
     * 自动生成length条数据
     * @param length 集合长度
     * @return 用户集合
     */
    List<User> getUserList(Integer length);

    /**
     * 大数据分批次查询
     * @param number 总共查询的数据大小
     * @param size 每次查询的数据大小
     * @return 用户集合
     */
    List<User> queryUserByPage(Integer number, Integer size);

    /**
     * 通过Dom4J生成Document
     * @param users 用户集合
     * @return Document
     */
    Document createDom4jXmlUser(List<User> users);

    /**
     * 保存XML文档
     * @param document 文档
     * @param outputXml 文件
     * @return Boolean
     */
    Boolean saveDocument(Document document, File outputXml);

    /**
     * 从XML读取数据
     * @param fileName 文件名
     * @return 用户集合
     */
    List<User> getUserListXmlDom(String fileName);

    /**
     * 批量添加用户至数据表 t_user_bak
     * @param users 用户集合
     * @return Boolean
     */
    Boolean insertUserToBak(List<User> users);

    /**
     * 将用户集合写入JSON文件中
     * @param users 用户集合
     * @return Boolean
     */
    Boolean insertUsersToJsonFile(List<User> users);

    /**
     * 验证登录
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    User checkUserLogin(String username, String password);
}
