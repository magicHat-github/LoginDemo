package com.bosssoft.hr.train.chp2.dao;

import com.bosssoft.hr.train.chp2.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据库连接层
 * @author likang
 */
public interface UserDao {

    /**
     * 添加用户
     * @param user 用户
     */
    void saveOne(@Param("user") User user);

    /**
     * 批量添加用户至 t_user
     * @param userList 添加的用户集合
     * @return 插入的数据条数
     */
    Integer saveBatch(@Param("users") List<User> userList);

    /**
     * 批量添加用户至 t_user_bak
     * @param userList 添加的用户集合
     * @return 插入的数据条数
     */
    Integer saveBatchToBak(@Param("users") List<User> userList);

    /**
     * 获取全部用户集合
     * @return 用户集合
     */
    List<User> queryList();

    /**
     * 分页查询
     * @param start 起始位置
     * @param size 查询大小
     * @return 用户集合
     */
    List<User> queryByPage(@Param("start")Integer start,@Param("size")Integer size);

    /**
     * 登录验证
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    List<User> queryByNamePassword(@Param("username") String username, @Param("password") String password);
}
