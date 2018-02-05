package com.wbda.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class JwtAccessTokenConverterConfig {

    @Value("${app.security.tokenSigningKey:wbda123}")
    private String tokenSigningKey;

    @Bean
    public JwtAccessTokenConverter getJwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                //((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionInformation);
                return super.enhance(accessToken, authentication);
            }
        };
        converter.setSigningKey(tokenSigningKey);

        return converter;
    }

}
