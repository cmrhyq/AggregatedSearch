package com.cmrhyq.search.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.cmrhyq.search.annotation.AuthCheck;
import com.cmrhyq.search.common.BaseResponse;
import com.cmrhyq.search.common.DeleteRequest;
import com.cmrhyq.search.common.ErrorCode;
import com.cmrhyq.search.common.ResultUtils;
import com.cmrhyq.search.constant.UserConstant;
import com.cmrhyq.search.exception.BusinessException;
import com.cmrhyq.search.exception.ThrowUtils;
import com.cmrhyq.search.model.dto.picture.PictureQueryRequest;
import com.cmrhyq.search.model.dto.post.PostAddRequest;
import com.cmrhyq.search.model.dto.post.PostEditRequest;
import com.cmrhyq.search.model.dto.post.PostQueryRequest;
import com.cmrhyq.search.model.dto.post.PostUpdateRequest;
import com.cmrhyq.search.model.entity.Picture;
import com.cmrhyq.search.model.entity.Post;
import com.cmrhyq.search.model.entity.User;
import com.cmrhyq.search.model.vo.PostVO;
import com.cmrhyq.search.service.PictureService;
import com.cmrhyq.search.service.PostService;
import com.cmrhyq.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/cmrhyq">AlanHuang</a>
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;

    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                         HttpServletRequest request) throws IOException {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        String searchText = pictureQueryRequest.getSearchText();
        Page<Picture> picturePage = pictureService.searchPicture(searchText, current, size);
        return ResultUtils.success(picturePage);
    }
}
