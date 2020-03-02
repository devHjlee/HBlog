package com.hblog.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

public interface MybatisTestMapper {
    public List<Map<String,String>> selectTestList() throws Exception;
}
