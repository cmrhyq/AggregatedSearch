package com.cmrhyq.search.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>bing抓取的图片</p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname Picture.java
 * @project springboot-init
 * @package com.cmrhyq.search.model.entity
 * @date 2024/3/6 23:42
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Data
public class Picture implements Serializable {

    private String title;

    private String url;

    private static final long serialVersionUID = 1L;
}
