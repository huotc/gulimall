package com.htc.gulimall.common.enums;

public class ProductConstant {


    public enum  AttrEnum{
        ATTR_TYPE_BASE(1, "base","基本属性"),ATTR_TYPE_SALE(0, "sale","销售属性");
        private int code;
        private String type;
        private String msg;

        AttrEnum(int code, String type,String msg){
            this.code = code;
            this.type = type;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getType() {
            return type;
        }

        public String getMsg() {
            return msg;
        }

        public static int getCodeByType(String type){
            if (ATTR_TYPE_BASE.getType().equalsIgnoreCase(type)) {
                return ATTR_TYPE_BASE.getCode();
            }
            return ATTR_TYPE_SALE.getCode();
        }
    }
}
