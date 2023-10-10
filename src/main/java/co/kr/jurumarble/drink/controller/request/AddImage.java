package co.kr.jurumarble.drink.controller.request;

import lombok.Getter;

@Getter
public class AddImage {
    private Long drinkId;
    private String imageUrl;
}
