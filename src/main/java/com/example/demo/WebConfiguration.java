package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * 类名： WebConfiguration
 * 描述 TODO：
 * 创建时间： 2022/4/1 15:31
 * 创建人： Administrator
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    // 如果要实现在页面上切换本地化环境，就要加这个bean
   // @Bean
//    public LocaleResolver localeResolver() {
//
//        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//        localeResolver.setDefaultLocale(new Locale("es", "ES"));
//        return localeResolver;
//    }

//    @Bean
//    public CookieLocaleResolver LocaleResolver()
//    {
//        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
//        localeResolver.setDefaultLocale(new Locale("zh","CN"));
//        localeResolver.setCookieName("language");
//        localeResolver.setCookieMaxAge(3600);
//        return localeResolver;
//    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}