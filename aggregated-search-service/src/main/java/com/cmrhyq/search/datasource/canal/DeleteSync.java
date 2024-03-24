package com.cmrhyq.search.datasource.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cmrhyq.search.esdao.PostEsDao;
import com.cmrhyq.search.model.dto.post.PostEsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname DeleteSync.java
 * @project aggregated-search-service
 * @package com.cmrhyq.search.datasource.canal
 * @date 2024/3/24 14:03
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Slf4j
@Service
public class DeleteSync implements CanalSync {

    @Resource
    private PostEsDao postEsDao;

    /**
     * 同步文章数据
     *
     * @param rowData
     * @return
     */
    @Override
    public void syncData(CanalEntry.RowData rowData) throws ParseException {
        try {
            Map<String, Object> param = rowData.getBeforeColumnsList().stream().collect(Collectors.toMap(CanalEntry.Column::getName, CanalEntry.Column::getValue));
            postEsDao.deletePostById(Long.valueOf((String) param.get("id")));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
