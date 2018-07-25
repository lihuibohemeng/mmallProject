package com.mmallshop.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmallshop.common.ServerResponse;
import com.mmallshop.dao.CategoryMapper;
import com.mmallshop.dao.UserMapper;
import com.mmallshop.pojo.Category;
import com.mmallshop.service.ICategoryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/7/25
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService{

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    UserMapper userMapper;

    @Autowired
    CategoryMapper categoryMapper;


     public ServerResponse<String> addCategory(String categoryName,Integer parentId){
         if(parentId == null || StringUtils.isBlank(categoryName)){
             return ServerResponse.createByErrorMessage("参数错误");
         }
         Category category = new Category();
         category.setName(categoryName);
         category.setParentId(parentId);
         category.setStatus(true);
         int resultCount = categoryMapper.insert(category);
         if(resultCount>0){
             return ServerResponse.createBySuccessMessage("添加成功");
         }else{
             return ServerResponse.createByErrorMessage("添加失败");
         }
     }

     public ServerResponse<String> updateCategory(String categoryName,Integer categoryId){
         if(categoryId == null || StringUtils.isBlank(categoryName)){
             return ServerResponse.createByErrorMessage("参数错误");
         }

         Category category = new Category();
         category.setId(categoryId);
         category.setName(categoryName);
         int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
         if(resultCount>0){
             return ServerResponse.createBySuccessMessage("更新品类名称成功");
         }else{
             return ServerResponse.createByErrorMessage("更新品类名称失败");
         }
     }
//     获取平级的子节点   就是给你parentId 找节点parentId值和给的id数值一致的 节点
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId){
       List<Category> categoryList = categoryMapper.getChildrenParallelCategory(parentId);
        if (CollectionUtils.isEmpty(categoryList)) {

            logger.info("未找到当前分类的子类");
        }
       return ServerResponse.createBySuccess(categoryList);
    }


    /**
     * 递归查询本节点的id及孩子节点的id
     * @param categoryId
     * @return
     */
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);


        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }


    //递归算法,算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet ,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        //查找子节点,递归算法一定要有一个退出的条件
        List<Category> categoryList = categoryMapper.getChildrenParallelCategory(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }
}
