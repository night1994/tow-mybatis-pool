
/*
 * 文件名：DatabaseContextHolder.java
 * 版权：Copyright by unionbigdata
 * 描述：
 * 修改人：night
 * 修改时间：2019年3月6日
 */

package com.unionbigdata.mybatis.config;

public class DatabaseContextHolder {
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<DatabaseType>();
    
    public static void setDatabaseType(DatabaseType type){
        contextHolder.set(type);
    }
    
    public static DatabaseType getDatabaseType(){
        return contextHolder.get();
    }
}

