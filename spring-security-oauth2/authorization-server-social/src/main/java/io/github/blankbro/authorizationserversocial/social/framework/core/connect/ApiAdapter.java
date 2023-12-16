package io.github.blankbro.authorizationserversocial.social.framework.core.connect;

public interface ApiAdapter<A> {

    void setConnectionData(A api, ConnectionData values);
}
