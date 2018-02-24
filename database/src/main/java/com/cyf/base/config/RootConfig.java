package com.cyf.base.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * TODO
 *
 * @author chenyafei
 * @version 1.0
 * @date 05/02/2018 22:59
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.cyf.base")
public class RootConfig {
    @Bean
    public ObjectMapper objectMapper() {
        String datetimeFormat = "yyyy-MM-dd HH:mm:ss";
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(datetimeFormat)));
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(datetimeFormat)));
        return Jackson2ObjectMapperBuilder
                .json()
                .modules(javaTimeModule)
                .featuresToDisable(WRITE_DATES_AS_TIMESTAMPS)
                .dateFormat(new SimpleDateFormat(datetimeFormat)) // Date对象
                .build();
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setCache(false);
        resolver.setExposeRequestAttributes(true);
        resolver.setRequestContextAttribute("request");
        resolver.setOrder(0);
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("utf-8");
        configurer.setTemplateLoaderPath("/WEB-INF/views/");
        Properties properties = new Properties();
        properties.setProperty("defaultEncoding", "UTF-8");
        configurer.setFreemarkerSettings(properties);
        return configurer;
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource myDataSource(DruidConfig druidConfig, ConfigManager cm) {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        MysqlDb mysqlConfig = cm.getMysqlConfig("trade_public_mysql", "cd_tiaotiao_w", "tiaotiao");
        configDataSource(druidConfig, ds, mysqlConfig);
        return ds;
    }

    static void configDataSource(DruidConfig druidConfig, DruidDataSource ds, MysqlDb mysqlConfig) {
        ds.setUrl(mysqlConfig.getUrl() + "&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8");
        ds.setUsername(mysqlConfig.getUser());
        ds.setPassword(mysqlConfig.getPwd());
        ds.setInitialSize(druidConfig.getInitialSize());
        ds.setMinIdle(druidConfig.getMinIdle());
        ds.setMaxActive(druidConfig.getMaxActive());
        ds.setMaxWait(druidConfig.getMaxWait());
        ds.setTimeBetweenEvictionRunsMillis(druidConfig.getTimeBetweenEvictionRunsMillis());
        ds.setMinEvictableIdleTimeMillis(druidConfig.getMinEvictableIdleTimeMillis());
        ds.setValidationQuery(druidConfig.getValidationQuery());
        ds.setTestWhileIdle(druidConfig.getTestWhileIdle());
        ds.setTestOnBorrow(druidConfig.getTestOnBorrow());
        ds.setTestOnReturn(druidConfig.getTestOnReturn());
    }
}
