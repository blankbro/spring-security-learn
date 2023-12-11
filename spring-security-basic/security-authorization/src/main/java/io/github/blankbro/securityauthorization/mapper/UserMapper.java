package io.github.blankbro.securityauthorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.blankbro.securityauthorization.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
}
