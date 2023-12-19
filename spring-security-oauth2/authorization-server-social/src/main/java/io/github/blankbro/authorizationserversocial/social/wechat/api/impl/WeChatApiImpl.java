package io.github.blankbro.authorizationserversocial.social.wechat.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.blankbro.authorizationserversocial.social.wechat.api.WeChatApi;
import io.github.blankbro.authorizationserversocial.social.wechat.dto.WeChatUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 微信API调用模板，scope为Request的SpringBean，根据当前用户的accessToken创建。
 */
@Slf4j
public class WeChatApiImpl extends AbstractOAuth2ApiBinding implements WeChatApi {
    /**
     * 用于序列化Json数据
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取用户信息url
     */
    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";

    /**
     * WeChatImpl 构造器
     *
     * @param accessToken
     */
    public WeChatApiImpl(String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    /**
     * 默认注册的HttpMessageConverter字符集为ISO-8859-1,而微信返回的是UTF-8,因此必须覆盖原来的方法
     *
     * @return
     */
    @Override
    protected List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        messageConverters.remove(0);    // 删除StringHttpMessageConverter
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return messageConverters;
    }

    /**
     * 获取微信用户信息
     *
     * @param openId
     * @return
     */
    @Override
    public WeChatUserInfo getUserInfo(String openId) {
        String url = URL_GET_USER_INFO + openId;
        String response = getRestTemplate().getForObject(url, String.class);
        if (StringUtils.contains(response, "errcode")) {   // 如果响应中存在错误码则返回null
            log.warn("getUserInfo(openId = {}) error: {}", openId, response);
            return null;
        }
        WeChatUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(response, WeChatUserInfo.class);
        } catch (Exception e) {

        }
        return userInfo;
    }
}
