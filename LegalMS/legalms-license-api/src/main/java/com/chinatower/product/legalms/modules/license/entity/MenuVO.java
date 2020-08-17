package com.chinatower.product.legalms.modules.license.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
@Data
@Table(name = "t_pub_menu")
@ToString
public class MenuVO {
        @JsonProperty("flag")
        @Column(name = "flag")
        private String flag;

        @JsonProperty("serviceId")
        @NotEmpty(message = "应用标识不能为空")
        @Column(name = "service_id")
        private String serviceId;

        @JsonProperty("menuId")
        @Column(name = "menu_id")
        private Integer menuId;

        @JsonProperty("menuName")
        @NotEmpty(message = "菜单名称不能为空")
        @Column(name = "menu_name")
        private String menuName;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonProperty("menuCode")
        @Column(name = "menu_code")
        private String menuCode;

        @JsonProperty("menuLevel")
        @NotEmpty(message = "菜单层级不能为空")
        @Column(name = "menu_level")
        private Integer menuLevel;

        @JsonProperty("menuUrl")
        @Column(name = "menu_url")
        private String menuUrl;

        @JsonProperty("parentMenuId")
        @Column(name = "parent_menu_id")
        private Integer parentMenuId;

        @JsonProperty("parentIds")
        @Column(name = "parent_ids")
        private String parentIds;

        @JsonProperty("paramName")
        @Column(name = "param_name")
        private String paramName;

        @JsonProperty("menuType")
        @NotEmpty(message = "菜单类型不能为空")
        @Column(name = "menu_type")
        private Integer menuType;

        @JsonProperty("menuDesc")
        @NotEmpty(message = "菜单描述不能为空")
        @Column(name = "menu_desc")
        private String menuDesc;

        @JsonProperty("parentMenuCode")
        @NotEmpty(message = "父菜单编码不能为空")
        @Column(name = "parent_menu_code")
        private String parentMenuCode;

        public String getFlag() {
                return flag;
        }

        public void setFlag(String flag) {
                this.flag = flag;
        }

        public String getServiceId() {
                return serviceId;
        }

        public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
        }

        public Integer getMenuId() {
                return menuId;
        }

        public void setMenuId(Integer menuId) {
                this.menuId = menuId;
        }

        public String getMenuName() {
                return menuName;
        }

        public void setMenuName(String menuName) {
                this.menuName = menuName;
        }

        public String getMenuCode() {
                return menuCode;
        }

        public void setMenuCode(String menuCode) {
                this.menuCode = menuCode;
        }

        public Integer getMenuLevel() {
                return menuLevel;
        }

        public void setMenuLevel(Integer menuLevel) {
                this.menuLevel = menuLevel;
        }

        public String getMenuUrl() {
                return menuUrl;
        }

        public void setMenuUrl(String menuUrl) {
                this.menuUrl = menuUrl;
        }

        public Integer getParentMenuId() {
                return parentMenuId;
        }

        public void setParentMenuId(Integer parentMenuId) {
                this.parentMenuId = parentMenuId;
        }

        public String getParentIds() {
                return parentIds;
        }

        public void setParentIds(String parentIds) {
                this.parentIds = parentIds;
        }

        public String getParamName() {
                return paramName;
        }

        public void setParamName(String paramName) {
                this.paramName = paramName;
        }

        public Integer getMenuType() {
                return menuType;
        }

        public void setMenuType(Integer menuType) {
                this.menuType = menuType;
        }

        public String getMenuDesc() {
                return menuDesc;
        }

        public void setMenuDesc(String menuDesc) {
                this.menuDesc = menuDesc;
        }

        public String getParentMenuCode() {
                return parentMenuCode;
        }

        public void setParentMenuCode(String parentMenuCode) {
                this.parentMenuCode = parentMenuCode;
        }
}
