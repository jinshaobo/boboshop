package com.jinshaobo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinshaobo.mapper.ProductInfoMapper;
import com.jinshaobo.pojo.ProductInfo;
import com.jinshaobo.pojo.ProductInfoExample;
import com.jinshaobo.pojo.vo.ProductInfoVo;
import com.jinshaobo.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    ProductInfoMapper productInfoMapper;




    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {

        //分页插件使用PageHelper工具类完成分页设置
        PageHelper.startPage(pageNum,pageSize);//list 5,5

        //增加降序条件，必须进行创建ProductInfoExample，在这之前必须进行分页设置
        ProductInfoExample example = new ProductInfoExample();//select * from product_info
        example.setOrderByClause("p_id desc");//select * from product_info order by p_id desc

        //执行查询语句
        List<ProductInfo> list = productInfoMapper.selectByExample(example);

        //将查询到的list集合封装到PageInfo对象
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
    public int delete(int pid) {

        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selecCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);

    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        //取出集合之前，先要设置PageHelper.startPage()属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);

    }
}
