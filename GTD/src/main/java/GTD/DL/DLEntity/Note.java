package GTD.DL.DLEntity;

import GTD.restapi.ApiConstants;
import GTD.restapi.Serialization.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Sláma;
 */
@Entity
@Table(name = "note")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Note {

    private static final int MAX_LENGTH_TEXT = 65535;

    @Id
    @GeneratedValue
    private int id;

    /**
     * Podpoznamky.
     */
    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<Note> notes;

    /**
     *
     */
    @Column(name = "orderNote", nullable = false, columnDefinition = "int default 1")
    private Integer order;

    /**
     * Nadřízená poznamka
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    @JsonSerialize(using = NoteSerializer.class)
    @JsonDeserialize(using = NoteDeserializer.class)
    @JsonProperty(value = ApiConstants.NOTE_PARENT)
    private Note parent;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = true)
    @JsonSerialize(using = ProjectSerializer.class)
    @JsonDeserialize(using = ProjectDeserializer.class)
    @JsonProperty(value = ApiConstants.NOTE_PARENT_PROJECT)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = true)
    @JsonSerialize(using = TaskSerializer.class)
    @JsonDeserialize(using = TaskDeserializer.class)
    @JsonProperty(value = ApiConstants.NOTE_PARENT_TASK)
    private Task task;

    /**
     * Text of note
     */
    @Column(name = "textNote", length = MAX_LENGTH_TEXT, nullable = false)
    private String text;

    /**
     * state of note
     */
    @ManyToOne
    @JoinColumn(name = "stav_id", nullable = false)
    @JsonProperty(value = ApiConstants.STATE)
    private NoteState state;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date edited;

    /**
     * Konstruktor projektu
     */
    public Note() {
    }

    public boolean checkLengths() {
        boolean testOK = text != null && text.length() <= MAX_LENGTH_TEXT;
//    boolean orderOK = order != null && order > 0;
//    boolean referenceOK = project != null || task != null;

        return testOK; //&& orderOK && referenceOK;
    }

    public void setUpdateAttr(Note oldNote) {
        if (getState() == null) {
            setState(oldNote.getState());
        }
        setEdited(new Date());
    }

    public void setCreateAttr(Note note) {
        setCreated(new Date());
        setEdited(new Date());
        setOrder(1);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.parent);
        hash = 37 * hash + Objects.hashCode(this.order);
        hash = 37 * hash + Objects.hashCode(this.project);
        hash = 37 * hash + Objects.hashCode(this.task);
        hash = 37 * hash + Objects.hashCode(this.text);
        hash = 37 * hash + Objects.hashCode(this.state);
        hash = 37 * hash + Objects.hashCode(this.created);
        hash = 37 * hash + Objects.hashCode(this.edited);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        final Note other = (Note) obj;
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        if (!Objects.equals(this.order, other.order)) {
            return false;
        }
        if (!Objects.equals(this.project, other.project)) {
            return false;
        }
        if (!Objects.equals(this.task, other.task)) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.created, other.created)) {
            return false;
        }
        if (!Objects.equals(this.edited, other.edited)) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Note getParent() {
        return parent;
    }

    public void setParent(Note parent) {
        this.parent = parent;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NoteState getState() {
        return state;
    }

    public void setState(NoteState stav) {
        this.state = stav;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

}
