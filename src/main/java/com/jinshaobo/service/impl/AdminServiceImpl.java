package com.jinshaobo.service.impl;

import com.jinshaobo.mapper.AdminMapper;
import com.jinshaobo.pojo.Admin;
import com.jinshaobo.pojo.AdminExample;
import com.jinshaobo.service.AdminService;
import com.jinshaobo.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

    //传入dao对象访问数据库
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        //根据用户提供的name与数据库中的name对比
        //select a_id,a_name,a_pass from admin where a_name = 'admin';
        //如果有条件一定创建example对象,用来封装条件
        AdminExample example = new AdminExample();
        example.createCriteria().andANameEqualTo(name);
        //执行查询语句
        List<Admin> list = adminMapper.selectByExample(example);

        //如果查询到对象
        if (list.size() > 0) {
            //取出对象
            Admin admin = list.get(0);
            //进行密码对比
            String pwdMD5 = MD5Util.getMD5(pwd);
            if (admin.getaPass().equals(pwdMD5)) {
                return admin;
            }
        }
        //其他情况返回空
        return null;
    }
}
