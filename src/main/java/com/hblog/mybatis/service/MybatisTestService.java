package com.hblog.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hblog.mybatis.mapper.MybatisTestMapper;

@Component
public class MybatisTestService {

    @Autowired
    private MybatisTestMapper mybatisTestMapper;

    public List<Map<String,Object>> selectUserList() throws Exception {
        return mybatisTestMapper.selectUserList();
    }

    public List<Map<String,Object>> selectUserListPreparedStatement(String psName) throws Exception{
        return mybatisTestMapper.selectUserListPreparedStatement(psName);
    }

    public List<Map<String,Object>> selectUserListStatement(String sName) throws Exception{
        return mybatisTestMapper.selectUserListStatement(sName);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertTest() throws Exception{

        Map<String,String> params = new HashMap<String,String>();
        Map<String,String> params2 = new HashMap<String,String>();

        params.put("email", "LEE@gmail.com");
        params.put("name", "LEE");
        params.put("startDt", "aaaaaaa");

        mybatisTestMapper.insertTest(params);

        params2.put("email", "LEE123@gmail.com");
        params2.put("name", "LEE123");
        params2.put("startDt", "aaaaaa");

        mybatisTestMapper.insertTest(params2);

        throw new RuntimeException();
    }

}
