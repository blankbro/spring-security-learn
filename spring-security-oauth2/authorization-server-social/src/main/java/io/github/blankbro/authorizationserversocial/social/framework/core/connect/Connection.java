package io.github.blankbro.authorizationserversocial.social.framework.core.connect;

import java.io.Serializable;

public interface Connection<A> extends Serializable {

    ConnectionKey getKey();

    void sync();

    A getApi();

    ConnectionData createData();
}
