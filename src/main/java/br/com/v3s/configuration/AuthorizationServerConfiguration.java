package br.com.v3s.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService service;
	private final String jwtKeyValue;

	public AuthorizationServerConfiguration(AuthenticationManager authenticationManager, UserDetailsService service, @Value("${security.oauth2.authorization.jwt.key-value}") String jwtKeyValue) {
		this.authenticationManager = authenticationManager;
		this.service = service;
		this.jwtKeyValue = jwtKeyValue;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient("xpto")
		.secret("{bcrypt}$2a$10$PzBxVniJPYT1esPRJvAg7.enuY.u3CMsnkyeo2/U.IQ/YGhCJIPoe") //xpto123
		.authorizedGrantTypes("password", "refresh_token")
		.scopes("xpto:all", "xpto:read", "xpto:write", "xpto2:read", "xpto3:read")
		.accessTokenValiditySeconds(60*60) //1 hora
        .refreshTokenValiditySeconds(60*60*24); //1 dia
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.tokenStore(tokenStore())
		.accessTokenConverter(tokenConverter())
		.userDetailsService(service)
		.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
		.checkTokenAccess("permitAll()")
		.tokenKeyAccess("isAuthenticated()");
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter tokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(jwtKeyValue);
		return converter;
	}
}
