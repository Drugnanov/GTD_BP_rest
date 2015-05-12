/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Třída reprezentuje stav úkolu
 *
 * @author Sláma
 */
@Entity
@DiscriminatorValue("TaskState")
public class TaskState extends Type {

}
