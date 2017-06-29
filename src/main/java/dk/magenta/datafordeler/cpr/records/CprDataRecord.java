package dk.magenta.datafordeler.cpr.records;

import dk.magenta.datafordeler.core.database.Effect;
import dk.magenta.datafordeler.core.exception.ParseException;
import dk.magenta.datafordeler.core.util.ListHashMap;
import dk.magenta.datafordeler.cpr.data.CprData;
import dk.magenta.datafordeler.cpr.data.CprEffect;
import dk.magenta.datafordeler.cpr.data.road.RoadEffect;
import dk.magenta.datafordeler.cpr.data.road.data.RoadBaseData;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by lars on 04-11-14.
 */
public abstract class CprDataRecord<V extends CprEffect, B extends CprData> extends CprRecord {

    public CprDataRecord(String line) throws ParseException {
        super(line);
    }

    public abstract void getDataEffects(HashMap<V, B> data, String registrationFrom);


    protected B getBaseDataItem(HashMap<V, B> data) {
        return this.getBaseDataItem(data, null, false, null, false);
    }

    protected B getBaseDataItem(HashMap<V, B> data, OffsetDateTime effectFrom, boolean effectFromUncertain) {
        return this.getBaseDataItem(data, effectFrom, effectFromUncertain, null, false);
    }

    protected B getBaseDataItem(HashMap<V, B> data, OffsetDateTime effectFrom, boolean effectFromUncertain, OffsetDateTime effectTo, boolean effectToUncertain) {
        V effect = null;
        for (V e : data.keySet()) {
            if (e.compareRange(effectFrom, effectFromUncertain, effectTo, effectToUncertain)) {
                effect = e;
                break;
            }
        }
        if (effect == null) {
            effect = this.createEffect(effectFrom, effectFromUncertain, effectTo, effectToUncertain);
            data.put(effect, this.createEmptyBaseData());
        }
        return data.get(effect);
    }

    protected abstract V createEffect(OffsetDateTime effectFrom, boolean effectFromUncertain, OffsetDateTime effectTo, boolean effectToUncertain);

    protected abstract B createEmptyBaseData();

}