package co.kr.jurumarble.client.tourApi;

import co.kr.jurumarble.exception.comment.NoDataFoundException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TourSearchKeyWordResponse {
    @JsonProperty("response")
    private TourSearchKeyWordResponse.Response response;

    public List<String> getContentIds() {
        if (response != null && response.getBody() != null && response.getBody().getItems() != null) {
            return response.getBody().getItems().getItem().stream()
                    .map(TourSearchKeyWordResponse.Item::getContentid)
                    .collect(Collectors.toList());
        }
        throw new NoDataFoundException();
    }

    public List<String> getFirstImages() {
        if (response != null && response.getBody() != null && response.getBody().getItems() != null) {
            return response.getBody().getItems().getItem().stream()
                    .map(TourSearchKeyWordResponse.Item::getFirstimage)
                    .collect(Collectors.toList());
        }
        throw new NoDataFoundException();
    }

    public List<String> getTitles() {
        if (response != null && response.getBody() != null && response.getBody().getItems() != null) {
            return response.getBody().getItems().getItem().stream()
                    .map(TourSearchKeyWordResponse.Item::getTitle)
                    .collect(Collectors.toList());
        }
        throw new NoDataFoundException();
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
