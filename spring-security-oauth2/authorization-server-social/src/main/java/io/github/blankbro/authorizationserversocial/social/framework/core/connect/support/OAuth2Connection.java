package io.github.blankbro.authorizationserversocial.social.framework.core.connect.support;

import io.github.blankbro.authorizationserversocial.social.framework.core.ServiceProvider;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ApiAdapter;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ConnectionData;
import io.github.blankbro.authorizationserversocial.social.framework.core.oauth2.OAuth2ServiceProvider;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class OAuth2Connection<A> extends AbstractConnection<A> {

    private static final long serialVersionUID = 4057584084077577480L;

    private transient final OAuth2ServiceProvider<A> serviceProvider;

    private String accessToken;

    private String refreshToken;

    private Long expireTime;

    private transient A api;

    private transient A apiProxy;

    public OAuth2Connection(String providerId, String providerUserId, String registrationId,
                            String accessToken, String refreshToken, Long expireTime,
                            OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
        super(apiAdapter);
        this.serviceProvider = serviceProvider;
        initAccessTokens(accessToken, refreshToken, expireTime);
        initApi(registrationId, providerUserId);
        initApiProxy();
        initKey(providerId, providerUserId);
    }

    public OAuth2Connection(ConnectionData data, String registrationId, OAuth2ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
        super(data, apiAdapter);
        this.serviceProvider = serviceProvider;
        initAccessTokens(data.getAccessToken(), data.getRefreshToken(), data.getExpireTime());
        initApi(registrationId, data.getProviderUserId());
        initApiProxy();
    }

    public boolean hasExpired() {
        synchronized (getMonitor()) {
            return expireTime != null && System.currentTimeMillis() >= expireTime;
        }
    }

    public A getApi() {
        if (apiProxy != null) {
            return apiProxy;
        } else {
            synchronized (getMonitor()) {
                return api;
            }
        }
    }

    public ConnectionData createData() {
        synchronized (getMonitor()) {
            return setValues();
        }
    }

    private void initAccessTokens(String accessToken, String refreshToken, Long expireTime) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }

    private void initApi(String registrationId, String providerUserId) {
        api = serviceProvider.getApi(registrationId, providerUserId);
    }

    @SuppressWarnings("unchecked")
    private void initApiProxy() {
        Class<?> apiType = GenericTypeResolver.resolveTypeArgument(serviceProvider.getClass(), ServiceProvider.class);
        if (apiType != null && apiType.isInterface()) {
            apiProxy = (A) Proxy.newProxyInstance(apiType.getClassLoader(), new Class<?>[]{apiType}, new ApiInvocationHandler());
        }
    }

    private class ApiInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            synchronized (getMonitor()) {
//				if (hasExpired()) {
//					throw new ExpiredAuthorizationException(getKey().getProviderId());
//				}
                try {
                    return method.invoke(OAuth2Connection.this.api, args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }
        }
    }

    // equas() and hashCode() generated by Eclipse
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
        result = prime * result + ((expireTime == null) ? 0 : expireTime.hashCode());
        result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        @SuppressWarnings("rawtypes")
        OAuth2Connection other = (OAuth2Connection) obj;

        if (accessToken == null) {
            if (other.accessToken != null) return false;
        } else if (!accessToken.equals(other.accessToken)) return false;

        if (expireTime == null) {
            if (other.expireTime != null) return false;
        } else if (!expireTime.equals(other.expireTime)) return false;

        if (refreshToken == null) {
            if (other.refreshToken != null) return false;
        } else if (!refreshToken.equals(other.refreshToken)) return false;

        return true;
    }


}