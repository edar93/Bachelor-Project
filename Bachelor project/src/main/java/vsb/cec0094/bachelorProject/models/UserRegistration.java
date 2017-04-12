package vsb.cec0094.bachelorProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "player")
@Immutable
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
