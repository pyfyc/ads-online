package com.skypro.adsonline.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.skypro.adsonline.constant.SecurityConstantsTest.*;

@TestConfiguration
public class InMemoryUserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService(@Qualifier(value = "userDetails") UserDetails user) {
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public UserDetails userDetails(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(SECURITY_USER_NAME)
                .password(SECURITY_USER_PASSWORD)
                .passwordEncoder((plainText) -> passwordEncoder.encode(plainText))
                .roles(SECURITY_USER_ROLE)
                .build();
    }
}
