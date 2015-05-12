/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLInterfaces;

/**
 *
 * @author slama
 * @param <T>
 */
public interface IDAOGeneric<T> {

    void create(T entity);

    void update(T entity);

    void delete(T entity);

}
