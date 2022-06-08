package com.jinshaobo.service.impl;

import com.jinshaobo.mapper.ProductTypeMapper;
import com.jinshaobo.pojo.ProductType;
import com.jinshaobo.pojo.ProductTypeExample;
import com.jinshaobo.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeMapper productTypeMapper;

    //查询所有商品类型
    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
