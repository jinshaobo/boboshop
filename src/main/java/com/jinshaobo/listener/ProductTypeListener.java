package com.jinshaobo.listener;

import com.jinshaobo.pojo.ProductType;
import com.jinshaobo.service.ProductTypeService;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //在服务器启动时，在数据库查询到所有的商品类型
        //先从spring容器获取service对象，调用mapper查询数据库
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService)ac.getBean("productTypeServiceImpl");
        List<ProductType> typeList = productTypeService.getAll();
        //放到应用域中
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
