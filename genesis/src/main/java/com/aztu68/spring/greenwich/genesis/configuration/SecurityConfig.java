package com.aztu68.spring.greenwich.genesis.configuration;

import com.aztu68.spring.greenwich.genesis.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").access("hasRole('READER')")
                .antMatchers("/actuator/**").hasRole("READER")
                .antMatchers("/**").permitAll();

        http.formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (username) ->
                readerRepository.findById(username).orElseThrow(() -> {
                    throw new UsernameNotFoundException(
                            String.format("User %s not found.", username));
                });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
