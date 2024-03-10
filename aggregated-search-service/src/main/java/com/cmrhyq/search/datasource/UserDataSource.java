package com.cmrhyq.search.datasource;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cmrhyq.search.common.ErrorCode;
import com.cmrhyq.search.constant.CommonConstant;
import com.cmrhyq.search.exception.BusinessException;
import com.cmrhyq.search.mapper.UserMapper;
import com.cmrhyq.search.model.dto.user.UserQueryRequest;
import com.cmrhyq.search.model.entity.Picture;
import com.cmrhyq.search.model.entity.User;
import com.cmrhyq.search.model.enums.UserRoleEnum;
import com.cmrhyq.search.model.vo.LoginUserVO;
import com.cmrhyq.search.model.vo.UserVO;
import com.cmrhyq.search.service.UserService;
import com.cmrhyq.search.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmrhyq.search.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现
 *
 * @author <a href="https://github.com/cmrhyq">AlanHuang</a>
 */
@Service
@Slf4j
public class UserDataSource implements DataSource<UserVO> {

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
