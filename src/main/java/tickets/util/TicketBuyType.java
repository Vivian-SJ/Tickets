package tickets.util;

public enum TicketBuyType {
    SELECT_SEAT,
    DIIRECT;

    public String toString() {
        switch (this) {
            case DIIRECT:
                return "立即购买";
            case SELECT_SEAT:
                return "选座购买";
                default:
                    return null;
        }
    }

    public static TicketBuyType toEnumValue(String name) {
        for (TicketBuyType ticketBuyType : TicketBuyType.values()) {
            if (ticketBuyType.toString().equals(name)) {
                return ticketBuyType;
            }
        }
        return null;
    }
}
