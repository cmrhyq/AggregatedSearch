package com.cmrhyq.search.config;
import java.net.InetSocketAddress;
import java.util.List;


import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Canal配置</p>
 *
 * @author Alan Huang
 * @version v0.0.1
 * @classname CanalClient.java
 * @project aggregated-search-service
 * @package com.cmrhyq.search.common
 * @date 2024/3/17 23:05
 * @email cmrhyq@163.com
 * @since v0.0.1
 */
@Data
@Component
@ConfigurationProperties(prefix = "canal")
public class CanalConfig {

    private String dbUser;

    private String dbPassword;

    private String host;

    private Integer port = 11111;

    private String destination;

    private String syncName;

//    public static void getCanal(){
//        // 创建链接
//        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
//                11111), "example", "", "");
//        int batchSize = 1000;
//        int emptyCount = 0;
//        try {
//            connector.connect();
//            connector.subscribe(".*\\..*");
//            connector.rollback();
//            int totalEmptyCount = 120;
//            while (emptyCount < totalEmptyCount) {
//                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
//                long batchId = message.getId();
//                int size = message.getEntries().size();
//                if (batchId == -1 || size == 0) {
//                    emptyCount++;
//                    System.out.println("empty count : " + emptyCount);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                    }
//                } else {
//                    emptyCount = 0;
//                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
//                    printEntry(message.getEntries());
//                }
//
//                connector.ack(batchId); // 提交确认
//                // connector.rollback(batchId); // 处理失败, 回滚数据
//            }
//
//            System.out.println("empty too many times, exit");
//        } finally {
//            connector.disconnect();
//        }
//    }
//
//    private static void printEntry(List<Entry> entrys) {
//        for (Entry entry : entrys) {
//            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
//                continue;
//            }
//
//            RowChange rowChage = null;
//            try {
//                rowChage = RowChange.parseFrom(entry.getStoreValue());
//            } catch (Exception e) {
//                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
//                        e);
//            }
//
//            EventType eventType = rowChage.getEventType();
//            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
//                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
//                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
//                    eventType));
//
//            for (RowData rowData : rowChage.getRowDatasList()) {
//                if (eventType == EventType.DELETE) {
//                    printColumn(rowData.getBeforeColumnsList());
//                } else if (eventType == EventType.INSERT) {
//                    printColumn(rowData.getAfterColumnsList());
//                } else {
//                    System.out.println("-------&gt; before");
//                    printColumn(rowData.getBeforeColumnsList());
//                    System.out.println("-------&gt; after");
//                    printColumn(rowData.getAfterColumnsList());
//                }
//            }
//        }
//    }
//
//    private static void printColumn(List<Column> columns) {
//        for (Column column : columns) {
//            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
//        }
//    }
}
