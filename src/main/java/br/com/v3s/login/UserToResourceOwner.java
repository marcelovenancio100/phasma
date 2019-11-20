package br.com.v3s.login;

import org.springframework.stereotype.Component;

@Component
public class UserToResourceOwner {
	CustomUserDetails convert(User user) {
        return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
