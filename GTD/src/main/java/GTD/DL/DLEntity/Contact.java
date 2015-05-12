package GTD.DL.DLEntity;

import GTD.restapi.ApiConstants;
import GTD.restapi.Serialization.PersonDeserializer;
import GTD.restapi.Serialization.PersonSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Trída predstavuje kontakt na osobu evidovanou v systému GTD.
 *
 * @author Sláma
 */
@Entity
@Table(name = "contact")
public class Contact {

    public static final int MAX_LENGTH_CONTACT = 100;

    @Id
    @GeneratedValue
    private int id;
    /**
     * Contact pro zasílání upozornění
     */
    @Column(length = MAX_LENGTH_CONTACT, nullable = false)
    private String contact;
    /**
     * typ spojení (email, telefon, ...)
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonProperty(value = ApiConstants.STATE)
    private ContactType type;

    /**
     * vlastník kontaktu
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonSerialize(using = PersonSerializer.class)
    @JsonDeserialize(using = PersonDeserializer.class)
    private Person owner;

    /**
     * Konstruktor kontaktu
     */
    public Contact() {

    }

    public Contact(String contact, ContactType type, Person owner) {
        this.contact = contact;
        this.type = type;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType typ) {
        this.type = typ;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person person) {
        this.owner = person;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.contact);
        hash = 59 * hash + Objects.hashCode(this.type);
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
        final Contact other = (Contact) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        return true;
    }

    /**
     * Checks that lengths of certain attributes are not higher than their max value
     *
     * @return
     */
    public boolean checkLengths() {
        return contact == null || contact.length() <= MAX_LENGTH_CONTACT;
    }

}
