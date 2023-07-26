package co.kr.jurumarble.user.dto;

import co.kr.jurumarble.user.enums.ProviderType;
import lombok.Getter;

import java.util.Map;

@Getter
public class ThirdPartySignupInfo {
    private final ProviderType providerType;
    private final Map<String, String> propertiesValues;

    public ThirdPartySignupInfo(ProviderType providerType, Map<String, String> propertiesValues) {
        this.providerType = providerType;
        this.propertiesValues = propertiesValues;
    }
}