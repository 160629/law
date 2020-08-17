package com.chinatower.product.legalms.modules.license.commen;

public class ServerResponseAdm{
        private String flag;

        private String desc;

    public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        private ServerResponseAdm(String flag, String desc) {
            this.flag = flag;
            this.desc = desc;
        }

    //调用成功
    public static ServerResponseAdm success(ResponseAdm responseAdm) {
        return new ServerResponseAdm(responseAdm.getFlag(),responseAdm.getDesc());
    }
    //异常提示
    public static ServerResponseAdm error(ResponseAdm responseAdm) {
        return new ServerResponseAdm(responseAdm.getFlag(),responseAdm.getDesc());
    }
}
