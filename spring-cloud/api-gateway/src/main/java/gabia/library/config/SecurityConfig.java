package gabia.library.config;

import gabia.library.filter.JwtAuthenticationFilter;
import gabia.library.utils.jwt.JwtUtils;
import gabia.library.utils.role.AuthRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;
    private final JwtUtils jwtUtils;

    private static final String[] SWAGGER_AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/swagger-ui/**",
            "/user-service/v2/api-docs",
            "/book-service/v2/api-docs"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(POST, "/auth-service/auth/**")
                .antMatchers(HttpMethod.POST, "/user-service/users")
                .antMatchers(HttpMethod.GET, "/book-service/books/**")
                .antMatchers(SWAGGER_AUTH_WHITELIST);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setExposedHeaders(Arrays.asList(jwtConfig.getHeader(), "authority", "id"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtConfig, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/book-service/books").hasRole(AuthRole.USER.getAuthority())
                .antMatchers(HttpMethod.GET,"/user-service/admin").hasRole(AuthRole.ADMIN.getAuthority())
                .anyRequest().authenticated();
    }
}
