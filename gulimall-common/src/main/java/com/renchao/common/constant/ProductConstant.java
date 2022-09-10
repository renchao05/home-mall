package com.renchao.common.constant;

public class ProductConstant {

    public enum PublishStatus {
        OFFLINE(0,"下架"),
        ONLINE(1, "上架");

        private final int code;
        private final String msg;

        PublishStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
