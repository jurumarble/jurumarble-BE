package co.kr.jurumarble.client.tourApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TourDetailIntroResponse {
    @JsonProperty("response")
    private TourDetailIntroResponse.Response response;

    public String getFirstMenu() {
        return response.getBody().getItems().getItem().get(0).getFirstmenu();
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
        @JsonProperty("firstmenu")
        private String firstmenu;
    }
}