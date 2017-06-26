package dk.magenta.datafordeler.cpr.records;

/**
 * @author lanre.
 */
public abstract class CprRecordFactory {
    public abstract Record createRecord(String type, String Line);
}
