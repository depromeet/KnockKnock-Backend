package io.github.depromeet.knockknockbackend.domain.credential.service;


import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OauthFactory {


    private final Map<String, OauthStrategy> oauthStrategyMap ;


    public OauthStrategy getOauthstrategy(String strategyName){

        return oauthStrategyMap.get(strategyName);
    }
}
