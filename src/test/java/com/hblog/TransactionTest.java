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
public class TransactionTest {

    @Autowired
    private MybatisTestService mybatisTestService;

    @Test
    public void testByTransaction() {
        List<Map<String,Object>> result = null;

        try {
            System.out.println("=====================ALL USER LIST==================");
            result = mybatisTestService.selectUserList();

            for(int i=0; i < result.size(); i++) {
                System.out.println(result.get(i));
            }

            System.out.println("=====================Insert USER LIST==================");
            mybatisTestService.insertTest();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
