package com.chinatower.product.legalms.common;

/**
 * @Date: 2020/7/31 16:46
 * @Description:
 */
public class SystemTag {


    public enum OrgEnterTypeEnum {
        ORG_ENTERTYPE_CT("CT","01"),
        ORG_ENTERTYPE_TE("TE","02"),
        ORG_ENTERTYPE_TZ("TZ","03"),;

        private String index;

        private String desc;

        public static OrgEnterTypeEnum flag (String enterType){
            for (OrgEnterTypeEnum orgEnterTypeEnum : OrgEnterTypeEnum.values()) {
                if(orgEnterTypeEnum.index.equals(enterType)){
                    return orgEnterTypeEnum;
                }
            }
            return null ;
        }

        public String getIndex() {
            return index;
        }

        public String getDesc() {
            return desc;
        }

        OrgEnterTypeEnum(String index, String desc) {
            this.index = index;
            this.desc = desc;
        }
    }
    public enum OrgLevelEnum {

        ORG_LEVEL_ZEROONE("01","总部"),
        ORG_LEVEL_ZEROZERO("00","中国铁塔集团"),
        ORG_LEVEL_ZEROTWO("02","省分"),
        ORG_LEVEL_ZEROTHREE("03","地市"),
        ORG_LEVEL_ZEROFOUR("04","部门"),;

        private String index ;

        private String desc;

        OrgLevelEnum(String index, String desc) {
            this.index = index;
            this.desc = desc;
        }

        public String getIndex() {

            return index;
        }

        public String getDesc() {
            return desc;
        }
        public static OrgLevelEnum flag (String level){
            for (OrgLevelEnum result : OrgLevelEnum.values()) {
                if(result.getIndex().equals(level)){
                    return result;
                }
            }
            return null;
        }
    }

    public enum OrgStatusEnum {

        ORG_STATUS_ONE("1", "停用"),
        ORG_STATUS_ZERO("0", "正常"),
        ORG_STATUS_ZEROZERO("00", "停用"),
        ORG_STATUS_ZEROONE("01", "正常"),;

        private String index;

        private String desc;

        public String getIndex() {
            return index;
        }

        public String getDesc() {
            return desc;
        }

        OrgStatusEnum(String index, String desc) {
            this.index = index;
            this.desc = desc;
        }

        public static OrgStatusEnum flag(String status) {
            for (OrgStatusEnum restut : OrgStatusEnum.values()) {
                if (restut.getIndex().equals(status)) {
                    return restut;
                }
            }
            return null;
        }
    }


    public enum OrgTypeEnum {


        ORG_TYPE_ZEROONE("01","铁塔"),
        ORG_TYPE_ZEROTWO("02","智联"),
        ORG_TYPE_ZEROTHREE("03","能源"),;

        private String index;
        private String desc;


        public static  OrgTypeEnum flag (String type){
            for (OrgTypeEnum orgTypeEnum : OrgTypeEnum.values()) {
                if(orgTypeEnum.index.equals(type)){
                    return orgTypeEnum;

                }
            }
            return null;
        }

        OrgTypeEnum(String index, String desc) {
            this.index = index;
            this.desc = desc;
        }

        public String getIndex() {

            return index;
        }

        public String getDesc() {
            return desc;
        }
    }

    public enum UserStatusEnum {

        USER_STATUS_ONE("1","正常"),
        USER_STATUS_TWO("2","正常"),
        USER_STATUS_THREE("3","离职"),;

        private String index;

        private String desc;

        public String getIndex() {
            return index;
        }

        public String getDesc() {
            return desc;
        }

        UserStatusEnum(String index, String desc) {
            this.index = index;
            this.desc = desc;
        }

        public static UserStatusEnum flag(String status){
            for (UserStatusEnum restut : UserStatusEnum.values()) {
                if(restut.getIndex().equals(status)){
                    return restut;
                }
            }
            return null;
        }
    }

}
