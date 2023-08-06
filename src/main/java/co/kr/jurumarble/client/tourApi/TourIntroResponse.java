package co.kr.jurumarble.client.tourApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TourIntroResponse {
    @JsonProperty("response")
    private Response response;

    public String getTreatMenu() {
        return response.getBody().getItems().getItem().get(0).getTreatmenu();
    }
}

@Data
class Response {
    @JsonProperty("body")
    private Body body;
}

@Data
class Body {
    @JsonProperty("items")
    private Items items;
}

@Data
class Items {
    @JsonProperty("item")
    private List<Item> item;
}

@Data
class Item {
    @JsonProperty("treatmenu")
    private String treatmenu;
}