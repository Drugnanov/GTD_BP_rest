package GTD.DL.DLEntity;

import GTD.restapi.ApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Tato trída predstavuje spolecný nadtyp pro trídy Kontext, Složka (není rešena) a Kategorie (není rešena).
 *
 * @author Sláma
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Filter {

    public static final int MAX_LENGTH_TITLE = 100;

    @Id
    @GeneratedValue
    private int id;
    /**
     * Název filtru (filtry jedné osoby musí mít různá jména).
     */
    @Column(length = MAX_LENGTH_TITLE, nullable = false)
    @JsonProperty(value = ApiConstants.FILTER_TITLE)
    private String title;

    /**
     * Vlastník filtru (filtry má každý uživatel své)
     */
//  @JsonSerialize(using = PersonSerializer.class)
//  @JsonDeserialize(using = PersonDeserializer.class)
//  @JsonProperty(value = ApiConstants.FILTER_OWNER)
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Person owner;

    /**
     * Konstruktor filtru
     */
    public Filter() {

    }

    public Filter(String title, Person owner) {
        this.title = title;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Nastavi id a nazev filtru
     *
     * @param id
     * @param nazev nazev
     */
    public void setFiltr(int id, String nazev) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String nazev) {
        this.title = nazev;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person vlastnik) {
        this.owner = vlastnik;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.title);
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
        final Filter other = (Filter) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        return true;
    }

    /**
     * Updates this filter based on not-null properties of another filter
     *
     * @param other
     */
    public void update(Filter other) {
        if (other == null) {
            return;
        }

        if (getClass() != other.getClass()) {
            throw new IllegalArgumentException(
                    "Can't setUpdateAttr '" + getClass().getSimpleName()
                            + "' with '" + other.getClass().getSimpleName()
                            + "' (they must be of same class)");
        }
        if (other.getTitle() != null) {
            setTitle(other.getTitle());
        }
    }

    /**
     * Checks that lengths of certain attributes are not higher than their max value
     *
     * @return
     */
    public boolean checkLengths() {
        return title == null || title.length() <= MAX_LENGTH_TITLE;
    }

}
