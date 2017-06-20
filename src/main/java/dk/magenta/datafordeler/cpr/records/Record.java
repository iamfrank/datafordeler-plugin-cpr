package dk.magenta.datafordeler.cpr.records;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by lanre
 */
public abstract class Record {

    public static final String RECORDTYPE_START = "000";
    public static final String RECORDTYPE_SLUT = "999";

    /**
     * All records start with the 3 digit type code followed by the personnummer so we use this to form the basis of
     * every record.
     * The record elements for records are instantiated for these two elements so that, ideally, we only need to just
     * set their property values.
     */
    RecordField recordType = new RecordField(RecordField.FormatType.NUMERIC, 3, 1, "RecordType");
    RecordField personNummer = new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 4, "PNR");
    // Do we actually need a HashMap for the additional records?
    private HashMap<String, RecordField> additionalFields;

    /**
     * Our ideal constructor
     * @param recordType
     * @param personNummer
     */
    public Record(RecordField recordType, RecordField personNummer) {
        this.recordType = recordType;
        this.personNummer = personNummer;
    }

    /**
     * THis is the one most likely to be used since the subclasses need only set the record values for the constant
     * types of this super class.
     * @param cprLine
     */
    public Record(String cprLine) {
        if (cprLine.length() < 13)
            throw new IllegalArgumentException("The provided record is not long enough to be considered valid for" +
                    "creation of a CPR record.");
        try {
            int type = Integer.valueOf(StringUtils.substring(cprLine, 0, 3));
            String pnr = StringUtils.substring(cprLine, 3, 3 + recordType.getProp_length() + 1);
            this.recordType.setProp_value(type);
            this.personNummer.setProp_value(pnr);
        }
        catch (NullPointerException | NumberFormatException | InvalidPropertiesFormatException nfe){
            throw new IllegalArgumentException("Exception determining record type from provided record:\n"+ nfe.getMessage());
        }
    }

    /**
     * The get method returns an org.apache.commons.lang3.tuple.ImmutablePair witrh the name of the record on the left
     * and its numeric value on the right
     * See https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/tuple/Pair.html
     * @return
     */
    public Pair getRecordType() {
        return new ImmutablePair<>(this.recordType.getProp_name(), this.recordType.getProp_value());
    }

    /**
     * Get the string representing the personnummer or PNR
     * @return
     */
    public String getPersonNummer() {
        return this.personNummer.getProp_value().toString();
    }

    /**
     * Returns an immutable pair that contains the property name in the left side and the property value itself on the
     * right
     * @param propkey
     * @return
     */
    public Pair getAdditionalPropertyValue(String propkey){
        RecordField recordField = this.getAdditionalProperty(propkey);
        return new ImmutablePair<>(recordField.getProp_name(), recordField.getProp_value());
    }

    /**
     * retrieve a record field from the additional fields map
     * @param propKey
     * @return
     */
    public RecordField getAdditionalProperty(String propKey){
        return this.additionalFields.get(propKey);
    }

    /**
     * If we happen to just have a hash map handy with the props all ready ta go!
     * @param additionalFields
     */
    public void setAdditionalFields(HashMap<String, RecordField> additionalFields) {
        this.additionalFields = additionalFields;
    }

    /**
     * Adds an additional field to the record. Requires only that a record field given since the symmetry between the
     * key and the field name must be maintained.
     * @param record
     */
    public void addField(RecordField record){
        this.additionalFields.put(record.getProp_name(), record);
    };

    /**
     * This is the method by which it is expected to be used to populate the additional fields of a record type.
     * Left to subclasses to implement.
     */
    public abstract void populateAdditionalFields();

}
