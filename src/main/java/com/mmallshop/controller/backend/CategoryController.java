package com.mmallshop.controller.backend;

import com.mmallshop.common.Const;
import com.mmallshop.common.ResponseCode;
import com.mmallshop.common.ServerResponse;
import com.mmallshop.pojo.Category;
import com.mmallshop.pojo.User;
import com.mmallshop.service.ICategoryService;
import com.mmallshop.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/7/25
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manager/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;
    @Autowired
    IUserService userService;

    @RequestMapping("/add_category.do")
    @ResponseBody
    public ServerResponse<String> addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0")Integer parentId){

        //       先判断用户有没有登录  是不是管理员
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
         if(userService.checkAdminRole(user).isSuccess()){
           return categoryService.addCategory(categoryName,parentId);
         }else{
             return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
         }
    }

    @RequestMapping("/set_category.do")
    @ResponseBody
    public ServerResponse<String> updateCategory(HttpSession session,String categoryName,Integer categoryId){

        //       先判断用户有没有登录  是不是管理员
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录");
        }
        if(userService.checkAdminRole(user).isSuccess()){
           return categoryService.updateCategory(categoryName,categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("/get_category.do")
    @ResponseBody
    public ServerResponse<List<Category>> getChildrenParallelCategory(HttpSession session,@RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //查询子节点的category信息,并且不递归,保持平级
            return categoryService.getChildrenParallelCategory(parentId);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

        @RequestMapping("/get_deep_category.do")
        @ResponseBody
        public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
            User user = (User)session.getAttribute(Const.CURRENT_USER);
            if(user == null){
                return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
            }
            if(userService.checkAdminRole(user).isSuccess()){
                //查询当前节点的id和递归子节点的id
//            0->10000->100000
                return categoryService.selectCategoryAndChildrenById(categoryId);

            }else{
                return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
            }

    }

}
