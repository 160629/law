package com.chinatower.product.legalms.modules.license.entity;

/**
 * @Auther: G D
 * @Date: 2019/10/15 15:22
 * @Description:
 */
public class OrgTreeVO  {

        private Integer asyn;
        private String disabled;
        private String id;
        private String isParent;
        private String orderby;
        private String pid;
        private Integer type;
        private String val;
        private String deptName;
        private String orgId;
        private String orgName;
        private String nocheck;
        private String orgLevel;
        private String phone;
        private String tableType;

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getNocheck() {
        return nocheck;
    }

    public void setNocheck(String nocheck) {
        this.nocheck = nocheck;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setDisabled(String disabled) {
            this.disabled = disabled;
        }
        public String getDisabled() {
            return disabled;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setIsParent(String isParent) {
            this.isParent = isParent;
        }
        public String getIsParent() {
            return isParent;
        }

        public void setOrderby(String orderby) {
            this.orderby = orderby;
        }
        public String getOrderby() {
            return orderby;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
        public String getPid() {
            return pid;
        }

    public Integer getAsyn() {
        return asyn;
    }

    public void setAsyn(Integer asyn) {
        this.asyn = asyn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setVal(String val) {
            this.val = val;
        }
        public String getVal() {
            return val;
        }
}
