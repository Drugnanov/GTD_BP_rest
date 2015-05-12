package GTD.DL.DLEntity;

import GTD.restapi.Serialization.PersonDeserializer;
import GTD.restapi.Serialization.PersonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Tato trída predstavuje spolecný nadtyp pro trídy Cinnost, Úkol a Projekt.
 *
 * @author Sláma
 */
@MappedSuperclass
public abstract class Action {

    public static final int MAX_LENGTH_TITLE = 100;
    public static final int MAX_LENGTH_DESCRIPTION = 1000;

    @Id
    @GeneratedValue
    private int id;
    /**
     * Název aktivity
     */
    @Column(name = "title", length = MAX_LENGTH_TITLE, nullable = false)
    private String title;
    /**
     * Popis aktivity
     */
    @Column(name = "description", length = MAX_LENGTH_DESCRIPTION, nullable = true)
    private String description;

    /**
     * Vlastník aktivity - má právo ji upravovat (změnit název/popis, smazat, v případě úkolu a projektu dokončit a
     * delegovat).
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonSerialize(using = PersonSerializer.class)
    @JsonDeserialize(using = PersonDeserializer.class)
    private Person owner;

    /**
     * Konstruktor aktivity
     */
    public Action() {

    }

    /**
     * Konstruktor aktivity
     *
     * @param title
     * @param description
     * @param owner
     */
    public Action(String title, String description, Person owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }

    /**
     * Vrátí id aktivity
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Vrátí název aktivity
     *
     * @return nazev
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Vrátí popis aktivity
     *
     * @return popis
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Vrátí id vlastníka aktivity
     *
     * @return vlastnik_id
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * Nastav vlastníka aktivity
     *
     * @param owner
     */
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    /**
     * Vrátí název a popis aktivity v jendom řetězci
     *
     * @return popis
     */
    @Override
    public String toString() {
        return "Aktivita: id=" + id + ", nazev=" + title;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.title);
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + Objects.hashCode(this.owner);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Action other = (Action) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        return true;
    }

    public void update(Action a) {
        if (a == null) {
            return;
        }
        if (getClass() != a.getClass()) {
            throw new IllegalArgumentException("Can't setUpdateAttr '" + getClass().getSimpleName() + "' with '" + a.
                    getClass().getSimpleName() + "' (they must be of same class)");
        }
        if (a.getTitle() != null) {
            setTitle(a.getTitle());
        }
        if (a.getDescription() != null) {
            setDescription(a.getDescription());
        }
        if (a.getOwner() != null) {
            setOwner(a.getOwner());
        }

    }

    /**
     * Checks that lengths of certain attributes are not higher than their max value
     *
     * @return
     */
    public boolean checkLengths() {
        boolean titleOK = title == null || title.length() <= MAX_LENGTH_TITLE;
        boolean descriptionOK = description == null || description.length() <= MAX_LENGTH_TITLE;
        return titleOK && descriptionOK;
    }

}
