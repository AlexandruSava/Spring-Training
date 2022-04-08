package org.elysian.spring.training.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.elysian.spring.training.config.Roles.ADMIN_ROLE;
import static org.elysian.spring.training.config.Roles.USER_ROLE;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                    .antMatchers("/store").permitAll()
                    .antMatchers("/section").permitAll()
                    .antMatchers("/product").permitAll()
                    .anyRequest().authenticated();

        httpSecurity.cors().and().csrf().disable();
        httpSecurity.httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("password123"))
                                .roles(ADMIN_ROLE)
                                .build();

        UserDetails user = User.builder()
                               .username("user")
                               .password(passwordEncoder.encode("password"))
                               .roles(USER_ROLE)
                               .build();

        return new InMemoryUserDetailsManager(
                user,
                admin
        );
    }

}
