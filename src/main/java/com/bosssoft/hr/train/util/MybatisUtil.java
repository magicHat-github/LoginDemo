package com.bosssoft.hr.train.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * mybatis 工具类
 * @author likang
 */
public class MybatisUtil {

    /**
     * 生成 Mybatis SqlSessionFactory
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory(){
        String resource = "mybatis-config.xml";
        InputStream inputStream;
        SqlSessionFactory sqlSessionFactory = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory =  new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }
}
