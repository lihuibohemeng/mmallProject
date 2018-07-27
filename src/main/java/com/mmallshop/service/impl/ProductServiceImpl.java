package com.mmallshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmallshop.common.ResponseCode;
import com.mmallshop.common.ServerResponse;
import com.mmallshop.dao.CategoryMapper;
import com.mmallshop.dao.ProductMapper;
import com.mmallshop.pojo.Category;
import com.mmallshop.pojo.Product;
import com.mmallshop.service.ICategoryService;
import com.mmallshop.service.IProductService;
import com.mmallshop.util.DateTimeUtil;
import com.mmallshop.util.PropertiesUtil;
import com.mmallshop.vo.ProductDetailVo;
import com.mmallshop.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/7/26
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

   @Autowired
   ProductMapper productMapper;


    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse saveOrUpdateProduct(Product product){
        if(product != null)
        {
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length > 0){
                    product.setMainImage(subImageArray[0]);
                }
            }

            if(product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKey(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                return ServerResponse.createBySuccess("更新产品失败");
            }else{
                int rowCount = productMapper.insert(product);
                if(rowCount > 0){
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                return ServerResponse.createBySuccess("新增产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }


    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMessage("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
        if(productId == null || status == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }


    private  ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
//    图片服务器位置
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }


//    商品列表获取 （Mybatis的分页功能）

    public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
//        startPage --start
//       填充自己的sql查询逻辑
//       pagehelper 收尾
       PageHelper.startPage(pageNum,pageSize);
       List<Product> productList = productMapper.selectList();
       List<ProductListVo> productListVoList = Lists.newArrayList();
       for(Product productItem:productList){
           ProductListVo productListVo = assembleProductListVo(productItem);
           productListVoList.add(productListVo);
       }
       PageInfo pageInfo = new PageInfo(productList);
       pageInfo.setList(productListVoList);
       return ServerResponse.createBySuccess(pageInfo);

   }



// 从数据库读取的List 进行包装 成Vo
   private ProductListVo assembleProductListVo(Product product){
       ProductListVo productListVo = new ProductListVo();
       productListVo.setId(product.getId());
       productListVo.setName(product.getName());
       productListVo.setCategoryId(product.getCategoryId());
       productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
       productListVo.setMainImage(product.getMainImage());
       productListVo.setPrice(product.getPrice());
       productListVo.setSubtitle(product.getSubtitle());
       productListVo.setStatus(product.getStatus());
       return productListVo;
   }
}
