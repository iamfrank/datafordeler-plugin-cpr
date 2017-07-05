package dk.magenta.datafordeler.cpr.data.person.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import dk.magenta.datafordeler.cpr.data.DetailData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.Map;

/**
 * Created by lars on 22-06-17.
 */
@Entity
@Table(name = "cpr_person_address_coname")
public class PersonAddressConameData extends DetailData {

    @Column
    @JsonProperty(value = "conavn")
    @XmlElement(name = "conavn")
    private String conavn;

    public String getConavn() {
        return this.conavn;
    }

    public void setConavn(String conavn) {
        this.conavn = conavn;
    }

    @Override
    public Map<String, Object> asMap() {
        return Collections.singletonMap("conavn", this.conavn);
    }
}
