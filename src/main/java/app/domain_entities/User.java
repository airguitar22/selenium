package app.domain_entities;

/**
 * Created by Rich Mischler on 10/13/16.
 *
 * Description: Add users here.
 */
public enum User {

    USERNAME("administrator"),
    BLANK_USERNAME(""),
    BLANK_PASSWORD(""),
    INVALID_USERNAME("i.do.not.exist"),
    INVALID_PASSWORD("administrator", "jibberish1234");

    private final String user;
    private final String password;

    User(String userName, String password) {
        this.user = userName;
        this.password = password;
    }

    User(String userName) {
        this(userName, "hAllow33n!%");
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
