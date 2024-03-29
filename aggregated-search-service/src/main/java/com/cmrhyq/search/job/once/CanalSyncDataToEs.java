package com.cmrhyq.search.job.once;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.cmrhyq.search.config.CanalConfig;
import com.cmrhyq.search.datasource.canal.CanalSync;
import com.cmrhyq.search.datasource.canal.CanalSyncRegistry;
import com.cmrhyq.search.esdao.PostEsDao;
import com.cmrhyq.search.mapper.PostMapper;
import com.cmrhyq.search.model.dto.post.PostEsDTO;
import com.cmrhyq.search.model.entity.Post;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>同步帖子数据到es</p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname CanalSyncDataToEs.java
 * @project aggregated-search-service
 * @package com.cmrhyq.search.job.cycle
 * @date 2024/3/23 17:57
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Slf4j
@Component
public class CanalSyncDataToEs implements CommandLineRunner {

    @Resource
    private CanalConfig canalConfig;

    @Resource
    private PostEsDao postEsDao;

    @Resource
    private CanalSyncRegistry canalSyncRegistry;

    /**
     * 链接Canal并准备同步数据
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        Thread canalThread = new Thread(() -> {
            log.info("连接canal并开始同步");
            CanalConnector connector = CanalConnectors.newSingleConnector(
                    new InetSocketAddress(
                            canalConfig.getHost(),
                            canalConfig.getPort()
                    ),
                    canalConfig.getDestination(),
                    canalConfig.getDbUser(),
                    canalConfig.getDbPassword()
            );
            try {
                connector.connect();
                // 这里订阅的参数会覆盖掉 canal 服务 example 中的 instance.properties 的 canal.instance.filter.regex 配置
                // 这里要注意官方的文档对于这项配置的描述貌似有一些歧义，很容易配错
                connector.subscribe(canalConfig.getSyncName());
                connector.rollback();
                while (true) {
                    Message message = connector.getWithoutAck(100);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId != -1 && size > 0) {
                        try {
                            for (CanalEntry.Entry entry : message.getEntries()) {
                                this.handle(entry);
                            }
                            connector.ack(batchId);
                        } catch (Exception e) {
                            connector.rollback(batchId);
                            // 处理异常
                            log.error("异常,回滚...", e);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("连接canal出现异常", e);
            } finally {
                connector.disconnect();
            }
        });
        canalThread.setName("canal-thread-0");
        canalThread.start();
    }

    /**
     * 根据数据的不同动作来 同步数据
     *
     * @param entry CanalEntry
     * @throws InvalidProtocolBufferException
     * @throws ParseException
     */
    public void handle(CanalEntry.Entry entry) throws InvalidProtocolBufferException, ParseException {
        if (entry.getEntryType().equals(CanalEntry.EntryType.ROWDATA)) {
            String tableName = entry.getHeader().getTableName();
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                CanalEntry.EventType eventType = rowChange.getEventType();
                CanalSync canalSync = canalSyncRegistry.getCanalSyncType(eventType);
                canalSync.syncData(rowData);
            }
        }
    }
}
