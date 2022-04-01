package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@SpringBootTest
class I18nconfigApplicationTests {

    @Test
    void contextLoads() {
        // JDK的java.util包中提供了几个支持本地化的格式化操作工具类，比如NumberFormat、DateFormat、MessageFormat。
        // 中文本地化信息
        Locale locale = Locale.CHINA;
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

        double amt = 123456.78;
        System.out.println(numberFormat.format(amt));
        // 英文本地化信息
        Locale locale2 = Locale.US;
        NumberFormat nFormat = NumberFormat.getCurrencyInstance(locale2);

        double amt2 = 123456.78;
        System.out.println(nFormat.format(amt2));

        // 英文本地化信息
        Locale locale3 = new Locale("en", "US");
        Date date = new Date();
        // 按照本地化的方式对日期进行格式化操作
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM,
                locale3);
        System.out.println(dateFormat.format(date));

        // 中文本地化信息
        Locale locale4 = new Locale("zh", "CN");
        Date date2 = new Date();
        // 按照本地化的方式对日期进行格式化操作
        DateFormat dateFormat2 = DateFormat.getDateInstance(DateFormat.MEDIUM,
                locale4);
        System.out.println(dateFormat2.format(date2));


        // MessageFormat在NumberFormat和DateFormat的基础上提供了强大的占位符字符串的格式化功能，支持时间、货币、数字以及对象属性的格式化操作。
        // (1)格式化信息串
        String pattern1 = "{0},你好！你与{1}支付了货款{2}元";
        String pattern2 = "At {1,time,short} on {1,date,long},{0} paid {2,number,currency}";
        // (2)用于动态替换占位符的参数
        Object[] params = { "XiaoGongJiang", new GregorianCalendar().getTime(),200 };
        // (3)使用默认的本地化对象格式化信息
        String msg1 = MessageFormat.format(pattern1, params);
        // (4)使用指定的本地化对象格式化信息
        MessageFormat messageFormat = new MessageFormat(pattern2, Locale.US);
        String msg2 = messageFormat.format(params);

        System.out.println(msg1);
        System.out.println(msg2);



    }

}
