package com.gorugoru.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gorugoru.api.config.handler.CustomAccessDeniedHandler;
import com.gorugoru.api.config.handler.CustomLogoutHandler;
import com.gorugoru.api.jwt.TokenAuthenticationService;
import com.gorugoru.api.service.UserService;

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
		web.ignoring().antMatchers("/resources/**").antMatchers("/auth/**");
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//http.csrf().disable();
        http
        	.exceptionHandling().and()
        	.anonymous().and()
        	.servletApi().and()
        	.headers().cacheControl().and().and()
        	.authorizeRequests()
        	.antMatchers("/auth/**").permitAll()
        	.antMatchers("/admin").hasRole("ADMIN")
        	.anyRequest().permitAll()//.authenticated()//.permitAll()
        	
        	//JWT do not need login & logout page
        	//.and().formLogin().loginPage("/auth/login").permitAll()
			//.and().logout().logoutUrl("/auth/logout").addLogoutHandler(new CustomLogoutHandler())
			//.logoutSuccessUrl("/") //TODO 리다이렉트필요??
			//.permitAll()
			
			.and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
			
			// Custom Token based authentication based on the header previously given to the client
            .and().addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
                    UsernamePasswordAuthenticationFilter.class);
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

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("1234").roles("ADMIN")
            .and()
            .withUser("user").password("1234").roles("USER");
    }
}