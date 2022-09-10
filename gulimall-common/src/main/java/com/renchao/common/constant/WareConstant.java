package com.renchao.common.constant;

public class WareConstant {
    public static final String WARE_EVENT_EXCHANGE = "ware-event-exchange";
    public static final String WARE_DELAY_QUEUE = "ware.delay.queue";
    public static final String WARE_RELEASE_STOCK_QUEUE = "ware.release.stock.queue";
    public static final String WARE_DELAY_BINDING = "delay.release.stock";
    public static final String WARE_RELEASE_STOCK_BINDING = "release.stock";
    public static final int WARE_DELAY_UNLOCK_TIME = 5000; // 延时解锁等待时间

    public enum PurchaseStatus {
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        RECEIVED(2,"已领取"),
        COMPLETED(3,"已完成"),
        HAS_ERROR(4,"有异常");

        private final int code;
        private final String msg;

        PurchaseStatus(int code, String msg) {
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
    public enum PurchaseDetailStatus {
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        PURCHASING(2,"正在采购"),
        COMPLETED(3,"已完成"),
        FAILED(4,"采购失败");

        private final int code;
        private final String msg;

        PurchaseDetailStatus(int code, String msg) {
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
