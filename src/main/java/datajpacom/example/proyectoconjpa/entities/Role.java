package datajpacom.example.proyectoconjpa.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})
public class Role implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String authoritY;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthoritY() {
        return authoritY;
    }

    public void setAuthoritY(String authoritY) {
        this.authoritY = authoritY;
    }

    
}
