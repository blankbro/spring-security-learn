package io.github.blankbro.authorizationserversocial.social.framework.security;

import io.github.blankbro.authorizationserversocial.social.framework.security.provider.SocialAuthenticationService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class SocialAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_FILTER_PROCESSES_URL = "/auth/**";
    private static final String DEFAULT_FAILURE_URL = "/login?error";

    private SocialAuthenticationServiceLocator authServiceLocator;

    public SocialAuthenticationFilter(SocialAuthenticationServiceLocator authServiceLocator) {
        super(DEFAULT_FILTER_PROCESSES_URL);
        super.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(DEFAULT_FAILURE_URL));
        this.authServiceLocator = authServiceLocator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (detectRejection(request)) {
            throw new AuthenticationServiceException("Authentication failed because user rejected authorization.");
        }

        Authentication authentication = null;
        Set<String> authProviders = authServiceLocator.registeredAuthenticationProviderIds();
        String[] pathParam = getRequestedProviderId(request);
        if (pathParam == null || pathParam.length != 2) {
            throw new AuthenticationServiceException("Authentication failed because redirect_uri illegal.");
        }
        if (!authProviders.isEmpty() && authProviders.contains(pathParam[0])) {
            SocialAuthenticationService<?> authService = authServiceLocator.getAuthenticationService(pathParam[0]);
            authentication = attemptAuthService(authService, pathParam[1], request, response);
            if (authentication == null) {
                throw new AuthenticationServiceException("authentication failed: authentication is null!");
            }
        }
        return authentication;
    }

    protected boolean detectRejection(HttpServletRequest request) {
        Set<?> parameterKeys = request.getParameterMap().keySet();
        return !parameterKeys.isEmpty() && !parameterKeys.contains("oauth_token") && !parameterKeys.contains("code") && !parameterKeys.contains("scope");
    }

    private String[] getRequestedProviderId(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');
        if (pathParamIndex > 0) {
            uri = uri.substring(0, pathParamIndex);
        }
        uri = uri.substring(request.getContextPath().length());
        if (!uri.startsWith("/auth")) {
            return null;
        }
        uri = uri.substring("/auth".length());
        if (uri.startsWith("/")) {
            return uri.substring(1).split("/");
        } else {
            return null;
        }
    }

    private Authentication attemptAuthService(final SocialAuthenticationService<?> authService, final String registrationId,
                                              final HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        final SocialAuthenticationToken authenticationToken = authService.getAuthenticationToken(registrationId, request, response);
        if (authenticationToken == null) return null;

        Assert.notNull(authenticationToken.getConnection(), "SocialAuthenticationToken get connection can not be null !");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return doAuthentication(authService, request, authenticationToken);
        } else {
            return authentication;
        }
    }

    private Authentication doAuthentication(SocialAuthenticationService<?> authService, HttpServletRequest request, SocialAuthenticationToken token) {
        if (!authService.getConnectionCardinality().isAuthenticatePossible()) return null;

        token.setDetails(authenticationDetailsSource.buildDetails(request));
        Authentication authentication = getAuthenticationManager().authenticate(token);
        Assert.isInstanceOf(UserDetails.class, authentication.getPrincipal(), "unexpected principle type");
        return authentication;
    }
}
