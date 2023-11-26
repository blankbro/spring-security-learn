package io.github.blankbro.securitycustomauthorization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("t_user")
@Data
public class UserEntity {
    private Integer id;
    private String username;
    private String password;
}
