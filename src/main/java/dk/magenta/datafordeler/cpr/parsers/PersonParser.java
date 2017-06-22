package dk.magenta.datafordeler.cpr.parsers;

import dk.magenta.datafordeler.core.util.DoubleHashMap;
import dk.magenta.datafordeler.cpr.data.CprData;
import dk.magenta.datafordeler.cpr.data.person.data.PersonBaseData;
import dk.magenta.datafordeler.cpr.records.CprRecord;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashSet;


/**
 * Created by lars on 04-11-14.
 */
@Component
public class PersonParser extends CprSubParser {

    /*
    * Inner classes for parsed data
    * */

    public abstract class PersonDataRecord<B extends CprData> extends CprRecord {

        public static final String RECORDTYPE_PERSON = "001";
        public static final String RECORDTYPE_DOMESTIC_ADDRESS = "025";
        // TODO: Add one for each data type

        public PersonDataRecord(String line) throws ParseException {
            super(line);
            this.obtain("pnr", 4, 10);
        }

        protected int getTimestampStart() {
            return 21;
        }

        public int getCprNumber() {
            return Integer.parseInt(this.get("pnr"));
        }

        public HashSet<String> getTimestamps() {
            return new HashSet<>();
        }

        protected B getBaseDataItem(DoubleHashMap<String, String, B> data, String effectStart, String effectEnd) {
            effectStart = CprRecord.normalizeDate(effectStart);
            effectEnd = CprRecord.normalizeDate(effectEnd);
            B personBaseData = data.get(effectStart, effectEnd);
            if (personBaseData == null) {
                personBaseData = this.createEmptyBaseData();
                data.put(effectStart, effectEnd, personBaseData);
            }
            return personBaseData;
        }

        protected abstract B createEmptyBaseData();
    }

    public class PersonData extends PersonDataRecord<PersonBaseData> {
        public PersonData(String line) throws ParseException {
            super(line);
            this.obtain("pnrgaeld", 14, 10);
            this.obtain("status_ts", 24, 12);
            this.obtain("status", 36, 2);
            this.obtain("statushaenstart", 38, 12);
            this.obtain("statusdto_umrk", 50, 1);
            this.obtain("start_mynkod-person", 51, 4);
            this.obtain("start_ts-person", 55, 12);
            this.obtain("koen", 67, 1);
            this.obtain("foed_dt", 68, 10);
            this.obtain("foed_dt_umrk", 78, 1);
            this.obtain("foed_tm", 79, 8);
            this.obtain("foedsekvens", 87, 4);
            this.obtain("start_dt-person", 91, 10);
            this.obtain("start_dt_umrk-person", 101, 1);
            this.obtain("slut_dt-person", 102, 10);
            this.obtain("slut_dt_umrk-person", 112, 1);
            this.obtain("stilling_mynkod", 113, 4);
            this.obtain("stilling_ts", 117, 12);
            this.obtain("stilling", 129, 34);
            this.obtain("mor_ts", 163, 12);
            this.obtain("mor_mynkod", 175, 4);
            this.obtain("mor_dt", 179, 10);
            this.obtain("mor_dt_umrk", 189, 1);
            this.obtain("pnrmor", 190, 10);
            this.obtain("mor_foed_dt", 200, 10);
            this.obtain("mor_foed_dt_umrk", 210, 1);
            this.obtain("mornvn", 211, 34);
            this.obtain("mornvn_mrk", 245, 1);
            this.obtain("mor_dok_mynkod", 246, 4);
            this.obtain("mor_dok_ts", 250, 12);
            this.obtain("mor_dok", 262, 3);
            this.obtain("far_ts", 265, 12);
            this.obtain("far_mynkod", 277, 4);
            this.obtain("far_dt", 281, 10);
            this.obtain("far_dt_umrk", 291, 1);
            this.obtain("pnrfar", 292, 10);
            this.obtain("far_foed_dt", 302, 10);
            this.obtain("far_foed_dt_umrk", 312, 1);
            this.obtain("farnvn", 313, 34);
            this.obtain("farnvn_mrk", 347, 1);
            this.obtain("far_dok_mynkod", 348, 4);
            this.obtain("far_dok_ts", 352, 12);
            this.obtain("far_dok", 364, 3);
        }

