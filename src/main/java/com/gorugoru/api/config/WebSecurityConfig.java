package com.gorugoru.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;

import com.gorugoru.api.config.handler.CustomAccessDeniedHandler;
import com.gorugoru.api.jwt.TokenAuthenticationService;
import com.gorugoru.api.service.UserService;

/**
 * spring security config with JWT
 * @author Administrator
 *
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserService userService;
    private final TokenAuthenticationService tokenAuthenticationService;

    public WebSecurityConfig() {
        super(true);
        this.userService = new UserService();
        tokenAuthenticationService = new TokenAuthenticationService("RUGOURGO###", userService);
    }
    
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//stateless(if stateless, not necessary CSRF)
    	//TODO 하지만 세션이 만들어진다.ㅠㅠ 스프링단에서 불가능한듯..
    	http
    		.csrf().disable()
    		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    		.securityContext().securityContextRepository(new NullSecurityContextRepository()).and()
    		// Custom Token based authentication based on the header previously given to the client
    		.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
    		.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll(); //allow CORS option calls
     	
    	//default
     	http
        	.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()).and()//only applies to authenticated user
     		.anonymous().and()
     		.servletApi().and()
     		.headers().cacheControl().and().and()
     		.authorizeRequests()
        	.antMatchers("/auth/**").permitAll()
        	.antMatchers("/rsnt/**").permitAll()
        	.antMatchers("/geo/**").permitAll()
        	.antMatchers("/admin").hasRole("ADMIN")
        	.anyRequest().authenticated();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserService userDetailsService() {
        return userService;
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return tokenAuthenticationService;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("admin").password("1234").roles("ADMIN")
//            .and()
//            .withUser("user").password("1234").roles("USER");
//    }
}