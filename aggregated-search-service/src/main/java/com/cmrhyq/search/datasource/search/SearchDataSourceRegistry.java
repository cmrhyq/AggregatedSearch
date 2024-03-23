package com.cmrhyq.search.datasource.search;

import com.cmrhyq.search.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>数据源注册器，注册器模式</p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname DataSourceRegistry.java
 * @project aggregated-search-service
 * @package com.cmrhyq.search.datasource
 * @date 2024/3/10 23:27
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Component
public class SearchDataSourceRegistry {

    @Resource
    private PictureSearchDataSource pictureDataSource;

    @Resource
    private UserSearchDataSource userDataSource;

    @Resource
    private PostSearchDataSource postDataSource;

    private Map<String, SearchDataSource<T>> typeDataSourceMap;

    /**
     * 该方法用于注册这些数据源
     * PostConstruct注解: 当依赖注入完成后用于执行初始化的方法，并且只会被执行一次
     */
    @PostConstruct
    public void doInit() {
        typeDataSourceMap = new HashMap() {{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
        }};
    }


    /**
     * 根据数据源类型返回数据源
     *
     * @param type 类型
     * @return DataSource
     */
    public SearchDataSource getDataSourceByType(String type) {
        if (typeDataSourceMap == null) {
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
