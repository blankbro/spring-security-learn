package io.github.blankbro.authorizationserversocial.social.config;

import java.util.HashMap;
import java.util.Map;

public final class SocialPropertiesClientRegistrationAdapter {

    public static Map<String, ClientRegistration> getClientRegistrations(SocialProperties properties) {
        Map<String, ClientRegistration> clientRegistrations = new HashMap<>();
        properties.getRegistration().forEach((key, value) ->
                clientRegistrations.put(key, getClientRegistration(key, value, properties.getProvider())));
        return clientRegistrations;
    }

    public static Map<String, Map<String, ClientRegistration>> getClientProviders(SocialProperties properties) {
        Map<String, Map<String, ClientRegistration>> clientProviders = new HashMap<>();
        properties.getProvider().forEach((key, value) ->
                clientProviders.put(key, getClientProviders(key, value, properties.getRegistration())));
        return clientProviders;
    }

    private static Map<String, ClientRegistration> getClientProviders(String providerId,
                                                                      SocialProperties.Provider properties,
                                                                      Map<String, SocialProperties.Registration> registrations) {
        Map<String, ClientRegistration> result = new HashMap<>();
        registrations.forEach((key, value) -> {
            if (providerId.equals(value.getProvider())) {
                ClientRegistration.ClientRegistrationBuilder builder = ClientRegistration.builder()
                        .registrationId(key)
                        .scope(properties.getScope())
                        .authorizationUri(properties.getAuthorizationUri())
                        .tokenUri(properties.getTokenUri())
                        .userInfoUri(properties.getUserInfoUri())
                        .corpId(value.getCorpId())
                        .appId(value.getAppId())
                        .appSecret(value.getAppSecret())
                        .redirectUri(value.getRedirectUri())
                        .provider(value.getProvider());
                result.put(key, builder.build());
            }
        });
        return result;
    }


    private static ClientRegistration getClientRegistration(String registrationId,
                                                            SocialProperties.Registration properties,
                                                            Map<String, SocialProperties.Provider> providers) {
        String configuredProviderId = properties.getProvider();
        if (configuredProviderId == null) {
            throw new IllegalStateException(getErrorMessage(registrationId));
        }

        ClientRegistration.ClientRegistrationBuilder builder = ClientRegistration.builder().registrationId(registrationId);

        if (providers.containsKey(configuredProviderId)) {
            SocialProperties.Provider provider = providers.get(configuredProviderId);
            builder
                    .scope(provider.getScope())
                    .authorizationUri(provider.getAuthorizationUri())
                    .tokenUri(provider.getTokenUri())
                    .userInfoUri(provider.getUserInfoUri());
        }
        builder
                .corpId(properties.getCorpId())
                .appId(properties.getAppId())
                .appSecret(properties.getAppSecret())
                .redirectUri(properties.getRedirectUri())
                .provider(properties.getProvider());
        return builder.build();
    }

    private static String getErrorMessage(String registrationId) {
        return "Provider ID must be specified for client registration '" + registrationId + "'";
    }
}
