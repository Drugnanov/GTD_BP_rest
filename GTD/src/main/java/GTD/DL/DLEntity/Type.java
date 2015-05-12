/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLEntity;

import GTD.restapi.ApiConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Třída reprezentuje obecný typ (vlastnost která nabývá jen urč. konkrétních hodnot)
 *
 * @author Sláma
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "type",
        discriminatorType = DiscriminatorType.STRING
)
@Table(
        name = "type",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"type", "code"})}
)
public abstract class Type implements Serializable {

    public static final int MAX_LENGTH_CODE = 2;
    public static final int MAX_LENGTH_TITLE = 20;
    public static final int MAX_LENGTH_DESCRIPTION = 200;

    /**
     * Id typu
     */
    @Id
    @GeneratedValue
    private int id;

    /**
     * Kód typu
     */
    @Column(length = MAX_LENGTH_CODE, nullable = false)
    @JsonProperty(value = ApiConstants.STATE_CODE)
    private String code;

    /**
     * Název typu
     */
    @Column(length = MAX_LENGTH_TITLE, nullable = false)
    @JsonProperty(value = ApiConstants.STATE_TITLE)
    private String title;

    /**
     * Popis typu
     */
    @Column(length = MAX_LENGTH_DESCRIPTION)
    @JsonProperty(value = ApiConstants.STATE_DESCRIPTION)
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + this.id;
        hash = 43 * hash + Objects.hashCode(this.code);
        hash = 43 * hash + Objects.hashCode(this.title);
        hash = 43 * hash + Objects.hashCode(this.description);
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
        final Type other = (Type) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": id=" + id + ", code=" + code + ", name=" + title;
    }

    /**
     * Checks that lengths of certain attributes are not higher than their max value
     *
     * @return
     */
    public boolean checkLengths() {
        boolean codeOK = code == null || code.length() <= MAX_LENGTH_CODE;
        boolean titleOK = title == null || title.length() <= MAX_LENGTH_TITLE;
        boolean descriptionOK = description == null || description.length() <= MAX_LENGTH_DESCRIPTION;

        return codeOK && titleOK && descriptionOK;
    }

}
