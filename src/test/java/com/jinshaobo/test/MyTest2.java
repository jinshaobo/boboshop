package com.jinshaobo.test;

import com.jinshaobo.mapper.ProductInfoMapper;
import com.jinshaobo.pojo.ProductInfo;
import com.jinshaobo.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//测试ProductInfoMapper.xml文件中的sql代码
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest2 {
    @Autowired
    ProductInfoMapper mapper;

    @Test
    public void testSelectCondition(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setPname("手机");
        vo.setTypeid(1);
        vo.setHprice(5000);
        vo.setLprice(1000);
        List<ProductInfo> infoList = mapper.selectCondition(vo);
        infoList.forEach(productInfo -> System.out.println(productInfo));
    }

}
