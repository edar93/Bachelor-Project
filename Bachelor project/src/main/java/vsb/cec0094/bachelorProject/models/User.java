package vsb.cec0094.bachelorProject.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by User on 19. 12. 2016.
 */

@Entity
@Table(name ="user")
public class User {

    @Id
    @Column(name = "login")
    @Size(max = 100)
    private String login;


    @Column(name = "password")
    @NotNull
    @Size(max = 100)
    private String password;

    @Column(name = "enabled")
    private int enabled;

    public User() {
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
