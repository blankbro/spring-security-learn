package io.github.blankbro.securitycustomlogin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.blankbro.securitycustomlogin.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
}
