package io.github.blankbro.securityoauth2authorizationserverjwt.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonResponse {
    private String code;
    private String message;
    private Object data;
}
