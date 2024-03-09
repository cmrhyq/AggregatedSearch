package com.cmrhyq.search.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.model.entity.Picture;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 图片服务
 *
 * @author <a href="https://github.com/cmrhyq">AlanHuang</a>
 */
public interface PictureService {

    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize) throws UnsupportedEncodingException;
}
