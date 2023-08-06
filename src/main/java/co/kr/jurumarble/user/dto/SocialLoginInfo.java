package co.kr.jurumarble.user.dto;

import co.kr.jurumarble.user.enums.ProviderType;
import lombok.Getter;

@Getter
public class SocialLoginInfo {
    private String email;
    private ProviderType providerType;
    private String providerId;

    public SocialLoginInfo(String email, ProviderType providerType, String providerId) {
        this.email = email;
        this.providerType = providerType;
        this.providerId = providerId;
    }
}