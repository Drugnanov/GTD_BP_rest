package GTD.DL.DLEntity;

import GTD.restapi.Serialization.DateSerializer;
import GTD.restapi.Serialization.PersonDeserializer;
import GTD.restapi.Serialization.PersonSerializer;
import GTD.restapi.Serialization.ProjectDeserializer;
import GTD.restapi.Serialization.ProjectSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * Třída predstavující úkol - realizovatelnou akci.
 *
 * @author Sláma
 */
@Entity
@Table(name = "task")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task extends Action {

    /**
     * Projekt úkolu.
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonSerialize(using = ProjectSerializer.class)
    @JsonDeserialize(using = ProjectDeserializer.class)
    private Project project;
    /**
     * Tvůrce úkolu (může se lišit od vlastníka - což je přiřazená osoba)
     */
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    @JsonSerialize(using = PersonSerializer.class)
    @JsonDeserialize(using = PersonDeserializer.class)
    private Person creator;
    /**
     * Záznam o úkolu v kalendáři.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calendar_id")
    @JsonSerialize(using = DateSerializer.class)
    private Interval calendar;
    /**
     * Context úkolu.
     */
    @ManyToOne
    @JoinColumn(name = "context_id")
    private Context context;

    /**
     * Stav úkolu
     */
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private TaskState state;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Note> notes = new ArrayList<Note>();

    @Column(name = "facebook_id", nullable = true)
    private String facebookStringID;

    @Column(name = "facebook_publish_date", nullable = true)
    private Date facebookPublishDate;

    @Column(name = "google_id", nullable = true)
    private String googleStringID;

    @Column(name = "google_publish_date", nullable = true)
    private Date googlePublishDate;

    public Task(String title, String description, Person owner, Project project, Person creator, TaskState state) {
        super(title, description, owner);
        this.project = project;
        this.creator = creator;
        this.state = state;
    }

    /**
     * Konstruktor ukolu
     */
    public Task() {

    }

    /**
     * Vrati kalendat ukolu
     *
     * @return kalendar
     */
    public Interval getCalendar() {
        return calendar;
    }

    /**
     * Vrati kontext ukolu
     *
     * @return kontext
     */
    public Context getContext() {
        return context;
    }

    /**
     * Vrati projekt úkolu
     *
     * @return projekt
     */
    public Project getProject() {
        return project;
    }

    /**
     * Vrati tvurce úkolu
     *
     * @return tvurce
     */
    public Person getCreator() {
        return creator;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public void setCalendar(Interval calendar) {
        this.calendar = calendar;
    }

    public void setContext(Context context) {
        // TODO steklsim vyjimka pokud vlastnik ukolu != vlastnik kontextu
        this.context = context;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Date getFacebookPublishDate() {
        return facebookPublishDate;
    }

    public void setFacebookPublishDate(Date facebookPublishDate) {
        this.facebookPublishDate = facebookPublishDate;
    }

    public String getFacebookStringID() {
        return facebookStringID;
    }

    public void setFacebookStringID(String facebookStringID) {
        this.facebookStringID = facebookStringID;
    }

    public String getGoogleStringID() {
        return googleStringID;
    }

    public void setGoogleStringID(String googleStringID) {
        this.googleStringID = googleStringID;
    }

    public Date getGooglePublishDate() {
        return googlePublishDate;
    }

    public void setGooglePublishDate(Date googlePublishDate) {
        this.googlePublishDate = googlePublishDate;
    }

    @Override
    public String toString() {
        return "Ukol: id=" + this.getId() + ", nazev=" + this.getTitle();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.calendar);
        hash = 41 * hash + Objects.hashCode(this.context);
        hash = 41 * hash + Objects.hashCode(this.state);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        final Task other = (Task) obj;
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        if (!Objects.equals(this.creator, other.creator)) {
            return false;
        }
        if (!Objects.equals(this.calendar, other.calendar)) {
            return false;
        }
        if (!Objects.equals(this.context, other.context)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    /**
     * Updates this task based on not-null properties of given task
     *
     * @param task
     */
    @Override
    public void update(Action task) {
        super.update(task);
        Task t = (Task) task;
        //edited we can remove task from project hierarchy
        if (t.getProject() != null || t.getProject() == null) {
            setProject(t.getProject());
        }
        if (t.getCalendar() != null) {
            setCalendar(t.getCalendar());
        }
        //edited we can remove context
        if (t.getContext() != null || t.getContext() == null) {
            setContext(t.getContext());
        }
        if (t.getState() != null) {
            setState(t.getState());
        }
        if ((t.getNotes() != null && t.getNotes().size() > 0)
            || t.getNotes() == null || t.getNotes().size() == 0) {
            setNotes(t.getNotes());
        }
        if (t.getFacebookPublishDate() != null) {
            setFacebookPublishDate(t.getFacebookPublishDate());
        }
    }

    public void setTaskForNotes(Task task) {
        for (Note note : notes) {
            note.setTask(task);
        }
    }
}
