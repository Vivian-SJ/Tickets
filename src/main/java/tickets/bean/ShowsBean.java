package tickets.bean;

import java.util.List;
import java.util.Map;

public class ShowsBean {
    private Map<String, List<ShowBean>> typesAndShows;

    public ShowsBean() {
    }

    public ShowsBean(Map<String, List<ShowBean>> typesAndShows) {
        this.typesAndShows = typesAndShows;
    }

    public Map<String, List<ShowBean>> getTypesAndShows() {
        return typesAndShows;
    }

    public void setTypesAndShows(Map<String, List<ShowBean>> typesAndShows) {
        this.typesAndShows = typesAndShows;
    }
}
