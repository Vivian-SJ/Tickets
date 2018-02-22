package tickets.bean;

public class ResultBean {
    private boolean result;
    private String message;

    public ResultBean() {
    }

    public ResultBean(boolean result) {
        this.result = true;
        this.message = "成功";
    }

    public ResultBean(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
