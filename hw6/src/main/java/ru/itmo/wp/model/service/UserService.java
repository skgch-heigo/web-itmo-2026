package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {
    private static final String PASSWORD_SALT = "1174f9d7bc21e00e9a5fd0a783a44c9a9f73413c";

    private final UserRepository userRepository = new UserRepositoryImpl();

    private boolean isEmail(String email) {
        return email.matches("[a-zA-Z0-9_%+-]+@[a-zA-Z0-9._%+-]+");
    }

    public void validateRegistration(User user, String password) throws ValidationException {
        if (Strings.isNullOrEmpty(user.getLogin())) {
            throw new ValidationException("Login is required");
        }
        if (!user.getLogin().matches("[a-z]+")) {
            throw new ValidationException("Login can contain only lowercase Latin letters");
        }
        if (user.getLogin().length() > 8) {
            throw new ValidationException("Login can't be longer than 8 letters");
        }
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new ValidationException("Login is already in use");
        }

        if (Strings.isNullOrEmpty(user.getEmail())) {
            throw new ValidationException("Email is required");
        }
        if (!isEmail(user.getEmail())) {
            throw new ValidationException("Not a valid email");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("Email is already in use");
        }

        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        if (password.length() < 4) {
            throw new ValidationException("Password can't be shorter than 4 characters");
        }
        if (password.length() > 64) {
            throw new ValidationException("Password can't be longer than 64 characters");
        }
    }

    public void register(User user, String password) {
        userRepository.save(user, getPasswordSha(password));
    }

    public void validateEnter(String loginOrEmail, String password) throws ValidationException {
        if (Strings.isNullOrEmpty(loginOrEmail)) {
            throw new ValidationException("Login or email is required");
        }
        if (Strings.isNullOrEmpty(password)) {
            throw new ValidationException("Password is required");
        }
        String login = loginOrEmail;
        if (isEmail(loginOrEmail)) {
            User user = userRepository.findByEmail(loginOrEmail);
            if (user == null) {
                throw new ValidationException("Invalid email or password");
            }
            login = user.getLogin();
        }
        if (findByLoginOrEmailAndPassword(login, password) == null) {
            throw new ValidationException("Invalid login or password");
        }
    }

    public void validateAdmin(long id) throws ValidationException {
        if (!userRepository.find(id).getAdmin()) {
            throw new ValidationException("Not enough rights");
        }
    }


    public User findByLoginOrEmailAndPassword(String loginOrEmail, String password) {
        return userRepository.findByFieldAndPasswordSha(loginOrEmail, getPasswordSha(password));
    }

    public User find(long id) {
        return userRepository.find(id);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    private String getPasswordSha(String password) {
        return Hashing.sha256().hashBytes((PASSWORD_SALT + password).getBytes(StandardCharsets.UTF_8)).toString();
    }

    public long findCount() {
        return userRepository.findCount();
    }

    public void setAdmin(long id, boolean admin) {
        userRepository.setAdmin(id, admin);
    }
}
