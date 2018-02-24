package com.cyf.base;

import com.cyf.base.config.RootConfig;
import com.cyf.base.config.ServletConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * AppConfig类
 *
 * @author chenyafei
 * @version 1.0
 * @date 05/02/2018 22:57
 */

public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    //Spring 上下文配置
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    //Servlet上下文配置
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{ServletConfig.class};
    }

    //mapping配置
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
