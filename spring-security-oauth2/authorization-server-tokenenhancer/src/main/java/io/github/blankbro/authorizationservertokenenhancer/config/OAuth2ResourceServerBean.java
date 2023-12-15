// package io.github.blankbro.authorizationservertokenenhancer.config;
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
// import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//
// import javax.crypto.spec.SecretKeySpec;
// import java.util.Map;
//
// @Slf4j
// @Configuration
// public class OAuth2ResourceServerBean {
//
//     @Autowired
//     private JwtAccessTokenConverter jwtAccessTokenConverter;
//
//     @Bean
//     public JwtDecoder jwtDecoder() {
//         Map<String, String> jwtAccessTokenConverterKey = jwtAccessTokenConverter.getKey();
//         String signerAlgorithm = jwtAccessTokenConverterKey.get("alg");
//         String verifierKey = jwtAccessTokenConverterKey.get("value");
//         if ("HMACSHA256".equals(signerAlgorithm)) {
//             return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(verifierKey.getBytes(), signerAlgorithm)).build();
//         }
//         return null;
//     }
//
// }
