package com.yupi.springbootinit.controller;

import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.picture.PictureQueryRequest;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVo;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
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
