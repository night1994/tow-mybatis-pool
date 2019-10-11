
/*
 * 文件名：MybatisDaoImpl.java
 * 版权：Copyright by night
 * 描述：
 * 修改人：night
 * 修改时间：2019年1月7日
 */

package com.unionbigdata.mybatis.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.unionbigdata.mybatis.config.MyBatisConfig;
import com.unionbigdata.mybatis.config.MybatisScannerConfig;
import com.unionbigdata.mybatis.service.MyBatisDao;

@Component(value="dao")
@AutoConfigureAfter({MybatisScannerConfig.class,MyBatisConfig.class})
public class MybatisDaoImpl extends SqlSessionDaoSupport implements MyBatisDao{

	@Autowired
    @Override
    public void setSqlSessionFactory(@Qualifier("sqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
    
    public int delete(String statementName){
        return getSqlSession().delete(statementName);
    }

    public int delete(String statementName, Object parameterObject){
        return getSqlSession().delete(statementName, parameterObject);
    }



    public Object insert(String statementName){
        return getSqlSession().insert(statementName);
    }

    public Object insert(String statementName, Object parameterObject){
        return getSqlSession().insert(statementName, parameterObject);
    }

    
    
    public List queryForList(String statementName){
        return getSqlSession().selectList(statementName);
    }

    public List queryForList(String statementName, Object parameterObject)
            {
        return getSqlSession().selectList(statementName, parameterObject);
    }

    public Map queryForMap(String statementName, Object parameterObject, String keyProperty)
             {
        return getSqlSession().selectMap(statementName, parameterObject, keyProperty);
    }



    public Object queryForObject(String statementName){
        return  getSqlSession().selectOne(statementName);
    }

    public Object queryForObject(String statementName, Object parameterObject)
             {
        return  getSqlSession().selectOne(statementName, parameterObject);
    }


    public void queryWithRowHandler(String statementName, ResultHandler rowHandler)
            {
         getSqlSession().select(statementName, rowHandler);
    }

    public void queryWithRowHandler(String statementName, Object parameterObject, ResultHandler rowHandler)
            {
         getSqlSession().select(statementName, parameterObject, rowHandler);
    }

    public int update(String statementName) {
        return getSqlSession().update(statementName);
    }

    public int update(String statementName, Object parameterObject){
        return getSqlSession().update(statementName, parameterObject);
    }

}

