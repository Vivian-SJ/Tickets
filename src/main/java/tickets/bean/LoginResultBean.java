package tickets.bean;

public class LoginResultBean {
    private ResultBean resultBean;
    private int id;

    public LoginResultBean() {
    }

    public LoginResultBean(ResultBean resultBean, int id) {
        this.resultBean = resultBean;
        this.id = id;
    }

    public ResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(ResultBean resultBean) {
        this.resultBean = resultBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
