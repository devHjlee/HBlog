package com.hblog;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hblog.mybatis.service.MybatisTestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisSqlInjectionTest {
    @Autowired
    private MybatisTestService mybatisTestService;

    @Test
    public void testBySqlInjection() {

        List<Map<String,Object>> psResult = null;
        List<Map<String,Object>> sResult = null;

        String psName = "이형재";
        String sName = "'이형재' OR 'a'='a'";

        try {
            psResult = mybatisTestService.selectUserListPreparedStatement(psName);
            sResult = mybatisTestService.selectUserListStatement(sName);

            System.out.println("=============PreparedStatement Start=================");
            for(int i = 0; i < psResult.size(); i++) {
                System.out.println(psResult.get(i));
            }

            System.out.println("=============Statement Start=================");
            for(int i = 0; i < sResult.size(); i++) {
                System.out.println(sResult.get(i));
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
