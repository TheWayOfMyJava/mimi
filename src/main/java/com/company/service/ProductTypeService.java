package com.company.service;

import com.company.pojo.ProductType;

import java.util.List;


public interface ProductTypeService {
    //获取全部商品类别
    List<ProductType> getAll();
}
