package tickets.util;

public enum OrderStatus {
    TO_BE_PAID,
    WAIT_TICKET,
    TICKET_OUT,
    CANCELED,
    USED;
//    VALID;

    public String toString() {
        switch (this){
            case TO_BE_PAID:
                return "待支付";
            case WAIT_TICKET:
                return "待出票";
            case TICKET_OUT:
                return "已出票";
            case CANCELED:
                return "已取消";
            case USED:
                return "已使用";
//            case VALID:
//                return "有效单";
                default:
                    return null;
        }
    }

    public static OrderStatus getEnumValue(String name) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.toString().equals(name)) {
                return orderStatus;
            }
        }
        return null;
    }
}


