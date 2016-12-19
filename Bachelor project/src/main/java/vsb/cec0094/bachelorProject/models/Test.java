package vsb.cec0094.bachelorProject.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TEST_TABLE")
public class Test implements Serializable{

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "value")
    private String value;

    public Test() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
