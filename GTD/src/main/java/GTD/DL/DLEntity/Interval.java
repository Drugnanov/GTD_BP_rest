package GTD.DL.DLEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Trída predstavuje casový interval pro daný úkol.
 *
 * @author Sláma
 */
@Entity
@Table(name = "intervals")
public class Interval implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    /**
     * Casový pocátek úkolu. Minimální presnost jsou dny.
     */
    @Column(name = "from_date", nullable = false)
    private Date from;
    /**
     * Casový konec úkolu. Minimální presnost jsou dny.
     */
    @Column(name = "to_date", nullable = false)
    private Date to;

    /**
     * Konstruktor intevalu
     */
    public Interval() {

    }

    public Interval(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Vrátí datum do z intervalu
     *
     * @return from
     */
    public Date getFrom() {
        return from;
    }

    /**
     * Vrátí datum od z intervalu
     *
     * @return to
     */
    public Date getTo() {
        return to;
    }

    /**
     * Vrátí tru, pikud je interval nastaven
     *
     * @return
     */
    public boolean isSet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Nastav interval
     *
     * @param from
     * @param to to
     */
    public void setInterval(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + Objects.hashCode(this.from);
        hash = 29 * hash + Objects.hashCode(this.to);
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
        final Interval other = (Interval) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.from, other.from)) {
            return false;
        }
        if (!Objects.equals(this.to, other.to)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return from.toString() + " - " + to.toString();
    }

}
