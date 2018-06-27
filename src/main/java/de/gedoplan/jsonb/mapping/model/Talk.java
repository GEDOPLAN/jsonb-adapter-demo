package de.gedoplan.jsonb.mapping.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author GEDOPLAN, Dominik Mathmann
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = Talk.TABLE_NAME)
public class Talk extends JPAEntity {

    public static final String TABLE_NAME = "GEDO_TALK";

    @NotNull
    @Size(min = 5)
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    //@JsonbTransient //> simple option
    //@JsonbTypeAdapter() // > alternative, but won't work with generic type adapter
    private List<Speaker> speakers = new ArrayList<>();

    @NotNull
    private TalkType talkType;

    @Min(15)
    private Integer duration;

    public Talk(String title, TalkType talkType, int duration, Speaker... speakers) {
        this.title = title;
        this.talkType = talkType;
        this.duration = duration;
        this.speakers = new ArrayList<>();
        for (Speaker speaker : speakers) {
            this.speakers.add(speaker);
        }
    }

    protected Talk() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Speaker> getSpeakers() {
        return this.speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }

    public TalkType getTalkType() {
        return this.talkType;
    }

    public void setTalkType(TalkType talkType) {
        this.talkType = talkType;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
