package dk.magenta.datafordeler.cpr.records;

import java.text.ParseException;

/**
 * Created by lanre.
 */
public class ValidatedAddressRecord extends Record {

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
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 14, "PNRGAELD"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 2, 24, "STATUS"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 12, 26, "STATUSHAENSTART"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 38, "STATUSDTO_UMRK"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 39, "KOEN"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 40, "FOED_DT"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 50, "FOED_DT_UMRK"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 51, "START_DT_PERSON"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 61, "START_DT_UMRK_PERSON"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 10, 62, "SLUT_DT_PERSON"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 1, 72, "SLUT_DT_UMRK_PERSON"));
        this.addField(new RecordField(RecordField.FormatType.ALPHA_NUMERIC, 34, 73, "STILLING"));
    }
}