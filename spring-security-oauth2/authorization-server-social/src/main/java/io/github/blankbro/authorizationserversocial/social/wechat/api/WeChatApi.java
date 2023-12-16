package io.github.blankbro.authorizationserversocial.social.wechat.api;

import io.github.blankbro.authorizationserversocial.social.wechat.dto.WeChatUserInfo;

/**
 * 微信API调用接口
 */
public interface WeChatApi {

    /**
     * 获取微信用户信息
     *
     * @param openId
     * @return
     */
    WeChatUserInfo getUserInfo(String openId);
}
