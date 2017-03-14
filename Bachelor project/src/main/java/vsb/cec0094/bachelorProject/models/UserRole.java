package vsb.cec0094.bachelorProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="User_roles")
public class UserRole {

    @Id
    @JsonIgnore
    private int id;

    @Column(name="user_role")
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
