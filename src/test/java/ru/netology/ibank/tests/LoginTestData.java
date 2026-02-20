package ru.netology.ibank.tests;

public class LoginTestData {
    public boolean userExists;
    public String status;
    public boolean invalidLogin;
    public boolean invalidPassword;
    public boolean expectedSuccess;

    public LoginTestData(boolean userExists, String status,
                         boolean invalidLogin, boolean invalidPassword,
                         boolean expectedSuccess) {
        this.userExists = userExists;
        this.status = status;
        this.invalidLogin = invalidLogin;
        this.invalidPassword = invalidPassword;
        this.expectedSuccess = expectedSuccess;
    }

    @Override
    public String toString() {
        return String.format(
                "userExists=%s, status=%s, invalidLogin=%s, invalidPassword=%s, expected=%s",
                userExists, status, invalidLogin, invalidPassword, expectedSuccess
        );
    }
}