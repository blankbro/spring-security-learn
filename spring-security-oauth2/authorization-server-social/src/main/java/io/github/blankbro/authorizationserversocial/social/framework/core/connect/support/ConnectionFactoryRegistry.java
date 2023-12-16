package io.github.blankbro.authorizationserversocial.social.framework.core.connect.support;

import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ConnectionFactory;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ConnectionFactoryLocator;
import org.springframework.core.GenericTypeResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConnectionFactoryRegistry implements ConnectionFactoryLocator {

    private final Map<String, ConnectionFactory<?>> connectionFactories = new HashMap<>();

    private final Map<Class<?>, String> apiTypeIndex = new HashMap<>();

    public void addConnectionFactory(ConnectionFactory<?> connectionFactory) {
        if (connectionFactories.containsKey(connectionFactory.getProviderId())) {
            throw new IllegalArgumentException("A ConnectionFactory for provider '" + connectionFactory.getProviderId() + "' has already been registered");
        }
        Class<?> apiType = GenericTypeResolver.resolveTypeArgument(connectionFactory.getClass(), ConnectionFactory.class);
        if (apiTypeIndex.containsKey(apiType)) {
            throw new IllegalArgumentException("A ConnectionFactory for API [" + apiType.getName() + "] has already been registered");
        }
        connectionFactories.put(connectionFactory.getProviderId(), connectionFactory);
        apiTypeIndex.put(apiType, connectionFactory.getProviderId());
    }

    public void setConnectionFactories(List<ConnectionFactory<?>> connectionFactories) {
        for (ConnectionFactory<?> connectionFactory : connectionFactories) {
            addConnectionFactory(connectionFactory);
        }
    }

    @Override
    public Set<String> registeredProviderIds() {
        return connectionFactories.keySet();
    }
}
