package tickets.bean;

public class StadiumRegisterBean {
    private int stadiumId;
    private ResultBean resultBean;

    public StadiumRegisterBean() {
    }

    public StadiumRegisterBean(int stadiumId, ResultBean resultBean) {
        this.stadiumId = stadiumId;
        this.resultBean = resultBean;
    }

    public int getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }

    public ResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(ResultBean resultBean) {
        this.resultBean = resultBean;
    }
}
