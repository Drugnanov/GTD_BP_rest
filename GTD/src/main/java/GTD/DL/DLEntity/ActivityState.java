/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Třída reprezentuje stav činnosti
 *
 * @author Sláma
 */
@Entity
@DiscriminatorValue("ActivityState")
public class ActivityState extends Type {

    @Override
    public String toString() {
        return ActivityState.class.getSimpleName() + ": id=" + this.getId() + ", nazev=" + this.getTitle();
    }

}
