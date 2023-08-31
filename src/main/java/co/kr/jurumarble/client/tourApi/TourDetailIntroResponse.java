package co.kr.jurumarble.client.tourApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TourDetailIntroResponse {
    @JsonProperty("response")
    private TourDetailIntroResponse.Response response;

    public String getTreatMenu() {
        return response.getBody().getItems().getItem().get(0).getTreatmenu();
    }

    @Data
    static class Response {
        @JsonProperty("body")
        private TourDetailIntroResponse.Body body;
    }

    @Data
    static class Body {
        @JsonProperty("items")
        private TourDetailIntroResponse.Items items;
    }

    @Data
    static class Items {
        @JsonProperty("item")
        private List<TourDetailIntroResponse.Item> item;
    }

    @Data
    static class Item {
        @JsonProperty("treatmenu")
        private String treatmenu;
    }
}