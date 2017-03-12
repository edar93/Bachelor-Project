package vsb.cec0094.bachelorProject.models;

import javax.persistence.*;

@Entity
@Table(name="User_roles")
public class UserRole {

    @Id
    private int id;

//    @ManyToOne//(fetch = FetchType.EAGER)
//    @JoinColumn(name = "userRoleList")
//    private AdministrationUser administrationUser;

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
