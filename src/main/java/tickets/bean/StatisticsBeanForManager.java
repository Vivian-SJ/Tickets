package tickets.bean;

public class StatisticsBeanForManager {
    private int stadiumAmount;

    private int memberAmount;

    public StatisticsBeanForManager() {
    }

    public StatisticsBeanForManager(int stadiumAmount, int memberAmount) {
        this.stadiumAmount = stadiumAmount;
        this.memberAmount = memberAmount;
    }

    public int getStadiumAmount() {
        return stadiumAmount;
    }

    public void setStadiumAmount(int stadiumAmount) {
        this.stadiumAmount = stadiumAmount;
    }

    public int getMemberAmount() {
        return memberAmount;
    }

    public void setMemberAmount(int memberAmount) {
        this.memberAmount = memberAmount;
    }
}
