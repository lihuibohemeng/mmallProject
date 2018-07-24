package com.mmallshop.controller.portal;

import com.mmallshop.common.Const;
import com.mmallshop.common.ServerResponse;
import com.mmallshop.pojo.User;
import com.mmallshop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/7/24
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody  // 序列化为json数据返回
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> serverResponse = iUserService.login(username,password);
     if(serverResponse.isSuccess()){
         session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
     }
     return serverResponse;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        ServerResponse serverResponse = ServerResponse.createBySuccess();
        return serverResponse;
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(User user){
        return iUserService.register(user);
    }




}
