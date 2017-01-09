package vsb.cec0094.bachelorProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name ="user")
public class UserRegistration {

    @Id
    @Column(name = "login")
    @Size(max = 100)
    private String login;

    @Column(name = "password")
    @NotNull
    @Size(max = 100)
    private String password;

    @JsonIgnore
    @Column(name = "enabled")
    private int enabled;

    public UserRegistration() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}