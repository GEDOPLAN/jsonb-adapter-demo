package de.gedoplan.jsonb.mapping.model;

import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author GEDOPLAN, Dominik Mathmann
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = Speaker.TABLE_NAME)
public class Speaker extends JPAEntity {

    public static final String TABLE_NAME = "GEDO_SPEAKER";
    
    private String firstname;

    private String lastname;

    @ManyToMany(mappedBy = "speakers", fetch = FetchType.EAGER)
    private List<Talk> talks;

    protected Speaker() {
    }

    public Speaker(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Talk> getTalks() {
        return talks;
    }

    public void setTalks(List<Talk> talks) {
        this.talks = talks;
    }

}
