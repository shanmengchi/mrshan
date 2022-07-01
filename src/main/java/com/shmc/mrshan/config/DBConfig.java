package com.shmc.mrshan.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Aswords
 * @Date 2020/09/26
 * @since v0.1
 */
@Configuration
public class DBConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
//    @Value("${spring.datasource.initial-size}")
//    private int initialSize;
//    @Value("${spring.datasource.min-idle}")
//    private int minIdle;
//    @Value("${spring.datasource.max-active}")
//    private int maxActive;
//    @Value("${spring.datasource.max-wait}")
//    private int maxWait;
//    @Value("${spring.datasource.filters}")
//    private String filters;
//    @Value("{spring.datasource.connectionProperties}")
//    private String connectionProperties;


    @Bean(name = "checkDataSource")
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dbUrl);
        datasource.setDriverClassName(driverClassName);
        datasource.setUsername(username);
        datasource.setPassword(password);
        //configuration
//        datasource.setInitialSize(initialSize);
//        datasource.setMinIdle(minIdle);
//        datasource.setMaxActive(maxActive);
//        datasource.setMaxWait(maxWait);
//        try {
//            /*
//             * 设置StatFilter，用于统计监控信息。
//             * StatFilter的别名是stat
//             */
//            datasource.setFilters(filters);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

//    @Bean(name = "checkSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource());
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:config/mybatis/mapper/**/*.xml"));
//        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:config/mybatis/mybatis-config.xml"));
//        return bean.getObject();
//    }

    @Bean(name = "checkTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

//    @Bean(name = "checkSqlSessionTemplate")
//    public SqlSessionTemplate testSqlSessionTemplate() throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory());
//    }
}
