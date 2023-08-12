package co.kr.jurumarble.user.controller;

import co.kr.jurumarble.user.dto.LoginToken;
import co.kr.jurumarble.user.dto.request.AddInfoRequest;
import co.kr.jurumarble.user.dto.request.KakaoLoginRequest;
import co.kr.jurumarble.user.dto.request.NaverLoginRequest;
import co.kr.jurumarble.user.dto.response.TokenResponse;
import co.kr.jurumarble.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup/kakao")
    public ResponseEntity<TokenResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        LoginToken loginToken = userService.signupByThirdParty(kakaoLoginRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(loginToken));
    }

    @PatchMapping("/additional-info")
    public ResponseEntity<HttpStatus> addUserInfo(@RequestAttribute Long userId, @RequestBody AddInfoRequest addInfoRequest) {
        userService.addUserInfo(userId, addInfoRequest.toAddUserInfo());
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<HttpStatus> getUserInfo(@RequestAttribute Long userId) {
        userService.getUserInfo(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup/naver")
    public ResponseEntity<TokenResponse> naverLogin(@Valid @RequestBody NaverLoginRequest naverLoginRequest) {
        LoginToken loginToken = userService.signupByThirdParty(naverLoginRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(loginToken));
    }

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteUser(@RequestAttribute Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
