package com.liuye.common.mybatisplus.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.liuye.common.mybatisplus.noahsql.NoahSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfiguration {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 逻辑删除
     */
    @Bean
    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
        // 增加SQLServer下的批量插入
        return new NoahSqlInjector();
    }

}
