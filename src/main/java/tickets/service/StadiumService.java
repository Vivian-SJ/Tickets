package tickets.service;

import tickets.bean.ResultBean;
import tickets.bean.StadiumBean;
import tickets.bean.StadiumRegisterBean;

public interface StadiumService {
    public StadiumRegisterBean register(StadiumBean stadiumBean);

    public StadiumBean getInfoById(int stadiumId);

    public ResultBean modifyInfo(StadiumBean stadiumBean);
}
