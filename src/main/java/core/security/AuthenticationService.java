package core.security;

import core.model.exception.AuthenticationException;

public interface AuthenticationService<T> {
    T login(String login, String password) throws AuthenticationException;
}
