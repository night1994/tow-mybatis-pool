
/*
 * 文件名：MyBatisDao.java
 * 版权：Copyright by night
 * 描述：
 * 修改人：night
 * 修改时间：2019年1月7日
 */

package com.unionbigdata.mybatis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSessionFactory;

public interface MyBatisDao {

	
	
    int delete(String statementName);
    int delete(String statementName, Object parameterObject);
    Object insert(String statementName);
    Object insert(String statementName, Object parameterObject);
    List queryForList(String statementName);
    
    
    List queryForList(String statementName, Object parameterObject);
    Map queryForMap(String statementName, Object parameterObject, String keyProperty);
    Object queryForObject(String statementName);
    Object queryForObject(String statementName, Object parameterObject);
    void queryWithRowHandler(String statementName, ResultHandler rowHandler);
    void queryWithRowHandler(String statementName, Object parameterObject, ResultHandler rowHandler);
    int update(String statementName);
    int update(String statementName, Object parameterObject);
}

