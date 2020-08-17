package com.chinatower.product.legalms.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "nginx")
public class ScheduledProperties {

    private String formurl;

    public String getFormurl() {
        return formurl;
    }

    public void setFormurl(String formurl) {
        this.formurl = formurl;
    }

}
