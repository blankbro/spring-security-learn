package io.github.blankbro.authorizationserversocial.social.framework.core.connect;

import java.util.Set;

public interface ConnectionFactoryLocator {
    Set<String> registeredProviderIds();
}
