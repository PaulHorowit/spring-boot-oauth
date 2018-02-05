package com.wbda.oauth2.config;

import com.wbda.oauth2.service.CustomUserDetails;
import com.wbda.oauth2.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.userDetailsService(userDetailsService) //配置用户来源于数据库
		//	.passwordEncoder(passwordEncoder);	//用户账号密码加密器

        //这个provider将添加到这个authenticationManagerBean底下,用于用户名密码校验
        //注释掉上面的两行，那么将不使用默认的用户名密码校验
        auth.authenticationProvider(customDaoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().requestMatchers().antMatchers(HttpMethod.OPTIONS) //允许http options
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/oauth/token").fullyAuthenticated()
				.and().authorizeRequests().antMatchers("/url/*").authenticated();
//        http.csrf().disable()
//                .authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("/oauth/authorize").hasAuthority("ROLE_USER") //授权码模式使用，这里的权限居然是用户权限而不是客户端权限
//                .and().authorizeRequests().anyRequest().authenticated();
//
//
//        http.httpBasic();
        //http.addFilterAfter(customClientCredentialsTokenEndpointFilter, BasicAuthenticationFilter.class);
        //ClientCredentialsTokenEndpointFilter filter = new ClientCredentialsTokenEndpointFilter();
        //filter.setAuthenticationManager(authenticationManagerBean());
       // http.addFilterBefore(new ClientCredentialsTokenEndpointFilter(), FilterSecurityInterceptor.class);
	}

	@Autowired
    CustomUserDetails customUserDetails;

	@Bean
    public CustomDaoAuthenticationProvider customDaoAuthenticationProvider() {
	    CustomDaoAuthenticationProvider provider = new CustomDaoAuthenticationProvider();
	    provider.setUserDetailsService(customUserDetails);
	    provider.setPasswordEncoder(passwordEncoder);
	    return  provider;
    }

	/* 导出AuthenticationManager使其成为bean */
	@Override
	@Bean(name="authenticationManagerBean")
	public AuthenticationManager authenticationManagerBean() throws Exception {
		AuthenticationManager manager = super.authenticationManagerBean();

		return manager;
	}

}
