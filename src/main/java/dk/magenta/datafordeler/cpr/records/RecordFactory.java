package dk.magenta.datafordeler.cpr.records;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * @author lanre.
 */
@Component
public class RecordFactory extends CprRecordFactory {
    protected Logger logger = Logger.getLogger(RecordFactory.class);
    @Override
    public Record createRecord(String type, String line) {
        Record record = null;
        switch(type.toLowerCase()){
            case "personaldatarecord":
                try {
                    record = new PersonalDataRecord(line);
                }
                catch (ParseException pe){
                    logger.error("\nUnable to parse personal data record due to:\n" + pe.getMessage());
                }
                break;
            case "currentaddressrecord":
                try {
                    record = new CurrentAddressRecord(line);
                }
                catch (ParseException pe){
                    logger.error("\nUnable to parse personal data record due to:\n" + pe.getMessage());
                }
                break;
            case "validatedaddressrecord":
                try {
                    record = new ValidatedAddressRecord(line);
                }
                catch (ParseException pe){
                    logger.error("\nUnable to parse personal data record due to:\n" + pe.getMessage());
                }
                break;
        }

        return record;
    }
}
