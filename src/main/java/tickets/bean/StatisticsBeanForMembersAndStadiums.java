package tickets.bean;

public class StatisticsBeanForMembersAndStadiums {
    private int id;
    private String name;
    private StatisticsBeanForMemberAndStadium statisticsBeanForMemberAndStadium;

    public StatisticsBeanForMembersAndStadiums() {
    }

    public StatisticsBeanForMembersAndStadiums(int id, String name, StatisticsBeanForMemberAndStadium statisticsBeanForMemberAndStadium) {
        this.id = id;
        this.name = name;
        this.statisticsBeanForMemberAndStadium = statisticsBeanForMemberAndStadium;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatisticsBeanForMemberAndStadium getStatisticsBeanForMemberAndStadium() {
        return statisticsBeanForMemberAndStadium;
    }

    public void setStatisticsBeanForMemberAndStadium(StatisticsBeanForMemberAndStadium statisticsBeanForMemberAndStadium) {
        this.statisticsBeanForMemberAndStadium = statisticsBeanForMemberAndStadium;
    }
}
