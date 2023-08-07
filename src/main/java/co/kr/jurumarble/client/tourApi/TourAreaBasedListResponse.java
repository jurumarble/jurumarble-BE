package co.kr.jurumarble.client.tourApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TourAreaBasedListResponse {
    @JsonProperty("response")
    private TourAreaBasedListResponse.Response response;

    public List<String> getContentIds() {
        return response.getBody().getItems().getItem().stream()
                .map(TourAreaBasedListResponse.Item::getContentid)
                .collect(Collectors.toList());
    }

    public List<String> getFirstImages() {
        return response.getBody().getItems().getItem().stream()
                .map(TourAreaBasedListResponse.Item::getFirstimage)
                .collect(Collectors.toList());
    }

    public List<String> getTitles() {
        return response.getBody().getItems().getItem().stream()
                .map(TourAreaBasedListResponse.Item::getTitle)
                .collect(Collectors.toList());
    }

    @Data
    static class Response {
        @JsonProperty("body")
        private TourAreaBasedListResponse.Body body;
    }

    @Data
    static class Body {
        @JsonProperty("items")
        private TourAreaBasedListResponse.Items items;
    }

    @Data
    static class Items {
        @JsonProperty("item")
        private List<TourAreaBasedListResponse.Item> item;
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