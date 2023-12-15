package io.github.blankbro.securityoauth2authorizationserverjwt.common;

public class CommonResponseBuilder {

    public static CommonResponse buildByEnum(ResponseCodeEnum responseCodeEnum) {
        return new CommonResponse(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), null);
    }

    public static CommonResponse buildErrorResponse(String errorInfo) {
        return new CommonResponse(ResponseCodeEnum.ERROR.getCode(), errorInfo, null);
    }
}
