package tickets.bean;

public class StatisticsBeanForManager {
    private double income;
    private int stadiumAmount;

    private int memberAmount;

    public StatisticsBeanForManager() {
    }

    public StatisticsBeanForManager(int stadiumAmount, int memberAmount) {
        this.stadiumAmount = stadiumAmount;
        this.memberAmount = memberAmount;
    }

    public StatisticsBeanForManager(double income, int stadiumAmount, int memberAmount) {
        this.income = income;
        this.stadiumAmount = stadiumAmount;
        this.memberAmount = memberAmount;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
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
