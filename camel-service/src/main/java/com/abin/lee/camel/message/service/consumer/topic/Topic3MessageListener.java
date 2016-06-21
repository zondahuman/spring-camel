package com.abin.lee.camel.message.service.consumer.topic;

import com.abin.lee.camel.message.common.util.system.OperateType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Message;
import javax.jms.MessageListener;

@Service
public class Topic3MessageListener implements MessageListener {
    final private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public void onMessage(Message message) {
        try {
            LOGGER.info(OperateType.getCmName() + "message={}", message.toString());
        } catch (Exception e) {
            LOGGER.error(OperateType.getCmName() + "message={} e.message={} e={}", message, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
