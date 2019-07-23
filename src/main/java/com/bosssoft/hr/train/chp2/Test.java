package com.bosssoft.hr.train.chp2;

import com.bosssoft.hr.train.chp2.pojo.User;
import com.bosssoft.hr.train.chp2.service.UserService;
import com.bosssoft.hr.train.chp2.service.impl.UserServiceImpl;
import com.bosssoft.hr.train.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * 文件和数据库相关的测试主类
 * @author likang
 */
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        logger.info("---------------------- 开始 ----------------------");
        long startTime =  System.currentTimeMillis();
        long lastTime;
        UserService userService = new UserServiceImpl();
        logger.info("测试新建1000条数据写入数据库中：");
        Boolean success = userService.insertUserData(Constants.DATA_SIZE);
        if (!success){
            logger.info("过程1 - 新建1000条数据写入数据库 - 失败！");
            return;
        }
        logger.info("新建1000条数据写入数据库成功! - 耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");

        logger.info("********************************");
        logger.info("测试从数据库读数据写入XML文件中:");
        lastTime = System.currentTimeMillis();
        List<User> list = userService.queryUserByPage(1000,200);
        logger.info("从数据库读取数据成功! - 耗时：" + (System.currentTimeMillis() - lastTime) + "毫秒");

        lastTime = System.currentTimeMillis();
        success = userService.saveDocument(userService.createDom4jXmlUser(list),new File("student.xml"));
        if (!success){
            logger.info("过程2 - 从数据库读数据写入XML文件 - 失败！");
            return;
        }
        logger.info("写入XML文件用时! - 耗时：" + (System.currentTimeMillis() - lastTime) + "毫秒");

        logger.info("********************************");
        logger.info("测试从XML文件中读数据写入数据库中:");
        lastTime = System.currentTimeMillis();
        List<User> users = userService.getUserListXmlDom("student.xml");
        logger.info("从XML读取数据成功! - 耗时：" + (System.currentTimeMillis() - lastTime) + "毫秒");

        lastTime = System.currentTimeMillis();
        success = userService.insertUserToBak(users);
        if (!success){
            logger.info("过程3 - 从XML文件中读数据写入数据库 - 失败！");
            return;
        }
        logger.info("写入数据库成功! - 耗时：" + (System.currentTimeMillis() - lastTime) + "毫秒");

        logger.info("********************************");
        logger.info("测试从数据库中读数据写入JSON文件中:");
        lastTime = System.currentTimeMillis();
        List<User> usersList = userService.queryUserByPage(1000,200);
        logger.info("从数据库读取数据成功! - 耗时：" + (System.currentTimeMillis() - lastTime) + "毫秒");

        lastTime = System.currentTimeMillis();
        success = userService.insertUsersToJsonFile(usersList);
        if (!success){
            logger.info("过程4 - 从数据库中读数据写入JSON文件 - 失败！");
            return;
        }
        logger.info("写入JSON文件成功! - 耗时：" + (System.currentTimeMillis() - lastTime) + "毫秒");

        logger.info("---------------------- 全部执行成功 ----------------------");
        logger.info("总共耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
    }
}
