
/*
 * 文件名：MyBatisConfig.java
 * 版权：Copyright by unionbigdata
 * 描述：
 * 修改人：night
 * 修改时间：2019年3月6日
 */

package com.unionbigdata.mybatis.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration // 该注解类似于spring配置文件
@PropertySource(value ={ "classpath:jdbc.properties","mybatis.properties"})

public class MyBatisConfig implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

    /**
     * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
     */

    @Bean(name="ppDataSource")
    public DataSource ppDataSource() throws Exception {
    	Map<String, Object> map = propertyResolver.getSubProperties("dbcp.");
    	
		DruidDataSource ds = new DruidDataSource();
		
		
		ds.setDriverClassName((String) map.get("driverClassName"));
		ds.setUrl((String) map.get("url"));
		ds.setUsername((String) map.get("username"));
		ds.setPassword((String) map.get("password"));
		ds.setInitialSize(Integer.valueOf((String) map.get("initialSize")));
		ds.setMaxActive(Integer.valueOf((String) map.get("maxActive")));
		ds.setMinIdle(Integer.valueOf((String) map.get("minIdle")));
		ds.setTimeBetweenEvictionRunsMillis(30000);
		ds.setMinEvictableIdleTimeMillis(30000);
		ds.setDefaultAutoCommit(true);
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(180);
		ds.setMaxWait(10000);
		ds.setTestWhileIdle(true);
		ds.setValidationQuery((String) map.get("validationQuery"));
		ds.setFilters("stat");
		return ds;
    }
    

    
    
  @Bean(name="oaDataSource")
  public DataSource oaDataSource() throws Exception {
  	Map<String, Object> map = propertyResolver.getSubProperties("dbcp1.");
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName((String) map.get("driverClassName"));
		ds.setUrl((String) map.get("url"));
		ds.setUsername((String) map.get("username"));
		ds.setPassword((String) map.get("password"));
		ds.setInitialSize(Integer.valueOf((String) map.get("initialSize")));
		ds.setMaxActive(Integer.valueOf((String) map.get("maxActive")));
		ds.setMinIdle(Integer.valueOf((String) map.get("minIdle")));
		ds.setTimeBetweenEvictionRunsMillis(30000);
		ds.setMinEvictableIdleTimeMillis(30000);
		ds.setDefaultAutoCommit(true);
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(180);
		ds.setMaxWait(10000);
		ds.setTestWhileIdle(true);
		ds.setValidationQuery((String) map.get("validationQuery"));
		ds.setFilters("stat");
		return ds;
  }

    
    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("oaDataSource") DataSource oaDataSource,
            @Qualifier("ppDataSource") DataSource ppDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DatabaseType.OA_DB, oaDataSource);
        targetDataSources.put(DatabaseType.PP_DB, ppDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(oaDataSource);// 默认的datasource设置为oaDataSource

        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
		Map<String, Object> map = propertyResolver.getSubProperties("mybatis.");
		// 添加XML目录
		List<Resource> list = new ArrayList<Resource>();
		String mapperLocations = (String) map.get("mapperLocations");
		for (String m : mapperLocations.split(",")) {

			Resource[] resources = new PathMatchingResourcePatternResolver().getResources(m);
			list.addAll(Arrays.asList(resources));
		}
		Resource[] resources = new Resource[] {};
		bean.setMapperLocations(list.toArray(resources));
		bean.setConfigLocation(new DefaultResourceLoader().getResource((String) map.get("configLocation")));

		return bean.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env);

	}
}
