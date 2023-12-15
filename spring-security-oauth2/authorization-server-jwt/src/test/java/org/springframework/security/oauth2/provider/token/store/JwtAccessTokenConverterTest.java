package org.springframework.security.oauth2.provider.token.store;

import org.junit.Test;

public class JwtAccessTokenConverterTest {

    @Test
    public void test202312121911() throws Exception {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("SingingKey");
        jwtAccessTokenConverter.afterPropertiesSet();
        System.out.println();
    }
}
