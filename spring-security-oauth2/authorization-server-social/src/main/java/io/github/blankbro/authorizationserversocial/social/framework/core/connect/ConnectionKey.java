package io.github.blankbro.authorizationserversocial.social.framework.core.connect;

import java.io.Serializable;

public final class ConnectionKey implements Serializable {

    private final String providerId;

    private final String providerUserId;

    public ConnectionKey(String providerId, String providerUserId) {
        this.providerId = providerId;
        this.providerUserId = providerUserId;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ConnectionKey)) {
            return false;
        }
        ConnectionKey other = (ConnectionKey) o;
        boolean sameProvider = providerId.equals(other.providerId);
        return providerUserId != null ? sameProvider && providerUserId.equals(other.providerUserId) : sameProvider && other.providerUserId == null;
    }

    public int hashCode() {
        int hashCode = providerId.hashCode();
        return providerUserId != null ? hashCode + providerUserId.hashCode() : hashCode;
    }

    public String toString() {
        return providerId + ":" + providerUserId;
    }
}
