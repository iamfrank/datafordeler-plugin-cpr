package dk.magenta.datafordeler.cpr.records;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.InvalidPropertiesFormatException;
import java.util.function.Supplier;

/**
 * This class is meant to essentially be used to constrain what is extracted to what is defined for each record type in
 * the Uddataformat ændringsudtræk offentlige U12170-P.pdf (starting from page 8). So one of this is equal to a line in
 * in the record type specification in that document. So one can imagine that a record for instance is configured with a
 * list of this class, with the exception that, absent from that list, are the first two fields. See
 * dk.magenta.datafordeler.cpr.records.Record.java
 *
 * @author lanre.
 */
public class RecordField {

    protected Logger logger = Logger.getLogger(RecordField.class);

    public enum FormatType {ALPHA_NUMERIC, NUMERIC}

    FormatType formatType;
    int prop_length;
    int prop_position;
    String prop_name;
    String prop_description;
    RecordValue prop_value;

    //<editor-fold desc="Getters">
    public FormatType getFormatType() {
        return formatType;
    }

    public int getProp_length() {
        return prop_length;
    }

    public int getProp_position() {
        return prop_position;
    }

    public String getProp_name() {
        return prop_name;
    }

    public String getProp_description() {
        return prop_description;
    }

    public RecordValue getProp_value() {
        return prop_value;
    }

    //</editor-fold>


    //<editor-fold desc="Setters">
    public void setFormatType(FormatType formatType) {
        this.formatType = formatType;
    }

    public void setProp_length(int prop_length) {
        this.prop_length = prop_length;
    }

    public void setProp_position(int prop_position) {
        this.prop_position = prop_position;
    }

    public void setProp_name(String prop_name) {
        this.prop_name = prop_name;
    }

    public void setProp_description(String prop_description) {
        this.prop_description = prop_description;
    }

    /**
     * Unsure whether there'll ever be a use case for this but for now we'l leave it here. Has potential, for now, to
     * bypass sanitisation checks. (See the TODO)
     *
     * @param prop_value
     */
    //TODO Sanitise the value. i.e. format type and length checks
    public void setProp_value(RecordValue prop_value) {
        this.prop_value = prop_value;
    }

    /**
     * This setter sets the property value if the value is an integer and the format type is set to numeric
     * @param prop_value
     * @throws InvalidPropertiesFormatException
     */
    public void setProp_value(int prop_value) throws InvalidPropertiesFormatException{
        String padded = String.format(getNumberFormat(this.getProp_length()) , prop_value);
        if (this.formatType.equals(FormatType.NUMERIC) && (Integer.valueOf(prop_value) instanceof Integer)
                && String.valueOf(padded).length() == this.getProp_length() )
            this.setProp_value(new RecordValue<>( () -> prop_value ) );
        else {
            throw new InvalidPropertiesFormatException("This property can only be a numeric value");
        }
    }

    /**
     * This setter sets the property value if alphanumeric. As with the other overloaded method of the same name, the
     * property is checked against the set FormatType i.e. {ALPHA_NUMERIC | NUMERIC} to constrain the input.
     * @param prop_value property value. Must be of type string
     * @throws InvalidPropertiesFormatException if the value isn't alpha numeric or if the format type is not
     * set to ALPHANUMERIC
     */
    public void setProp_value(String prop_value) throws InvalidPropertiesFormatException{
        if (this.formatType.equals(FormatType.ALPHA_NUMERIC) && !prop_value.matches("[A-Za-z0-9]+") && this.getProp_length()
                == prop_value.length())
            throw new InvalidPropertiesFormatException("This property can only be a alpha numeric value");
        else {
           this.setProp_value(new RecordValue<>( ()-> prop_value ) );
        }
    }

    /**
     * A generic method to set the value according to the type
     * @param prop_value
     * @param <T>
     */
    public <T> void setProp_value(T prop_value) throws InvalidPropertiesFormatException {
        if(prop_value == null)
            throw new InvalidPropertiesFormatException("This property can only be a numeric or alphanumeric value" +
                    "and can not be null");
        if (sanitised(prop_value))
            this.prop_value =  new RecordValue<>( ()-> prop_value );
        else
            throw new InvalidPropertiesFormatException("This property can only be a numeric or alphanumeric value" +
                    "and can not be null");
    }

