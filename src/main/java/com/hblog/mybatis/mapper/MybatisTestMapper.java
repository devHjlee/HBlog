package com.hblog.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MybatisTestMapper {

    public List<Map<String,Object>> selectUserList() throws Exception;

    public List<Map<String,Object>> selectUserListPreparedStatement(String psName) throws Exception;

    public List<Map<String,Object>> selectUserListStatement(String sName) throws Exception;

    public void insertTest(Map<String,String> params) throws Exception;
}
