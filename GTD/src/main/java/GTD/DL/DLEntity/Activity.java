package GTD.DL.DLEntity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Trída predstavuje surovou cinnost tak, jak ji uživatel vymyslí, bez dalšího clenení - je pripravena na prevedení do
 * úkolu nebo projektu.
 *
 * @author Sláma
 */
@Entity
@Table(name = "activity")
public class Activity extends Action {

    /**
     * stav činnosti
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private ActivityState state;

    public Activity() {

    }

    /**
     *
     * @param nazev
     * @param popis
     * @param vlastnik
     * @param state
     */
    public Activity(String nazev, String popis, Person vlastnik, ActivityState state) {
        super(nazev, popis, vlastnik);
        this.state = state;
    }

    public ActivityState getState() {
        return state;
    }

    public void setState(ActivityState stav) {
        this.state = stav;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.state);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        final Activity other = (Activity) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cinnost: id=" + getId() + ", nazev=" + getTitle();
    }

}
