package io.github.blankbro.authorizationserversocial.social.wechat.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * 完成微信的OAuth2认证流程的模板类。
 * 国内厂商实现的OAuth2方式不同, Spring默认提供的OAuth2Template无法完整适配，只能针对每个厂商调整。
 * <p>
 * WeChat 接口文档：https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html
 * 可参考的开源项目：https://github.com/LiHaodong888/pre
 */
public class WeChatOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String clientId;

    private String clientSecret;

    private String accessTokenUrl;

    /**
     * 微信获取授权码的url
     */
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 微信刷新accessToken的url
     */
    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    public WeChatOAuth2Template(String clientId, String clientSecret) {
        super(clientId, clientSecret, AUTHORIZE_URL, ACCESS_TOKEN_URL);
        setUseParametersForClientAuthentication(true);  // 请求中添加client_id和client_secret参数
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = ACCESS_TOKEN_URL;
    }

    /**
     * 微信变更了OAuth请求参数的名称，我们覆写相应的方法按照微信的文档改为微信请求参数的名字。
     *
     * @param authorizationCode
     * @param redirectUri
     * @param parameters
     * @return
     */
    @Override
    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri,
                                         MultiValueMap<String, String> parameters) {

        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);

        accessTokenRequestUrl.append("?appid=" + clientId);
        accessTokenRequestUrl.append("&secret=" + clientSecret);
        accessTokenRequestUrl.append("&code=" + authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");

        return getAccessToken(accessTokenRequestUrl);
    }

    /**
     * 微信变更了OAuth请求参数的名称，我们覆写相应的方法按照微信的文档改为微信请求参数的名字。
     *
     * @param refreshToken
     * @param additionalParameters
     * @return
     */
    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {

        StringBuilder refreshTokenUrl = new StringBuilder(REFRESH_TOKEN_URL);

        refreshTokenUrl.append("?appid=" + clientId);
        refreshTokenUrl.append("&grant_type=refresh_token");
        refreshTokenUrl.append("&refresh_token=" + refreshToken);

        return getAccessToken(refreshTokenUrl);
    }

    /**
     * 获取微信access_token信息
     *
     * @param accessTokenRequestUrl
     * @return
     */
    @SuppressWarnings("unchecked")
    private AccessGrant getAccessToken(StringBuilder accessTokenRequestUrl) {

        logger.info("获取access_token, 请求URL: " + accessTokenRequestUrl.toString());

        String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);

        logger.info("获取access_token, 响应内容: " + response);

        Map<String, Object> result = null;
        try {
            result = new ObjectMapper().readValue(response, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返回错误码时直接返回空
        if (StringUtils.isNotBlank(MapUtils.getString(result, "errcode"))) {
            String errcode = MapUtils.getString(result, "errcode");
            String errmsg = MapUtils.getString(result, "errmsg");
            throw new RuntimeException("获取access token失败, errcode:" + errcode + ", errmsg:" + errmsg);
        }

        WeChatAccessGrant accessToken = new WeChatAccessGrant(
                MapUtils.getString(result, "access_token"),
                MapUtils.getString(result, "scope"),
                MapUtils.getString(result, "refresh_token"),
                MapUtils.getLong(result, "expires_in"));

        accessToken.setOpenId(MapUtils.getString(result, "openid"));

        return accessToken;
    }

    /**
     * 构建获取授权码的请求。也就是引导用户跳转到微信的地址。
     */
    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        StringBuilder authenticateUrl = new StringBuilder(AUTHORIZE_URL);

        authenticateUrl.append("?appid=" + clientId);
        authenticateUrl.append("&redirect_uri=" + formEncode(parameters.getRedirectUri()));
        authenticateUrl.append("&response_type=code");
        authenticateUrl.append("&scope=snsapi_login");
        // authenticateUrl.append("&scope=snsapi_base");

        return authenticateUrl.toString();
    }

    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }

    /**
     * 微信返回的contentType是html/text，添加相应的HttpMessageConverter来处理。
     */
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    private String formEncode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // should not happen, UTF-8 is always supported
            throw new IllegalStateException(ex);
        }
    }

}