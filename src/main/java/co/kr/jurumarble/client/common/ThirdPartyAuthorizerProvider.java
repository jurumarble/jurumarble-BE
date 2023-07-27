package co.kr.jurumarble.client.common;

import co.kr.jurumarble.user.enums.ProviderType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ThirdPartyAuthorizerProvider {
    private final List<ThirdPartyAuthorizer> thirdPartyAuthorizers;

    public ThirdPartyAuthorizer get(ProviderType providerType) {
        return thirdPartyAuthorizers.stream()
                .filter(authorizer -> authorizer.getProviderType() == providerType)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당하는 제공자가 없습니다."));
    }
}