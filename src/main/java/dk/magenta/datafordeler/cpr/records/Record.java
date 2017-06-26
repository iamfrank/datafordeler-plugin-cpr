package dk.magenta.datafordeler.cpr.records;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by lanre
 */
public abstract class Record {

    protected Logger logger = Logger.getLogger(Record.class);

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
        this.additionalFields = new HashMap<>();
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
            String pnr = StringUtils.substring(cprLine, 3, 3 + this.personNummer.getProp_length() + 1);
            this.recordType.setProp_value(type);
            this.personNummer.setProp_value(pnr);
            this.additionalFields = new HashMap<>();
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
     * TODO Consider that we shouldn't be really using this since the record types should be initialised with all the
     * required fields.

     * @param record
     */
    public void addField(RecordField record){
        this.additionalFields.put(record.getProp_name(), record);
    }

    /**
     *
     * @param propName
     * @param prop_value
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public <T> void setPropertyValue(String propName, T prop_value) throws NullPointerException, IllegalArgumentException{
        if (prop_value == null || StringUtils.isBlank(propName))
            throw new NullPointerException("The property name or the property value can not be blank.");
        if(!additionalFields.containsKey(propName))
            throw new NullPointerException("The property you are trying to set in the record does not exist.\nPlease" +
                    "consider checking the spelling.\nList of properties:\n"+ this.additionalFields.keySet().toString());
        try {
            additionalFields.get(propName).setProp_value(prop_value);
        }
        catch (InvalidPropertiesFormatException ipfe){
            logger.error("The property you are trying to set in the record does not exist.\nPlease" +
                    "consider checking the spelling.\nList of properties:\n" + this.additionalFields.keySet().toString());
        }
    }


    /**
     * Intentionally return an object. This should then be cast to the right type at the point of consumption.
     * @param prop_key
     * @return
     */
    public Object getPropertyValue(String prop_key){
        return this.additionalFields.get(prop_key).getProp_value().get();

    }

    /**
     * This is the method by which it is expected to be used to populate the additional fields of a record type.
     * Left to subclasses to implement.
     */
    public abstract void populateAdditionalFields();

    /**
     * Print a few details
     */
    public abstract void print();

    /**
     * To string method for use with output.
     * @return
     */
    public abstract String toString();

}
