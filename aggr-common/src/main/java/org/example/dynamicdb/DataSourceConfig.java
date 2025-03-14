package org.example.dynamicdb;

import org.example.constant.DataSourceConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return createDataSource("master");
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return createDataSource("slave");
    }

    /**
     * 数据源创建工具方法
     * 根据指定的配置前缀创建数据源
     * 如果配置缺失或不正确，会抛出异常并记录日志
     *
     * @param prefix 配置前缀
     * @return 数据源实例
     */
    private DataSource createDataSource(String prefix) {
        try {
            return DataSourceBuilder.create().build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create DataSource with prefix: " + prefix, e);
        }
    }

    /**
     * 动态数据源配置
     * 设置默认数据源为主数据源，并注入所有数据源到动态数据源集合中
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        // 配置数据源集合，其中key代表数据源名称，DataSourceContextHolder中缓存的就是这个key
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceConstants.DEFAULT_MASTER_KEY, masterDataSource);
        targetDataSources.put(DataSourceConstants.DEFAULT_SLAVE_KEY, slaveDataSource);
        // 注入动态数据源到bean工厂
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        // 设置动态数据源集
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    /**
     * 配置事务管理器
     * 使用动态数据源作为事务管理的数据源
     */
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
