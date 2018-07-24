package com.mmallshop.service;

import com.mmallshop.common.ServerResponse;
import com.mmallshop.pojo.User;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/7/24
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    public ServerResponse<String> checkVaild(String str,String type);

    public ServerResponse<String> register(User user);

    public ServerResponse<String> selectQuestion(String userName);

    public ServerResponse<String> checkAnswer(String userName, String question,String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);
}
