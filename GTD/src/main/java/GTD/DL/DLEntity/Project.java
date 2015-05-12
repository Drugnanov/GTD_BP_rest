package GTD.DL.DLEntity;

import GTD.restapi.ApiConstants;
import GTD.restapi.Serialization.ProjectDeserializer;
import GTD.restapi.Serialization.ProjectSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * Třída predstavuje projekt - množinu souvisejících úkolu. Project muže krome úkolu obsahovat i další projekty (pocet
 * úrovní není omezen). Vlastník projektu může delegovat jeho úkoly a podprojekty (v 1.úrovni).
 *
 * @author Sláma
 *
 */
@Entity
@Table(name = "project")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends Action {

  /**
   * Podprojekty projektu.
   */
  @OneToMany(mappedBy = "parent")
  @JsonIgnore
  private List<Project> parents;
  /**
   * Rodič - nadřazený projekt.
   */
  @ManyToOne
//  @JoinColumn(nullable = false)
//  @JsonIgnore
//  @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
  @JsonSerialize(using = ProjectSerializer.class)
  @JsonDeserialize(using = ProjectDeserializer.class)
  @JsonProperty(value = ApiConstants.PROJECT_PARENT)
  private Project parent;
  /**
   * Skupina osob pracujících na projektu - slouží pro delegování aktivit v rámci projektu.
   */
  @ManyToMany
  @JsonIgnore
  private List<Person> group;
  /**
   * Úkoly projektu.
   */
  @OneToMany(mappedBy = "project", cascade = {CascadeType.ALL})
  @JsonIgnore
  private List<Task> tasks;

  /**
   * stav projektu
   */
  @ManyToOne
  @JoinColumn(nullable = false)
  @JsonProperty(value = ApiConstants.STATE)
  private ProjectState state;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Note> notes = new ArrayList<Note>();

  /**
   * Konstruktor projektu
   */
  public Project() {

  }

  public Project(String title, String description, Person owner, Project parent, ProjectState state) {
    super(title, description, owner);
    this.parent = parent;
    this.state = state;
  }

  /**
   * Pridej osobu do projektu
   *
   * @param person person
   */
  public void addOsoba(Person person) {
    group.add(person);
  }

  public boolean removeOsoba(Person person) {
    return group.remove(person);
  }

  /**
   * Pridej podprojekt do projektu
   *
   * @param project project
   */
  public void addProjekt(Project project) {
    parents.add(project);
  }

  public boolean removeProject(Project project) {
    return parents.remove(project);
  }

  /**
   * Pridej task do projektu
   *
   * @param task task
   */
  public void addTask(Task task) {
    tasks.add(task);
  }

  public boolean removeTask(Task task) {
    return tasks.remove(task);
  }

  /**
   * Vrátí podprojekty
   *
   * @return List<Projekt>
   */
  public List<Project> getParents() {
    return parents;
  }

  public void setParents(List<Project> projects) {
    this.parents = projects;
  }

  /**
   * Vrati id rodice projektu
   *
   * @return id
   */
  public Project getParent() {
    return parent;
  }

  public void setParent(Project rodic) {
    this.parent = rodic;
  }

  /**
   * Vrati skupinu v projektu
   *
   * @return List<Person>
   */
  public List<Person> getGroup() {
    return group;
  }

  public void setGroup(List<Person> group) {
    this.group = group;
  }

  /**
   * Vratí úkoly v projekty
   *
   * @returnList<Ukol>
   */
  public List<Task> getTasks() {
    return null;
  }

  public void setTasks(List<Task> ukoly) {
    this.tasks = ukoly;
  }

  /**
   * Vrátí název a popis projektu
   *
   * @return nazev_popis
   */
  @Override
  public String toString() {
    return "";
  }

  public ProjectState getState() {
    return state;
  }

  public void setState(ProjectState stav) {
    this.state = stav;
  }

  public List<Note> getNotes() {
    return notes;
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.group);
    hash = 37 * hash + Objects.hashCode(this.state);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }

    final Project other = (Project) obj;
    if (!Objects.equals(this.parents, other.parents)) {
      return false;
    }
    if (!Objects.equals(this.parent, other.parent)) {
      return false;
    }
    if (!Objects.equals(this.group, other.group)) {
      return false;
    }
    if (!Objects.equals(this.tasks, other.tasks)) {
      return false;
    }
    if (!Objects.equals(this.state, other.state)) {
      return false;
    }
    return true;
  }

  /**
   * Updates this project based on not-null properties of another project (doesn't setUpdateAttr collection
   * properties)
   *
   * @param project
   */
  @Override
  public void update(Action project) {
    super.update(project);
    Project p = (Project) project;
    if (p.getParent() != null || p.getParent() == null) {
      setParent(p.getParent());
    }
    if (p.getState() != null) {
      setState(p.getState());
    }
    if ((p.getNotes() != null && p.getNotes().size() > 0)
        || p.getNotes() == null || p.getNotes().size() == 0) {
      setNotes(p.getNotes());
    }
  }

  public void setProjectForNotes(Project prj) {
    for (Note note : notes) {
      note.setProject(prj);
    }
  }
}
