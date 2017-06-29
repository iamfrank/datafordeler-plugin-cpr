package dk.magenta.datafordeler.cpr.records;

import dk.magenta.datafordeler.core.exception.ParseException;

/**
 * Created by lars on 04-11-14.
 */
public class CprSlutRecord extends CprRecord {

    public String getRecordType() {
        return RECORDTYPE_SLUT;
    }

    public CprSlutRecord(String line) throws ParseException {
        super(line);
        this.put("taeller", substr(line, 4, 8));
    }

}
