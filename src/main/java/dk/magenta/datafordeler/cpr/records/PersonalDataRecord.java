package dk.magenta.datafordeler.cpr.records;

import org.apache.log4j.Logger;

import java.text.ParseException;

/**
 * Created by lanre.
 */
public class PersonalDataRecord extends Record {
    protected Logger logger = Logger.getLogger(PersonalDataRecord.class);
    private String line;

    public PersonalDataRecord(String line) throws ParseException {
        super(line);
        if (line == null)
            throw new ParseException("Invalid NULL input.", 0);
        this.line = line;
        this.populateAdditionalFields();
    }

    /**
     * This is the method by which it is expected to be used to populate the additional fields of a record type.
     * Left to subclasses to implement.
     */
    @Override
    public void populateAdditionalFields() {
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 14, "PNRGAELD", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 2, 24, "STATUS", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 12, 26, "STATUSHAENSTART", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 38, "STATUSDTO_UMRK", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 39, "KOEN", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 40, "FOED_DT", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 50, "FOED_DT_UMRK", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 51, "START_DT_PERSON", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 61, "START_DT_UMRK_PERSON", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 62, "SLUT_DT_PERSON", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 72, "SLUT_DT_UMRK_PERSON", this.getLine()));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 73, "STILLING", this.getLine()));
    }

    public String getLine() {
        return line;
    }

    /**
     * Print a few details
     */
    @Override
    public void print() {
        System.out.println("\nRecordType: "+ this.getRecordType().toString() +"\nPersonummer: "+ this.getPersonNummer()
        +"\n PNRGAELD: "+ this.getPropertyValue("PNRGAELD").toString());
    }

    /**
     * Print a few details
     */
    @Override
    public String toString() {
        return "\nRecordType: "+ this.getRecordType().toString() +"\nPersonummer: "+ this.getPersonNummer();
//        +"\n PNRGAELD: "+ this.getPropertyValue("PNRGAELD").toString();
    }
}