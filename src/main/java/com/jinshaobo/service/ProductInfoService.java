package com.jinshaobo.service;

import com.github.pagehelper.PageInfo;
import com.jinshaobo.pojo.ProductInfo;
import com.jinshaobo.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {




    //查询所有商品
    List<ProductInfo> getAll();

    //实现分页
    PageInfo splitPage(int pageNum,int pageSize);

    //保存商品
    int save(ProductInfo info);

    //点编辑，通过商品id查询商品所有信息
    ProductInfo getById(int pid);

    //点提交，通过商品id修改商品所有信息
    int update(ProductInfo info);

    //点删除，通过商品id删除该商品
    int delete(int pid);

    //批量删除商品
    int deleteBatch(String[] ids);

    //多条件查询商品
    List<ProductInfo> selecCondition(ProductInfoVo vo);

    //多条件查询分页
    PageInfo splitPageVo(ProductInfoVo vo,int pageSize);

}
