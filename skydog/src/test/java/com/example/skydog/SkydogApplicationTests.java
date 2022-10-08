package com.example.skydog;

import com.example.skydog.dao.UserDao;
import com.example.skydog.module.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.spel.ast.LongLiteral;
import scala.math.Ordering;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class SkydogApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    UserDao userDao;

    @Test

    void test(){
        System.out.println(System.currentTimeMillis());
        Long str = System.currentTimeMillis();
        String str1 = "1663815280162";
        Long s = Long.parseLong(str1);
        Date date1 = new Date(s);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-MM-dd HH:mm:ss
        System.out.println(formatter.format(date1));
    }
}
