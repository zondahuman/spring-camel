package com.abin.lee.camel.message.controller;

import com.abin.lee.camel.message.common.util.json.JsonUtil;
import com.abin.lee.camel.message.model.FeedEntity;
import com.abin.lee.camel.message.service.producer.queue.CamelQueueMessageProducer;
import com.abin.lee.camel.message.service.producer.queue.Queue1MessageProducer;
import com.abin.lee.camel.message.service.producer.queue.Queue2MessageProducer;
import com.abin.lee.camel.message.service.producer.topic.CamelTopicMessageProducer;
import com.abin.lee.camel.message.service.producer.topic.Topic1MessageProducer;
import com.abin.lee.camel.message.service.producer.topic.Topic2MessageProducer;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/camel")
public class ProducerController {
	@Resource
    Queue1MessageProducer queue1MessageProducer;
	@Resource
    Queue2MessageProducer queue2MessageProducer;
	@Resource
    Topic1MessageProducer topic1MessageProducer;
	@Resource
    Topic2MessageProducer topic2MessageProducer;
    @Resource
    CamelQueueMessageProducer camelQueueMessageProducer;
    @Resource
    CamelTopicMessageProducer camelTopicMessageProducer;

	@ResponseBody
	@RequestMapping(value = "queue1Sender", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queue1Sender(@RequestParam("title") String title,
			@RequestParam("content") String content) {
		String opt = "";
		try {
			FeedEntity feedEntity = new FeedEntity();
			feedEntity.setTitle(title);
			feedEntity.setContent(content);
			queue1MessageProducer.sendQueue1Message(feedEntity);
			opt = "success";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}
	
	@ResponseBody
	@RequestMapping(value = "queue2Sender", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queue2Sender(@RequestParam("title") String title,
			@RequestParam("content") String content) {
		String opt = "";
		try {
			FeedEntity feedEntity = new FeedEntity();
			feedEntity.setTitle(title);
			feedEntity.setContent(content);
			queue2MessageProducer.sendQueue2Message(feedEntity);
			opt = "success";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}
	@ResponseBody
	@RequestMapping(value="queueSender3", method={RequestMethod.GET, RequestMethod.POST})
	public String queueSender3(@RequestParam("message")String message){
		String opt="";
		try {
			this.queue1MessageProducer.sendQueue1Message(message);
			opt="success";
		} catch (Exception e) {
			opt=e.getCause().toString();
		}
		return opt;
	}
	
	@ResponseBody
	@RequestMapping(value = "queueSender", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String queueSender(@RequestParam("title") String title,
			@RequestParam("content") String content) {
		String opt = "";
		try {
			FeedEntity feedEntity = new FeedEntity();
			feedEntity.setTitle(title);
			feedEntity.setContent(content);
			queue1MessageProducer.sendQueue1Message(feedEntity);
			queue2MessageProducer.sendQueue2Message(feedEntity);
			opt = "success";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}
	
	@ResponseBody
	@RequestMapping(value = "topic1Sender", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String topic1Sender(@RequestParam("title") String title,
			@RequestParam("content") String content) {
		String opt = "";
		try {
			FeedEntity feedEntity = new FeedEntity();
			feedEntity.setTitle(title);
			feedEntity.setContent(content);
			topic1MessageProducer.sendTopic1Message(feedEntity);
			opt = "success";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}
	
	@ResponseBody
	@RequestMapping(value = "topic2Sender", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String topic2Sender(@RequestParam("title") String title,
			@RequestParam("content") String content) {
		String opt = "";
		try {
			FeedEntity feedEntity = new FeedEntity();
			feedEntity.setTitle(title);
			feedEntity.setContent(content);
			topic2MessageProducer.sendTopic2Message(feedEntity);
			opt = "success";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "topicSender", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String topicSender(@RequestParam("title") String title,
			@RequestParam("content") String content) {
		String opt = "";
		try {
			FeedEntity feedEntity = new FeedEntity();
			feedEntity.setTitle("my title");
			feedEntity.setContent(content);
			topic1MessageProducer.sendTopic1Message(feedEntity);
			topic2MessageProducer.sendTopic2Message(feedEntity);
			opt = "success";
		} catch (Exception e) {
			opt = e.getCause().toString();
		}
		return opt;
	}


    @ResponseBody
    @RequestMapping(value = "camelTopic", method = { RequestMethod.GET,
            RequestMethod.POST })
    public String camelTopic(@RequestParam("title") String title,
                               @RequestParam("content") String content) {
        String opt = "";
        try {
            Map<String,String> request = Maps.newHashMap();
            request.put("title", title);
            request.put("content", content);
            camelTopicMessageProducer.sendCamelTopicMessage(JsonUtil.serialize(request));
            opt = "success";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }

    @ResponseBody
    @RequestMapping(value = "camelQueue", method = { RequestMethod.GET,
            RequestMethod.POST })
    public String camelQueue(@RequestParam("title") String title,
                             @RequestParam("content") String content) {
        String opt = "";
        try {
            Map<String,String> request = Maps.newHashMap();
            request.put("title",title);
            request.put("content",content);
            camelQueueMessageProducer.sendCamelQueueMessage(JsonUtil.serialize(request));
            opt = "success";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }

    @ResponseBody
    @RequestMapping(value = "camelNativeTopic", method = { RequestMethod.GET,
            RequestMethod.POST })
    public String camelNativeTopic(@RequestParam("title") String title,
                             @RequestParam("content") String content) {
        String opt = "";
        try {
            Map<String,String> request = Maps.newHashMap();
            request.put("title",title);
            request.put("content",content);
            camelTopicMessageProducer.sendNative(JsonUtil.serialize(request));
            opt = "success";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }

}
