package co.kr.jurumarble.client.tourApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

public class TourSearchKeyWordResponse {
    @JsonProperty("response")
    private TourSearchKeyWordResponse.Response response;

    public List<String> getContentIds() {
        return response.getBody().getItems().getItem().stream()
                .map(TourSearchKeyWordResponse.Item::getContentid)
                .collect(Collectors.toList());
    }

    public List<String> getFirstImages() {
        return response.getBody().getItems().getItem().stream()
                .map(TourSearchKeyWordResponse.Item::getFirstimage)
                .collect(Collectors.toList());
    }

    public List<String> getTitles() {
        return response.getBody().getItems().getItem().stream()
                .map(TourSearchKeyWordResponse.Item::getTitle)
                .collect(Collectors.toList());
    }

    @Data
    static class Response {
        @JsonProperty("body")
        private TourSearchKeyWordResponse.Body body;
    }

    @Data
    static class Body {
        @JsonProperty("items")
        private TourSearchKeyWordResponse.Items items;
    }

    @Data
    static class Items {
        @JsonProperty("item")
        private List<TourSearchKeyWordResponse.Item> item;
    }

    @Data
    static class Item {
        @JsonProperty("contentid")
        private String contentid;

        @JsonProperty("firstimage")
        private String firstimage;

        @JsonProperty("title")
        private String title;
    }

}
