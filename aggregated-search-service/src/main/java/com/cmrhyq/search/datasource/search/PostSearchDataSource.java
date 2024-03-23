package com.cmrhyq.search.datasource.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.model.dto.post.PostQueryRequest;
import com.cmrhyq.search.model.entity.Post;
import com.cmrhyq.search.model.vo.PostVO;
import com.cmrhyq.search.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子服务实现
 *
 * @author <a href="https://github.com/cmrhyq">AlanHuang</a>
 */
@Service
@Slf4j
public class PostSearchDataSource implements SearchDataSource<PostVO> {

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
        Page<Post> postVOPage = postService.searchFromEs(postQueryRequest);
        return postService.getPostVOPage(postVOPage, request);
    }


}




