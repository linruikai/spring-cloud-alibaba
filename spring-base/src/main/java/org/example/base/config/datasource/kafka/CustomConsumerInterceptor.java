package org.example.base.config.datasource.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.example.base.config.Constant;
import org.example.base.config.GrayHeaderContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class CustomConsumerInterceptor<K, V> implements ConsumerInterceptor<K, V> {

    @Override
    public ConsumerRecords<K, V> onConsume(ConsumerRecords<K, V> consumerRecords) {
        consumerRecords.forEach(consumerRecord -> {
            Header header = consumerRecord.headers().lastHeader(Constant.X_GRAY_VERSION);
            if (Objects.nonNull(header)) {
                GrayHeaderContextHolder.setGrayHeader(new String(header.value()));
            }
        });
        return consumerRecords;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
        GrayHeaderContextHolder.clearGrayHeader();
    }

    @Override
    public void close() {
        log.info("消息关闭了");
    }

    @Override
    public void configure(Map<String, ?> map) {
        log.info("消息配置了");
    }
}

