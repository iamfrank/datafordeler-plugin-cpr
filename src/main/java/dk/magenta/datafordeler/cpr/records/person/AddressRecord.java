package dk.magenta.datafordeler.cpr.records.person;

import dk.magenta.datafordeler.core.database.QueryManager;
import dk.magenta.datafordeler.core.exception.ParseException;
import dk.magenta.datafordeler.cpr.data.person.PersonEffect;
import dk.magenta.datafordeler.cpr.data.person.data.PersonBaseData;
import org.hibernate.Session;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lars on 22-06-17.
 */
public class AddressRecord extends PersonDataRecord {
    public AddressRecord(String line) throws ParseException {
        super(line);
        this.obtain("start_mynkod-personbolig", 14, 4);
        this.obtain("adr_ts", 18, 12);
        this.obtain("komkod", 30, 4);
        this.obtain("vejkod", 34, 4);
        this.obtain("husnr", 38, 4);
        this.obtain("etage", 42, 2);
        this.obtain("sidedoer", 44, 4);
        this.obtain("bnr", 48, 4);
        this.obtain("convn", 52, 34);
        this.obtain("convn_ts", 86, 12);
        this.obtain("tilflydto", 98, 12);
        this.obtain("tilflydto_umrk", 110, 1);
        this.obtain("tilfra_mynkod", 111, 4);
        this.obtain("tilfra_ts", 115, 12);
        this.obtain("tilflykomdto", 127, 12);
        this.obtain("tilflykomdt_umrk", 139, 1);
        this.obtain("fraflykomkod", 140, 4);
        this.obtain("fraflykomdto", 144, 12);
        this.obtain("fraflykomdt_umrk", 156, 1);
        this.obtain("adrtxttype", 157, 4);
        this.obtain("start_mynkod-adrtxt", 161, 4);
        this.obtain("adr1-supladr", 165, 34);
        this.obtain("adr2-supladr", 199, 34);
        this.obtain("adr3-supladr", 233, 34);
        this.obtain("adr4-supladr", 267, 34);
        this.obtain("adr5-supladr", 301, 34);
        this.obtain("start_dt-adrtxt", 335, 10);
        this.obtain("slet_dt-adrtxt", 345, 10);
    }

    @Override
    public String getRecordType() {
        return RECORDTYPE_DOMESTIC_ADDRESS;
    }

    @Override
    public void populateBaseData(PersonBaseData data, PersonEffect effect, OffsetDateTime registrationTime, QueryManager queryManager, Session session) {
        if (registrationTime.equals(this.getOffsetDateTime("adr_ts")) && effect.compareRange(this.getOffsetDateTime("tilflydto"), this.getBoolean("tilflydto_umrk"), null, false)) {
            data.setAddress(
                this.getInt("start_mynkod-personbolig"),
                this.get("bnr"),
                null,
                this.getString("komkod", false),
                null,
                this.getString("vejkod", false),
                null,
                this.get("etage"),
                this.get("husnr"),
                null,
                null,
                this.get("sidedoer"),
                this.get("adr1-supladr"),
                this.get("adr2-supladr"),
                this.get("adr3-supladr"),
                this.get("adr4-supladr"),
                this.get("adr5-supladr"),
                this.getInt("adrtxttype"),
                this.getInt("start_mynkod-adrtxt")
            );
        }
        if (registrationTime.equals(this.getOffsetDateTime("convn_ts")) && effect.compareRange(null, false, null, false)) {
            data.setCoName(this.get("convn"));
        }
        if (registrationTime.equals(this.getOffsetDateTime("tilfra_ts")) && effect.compareRange(null, false, null, false)) {
            data.setMoveMunicipality(
                    this.getInt("tilfra_mynkod"),
                    this.getDateTime("tilflykomdto"),
                    this.getBoolean("tilflykomdt_umrk"),
                    this.getInt("fraflykomkod"),
                    this.getDateTime("fraflykomdto"),
                    this.getBoolean("fraflykomdt_umrk")
            );
        }
    }

    @Override
    public HashSet<OffsetDateTime> getRegistrationTimestamps() {
        HashSet<OffsetDateTime> timestamps = super.getRegistrationTimestamps();
        timestamps.add(this.getOffsetDateTime("adr_ts"));
        timestamps.add(this.getOffsetDateTime("convn_ts"));
        timestamps.add(this.getOffsetDateTime("tilfra_ts"));
        return timestamps;
    }

    @Override
    public Set<PersonEffect> getEffects() {
        HashSet<PersonEffect> effects = new HashSet<>();
        effects.add(new PersonEffect(null, this.getOffsetDateTime("tilflydto"), this.getMarking("tilflydto_umrk"), null, false));
        effects.add(new PersonEffect(null, null, false, null, false));
        return effects;
    }
}
