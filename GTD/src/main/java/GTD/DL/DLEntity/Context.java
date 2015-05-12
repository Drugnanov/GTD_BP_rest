package GTD.DL.DLEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Objects;

/**
 * Tato trída predstavuje kontext, ve kterém je úkol plnen. Urcuje v jaké souvislosti lze daný úkol vykonat (pr. práce,
 * doma). Každý uživatel má své kontexty.
 *
 * @author Sláma
 */
@Entity
@Table(name = "context", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"title", "owner_id"})})
public class Context extends Filter {

    /**
     * Kontruktor kontextu
     */
    public Context() {

    }

    public Context(String title, Person owner) {
        super(title, owner);
    }

    @Override
    public String toString() {
        return "Kontext: id=" + getId() + ", nazev=" + getTitle();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (!(obj instanceof Context)){
            return false;
        }
        Context contextToEq = (Context) obj;
        if (!Objects.equals(this.getOwner(), contextToEq.getOwner())) {
            return false;
        }
        if (!Objects.equals(this.getTitle(), contextToEq.getTitle())) {
            return false;
        }
        return true;
    }
}
