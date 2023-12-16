// package io.github.blankbro.authorizationserversocial.social.wechat.connect;
//
// import io.github.blankbro.authorizationserversocial.social.wechat.api.WeChatApi;
// import io.github.blankbro.authorizationserversocial.social.wechat.dto.WeChatUserInfo;
// import org.springframework.social.connect.ApiAdapter;
// import org.springframework.social.connect.ConnectionValues;
// import org.springframework.social.connect.UserProfile;
//
// /**
//  * 微信API适配器，将从微信API拿到的用户数据模型转换为Spring Social的标准用户数据模型。
//  *
//  * @author fanqi
//  */
// public class WeChatApiAdapter implements ApiAdapter<WeChatApi> {
//
//     private String openId;
//
//     public WeChatApiAdapter() {
//     }
//
//     public WeChatApiAdapter(String openId) {
//         this.openId = openId;
//     }
//
//     /**
//      * 用来测试当前的API是否可用
//      *
//      * @param api
//      * @return
//      */
//     @Override
//     public boolean test(WeChatApi api) {
//         return true;
//     }
//
//     /**
//      * 将微信的用户信息映射到ConnectionValues标准的数据化结构上
//      *
//      * @param api
//      * @param values
//      */
//     @Override
//     public void setConnectionValues(WeChatApi api, ConnectionValues values) {
//         WeChatUserInfo profile = api.getUserInfo(openId);
//         values.setProviderUserId(profile.getOpenid());
//         values.setDisplayName(profile.getNickname());
//         values.setImageUrl(profile.getHeadimgurl());
//     }
//
//     /**
//      * @param api
//      * @return
//      */
//     @Override
//     public UserProfile fetchUserProfile(WeChatApi api) {
//         return null;
//     }
//
//     /**
//      * @param api
//      * @param message
//      */
//     @Override
//     public void updateStatus(WeChatApi api, String message) {
//         // do nothing
//     }
//
// }
