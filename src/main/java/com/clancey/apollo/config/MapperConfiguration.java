package com.clancey.apollo.config;


import com.clancey.apollo.common.actable.utils.ClassTools;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;
import java.util.Set;


@Configuration
@AutoConfigureAfter({MybatisConfiguration.class})
public class MapperConfiguration implements EnvironmentAware{
    private String basePackage;
    private String mappers;
    private String identity;
    private String order;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(Environment environment){
        //处理通配符，转化成mybatis可识别的格式 com.**.mapper --> com.core.mapper;com.system.mapper;com.user.mapper
        StringBuilder sb = new StringBuilder();
        String[] packages = basePackage.split(";");
        for (String aPackage : packages) {
            if (aPackage.contains("**")) {
                Set<String> packageNames = ClassTools.getClassNames(aPackage, false);
                packageNames.forEach(p -> sb.append(p).append(';'));
                continue;
            }
            sb.append(aPackage).append(';');
        }

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(sb.toString());

        Properties properties=new Properties();
        properties.setProperty("mappers",mappers);
        properties.setProperty("IDENTITY",identity);
        properties.setProperty("ORDER",order);
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;

    }

    @Override
    public void setEnvironment(Environment environment) {
        this.basePackage = environment.getProperty("mybatis.basepackage");
        this.mappers = environment.getProperty("mybatis.mappers");
        this.identity = environment.getProperty("mybatis.identity");
        this.order = environment.getProperty("mybatis.order");
    }
}
