package GTD.DL.DLEntity;

import GTD.DL.DLDAO.util.HashConverter;
import GTD.restapi.Serialization.PersonDeserializer;
import GTD.restapi.Serialization.PersonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static java.lang.System.currentTimeMillis;

/**
 * Class/ table for store autorization tokens of users Created by Drugnanov on 3.1.2015.
 *
 * @author Sláma
 */
@Entity
@Table(name = "person_token")
public class PersonToken {

    public static final int MAX_LENGTH_SECURITY_TOKEN = 50;
    public static final int MIN_LENGTH_SECURITY_TOKEN = 20;
    public static final int MAX_LENGTH_DISABLED_WHY = 255;

    @Value("${gtd.api.token.salt}")
    private static String tokenSalt;

    /**
     * unique id of the record
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * owner of the token
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonSerialize(using = PersonSerializer.class)
    @JsonDeserialize(using = PersonDeserializer.class)
    private Person person;

    /**
     * security token
     */
    @Column(name = "security_token", length = MAX_LENGTH_SECURITY_TOKEN, nullable = false)
    private String securityToken;

    /**
     * information why was token disabled
     */
    @Column(nullable = false)
    private Boolean disabled;

    /**
     * information if token is disabled
     */
    @Column(name = "disabled_why", length = MAX_LENGTH_DISABLED_WHY)
    private String disabledWhy;

    public PersonToken() {
    }

    public PersonToken(Person person, String securityToken, Boolean disabled, String disabledWhy) {
        this.person = person;
        this.securityToken = securityToken;
        this.disabled = disabled;
        this.disabledWhy = disabledWhy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person osoba) {
        this.person = osoba;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getDisabledWhy() {
        return disabledWhy;
    }

    public void setDisabledWhy(String disabledWhy) {
        this.disabledWhy = disabledWhy;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 99 * hash + Objects.hashCode(this.getPerson());
        hash = 99 * hash + Objects.hashCode(this.getSecurityToken());
        hash = 99 * hash + Objects.hashCode(this.getDisabled());
        hash = 99 * hash + Objects.hashCode(this.getDisabledWhy());
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
        final PersonToken other = (PersonToken) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.getPerson(), other.getPerson())) {
            return false;
        }
        if (!Objects.equals(this.getSecurityToken(), other.getSecurityToken())) {
            return false;
        }
        if (!Objects.equals(this.getDisabled(), other.getDisabled())) {
            return false;
        }
        if (!Objects.equals(this.getDisabledWhy(), other.getDisabledWhy())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersonToken: id=" + getId() + ", Osoba=" + getPerson().toString()
                + ", SecurityToken=" + getSecurityToken()
                + ", Disabled=" + getDisabled() + ",DisabledWhy=" + getDisabledWhy();
    }

    /**
     * Check integration of the token instance
     *
     * @return
     */
    public boolean checkValidate() {
        boolean osobaOK = (person != null && person.getId() > 0);
        boolean securityTokenOK = (securityToken != null
                && (securityToken.length() >= MIN_LENGTH_SECURITY_TOKEN
                && securityToken.length() <= MAX_LENGTH_SECURITY_TOKEN));
        boolean disabledWhyOK = (disabledWhy == null || disabledWhy.length() <= MAX_LENGTH_DISABLED_WHY);
        return osobaOK && securityTokenOK && disabledWhyOK;
    }

    private static PersonToken getEmptySecurityToken() {
        PersonToken personToken = new PersonToken();
        personToken.setPerson(null);
        personToken.setDisabled(false);
        personToken.setDisabledWhy("");
        personToken.setSecurityToken("");
        return personToken;
    }

    private String generateSecurityToken(Person person) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String stringToCrypt = person.toString() + PersonToken.tokenSalt + currentTimeMillis();
        String hash = HashConverter.md5(stringToCrypt);
        return hash;
    }

    public static PersonToken createPersonToken(Person person)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PersonToken personToken = PersonToken.getEmptySecurityToken();
        personToken.setPerson(person);
        personToken.setSecurityToken(personToken.generateSecurityToken(person));
        return personToken;
    }
}
