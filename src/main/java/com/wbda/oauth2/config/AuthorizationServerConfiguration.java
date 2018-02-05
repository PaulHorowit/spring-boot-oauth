package com.wbda.oauth2.config;

import com.wbda.oauth2.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * 认证授权服务端
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
    @Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtAccessTokenConverter jwtTokenConverter;

	@Autowired
	private DataSource dataSource;

	@Autowired
	PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	    endpoints.authenticationManager(this.authenticationManager); //注入manager可以开启password模式
		endpoints.accessTokenConverter(this.jwtTokenConverter);
		endpoints.tokenStore(tokenStore());
		endpoints.userDetailsService(userDetailsService);  // refresh_token需要使用这个接口重新读取用户信息，注意：不会对用户密码进行验证
        //endpoints.pathMapping("/oauth/token", "/getToken"); //修改默认url

	}

	/* 这里主要是客户端模式相关配置 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		//开启这个才会使用ClientCredentialsTokenEndpointFilter，才允许把参数直接放在请求列表
	    oauthServer.allowFormAuthenticationForClients();
	    //oauthServer.addTokenEndpointAuthenticationFilter(customClientCredentialsTokenEndpointFilter);
	    //oauthServer.passwordEncoder(new StandardPasswordEncoder()); //可以在这里设置客户端模式使用的密码加密器
		//oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')");
		//oauthServer.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
        //oauthServer.checkTokenAccess("permitAll()");
	}

	/*
	 * 配置客户端信息
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);   //.passwordEncoder(passwordEncoder); //这样加入加密器不生效
	}

	/**
	 * token store
	 */
	@Bean
	public TokenStore tokenStore() {
		TokenStore tokenStore = new JwtTokenStore(this.jwtTokenConverter);
		return tokenStore;
	}
}