        /**
         * Create a set of populated PersonBaseData objects, each with its own unique effect period
         * @param timestamp
         * @return
         */
        @Override
        public DoubleHashMap<String, String, PersonBaseData> getDataEffects(String timestamp) {
            DoubleHashMap<String, String, PersonBaseData> data = new DoubleHashMap<>();
            PersonBaseData personBaseData;

            if (timestamp.equals(this.get("status_ts"))) {
                personBaseData = this.getBaseDataItem(data, this.get("statushaenstart"), null);
                personBaseData.setStatus(this.get("status"));
            }

            if (timestamp.equals(this.get("mor_ts"))) {
                personBaseData = this.getBaseDataItem(data, null, null);
                personBaseData.setMother(
                        this.get("mornvn"),
                        this.getBoolean("mornvn_mrk"),
                        this.getInt("pnrmor"),
                        this.getDate("mor_foed_dt"),
                        this.getBoolean("mor_foed_dt_umrk"),
                        this.getInt("mor_mynkod")
                );
            }

            if (timestamp.equals(this.get("far_ts"))) {
                personBaseData = this.getBaseDataItem(data, this.get("far_dt"), null);
                personBaseData.setFather(
                        this.get("farnvn"),
                        this.getBoolean("farnvn_mrk"),
                        this.getInt("pnrfar"),
                        this.getDate("far_foed_dt"),
                        this.getBoolean("far_foed_dt_umrk"),
                        this.getInt("far_mynkod")
                );
            }

            if (timestamp.equals(this.get("mor_dok_ts"))) {
                personBaseData = this.getBaseDataItem(data, null, null);
                personBaseData.setMotherVerification(
                        this.getInt("mor_dok_mynkod"),
                        this.getBoolean("mor_dok")
                );
            }

            if (timestamp.equals(this.get("far_dok_ts"))) {
                personBaseData = this.getBaseDataItem(data, null, null);
                personBaseData.setFatherVerification(
                        this.getInt("far_dok_mynkod"),
                        this.getBoolean("far_dok")
                );
            }

            if (timestamp.equals(this.get("stilling_ts"))) {
                personBaseData = this.getBaseDataItem(data, null, null);
                personBaseData.setPosition(
                        this.getInt("stilling_mynkod"),
                        this.get("stilling")
                );
            }

            if (timestamp.equals(this.get("start_ts-person"))) {

                personBaseData = this.getBaseDataItem(data, this.get("start_dt-person"), null);
                personBaseData.setBirth(
                        LocalDateTime.of(this.getDate("foed_dt"), this.getTime("foed_tm")),
                        this.getBoolean("foed_dt_umrk"),
                        this.getInt("foedsekvens")
                );
                personBaseData.setCurrentCprNumber(this.getInt("pnrgaeld"));
                personBaseData.setGender(this.get("koen"));
                personBaseData.setStartAuthority(this.getInt("start_mynkod-person"));
            }

            return data;
        }

        @Override
        protected PersonBaseData createEmptyBaseData() {
            return new PersonBaseData();
        }

        @Override
        public String getRecordType() {
            return RECORDTYPE_PERSON;
        }

        @Override
        public HashSet<String> getTimestamps() {
            HashSet<String> timestamps = super.getTimestamps();
            timestamps.add(this.get("status_ts"));
            timestamps.add(this.get("mor_ts"));
            timestamps.add(this.get("stilling_ts"));
            timestamps.add(this.get("far_ts"));
            timestamps.add(this.get("mor_dok_ts"));
            timestamps.add(this.get("far_dok_ts"));
            return timestamps;
        }
    }

    public class AddressData extends PersonDataRecord<PersonBaseData> {
        public AddressData(String line) throws ParseException {
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
        protected PersonBaseData createEmptyBaseData() {
            return new PersonBaseData();
        }

        @Override
        public DoubleHashMap<String, String, PersonBaseData> getDataEffects(String timestamp) {
            DoubleHashMap<String, String, PersonBaseData> data = new DoubleHashMap<>();
            PersonBaseData personBaseData;
            if (timestamp.equals(this.get("adr_ts"))) {
                personBaseData = this.getBaseDataItem(data, this.get("tilflydto"), null);
                personBaseData.setAddress(
                        this.getInt("start_mynkod-personbolig"),
                        this.getInt("komkod"),
                        this.getInt("vejkod"),
                        this.get("husnr"),
                        this.get("etage"),
                        this.get("sidedoer"),
                        this.get("bnr"),
                        this.getInt("adrtxttype"),
                        this.getInt("start_mynkod-adrtxt"),
                        this.get("adr1-supladr"),
                        this.get("adr2-supladr"),
                        this.get("adr3-supladr"),
                        this.get("adr4-supladr"),
                        this.get("adr5-supladr")
                );
            }
            if (timestamp.equals(this.get("convn_ts"))) {
                personBaseData = this.getBaseDataItem(data, null, null);
                personBaseData.setCoName(this.get("convn"));
            }
            if (timestamp.equals(this.get("tilfra_ts"))) {
                personBaseData = this.getBaseDataItem(data, null, null);
                personBaseData.setMoveMunicipality(
                        this.getInt("tilfra_mynkod"),
                        this.getDateTime("tilflykomdto"),
                        this.getBoolean("tilflykomdt_umrk"),
                        this.getInt("fraflykomkod"),
                        this.getDateTime("fraflykomdto"),
                        this.getBoolean("fraflykomdt_umrk")
                );
            }
            return data;
        }

        @Override
        public HashSet<String> getTimestamps() {
            HashSet<String> timestamps = super.getTimestamps();
            timestamps.add(this.get("adr_ts"));
            timestamps.add(this.get("convn_ts"));
            timestamps.add(this.get("tilfra_ts"));
            return timestamps;
        }
    }


    //------------------------------------------------------------------------------------------------------------------

    public PersonParser() {
    }

    private static Logger log = Logger.getLogger(PersonParser.class);


    @Override
    protected CprRecord parseLine(String recordType, String line) {
        CprRecord r = super.parseLine(recordType, line);
        if (r != null) {
            return r;
        }
        try {
            switch (recordType) {
                case PersonDataRecord.RECORDTYPE_PERSON:
                    return new PersonData(line);
                case PersonDataRecord.RECORDTYPE_DOMESTIC_ADDRESS:
                    return new AddressData(line);
                // TODO: Add one of these for each type...
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
