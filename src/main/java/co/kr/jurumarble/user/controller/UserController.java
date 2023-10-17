package co.kr.jurumarble.user.controller;

import co.kr.jurumarble.user.dto.LoginToken;
import co.kr.jurumarble.user.dto.request.AddInfoRequest;
import co.kr.jurumarble.user.dto.request.KakaoLoginRequest;
import co.kr.jurumarble.user.dto.request.NaverLoginRequest;
import co.kr.jurumarble.user.dto.request.UpdateUserRequest;
import co.kr.jurumarble.user.dto.response.GetUserResponse;
import co.kr.jurumarble.user.dto.response.TokenResponse;
import co.kr.jurumarble.user.dto.response.TokenTestResponse;
import co.kr.jurumarble.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "user", description = "user api")
public class UserController {

    private final UserService userService;

    @Operation(summary = "카카오 로그인")
    @PostMapping("/signup/kakao")
    public ResponseEntity<TokenResponse> kakaoLogin(@Valid @RequestBody KakaoLoginRequest kakaoLoginRequest) {
        LoginToken loginToken = userService.signupByThirdParty(kakaoLoginRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(loginToken));
    }

    @Operation(summary = "유저 정보 추가")
    @PutMapping("/additional-info")
    public ResponseEntity<HttpStatus> addUserInfo(@RequestAttribute Long userId, @RequestBody AddInfoRequest addInfoRequest) {
        userService.addUserInfo(userId, addInfoRequest.toAddUserInfo());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 정보 조회")
    @GetMapping()
    public ResponseEntity<GetUserResponse> getUserInfo(@RequestAttribute Long userId) {
        GetUserResponse userInfo = userService.getUserInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userInfo);
    }

    @Operation(summary = "네이버 로그인")
    @PostMapping("/signup/naver")
    public ResponseEntity<TokenResponse> naverLogin(@Valid @RequestBody NaverLoginRequest naverLoginRequest) {
        LoginToken loginToken = userService.signupByThirdParty(naverLoginRequest.toDomain());
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(loginToken));
    }

    @Operation(summary = "유저 탈퇴 기능")
    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteUser(@RequestAttribute Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 정보 수정")
    @PutMapping("")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UpdateUserRequest request, @RequestAttribute Long userId) {
        userService.updateUser(userId, request.toUpdateUserInfo());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 출생년도 검증")
    @GetMapping("/valid/birth/{year}")
    public ResponseEntity<HttpStatus> validBirth(@PathVariable Long year) {
        userService.validBirth(year);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "심사용 계정 로그인")
    @GetMapping("/test")
    public ResponseEntity<TokenTestResponse> tokenTest() {
        String token = userService.testToken();
        return ResponseEntity.ok().body(new TokenTestResponse(token));
    }

    @Operation(summary = "예비 심사용 계정 로그인")
    @GetMapping("/test-temporary")
    public ResponseEntity<TokenTestResponse> tokenTemporaryTest() {
        String token = userService.tokenTemporaryTest();
        return ResponseEntity.ok().body(new TokenTestResponse(token));
    }
}
