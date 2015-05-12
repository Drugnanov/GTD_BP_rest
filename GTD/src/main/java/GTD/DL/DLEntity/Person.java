package GTD.DL.DLEntity;

import GTD.restapi.ApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 * Trída predstavuje osobu, která je registrována v systému GTD.
 *
 * @author Sláma
 *
 */
@Entity
@Table(name = "person")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    public static final int MAX_LENGTH_FIRST_NAME = 20;
    public static final int MAX_LENGTH_LAST_NAME = 20;
    public static final int MAX_LENGTH_LOGIN = 20;
    public static final int MAX_LENGTH_PASSWORD = 50;

    /**
     * unikátní id v celém systému
     */
    @Id
    @GeneratedValue
    private int id;
    /**
     * Křestní jméno uživatele.
     */
    @Column(length = MAX_LENGTH_FIRST_NAME, nullable = false, name = "name")
    private String name;

    /**
     * Password.
     */
    @Column(length = MAX_LENGTH_PASSWORD, nullable = false)
    private String password;

    /**
     * Password in no encrypt version .
     */
    @Transient
    private String passwordRaw;

    /**
     * Has user right to generate new token.
     */
    @Column(nullable = false, name = "right_generate_token")
    @JsonIgnore
    private Boolean rightGenerateToken;

    /**
     * Kontakty na uživatele - od každého typu max. 1 (v budoucích iteracích se budou požívat pro různé notifikace).
     */
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PersonToken> tokens;

    /**
     * Kontakty na uživatele - od každého typu max. 1 (v budoucích iteracích se budou požívat pro různé notifikace).
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contact> contacts;
    /**
     * uživatelské jméno, unikátní v celém systému
     */
    @Column(length = MAX_LENGTH_LOGIN, nullable = false, unique = true, name = "login")
    private String username;

    /**
     * Příjmení uživatele.
     */
    @Column(length = MAX_LENGTH_LAST_NAME, nullable = false, name = "surname")
    private String surname;

    /**
     * state osoby
     */
    @ManyToOne
    @JoinColumn(nullable = false, name = "state_id")
    @JsonProperty(value = ApiConstants.STATE)
    private PersonState state;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projects;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Context> contexts;

    /**
     * Konstruktor osoby
     */
    public Person() {

    }

    public Person(String name, String surname, String login, String password, PersonState state) {
        this.name = name;
        this.username = login;
        this.surname = surname;
        this.state = state;
        this.password = password;
    }

    /**
     * Vrátí id osoby
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
     * Vrátí jméno osoby
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Vrátí contacts ososby
     *
     * @return List<Kontakt>
     */
    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * Vrátí username osoby
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Vrátí příjmení osoby
     *
     * @return surename
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Vrátí jméno a příjmení
     *
     * @return jmeno_prijmeni
     */
    @Override
    public String toString() {
        return "Osoba: id=" + id + ", username=" + username + ", name=" + name + " " + surname;
    }

    public PersonState getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSurname(String surename) {
        this.surname = surename;
    }

    public void setState(PersonState state) {
        this.state = state;
    }

    public Boolean getRightGenerateToken() {
        return rightGenerateToken;
    }

    public void setRightGenerateToken(Boolean rightGenerateToken) {
        this.rightGenerateToken = rightGenerateToken;
    }

    public void addContact(Contact kontakt) {
        contacts.add(kontakt);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PersonToken> getTokens() {
        return tokens;
    }

    public void setTokens(List<PersonToken> tokens) {
        this.tokens = tokens;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordRaw() {
        return passwordRaw;
    }

    public void setPasswordRaw(String passwordRaw) {
        this.passwordRaw = passwordRaw;
    }

    public boolean removeContact(Contact contact) {
        return contacts.remove(contact);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.contacts);
        hash = 37 * hash + Objects.hashCode(this.username);
        hash = 37 * hash + Objects.hashCode(this.surname);
        hash = 37 * hash + Objects.hashCode(this.state);
        hash = 37 * hash + Objects.hashCode(this.password);
        hash = 37 * hash + Objects.hashCode(this.rightGenerateToken);
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
        final Person other = (Person) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.rightGenerateToken, other.rightGenerateToken)) {
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
        boolean nameOk = name == null || name.length() <= MAX_LENGTH_FIRST_NAME;
        boolean surnameOk = surname == null || surname.length() <= MAX_LENGTH_LAST_NAME;
        boolean loginOK = username != null && username.length() <= MAX_LENGTH_LOGIN;
        boolean passwordOk = password == null || (password != null && password.length() <= MAX_LENGTH_PASSWORD);

        return nameOk && surnameOk && loginOK && passwordOk;
    }

    //  public String getPasswordHash() throws UnsupportedEncodingException, NoSuchAlgorithmException {
//    return HashConverter.md5(getPassword());
//  }
    public boolean hasNewPassword() {
        if (getPasswordRaw() != null && getPasswordRaw().equals("")) {
            return true;
        }
        return false;
    }
}
