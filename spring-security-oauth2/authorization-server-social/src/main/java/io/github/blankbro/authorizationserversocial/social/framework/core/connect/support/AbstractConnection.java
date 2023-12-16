package io.github.blankbro.authorizationserversocial.social.framework.core.connect.support;


import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ApiAdapter;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.Connection;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ConnectionData;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ConnectionKey;

public abstract class AbstractConnection<A> implements Connection<A> {

    private static final long serialVersionUID = 7330875324290049412L;

    private transient final ApiAdapter<A> apiAdapter;

    private ConnectionKey key;

    private boolean valuesInitialized;

    private transient final Object monitor = new Object();

    public AbstractConnection(ApiAdapter<A> apiAdapter) {
        this.apiAdapter = apiAdapter;
    }

    public AbstractConnection(ConnectionData data, ApiAdapter<A> apiAdapter) {
        key = new ConnectionKey(data.getProviderId(), data.getProviderUserId());
        this.apiAdapter = apiAdapter;
        valuesInitialized = true;
    }

    public ConnectionKey getKey() {
        return key;
    }

    public void sync() {
        synchronized (monitor) {
            setValues();
        }
    }

    public abstract A getApi();

    public abstract ConnectionData createData();

    protected void initKey(String providerId, String providerUserId) {
        if (providerUserId == null) {
            providerUserId = setValues().getProviderUserId();
        }
        key = new ConnectionKey(providerId, providerUserId);
    }

    protected Object getMonitor() {
        return monitor;
    }

    @SuppressWarnings("rawtypes")
    public boolean equals(Object o) {
        if (!(o instanceof Connection)) {
            return false;
        }
        Connection other = (Connection) o;
        return key.equals(other.getKey());
    }

    public int hashCode() {
        return key.hashCode();
    }

    private void initValues() {
        if (!valuesInitialized) {
            setValues();
        }
    }

    protected ConnectionData setValues() {
        ConnectionData data = new ConnectionData();
        data.setProviderId(getKey().getProviderId());
        apiAdapter.setConnectionData(getApi(), data);
        valuesInitialized = true;
        return data;
    }

}
