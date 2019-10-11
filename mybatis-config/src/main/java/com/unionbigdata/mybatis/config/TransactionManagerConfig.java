
/*
 * 文件名：TransactionManagerConfig.java
 * 版权：Copyright by night
 * 描述：
 * 修改人：night
 * 修改时间：2019年1月7日
 */

package com.unionbigdata.mybatis.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 事务管理 引入xml文件配置事务
 * 
 * @author mac
 *
 */
@Configuration
@AutoConfigureAfter({ MyBatisConfig.class})
@ImportResource("classpath:spring-tx.xml")
public class TransactionManagerConfig {

}
