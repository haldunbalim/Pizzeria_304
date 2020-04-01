package DataSource;

public enum AuthStatus {
    // Auth failed means there is no user with such username and pwd
    AUTH_FAILED,
    AUTH_SUCCESSFUL,
    NEW_REGISTRATION,
    CONNECTION_ERROR
}
