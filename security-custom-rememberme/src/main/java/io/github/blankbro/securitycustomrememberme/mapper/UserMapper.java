package io.github.blankbro.securitycustomrememberme.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.blankbro.securitycustomrememberme.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
}
