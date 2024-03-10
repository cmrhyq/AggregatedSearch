package com.cmrhyq.search.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.common.ErrorCode;
import com.cmrhyq.search.datasource.*;
import com.cmrhyq.search.exception.BusinessException;
import com.cmrhyq.search.exception.ThrowUtils;
import com.cmrhyq.search.model.dto.post.PostQueryRequest;
import com.cmrhyq.search.model.dto.search.SearchRequest;
import com.cmrhyq.search.model.dto.user.UserQueryRequest;
import com.cmrhyq.search.model.entity.Picture;
import com.cmrhyq.search.model.enums.SearchTypeEnum;
import com.cmrhyq.search.model.vo.PostVO;
import com.cmrhyq.search.model.vo.SearchVo;
import com.cmrhyq.search.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * <p>搜索门面模式</p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname SearchFacade.java
 * @project aggregated-search-service
 * @package com.cmrhyq.search.manager
 * @date 2024/3/10 1:18
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Slf4j
@Component
public class SearchFacade {

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    /**
     * 聚合查询方法
     *
     * @param searchRequest 接口请求参数
     * @param request       HttpRequest
     * @return BaseResponse<SearchVo>
     * @throws UnsupportedEncodingException
     */
    public SearchVo searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) throws UnsupportedEncodingException {
        String searchType = searchRequest.getSearchType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(searchType);
        ThrowUtils.throwIf(StringUtils.isBlank(searchType), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        if (searchTypeEnum == null) {
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = null;
                try {
                    picturePage = pictureDataSource.doSearch(searchText, current, pageSize);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                return picturePage;
            });

            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
                return userVOPage;
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
                return postVOPage;
            });

            CompletableFuture.allOf(userTask, postTask, pictureTask).join();
            try {
                Page<PostVO> postVOPage = postTask.get();
                Page<UserVO> userVOPage = userTask.get();
                Page<Picture> picturePage = pictureTask.get();
                SearchVo searchVo = new SearchVo();
                searchVo.setUserList(userVOPage.getRecords());
                searchVo.setPostList(postVOPage.getRecords());
                searchVo.setPictureList(picturePage.getRecords());
                return searchVo;
            } catch (Exception e) {
                log.error("查询异常 {}", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        } else {
            SearchVo searchVo = new SearchVo();
            DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(searchType);
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            searchVo.setDataList(page.getRecords());
            return searchVo;
        }
    }
}
