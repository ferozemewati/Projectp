package in.fk.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sess ->
                    sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)       
            )
            .exceptionHandling(ex -> ex
            .authenticationEntryPoint(jwtAuthEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            
            .authorizeHttpRequests(auth -> auth

            	    .requestMatchers("/auth/login").permitAll()

            	    // ADMIN only
            	    .requestMatchers("/students/delete/**").hasAuthority("ROLE_ADMIN")

            	    // USER + ADMIN
            	    .requestMatchers("/students/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

            	    .anyRequest().authenticated()
            	)
            
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
        
    }
    
}