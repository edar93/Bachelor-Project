package vsb.cec0094.bachelorProject.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "players")
public class administrationUser {

    @Id
    @Column(name = "login")
    @Size(max = 100)
    private String login;

    @Column(name = "enabled")
    private int enabled;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "login")
    private List<UserRole> userRoleList;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("login", login)
                .append("enabled", enabled)
                .append("userRoleList", userRoleList)
                .toString();
    }
}
