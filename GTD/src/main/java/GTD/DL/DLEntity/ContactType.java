/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Třída reprezentuje typ kontaktu
 *
 * @author Sláma
 */
@Entity
@DiscriminatorValue("ContactType")
public class ContactType extends Type {

}
