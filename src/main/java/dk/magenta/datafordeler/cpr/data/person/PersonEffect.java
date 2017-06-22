package dk.magenta.datafordeler.cpr.data.person;

import dk.magenta.datafordeler.core.database.Effect;
import dk.magenta.datafordeler.cpr.data.person.data.PersonBaseData;

import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalAccessor;

/**
 * Created by lars on 16-05-17.
 */
@javax.persistence.Entity
@Table(name="cpr_person_effect")
public class PersonEffect extends Effect<PersonRegistration, PersonEffect, PersonBaseData> {

    private boolean uncertainFrom;

    private boolean uncertainTo;

    public PersonEffect() {
    }

    public PersonEffect(PersonRegistration registration, OffsetDateTime effectFrom, OffsetDateTime effectTo) {
        super(registration, effectFrom, effectTo);
    }

    public PersonEffect(PersonRegistration registration, TemporalAccessor effectFrom, TemporalAccessor effectTo) {
        super(registration, effectFrom, effectTo);
    }

    public PersonEffect(PersonRegistration registration, String effectFrom, String effectTo) {
        super(registration, effectFrom, effectTo);
    }

    public boolean isUncertainFrom() {
        return this.uncertainFrom;
    }

    public void setUncertainFrom(boolean uncertainFrom) {
        this.uncertainFrom = uncertainFrom;
    }

    public boolean isUncertainTo() {
        return this.uncertainTo;
    }

    public void setUncertainTo(boolean uncertainTo) {
        this.uncertainTo = uncertainTo;
    }

    public void setRegistration(PersonRegistration registration) {
        super.setRegistration(registration);
    }
}
