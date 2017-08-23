package dk.magenta.datafordeler.cpr.records;

import dk.magenta.datafordeler.core.util.Equality;
import dk.magenta.datafordeler.cpr.data.CprEffect;

import java.time.OffsetDateTime;

public class Bitemporality {
    public OffsetDateTime registrationTime;
    public OffsetDateTime effectFrom;
    public boolean effectFromUncertain;
    public OffsetDateTime effectTo;
    public boolean effectToUncertain;

    public Bitemporality(OffsetDateTime registrationTime, OffsetDateTime effectFrom, boolean effectFromUncertain, OffsetDateTime effectTo, boolean effectToUncertain) {
        this.registrationTime = registrationTime;
        this.effectFrom = effectFrom;
        this.effectFromUncertain = effectFromUncertain;
        this.effectTo = effectTo;
        this.effectToUncertain = effectToUncertain;
    }

    public Bitemporality(OffsetDateTime registrationTime) {
        this(registrationTime, null, false, null, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bitemporality that = (Bitemporality) o;

        if (effectFromUncertain != that.effectFromUncertain) return false;
        if (effectToUncertain != that.effectToUncertain) return false;
        if (registrationTime != null ? !registrationTime.equals(that.registrationTime) : that.registrationTime != null)
            return false;
        if (effectFrom != null ? !effectFrom.equals(that.effectFrom) : that.effectFrom != null)
            return false;
        return effectTo != null ? effectTo.equals(that.effectTo) : that.effectTo == null;
    }

    @Override
    public int hashCode() {
        int result = registrationTime != null ? registrationTime.hashCode() : 0;
        result = 31 * result + (effectFrom != null ? effectFrom.hashCode() : 0);
        result = 31 * result + (effectFromUncertain ? 1 : 0);
        result = 31 * result + (effectTo != null ? effectTo.hashCode() : 0);
        result = 31 * result + (effectToUncertain ? 1 : 0);
        return result;
    }

    public boolean matches(OffsetDateTime registrationTime, CprEffect effect) {
        return Equality.equal(this.registrationTime, registrationTime) && effect.compareRange(this);
    }
}
