package com.company.service;

import com.company.pojo.Admin;
import com.company.pojo.ProductInfo;
import com.company.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {

    //显示全部商品不分页
    List<ProductInfo> getAll();

    //分页显示商品
    PageInfo splitPage(int pageNum,int pageSize);

    //增加商品
    int save(ProductInfo info);

    //按主键id查询商品
    ProductInfo getById(int pid);

    //更新商品
    int update(ProductInfo info);

    //删除商品
    int delete(int pId);

    //批量删除商品
    int deleteBatch(String []ids);

    //多条件查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    PageInfo splitPageVo(ProductInfoVo vo,int pageSize);
}
