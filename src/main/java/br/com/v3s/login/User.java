package br.com.v3s.login;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class User {
	private String username;
    private String password;
    private List<String> roles;

    public User(String username, String password, String... roles) {
        this.username = username;
        this.password = password;
        this.roles = Arrays.asList(roles);
    }
}
