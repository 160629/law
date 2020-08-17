package com.chinatower.product.legalms.modules.grant.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinatower.framework.mq.kafka.api.KafkaUtils;
import com.chinatower.product.legalms.RequestHolder;
import com.chinatower.product.legalms.api.TestApi;
import com.chinatower.product.legalms.base.BaseController;
import com.chinatower.product.legalms.common.UserInfo;
import com.chinatower.product.legalms.modules.flow.service.unview.TFlowCommonService;
import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;
@RestController
public class TestApiImpl extends BaseController implements TestApi{
    @Autowired
    private TFlowCommonService tFlowService;
	@Autowired
	private KafkaUtils kafkaUtils;
	/*@Autowired
	private FlowUtil flowUtil;*/
	public Integer autoUnViewTest(@RequestBody AutoView obj) {
		UserInfo info = RequestHolder.getUserInfo();

		return tFlowService.autoUnView(obj,info);
	/*	flowUtil.init(info.getLoginAcct(), info.getLoginName(), ConstClass.TENANLID, null, false);
		List<WFWorkItem> inst = flowUtil.getNextWorkItems(100107060);
		System.out.println(inst);
		return autoUnView2;*/
		
	}
	@Autowired
    public String sendByAsync(String topic){
    	Map<String, Object> test = tFlowService.test();
        topic = topic.trim();
        String str = test.toString();
		//审批完成时发送通知给Kafka消息队列
        kafkaUtils.asyncSend(topic, str);

        return "ok";
    }
}
