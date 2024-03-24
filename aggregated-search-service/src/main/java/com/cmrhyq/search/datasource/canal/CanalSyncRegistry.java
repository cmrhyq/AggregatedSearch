package com.cmrhyq.search.datasource.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p></p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname CanalDataSourceRegistry.java
 * @project aggregated-search-service
 * @package com.cmrhyq.search.datasource.canal
 * @date 2024/3/24 14:00
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Component
public class CanalSyncRegistry {

    @Resource
    private DeleteSync deleteSync;

    @Resource
    private SaveSync saveSync;

    private Map<String, CanalSync> syncMap;

    @PostConstruct
    public void doInit() {
        syncMap = new HashMap() {{
            put(CanalEntry.EventType.INSERT, saveSync);
            put(CanalEntry.EventType.UPDATE, saveSync);
            put(CanalEntry.EventType.DELETE, deleteSync);
        }};
    }

    public CanalSync getCanalSyncType(CanalEntry.EventType type) {
        if (syncMap == null) {
            return null;
        }
        return syncMap.get(type);
    }
}
