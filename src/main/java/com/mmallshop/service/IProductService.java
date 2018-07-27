package com.mmallshop.service;

import com.github.pagehelper.PageInfo;
import com.mmallshop.common.ServerResponse;
import com.mmallshop.pojo.Product;
import com.mmallshop.vo.ProductDetailVo;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/7/26
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public interface IProductService {
    public ServerResponse saveOrUpdateProduct(Product product);
    public ServerResponse<String> setSaleStatus(Integer productId,Integer status);


    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);
}
