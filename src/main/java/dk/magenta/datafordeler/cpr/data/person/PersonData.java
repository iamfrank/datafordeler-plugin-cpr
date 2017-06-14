package dk.magenta.datafordeler.cpr.data.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import dk.magenta.datafordeler.cpr.data.CprData;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lars on 16-05-17.
 */
@Entity
@Table(name="cpr_person_data")
public class PersonData extends CprData<PersonEffect, PersonData> {

    @Column(unique = true, nullable = false, insertable = true, updatable = false)
    @JsonProperty(value = "firstName")
    @XmlElement(name = "firstName")
    private String firstName;

    @Column(unique = true, nullable = false, insertable = true, updatable = false)
    @JsonProperty(value = "lastName")
    @XmlElement(name = "lastName")
    private String lastName;

    /**
     * Return a map of attributes, including those from the superclass
     * @return
     */
    @Override
    public Map<String, Object> asMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("firstName", this.firstName);
        map.put("lastName", this.lastName);
        return map;
    }

}
