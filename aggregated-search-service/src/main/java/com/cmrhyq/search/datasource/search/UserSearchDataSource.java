package com.cmrhyq.search.datasource.search;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.model.dto.user.UserQueryRequest;
import com.cmrhyq.search.model.vo.UserVO;
import com.cmrhyq.search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 用户服务实现
 *
 * @author <a href="https://github.com/cmrhyq">AlanHuang</a>
 */
@Service
@Slf4j
public class UserSearchDataSource implements SearchDataSource<UserVO> {

    @Resource
    private UserService userService;

    /**
     * 分页查询实现
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent((int) pageNum);
        userQueryRequest.setPageSize((int) pageSize);
        Page<UserVO> userVOPage = userService.listUserVoByPage(userQueryRequest);
        return userVOPage;
    }
}
