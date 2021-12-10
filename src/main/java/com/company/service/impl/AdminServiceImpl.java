package com.company.service.impl;

import com.company.mapper.AdminMapper;
import com.company.pojo.Admin;
import com.company.pojo.AdminExample;
import com.company.service.AdminService;
import com.company.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//将service类放入到spring容器中
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户到db中查询相应的用户对象
        //如果有条件一定创建AdminExample的对象，用来封装条件
        AdminExample example = new AdminExample();
        //添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);
        List<Admin> admins = adminMapper.selectByExample(example);
        if (admins.size() > 0){
            Admin admin = admins.get(0);
            String miPwd = MD5Util.getMD5(pwd);
            if (miPwd.equals(admin.getaPass())){
                return admins.get(0);
            }
        }

        return null;
    }
}