    /**
     * Returns a number format string for zero padding comparison since the codes are stored without padding.
     * i.e. depending on number length, lets say numberLength is 3, it'll return "%03d".
     * Used to verify that the property has the adequate length in the case where property is numeric.
     * @param numberLength
     * @return
     */
    private String getNumberFormat(int numberLength){
        if (numberLength < 10)
            return "%0"+numberLength+"d";
        else
            return "%"+numberLength+"d";
    }
    //</editor-fold>


    //<editor-fold desc="Constructors">

    /**
     * Implemented to avoid using null patterns.
     */
    public RecordField() {
    }

    /**
     * @param formatType    a format type enum value as defined in this class {ALPHA_NUMERIC | NUMERIC}
     * @param prop_length   the length of the property to be extracted from the string
     * @param prop_position the position at which to start extraction
     * @param prop_name     the name of the property
     */
    public RecordField(FormatType formatType, int prop_length, int prop_position, String prop_name) {
        this.formatType = formatType;
        this.prop_length = prop_length;
        this.prop_position = prop_position;
        this.prop_name = prop_name;
    }

    /**
     * The last parameter can either be the whole CPR record or the actual value itself
     * @param formatType
     * @param prop_length
     * @param prop_position
     * @param prop_name
     * @param prop_value
     */
    public RecordField(FormatType formatType, int prop_length, int prop_position, String prop_name, String prop_value) {
        this.formatType = formatType;
        this.prop_length = prop_length;
        this.prop_position = prop_position;
        this.prop_name = prop_name;

        int calculatedMinimalLength = this.prop_position + this.prop_length;
        String value = "";

        try{
            //Work out whether we're initilising the prop_value with the actual value or a record line
            //If the first if is true it means prop_value is the who cpr record.
            if(prop_value.length() >= calculatedMinimalLength)
                value =  StringUtils.substring(prop_value, this.getProp_position() - 1, this.getProp_position() + this.getProp_length()-1);
            else if (prop_value.length() == this.getProp_length())
                value = prop_value;

            value = this.sanitised(value) ? value : "";
            this.setProp_value(value);

        }
        catch(InvalidPropertiesFormatException | NumberFormatException inpfe){
            logger.error("Unable set property of record field <"+this.getProp_name()+ "> due to:\n");
            inpfe.printStackTrace();
        }

    }
    //</editor-fold>

    /**
     * Used to return an empty map (to avoid null patterns)
     */
    public static final RecordField EMPTY = new RecordField();

    /**
     * This is a generic code checking that should sanitise both types of input.
     * Type checking is caught when casting is attempted; otherwise we simply execute the other sanitisation checks in
     * the try clause(s).
     * @param value_type
     * @param <T>
     * @return
     */
    protected <T> boolean sanitised(T value_type){
        if (value_type == null)
            return false;
        switch (this.formatType) {
            case ALPHA_NUMERIC: {
                try {
                    String casted = String.class.cast(value_type);
                    return casted.length() == this.prop_length && casted.matches("[A-Za-z0-9]+")
                            && StringUtils.isNotBlank(casted);
                }
                catch (ClassCastException cce){
                    return false;
                }
            }
            case NUMERIC:{
                try {
                    String casted = String.class.cast(value_type);
                    if(casted.length() < 10) {
                        String padded = String.format(getNumberFormat(this.getProp_length()), Integer.class.cast(value_type));
                        return String.valueOf(padded).length() == this.getProp_length();
                    }
                    //Since Integer will throw an error on numbers longer than 10 digits.
                    if(casted.length() > 9){
                        String padded = String.format(getNumberFormat(this.getProp_length()), Long.class.cast(value_type));
                        return String.valueOf(padded).length() == this.getProp_length();
                    }
                }
                catch (ClassCastException cce){
                    return false;
                }
            }
        }
        //Guilty until presumed innocent; always return false.
        return false;
    }

    /**
     * Generic class to cater for the ambiguity of record value which can be either string (to represent alphanumeric)
     * or an integer.
     *
     * @param <T> the type of the value
     */
    public class RecordValue<T> {
        private Supplier<T> supplier;

        public RecordValue(Supplier<T> t) {
            this.supplier = t;
        }

        public T get() {
            return supplier.get();
        }

    }

}
