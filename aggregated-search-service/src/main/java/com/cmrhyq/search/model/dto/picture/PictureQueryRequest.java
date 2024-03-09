package com.cmrhyq.search.model.dto.picture;

import com.cmrhyq.search.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname Picture.java
 * @project springboot-init
 * @package com.cmrhyq.search.model.dto.picture
 * @date 2024/3/6 23:53
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}
