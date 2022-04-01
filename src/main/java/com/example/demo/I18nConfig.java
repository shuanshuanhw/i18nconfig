package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 类名： I18nConfig
 * 描述 TODO：
 * 创建时间： 2022/4/1 9:00
 * 创建人： Administrator
 */
@Configuration
public class I18nConfig {

//    @Bean
//    public ResourceBundleMessageSource messageSource()
//    {
//        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
//        rs.setBasename("i18n");
//        return rs;
//    }
    @Bean
    public ResourceBundleMessageSource messageSource()
    {
        ResourceBundleMessageSource rms = new ResourceBundleMessageSource();
        rms.setBasename("i18n/mess");
        return rms;
    }
}
