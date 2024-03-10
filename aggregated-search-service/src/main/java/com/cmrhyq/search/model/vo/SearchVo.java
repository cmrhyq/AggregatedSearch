package com.cmrhyq.search.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>搜索实体VO</p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname SearchVo.java
 * @project springboot-init
 * @package com.cmrhyq.search.model.vo
 * @date 2024/3/9 0:03
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Data
public class SearchVo implements Serializable {

    List<UserVO> userList;

    List<PostVO> postList;

    List<Picture> pictureList;

    List<?> dataList;

    private static final long serialVersionUID = 1L;
}
