package dk.magenta.datafordeler.cpr.records;

import java.util.InvalidPropertiesFormatException;
import java.util.function.Supplier;

/**
 * This class is meant to essentially be used to constrain what is extracted to what is defined for each record type in
 * the Uddataformat ændringsudtræk offentlige U12170-P.pdf (starting from page 8). So one of this is equal to a line in
 * in the record type specification in that document. So one can imagine that a record for instance is configured with a
 * list of this class.
 *
 * @author lanre.
 */
public class RecordField {

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
        if (this.formatType.equals(FormatType.NUMERIC) && (Integer.valueOf(prop_value) instanceof Integer)
                && String.valueOf(prop_value).length() == this.getProp_length() )
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
        if (this.formatType.equals(FormatType.NUMERIC) && !prop_value.matches("[A-Za-z0-9]+") && this.getProp_length()
                == prop_value.length())
            throw new InvalidPropertiesFormatException("This property can only be a alpha numeric value");
        else {
           this.setProp_value(new RecordValue<String>( ()-> prop_value ) );
        }
    }

    //</editor-fold>


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
