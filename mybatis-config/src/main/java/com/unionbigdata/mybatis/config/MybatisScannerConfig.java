
/*
 * 文件名：MybatisScannerConfig.java
 * 版权：Copyright by night
 * 描述：
 * 修改人：night
 * 修改时间：2019年1月7日
 */

package com.unionbigdata.mybatis.config;

import java.util.Map;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration       
@AutoConfigureAfter({ MyBatisConfig.class })
@PropertySource(value = "classpath:mybatis.properties")
public class MybatisScannerConfig implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer scanner = new MapperScannerConfigurer();
		Map<String, Object> map = propertyResolver.getSubProperties("mybatis.");
		scanner.setBasePackage((String) map.get("basePackage"));
		scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return scanner;
	}

	@Override
	public void setEnvironment(Environment evn) {
		this.propertyResolver = new RelaxedPropertyResolver(evn);

	}
}
