package gabia.library.config;

import gabia.library.filter.JwtAuthenticationFilter;
import gabia.library.utils.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtConfig, jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/book-service/books").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/user-service/admin").hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
