package com.cmrhyq.search.datasource.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.UnsupportedEncodingException;

/**
 * <p>数据源接口，适配器模式</p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname DataSource.java
 * @project aggregated-search-service
 * @package com.cmrhyq.search.datasource
 * @date 2024/3/10 21:53
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
public interface SearchDataSource<T> {

    /**
     * 搜索门面模式接口
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize) throws UnsupportedEncodingException;
}
