package co.kr.jurumarble.drink;

import co.kr.jurumarble.acceptance.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class DrinkAcceptanceTest extends AcceptanceTest {
    @Test
    void 전통주_리스트를_조회한다(){
        // given
        Map<String, String> params = new HashMap<>();
        params.put("sortBy", "ByPopularity");
        params.put("page", "0");
        params.put("size", "10");

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .queryParams(params)
                .when().get("/api/drinks")
                .then().log().all()
                .extract();

        // then
        Object o = response.jsonPath().get();
    }

}
