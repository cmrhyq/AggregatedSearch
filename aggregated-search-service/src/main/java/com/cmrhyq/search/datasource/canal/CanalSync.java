package com.cmrhyq.search.datasource.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.model.dto.post.PostEsDTO;
import org.apache.poi.ss.formula.functions.T;

import java.text.ParseException;

/**
 * <p></p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname CanalSync.java
 * @project aggregated-search-service
 * @package com.cmrhyq.search.datasource.canal
 * @date 2024/3/24 14:04
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
public interface CanalSync {

    /**
     * 同步文章数据
     *
     * @param rowData
     * @return
     */
    void syncData(CanalEntry.RowData rowData) throws ParseException;
}
