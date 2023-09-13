package co.kr.jurumarble.client.tourApi;

import co.kr.jurumarble.exception.comment.NoDataFoundException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TourAreaBasedListResponse {
    @JsonProperty("response")
    private TourAreaBasedListResponse.Response response;

    public List<String> getContentIds() {
        if (response != null && response.getBody() != null && response.getBody().getItems() != null) {
            return response.getBody().getItems().getItem().stream()
                    .map(TourAreaBasedListResponse.Item::getContentid)
                    .collect(Collectors.toList());
        }
        throw new NoDataFoundException();
    }

    public List<String> getFirstImages() {
        if (response != null && response.getBody() != null && response.getBody().getItems() != null) {
            return response.getBody().getItems().getItem().stream()
                    .map(TourAreaBasedListResponse.Item::getFirstimage)
                    .collect(Collectors.toList());
        }
        throw new NoDataFoundException();
    }

    public List<String> getTitles() {
        if (response != null && response.getBody() != null && response.getBody().getItems() != null) {
            return response.getBody().getItems().getItem().stream()
                    .map(TourAreaBasedListResponse.Item::getTitle)
                    .collect(Collectors.toList());
        }
        throw new NoDataFoundException();
    }

    public Integer getTotalCount() {
        if (response != null && response.getBody() != null) {
            return response.getBody().getTotalCount();
        }
        throw new NoDataFoundException();
    }

    public Integer getPageNo() {
        if (response != null && response.getBody() != null) {
            return response.getBody().getPageNo();
        }
        throw new NoDataFoundException();
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

        @JsonProperty("pageNo")
        private Integer pageNo;

        @JsonProperty("totalCount")
        private Integer totalCount;

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