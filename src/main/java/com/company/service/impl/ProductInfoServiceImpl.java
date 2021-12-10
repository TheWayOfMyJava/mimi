package com.company.service.impl;

import com.company.mapper.ProductInfoMapper;
import com.company.pojo.Admin;
import com.company.pojo.ProductInfo;
import com.company.pojo.ProductInfoExample;
import com.company.pojo.vo.ProductInfoVo;
import com.company.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    //切记：业务逻辑层中一定有数据访问层数据
    @Autowired
    ProductInfoMapper productInfoMapper;
    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }


    //select * from product_info limit 起始记录数=((当前页-1)*每页的条数)，每页取几条
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用PageHelper工具类玩成分页设置
        PageHelper.startPage(pageNum,pageSize);
        //进行PageInfo的数据封装
        //进行有条件的查询操作，必须创建ProductExample对象
        ProductInfoExample example = new ProductInfoExample();

        //设置排序，按主键降序降序排序
        example.setOrderByClause("p_id desc");

        //设置排序后，取集合，切记：一定在取集合前，设置PageHelper.startPage(pageNum,pageSize)
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //将查询后的结果封装在PageInfo对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pid) {

        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pId) {
        return productInfoMapper.deleteByPrimaryKey(pId);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        //取出集合之前，一定要对PageHelper.start()属性进行配置
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list= productInfoMapper.selectCondition(vo);

        return new PageInfo<>(list);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }
}
