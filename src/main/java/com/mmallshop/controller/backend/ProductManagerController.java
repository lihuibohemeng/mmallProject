package com.mmallshop.controller.backend;

import com.mmallshop.common.Const;
import com.mmallshop.common.ResponseCode;
import com.mmallshop.common.ServerResponse;
import com.mmallshop.pojo.Product;
import com.mmallshop.pojo.User;
import com.mmallshop.service.IProductService;
import com.mmallshop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/7/26
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manager/product")
public class ProductManagerController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    IProductService iProductService;

     //   所有用户全部强制登录

//     商品添加或者更新
    public ServerResponse productSave(HttpSession session,Product product ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
           return  iProductService.saveOrUpdateProduct(product);
        }
        return ServerResponse.createByErrorMessage(" 无权限操作");
    }

//    商品更改上下架信息
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId,Integer status){
    User user = (User)session.getAttribute(Const.CURRENT_USER);
    if(user == null){
        return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

    }
    if(iUserService.checkAdminRole(user).isSuccess()){
        return iProductService.setSaleStatus(productId,status);
    }else{
        return ServerResponse.createByErrorMessage("无权限操作");
    }
}
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            return iProductService.manageProductDetail(productId);

        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse  getList(HttpSession session, @RequestParam(value = "pageNum" ,defaultValue = "1")int pageNum,@RequestParam(value = "pageSize" ,defaultValue = "10")int pagesize){

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
         return iProductService.getProductList(pageNum,pagesize);

        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

}
