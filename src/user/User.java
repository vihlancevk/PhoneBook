package user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIncludeProperties({"username", "password"})
public class User {
    private final String username;
    private final String password;

    @JsonCreator
    public User(@JsonProperty("username") String username,
                @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
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

        if (Objects.equals(this.username, other.username))
            return true;

        return Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.username, this.password);
    }
}
