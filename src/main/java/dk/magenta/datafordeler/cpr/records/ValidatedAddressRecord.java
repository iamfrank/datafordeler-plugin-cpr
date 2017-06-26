package dk.magenta.datafordeler.cpr.records;

import org.apache.log4j.Logger;
import java.text.ParseException;

/**
 * Created by lanre.
 */
public class ValidatedAddressRecord extends Record {
    protected Logger logger = Logger.getLogger(ValidatedAddressRecord.class);

    private String line;

    public ValidatedAddressRecord(String line) throws ParseException {
        super(line);
        if (line == null)
            throw new ParseException("Invalid NULL input.", 0);
        this.line = line;
    }

    /**
     * This is the method by which it is expected to be used to populate the additional fields of a record type.
     * Left to subclasses to implement.
     */
    @Override
    public void populateAdditionalFields() {
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 14, "ADRNVN"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 48, "CONVN"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 82, "LOKALITET"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 116, "STANDARDADR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 150, "BYNVN"));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 4, 184, "POSTNR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 20, 188, "POSTDISTTXT"));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 4, 208, "KOMKOD"));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 4, 212, "VEJKOD"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 4, 216, "HUSNR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 2, 220, "ETAGE"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 4, 222, "SIDEDOER"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 4, 226, "BNR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 20, 230, "VEJADRNVN"));
    }

    public String getLine() {
        return line;
    }

    /**
     * Print a few details
     */
    @Override
    public void print(){
        System.out.println("\nRecordType: "+ this.getRecordType().toString() +"\nPersonummer: "+ this.getPersonNummer()
                +"\n ADRNVN: "+ this.getPropertyValue("ADRNVN").toString());
    }

    /**
     * Print a few details
     */
    @Override
    public String toString() {
        return "\nRecordType: "+ this.getRecordType().toString() +"\nPersonummer: "+ this.getPersonNummer();
    }
}