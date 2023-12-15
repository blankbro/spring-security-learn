package io.github.blankbro.authorizationserver;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

    @Test
    public void test202312081940() {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }
}
