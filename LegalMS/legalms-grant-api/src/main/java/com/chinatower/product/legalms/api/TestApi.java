package com.chinatower.product.legalms.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.chinatower.product.legalms.modules.flow.vo.unview.AutoView;

public interface TestApi {
	@PostMapping("/unviewTest/selectAll")
	public Integer autoUnViewTest(@RequestBody AutoView obj);
	
    @GetMapping("/unviewTest/async")
    public String sendByAsync(String topic);
}
