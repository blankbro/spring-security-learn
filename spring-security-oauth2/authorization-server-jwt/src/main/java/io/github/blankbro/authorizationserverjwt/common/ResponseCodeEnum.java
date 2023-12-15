package io.github.blankbro.authorizationserverjwt.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    SUCCESS("2000", "success"),
    BAD_CREDENTIALS("4000", "Bad credentials"),
    ERROR("5000", "error"),
    ;


    private String code;
    private String message;

}
