package dk.magenta.datafordeler.cpr.records;

import org.apache.log4j.Logger;

import java.text.ParseException;

/**
 * Created by lanre.
 */
public class CurrentAddressRecord extends Record {
    protected Logger logger = Logger.getLogger(CurrentAddressRecord.class);

    private String line;

    public CurrentAddressRecord(String line) throws ParseException {
        super(line);
        if (line == null)
            throw new ParseException("Invalid NULL input.", 0);
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    /**
     * This is the method by which it is expected to be used to populate the additional fields of a record type.
     * Left to subclasses to implement.
     */
    @Override
    public void populateAdditionalFields() {
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 4, 14, "KOMKOD"));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 4, 18, "VEJKOD"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 4, 22, "HUSNR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 2, 26, "ETAGE"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 4, 28, "SIDEDOER"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 4, 32, "BNR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 36, "CONVN"));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 12, 70, "TILFLYDTO"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 82, "TILFLYDTO_UMRK"));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 12, 83, "TILFLYKOMDTO"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 95, "TILFLYKOMDT_UMRK"));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 4, 96, "FRAFLYKOMKOD"));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 12, 100, "FRAFLYKOMDTO"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 112, "FRAFLYKOMDT_UMRK"));
        this.addField(new RecordField(RecordField.FormatType.NUMERIC, 4, 113, "START_MYNKOD_ADRTXT"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 117, "ADR1_SUPLADR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 151, "ADR2_SUPLADR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 185, "ADR3_SUPLADR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 219, "ADR4_SUPLADR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 253, "ADR5_SUPLADR"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 287, "START_DT_ADRTXT"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 297, "SLET_DT-ADRTXT"));
    }

    /**
     * Print a few details
     */
    @Override
    public void print() {
        System.out.println("\nRecordType: "+ this.getRecordType().toString() +"\nPersonummer: "+ this.getPersonNummer()
                +"\n HUSNR: "+ this.getPropertyValue("HUSNR").toString());
    }

    /**
     * Print a few details
     */
    @Override
    public String toString() {
        return "\nRecordType: "+ this.getRecordType().toString() +"\nPersonummer: "+ this.getPersonNummer();
    }
}
