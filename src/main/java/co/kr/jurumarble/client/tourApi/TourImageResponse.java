package co.kr.jurumarble.client.tourApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TourImageResponse {
    @JsonProperty("response")
    private TourImageResponse.Response response;

    public List<String> getDetailImages() {
        List<String> urls = new ArrayList<>();
        for (TourImageResponse.Item item : response.getBody().getItems().getItem()) {
            urls.add(item.getOriginimgurl());
        }
        return urls;
    }

    @Data
    static class Response {
        @JsonProperty("body")
        private TourImageResponse.Body body;
    }

    @Data
    static class Body {
        @JsonProperty("items")
        private TourImageResponse.Items items;
    }

    @Data
    static class Items {
        @JsonProperty("item")
        private List<TourImageResponse.Item> item;
    }

    @Data
    static class Item {
        @JsonProperty("originimgurl")
        private String originimgurl;
    }
}
