package vsb.cec0094.bachelorProject.models;

import javax.persistence.*;

@Entity
@Table(name="User_roles")
public class UserRole {

    @Id
    private int id;

    @Column(name = "user_login")
    private String user;

    @Column(name="user_role")
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
