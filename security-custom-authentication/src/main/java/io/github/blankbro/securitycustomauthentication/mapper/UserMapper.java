package io.github.blankbro.securitycustomauthentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.blankbro.securitycustomauthentication.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
}
