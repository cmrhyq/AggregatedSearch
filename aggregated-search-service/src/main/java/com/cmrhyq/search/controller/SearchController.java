package com.cmrhyq.search.controller;

import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.common.BaseResponse;
import com.cmrhyq.search.common.ErrorCode;
import com.cmrhyq.search.common.ResultUtils;
import com.cmrhyq.search.exception.BusinessException;
import com.cmrhyq.search.exception.ThrowUtils;
import com.cmrhyq.search.manager.SearchFacade;
import com.cmrhyq.search.model.dto.picture.PictureQueryRequest;
import com.cmrhyq.search.model.dto.post.PostQueryRequest;
import com.cmrhyq.search.model.dto.search.SearchRequest;
import com.cmrhyq.search.model.dto.user.UserQueryRequest;
import com.cmrhyq.search.model.entity.Picture;
import com.cmrhyq.search.model.enums.SearchTypeEnum;
import com.cmrhyq.search.model.vo.PostVO;
import com.cmrhyq.search.model.vo.SearchVo;
import com.cmrhyq.search.model.vo.UserVO;
import com.cmrhyq.search.service.PictureService;
import com.cmrhyq.search.service.PostService;
import com.cmrhyq.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 搜索接口
 *
 * @author <a href="https://github.com/cmrhyq">AlanHuang</a>
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private SearchFacade searchFacade;

    /**
     * 聚合查询接口
     * 采用门面模式
     *
     * @param searchRequest 接口请求参数
     * @param request       HttpRequest
     * @return BaseResponse<SearchVo>
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) throws UnsupportedEncodingException {
        return ResultUtils.success(searchFacade.searchAll(searchRequest, request));
    }
}
