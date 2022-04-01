package com.example.demo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 类名： I18nController
 * 描述 TODO：
 * 创建时间： 2022/4/1 9:00
 * 创建人： Administrator
 */
@Controller
public class I18nController {

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private MessageSource messageSource;


    @GetMapping(value = "/locale")
    public String localeHandler(HttpServletRequest request) {

        String lastUrl = request.getHeader("referer");
        return "redirect:" + lastUrl;
    }


    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String index(Model model, Locale locale) {

        model.addAttribute("title", messageSource.getMessage("greeting.common", null, locale));
        return "index";
    }


    @GetMapping("/test")
    public String test(HttpServletRequest req, HttpServletResponse resp)
    {
        // Spring采用的默认区域解析器是AcceptHeaderLocaleResolver 它通过检验HTTP请求的accept-language头部来解析区域
//        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(req);
//        Locale aDefault = Locale.getDefault();
//        System.out.println("默认的本地语言："+aDefault.getLanguage());
//
//        Locale newLocale = new Locale("fr");
//        localeResolver.setLocale(req,resp,newLocale);
//
//        System.out.println("本地语言："+aDefault.getLanguage());


        System.out.println(ctx.getMessage("greeting.common",null,Locale.CHINESE));
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println("目前本地语言："+locale.getLanguage());
        System.out.println("目前国家："+locale.getCountry());
        return "index";
    }



    //-- 【1】使用session中设置SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME属性方式
    private void setUserLocale(HttpServletRequest request,String localeStr){
        Locale locale = getLocaleFromStr(localeStr);
        if(locale == null)
            return;

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
    }

    //--【方式二】使用LocaleResolver的setLocale方法进行设置。

    @Inject
    private LocaleResolver localeresolver;

    private void setUserLocale(HttpServletRequest request,HttpServletResponse response,String localeStr){
        Locale locale = getLocaleFromStr(localeStr);
        if(locale == null)
            return;
        this.localeresolver.setLocale(request, response, locale);
    }

    // 将字符串转化为locale对象
    private Locale getLocaleFromStr(String str){
        if(str == null)
            return null;
        String[] p = StringUtils.split(str, "_");
        Locale locale ;
        switch(p.length){
            case 2:
                return new Locale(p[0], p[1]);
            case 3:
                return new Locale(p[0], p[1], p[2]);
            default:
                return new Locale(p[0]);
        }
    }

    public static void main(String[] args) {
        // Java为我们提供了用于加载本地化资源文件的方便类java.util.ResourceBoundle。
        ResourceBundle resBundle = ResourceBundle.getBundle("i18n/mess",Locale.getDefault());
        System.out.println(resBundle.getString("greeting.common"));
        System.out.println(resBundle.getString("greeting.morning"));
        System.out.println(resBundle.getString("greeting.afternoon"));

        ResourceBundle resBundle2 = ResourceBundle.getBundle("i18n/mess",Locale.US);
        System.out.println(resBundle2.getString("greeting.common"));
        System.out.println(resBundle2.getString("greeting.morning"));
        System.out.println(resBundle2.getString("greeting.afternoon"));


        // spring定义了访问国际化信息的MessageSource接口，并提供了几个易用的实现类.
        // ResourceBundleMessageSource该实现类允许用户通过beanName指定一个资源名（包括类路径的全限定资源名），或通过beanNames指定一组资源名.
        // ReloadableResourceBundleMessageSource 可以通过设置cacheSeconds参数进行实时更新文件数据

        // 可以通过bean将ResourceBundleMessageSource交给spring管理
        ResourceBundleMessageSource rms = new ResourceBundleMessageSource();
        rms.setBasename("i18n/mess");

        String msg1 = rms.getMessage("greeting.common", null, Locale.US);
        String msg2 = rms.getMessage("greeting.morning", null,Locale.US);
        String msg3 = rms.getMessage("greeting.afternoon", null,Locale.CHINESE);

        System.out.println(msg1);
        System.out.println(msg2);
        System.out.println(msg3);

    //    ApplicationContext ctx = WebApplicationContextUtils.

    }
}

