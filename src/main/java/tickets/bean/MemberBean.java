package tickets.bean;

import tickets.model.Member;

public class MemberBean {
    private int id;
    private String name;
    private String password;
    //头像
    private String image;
    private String email;
    private String gender;
    //会员是否有效
    private boolean valid;
    //会员等级
    private int rank;
    //会员积分
    private double credit;
    //总消费额
    private double sum_consumption;
    //可用余额
    private double money_available;
    //激活状态
    private boolean activate_state;
    //激活码
    private String activate_code;

    public MemberBean() {
    }

    public MemberBean(Member member) {
        this.name = member.getName();
        this.password = member.getPassword();
        this.email = member.getEmail();
        this.gender = member.getGender();
        this.valid = member.isValid();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getSum_consumption() {
        return sum_consumption;
    }

    public void setSum_consumption(double sum_consumption) {
        this.sum_consumption = sum_consumption;
    }

    public double getMoney_available() {
        return money_available;
    }

    public void setMoney_available(double money_available) {
        this.money_available = money_available;
    }

    public boolean isActivate_state() {
        return activate_state;
    }

    public void setActivate_state(boolean activate_state) {
        this.activate_state = activate_state;
    }

    public String getActivate_code() {
        return activate_code;
    }

    public void setActivate_code(String activate_code) {
        this.activate_code = activate_code;
    }
}
