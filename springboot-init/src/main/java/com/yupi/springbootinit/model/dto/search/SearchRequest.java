package com.yupi.springbootinit.model.dto.search;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname SearchRequest.java
 * @project springboot-init
 * @package com.yupi.springbootinit.model.dto.search
 * @date 2024/3/9 0:02
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}

