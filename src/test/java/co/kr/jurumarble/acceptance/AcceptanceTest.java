//package co.kr.jurumarble.acceptance;
//
//import io.restassured.RestAssured;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//
//import java.sql.SQLException;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class AcceptanceTest {
//
//    @LocalServerPort
//    int port;
//
////    @Autowired
////    private DatabaseCleanup databaseCleanup;
//
//    @BeforeEach
//    void setUp() throws SQLException {
//        if(isAcceptanceTestFirstInit()) {
//            RestAssured.port = port;
//        }
//    }
//
//    private boolean isAcceptanceTestFirstInit() {
//        return RestAssured.port == RestAssured.UNDEFINED_PORT;
//    }
//}
