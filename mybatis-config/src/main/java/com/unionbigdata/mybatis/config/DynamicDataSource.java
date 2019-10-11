
/*
 * 文件名：DynamicDataSource.java
 * 版权：Copyright by unionbigdata
 * 描述：
 * 修改人：night
 * 修改时间：2019年3月6日
 */

package com.unionbigdata.mybatis.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getDatabaseType();
    }
}
