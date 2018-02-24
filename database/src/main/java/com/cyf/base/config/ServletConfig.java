package com.cyf.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * ServletConfig类
 *
 * @author chenyafei
 * @version 1.0
 * @date 05/02/2018 23:00
 */


@Configuration
public class ServletConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        Charset urf8 = Charset.forName("UTF-8");
        converter.setSupportedMediaTypes(Collections.singletonList(new MediaType("text", "plain", urf8)));
        converter.setDefaultCharset(urf8);
        converters.add(converter);
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setDefaultCharset(urf8);
        jsonConverter.setObjectMapper(objectMapper);
        converters.add(jsonConverter);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.enableContentNegotiation(new MappingJackson2JsonView());
        registry.freeMarker().cache(false);
    }
}
