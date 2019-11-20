package br.com.v3s.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {
	private static final Map<String, User> database = new HashMap<>();

	static {
		User user = new User("marcelo@email.com", "{bcrypt}$2a$10$VzSlrfJBA2CHVjrENZtyXe7xZ70p0gBYhDDcyPc8huczfGq33EXYa", "ROLE_USER"); // 1234
		database.put(user.getUsername(), user);
	}

	Optional<User> findUserByUsername(String username) {
		return Optional.ofNullable(database.get(username));
	}
}
