package com.mmallshop.service;

import com.mmallshop.common.ServerResponse;
import com.mmallshop.pojo.Category;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/7/25
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public interface ICategoryService {

    public ServerResponse<String> addCategory(String categoryName, Integer parentId);

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId);
    public ServerResponse<String> updateCategory(String categoryName,Integer categoryId);

    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
