package com.mmallshop.dao;

import com.mmallshop.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUserName(String userName);

    int checkEmail(String email);

    String selectQuestion(String userName);

    int updatePasswordByUsername(@Param("username") String userName,@Param("password") String password);

    User selectLogin(@Param("username") String userName, @Param("password")String password);

    int checkAnswer(@Param("username")String userName,@Param("answer")String answer,@Param("question")String question);
}