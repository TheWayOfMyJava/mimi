package com.company.service.impl;

import com.company.mapper.ProductTypeMapper;
import com.company.pojo.ProductInfoExample;
import com.company.pojo.ProductType;
import com.company.pojo.ProductTypeExample;
import com.company.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {
    //业务逻辑层一定有数据访问层数据
    @Autowired
    ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> getAll() {

        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
