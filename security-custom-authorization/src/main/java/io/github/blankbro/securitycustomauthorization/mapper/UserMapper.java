package io.github.blankbro.securitycustomauthorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.blankbro.securitycustomauthorization.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserEntity> {
}
