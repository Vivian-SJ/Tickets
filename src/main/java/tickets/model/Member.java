package tickets.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member {
    @Id
    private int id;
    private String name;
    private String password;
    private String image;
    private String email;
    private String gender;
    private boolean valid;
    private int rank;
    private double credit;
    private double sum_consumption;
    private double money_available;

    public Member() {
    }

    public Member(String name, String password, String email, String gender) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.valid = true;
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
}
