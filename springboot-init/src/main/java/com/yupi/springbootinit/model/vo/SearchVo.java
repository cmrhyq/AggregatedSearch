package com.yupi.springbootinit.model.vo;

import com.google.gson.Gson;
import com.yupi.springbootinit.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname SearchVo.java
 * @project springboot-init
 * @package com.yupi.springbootinit.model.vo
 * @date 2024/3/9 0:03
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Data
public class SearchVo implements Serializable {

    List<UserVO> userList;

    List<PostVO> postList;

    List<Picture> pictureList;

    private static final long serialVersionUID = 1L;
}
