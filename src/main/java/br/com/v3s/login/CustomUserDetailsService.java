package br.com.v3s.login;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final LoginRepository repository;
	private final UserToResourceOwner converter;

	CustomUserDetailsService(LoginRepository repository, UserToResourceOwner converter) {
		this.repository = repository;
		this.converter = converter;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository
				.findUserByUsername(username)
				.map(converter::convert)
				.orElseThrow(() -> new UsernameNotFoundException("Cannot find user"));
	}
}
