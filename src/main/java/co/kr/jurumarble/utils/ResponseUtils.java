package co.kr.jurumarble.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {

    public static <T> ResponseEntity<Map<String, T>> wrapWithContent(T data) {
        Map<String, T> responseMap = new HashMap<>();
        responseMap.put("content", data);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}
