package user;

import java.util.Objects;

public class User {
    private final int userId;
    private final String login;
    private final String password;

    public User(String login,
                String password) {
        this.userId = -1;
        this.login = login;
        this.password = password;
    }

    public User(int userId,
                String login,
                String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof User))
            return false;

        User other = (User) obj;

        if (Objects.equals(this.userId, other.userId))
            return true;

        if (Objects.equals(this.login, other.login))
            return true;

        return Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.login, this.password);
    }
}
