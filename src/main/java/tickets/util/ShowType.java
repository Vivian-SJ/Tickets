package tickets.util;

public enum ShowType {
    CONCERT,
    DANCE,
    DRAMA,
    OPERA,
    VOCAL_CONCERT,
    SPORTS_GAMES;

    @Override
    public String toString() {
        switch (this) {
            case CONCERT:return "音乐会";
            case DANCE:return "舞蹈";
            case DRAMA:return "戏剧";
            case OPERA:return "歌剧";
            case SPORTS_GAMES:return "体育";
            case VOCAL_CONCERT:return "演唱会";
            default:return null;
        }
    }

    public static ShowType toEnumValue(String name) {
        for (ShowType showType: ShowType.values()) {
            if (showType.toString().equals(name)) {
                return showType;
            }
        }
        return null;
    }
}
