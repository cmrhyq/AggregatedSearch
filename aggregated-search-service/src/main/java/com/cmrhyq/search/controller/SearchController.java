package com.cmrhyq.search.controller;

import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.common.BaseResponse;
import com.cmrhyq.search.common.ErrorCode;
import com.cmrhyq.search.common.ResultUtils;
import com.cmrhyq.search.exception.BusinessException;
import com.cmrhyq.search.exception.ThrowUtils;
import com.cmrhyq.search.model.dto.picture.PictureQueryRequest;
import com.cmrhyq.search.model.dto.post.PostQueryRequest;
import com.cmrhyq.search.model.dto.search.SearchRequest;
import com.cmrhyq.search.model.dto.user.UserQueryRequest;
import com.cmrhyq.search.model.entity.Picture;
import com.cmrhyq.search.model.vo.PostVO;
import com.cmrhyq.search.model.vo.SearchVo;
import com.cmrhyq.search.model.vo.UserVO;
import com.cmrhyq.search.service.PictureService;
import com.cmrhyq.search.service.PostService;
import com.cmrhyq.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
    private PictureService pictureService;

    @Resource
    private PostService postService;

    @Resource
    private UserService userService;

    /**
     * 聚合查询接口
     *
     * @param searchRequest
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) throws UnsupportedEncodingException, ExecutionException, InterruptedException {
        String searchText = searchRequest.getSearchText();

        CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
            Page<Picture> picturePage = null;
            try {
                picturePage = pictureService.searchPicture(searchText, 1, 10);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            return picturePage;
        });

        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userService.listUserVoByPage(userQueryRequest);
            return userVOPage;
        });

        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postService.listPostVoByPage(postQueryRequest, request);
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
            return ResultUtils.success(searchVo);
        } catch (Exception e) {
            log.error("查询异常 {}", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
        }
    }
}
