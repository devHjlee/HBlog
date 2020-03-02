package com.hblog;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.hblog.mybatis.mapper.MybatisTestMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
class HlbogApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SqlSessionFactory sessionFactory;

    @Autowired
    private MybatisTestMapper mybatisTestMapper;

	@Test
	void contextLoads() {
	}

	@Test
	public void testByApplicationContext() {
	    System.out.println(context.getBean("sqlSessionFactory"));
	}

	@Test
	public void testBySqlSessionFactory() {
	    System.out.println(sessionFactory.toString());
	}

	@Test
	public void testByMybatis() throws Exception {
        List<Map<String, String>> result = mybatisTestMapper.selectTestList();
        System.out.println("=====================mybatisTestMapper==================");
        for(int i=0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
	}

}
