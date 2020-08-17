package com.chinatower.product.legalms.modules.flow.entity.unview;

import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 待阅配置表
 *
 * @author wangyong
 * @date   2019/12/11
 */
@Table(name="t_flow_unview_config")
public class TFlowUnviewConfig {
    @Id
    private Integer configId;
    
    private String configName;

    private String shortFlowName;

    private String configScene;

    private String sender;

    private String title;

    private String coment;

    private String configUrl;

    private String configUrlName;

    private Integer configOrder;

    private Integer configSendType;
    
    private String configRoles;
    
    private Integer openType;

    
    
    public Integer getOpenType() {
		return openType;
	}

	public void setOpenType(Integer openType) {
		this.openType = openType;
	}

	public Integer getConfigSendType() {
		return configSendType;
	}

	public void setConfigSendType(Integer configSendType) {
		this.configSendType = configSendType;
	}


	public String getConfigRoles() {
		return configRoles;
	}

	public void setConfigRoles(String configRoles) {
		this.configRoles = configRoles;
	}

	public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}



    public String getShortFlowName() {
		return shortFlowName;
	}

	public void setShortFlowName(String shortFlowName) {
		this.shortFlowName = shortFlowName;
	}

	public String getConfigScene() {
        return configScene;
    }

    public void setConfigScene(String configScene) {
        this.configScene = configScene == null ? null : configScene.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment == null ? null : coment.trim();
    }

    public String getConfigUrl() {
        return configUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.configUrl = configUrl == null ? null : configUrl.trim();
    }

    public String getConfigUrlName() {
        return configUrlName;
    }

    public void setConfigUrlName(String configUrlName) {
        this.configUrlName = configUrlName == null ? null : configUrlName.trim();
    }

    public Integer getConfigOrder() {
        return configOrder;
    }

    public void setConfigOrder(Integer configOrder) {
        this.configOrder = configOrder;
    }

	@Override
	public String toString() {
		return "TFlowUnviewConfig [configId=" + configId + ", configName=" + configName + ", shortFlowName="
				+ shortFlowName + ", configScene=" + configScene + ", sender=" + sender + ", title=" + title
				+ ", coment=" + coment + ", configUrl=" + configUrl + ", configUrlName=" + configUrlName
				+ ", configOrder=" + configOrder + ", configSendType=" + configSendType + ", configRoles=" + configRoles
				+ "]";
	}
    
    
}