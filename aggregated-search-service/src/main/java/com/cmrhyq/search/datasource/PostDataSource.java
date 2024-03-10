package com.cmrhyq.search.datasource;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmrhyq.search.common.ErrorCode;
import com.cmrhyq.search.constant.CommonConstant;
import com.cmrhyq.search.exception.BusinessException;
import com.cmrhyq.search.exception.ThrowUtils;
import com.cmrhyq.search.mapper.PostFavourMapper;
import com.cmrhyq.search.mapper.PostMapper;
import com.cmrhyq.search.mapper.PostThumbMapper;
import com.cmrhyq.search.model.dto.post.PostEsDTO;
import com.cmrhyq.search.model.dto.post.PostQueryRequest;
import com.cmrhyq.search.model.entity.Post;
import com.cmrhyq.search.model.entity.PostFavour;
import com.cmrhyq.search.model.entity.PostThumb;
import com.cmrhyq.search.model.entity.User;
import com.cmrhyq.search.model.vo.PostVO;
import com.cmrhyq.search.model.vo.UserVO;
import com.cmrhyq.search.service.PostService;
import com.cmrhyq.search.service.UserService;
import com.cmrhyq.search.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 帖子服务实现
 *
 * @author <a href="https://github.com/cmrhyq">AlanHuang</a>
 */
@Service
@Slf4j
public class PostDataSource implements DataSource<PostVO> {

    @Resource
    private PostService postService;

    /**
     * 搜索
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent((int) pageNum);
        postQueryRequest.setPageSize((int) pageSize);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Page<PostVO> postVOPage = postService.listPostVoByPage(postQueryRequest, null);
        return null;
    }
}




