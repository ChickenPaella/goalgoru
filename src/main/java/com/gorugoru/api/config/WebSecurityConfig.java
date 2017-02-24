package com.gorugoru.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.gorugoru.api.config.handler.CustomAccessDeniedHandler;
import com.gorugoru.api.config.handler.CustomLogoutHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/auth/**");
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/admin").hasRole("ADMIN")
        	.antMatchers("/user/**").authenticated()
        	.anyRequest().permitAll()
        	
        	//only kakao
        	.and().formLogin().loginPage("/auth/kakao").permitAll()//.loginPage("/auth/login")
			.and().logout().logoutUrl("/auth/logout").addLogoutHandler(new CustomLogoutHandler())
			.logoutSuccessUrl("/") //TODO 리다이렉트필요??
			.permitAll()
			
			.and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("1234").roles("ADMIN")
            .and()
            .withUser("user").password("1234").roles("USER");
    }
}