package tickets.util;

public enum CouponStatus {
    UNUSED,//未使用
    SELECTED;//已选择但还没付钱
    //如果已经用了这个coupon就会被删掉


    @Override
    public String toString() {
        switch (this) {
            case UNUSED:
                return "未使用";
            case SELECTED:
                return "已选择";
                default:return null;
        }
    }

    public static CouponStatus toEnumValue(String name) {
        for (CouponStatus couponStatus : CouponStatus.values()) {
            if (couponStatus.toString().equals(name)) {
                return couponStatus;
            }
        }
        return null;
    }
}
