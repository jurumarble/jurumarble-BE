package co.kr.jurumarble.client.tourApi;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RestaurantListDto {

    private List<RestaurantInfoDto> restaurantInfoList;
    private Integer totalCount;
    private Integer page;
    private Integer numOfRows;

    public RestaurantListDto(List<RestaurantInfoDto> restaurantInfoList, Integer totalCount, Integer page, Integer numOfRows) {
        this.restaurantInfoList = restaurantInfoList;
        this.totalCount = totalCount;
        this.page = page;
        this.numOfRows = numOfRows;
    }
}