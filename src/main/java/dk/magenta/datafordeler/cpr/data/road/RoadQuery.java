package dk.magenta.datafordeler.cpr.data.road;

import dk.magenta.datafordeler.core.database.LookupDefinition;
import dk.magenta.datafordeler.core.fapi.ParameterMap;
import dk.magenta.datafordeler.core.fapi.QueryField;
import dk.magenta.datafordeler.cpr.data.CprQuery;
import dk.magenta.datafordeler.cpr.data.road.data.RoadBaseData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lars on 19-05-17.
 */
public class RoadQuery extends CprQuery<RoadEntity> {

    public static final String VEJKODE = "vejkode";
    public static final String VEJNAVN = "vejnavn";
    public static final String KOMMUNEKODE = "kommunekode";

    @QueryField(type = QueryField.FieldType.INT, queryName = VEJKODE)
    private String vejkode;

    public String getVejkode() {
        return this.vejkode;
    }

    public void setVejkode(String vejkode) {
        this.vejkode = vejkode;
    }

    @QueryField(type = QueryField.FieldType.STRING, queryName = VEJNAVN)
    private String navn;

    public String getNavn() {
        return this.navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    @QueryField(type = QueryField.FieldType.INT, queryName = KOMMUNEKODE)
    private String kommunekode;

    public String getKommunekode() {
        return kommunekode;
    }

    public void setKommunekode(String kommunekode) {
        this.kommunekode = kommunekode;
    }

    @Override
    public Map<String, Object> getSearchParameters() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(VEJKODE, this.vejkode);
        map.put(VEJNAVN, this.navn);
        map.put(KOMMUNEKODE, this.kommunekode);
        return map;
    }

    @Override
    public void setFromParameters(ParameterMap parameters) {
        this.setVejkode(parameters.getFirst(VEJKODE));
        this.setNavn(parameters.getFirst(VEJNAVN));
        this.setKommunekode(parameters.getFirst(KOMMUNEKODE));
    }

    @Override
    public Class<RoadEntity> getEntityClass() {
        return RoadEntity.class;
    }

    @Override
    public Class getDataClass() {
        return RoadBaseData.class;
    }


    @Override
    public LookupDefinition getLookupDefinition() {
        LookupDefinition lookupDefinition = new LookupDefinition(this);
        if (this.vejkode != null) {
            lookupDefinition.put(LookupDefinition.entityref + "." + VEJKODE, this.vejkode);
        }
        if (this.navn != null) {
            lookupDefinition.put("coreData." + VEJNAVN, this.navn);
        }
        if (this.kommunekode != null) {
            lookupDefinition.put(LookupDefinition.entityref + "." + KOMMUNEKODE, this.navn);
        }
        return lookupDefinition;
    }

}
