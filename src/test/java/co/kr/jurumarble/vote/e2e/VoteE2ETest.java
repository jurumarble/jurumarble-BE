//package co.kr.jurumarble.vote.e2e;
//
//import co.kr.jurumarble.acceptance.AcceptanceTest;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//
//public class VoteE2ETest extends AcceptanceTest {
//
//
//    private String drinkVoteRequestJson() {
//        return "{ " +
//                "\"title\": \"A, B 중 어떤게 나을까요?\"," +
//                "\"drinkAId\": 1," +
//                "\"drinkBId\": 2," +
//                "\"detail\": \"전통주 투표 상세 내용\"" +
//                "}";
//    }
//    @Test
//    @DisplayName("전통주 투표를 등록한다")
//    void createDrinkVote() {
//        // given
//        ExtractableResponse<Response> response = RestAssured.given().log().all()
//                .contentType(ContentType.JSON)
//                .body(drinkVoteRequestJson())
//                .when().post("api/votes/drink")
//                .then().log().all()
//                .extract();
//
//        // when // then
//
//        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//    }
//}
