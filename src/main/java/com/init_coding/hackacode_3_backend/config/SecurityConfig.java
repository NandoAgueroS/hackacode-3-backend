package com.init_coding.hackacode_3_backend.config;

import com.init_coding.hackacode_3_backend.config.filter.JwtTokenValidator;
import com.init_coding.hackacode_3_backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers("/swagger-ui/**").permitAll();
                    http.requestMatchers("/v3/api-docs/**").permitAll();
                    http.requestMatchers("/swagger-ui.html").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/api/auth/verificar-token").authenticated();
                    http.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
                    //http.requestMatchers(HttpMethod.POST,"/api/auth/sign-up").hasAnyRole("ADMIN", "DIRECTOR");
                    http.requestMatchers("/api/medicos/**").hasAnyRole("ADMIN", "DIRECTOR", "RECEPCIONISTA");
                    http.requestMatchers("/api/pacientes/**").hasAnyRole("ADMIN", "DIRECTOR", "RECEPCIONISTA");
                    http.requestMatchers("/api/especialidades/**").hasAnyRole("ADMIN", "DIRECTOR", "RECEPCIONISTA");
                    http.requestMatchers("/api/consultas/**").hasAnyRole("ADMIN", "DIRECTOR", "RECEPCIONISTA");
                    http.requestMatchers("/api/servicios/**").hasAnyRole("ADMIN", "DIRECTOR", "RECEPCIONISTA");
                    http.requestMatchers("/api/pagos/**").hasRole("DIRECTOR");
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
