package io.github.blankbro.securitycustomauthentication.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.blankbro.securitycustomauthentication.entity.UserEntity;
import io.github.blankbro.securitycustomauthentication.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        // 此处为了方便，数据库密码直接明文存储了，生产环境要弄成密文
        return new User(userEntity.getUsername(), passwordEncoder.encode(userEntity.getPassword()), grantedAuthorities);
    }

}
