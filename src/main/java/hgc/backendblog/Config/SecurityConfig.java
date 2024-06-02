package hgc.backendblog.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hgc.backendblog.Jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private AuthenticationProvider authProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authRequest -> authRequest.requestMatchers(HttpMethod.GET).permitAll()
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/users/api/blogs/**").permitAll()
						.requestMatchers("/hgcBackendBlogs/users/api/blogs/**").authenticated()
						.requestMatchers("/hgcBackendBlogs/users/api/blogs/comments**").authenticated()
						.requestMatchers("/hgcBackendBlogs/users/outer/api/blogs/**").permitAll()
						//.requestMatchers("/hgcBackendBlogs/users/api").hasAuthority("ROLE_ADMIN")
						// .requestMatchers("/**").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(
						sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authProvider)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
	}
}
