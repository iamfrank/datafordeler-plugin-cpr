package dk.magenta.datafordeler.cpr.data.person.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import dk.magenta.datafordeler.cpr.data.DetailData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lars on 21-06-17.
 */
@Entity
@Table(name = "cpr_person_core")
public class PersonCoreData extends AuthorityDetailData {

    public enum Gender {
        M,
        K
    }

    @Column
    @JsonProperty(value = "nuværendeCprNummer")
    @XmlElement(name = "nuværendeCprNummer")
    private int currentCprNumber;

    public int getCurrentCprNumber() {
        return this.currentCprNumber;
    }

    public void setCurrentCprNumber(int currentCprNumber) {
        this.currentCprNumber = currentCprNumber;
    }



    @Column
    @JsonProperty(value = "køn")
    @XmlElement(name = "køn")
    private Gender gender;

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGender(String gender) {
        if (gender != null) {
            if (gender.equalsIgnoreCase("M")) {
                this.setGender(Gender.M);
            } else if (gender.equalsIgnoreCase("K")) {
                this.setGender(Gender.K);
            }
        }
    }


    @Override
    public Map<String, Object> asMap() {
        HashMap<String, Object> map = new HashMap<>(super.asMap());
        if (currentCprNumber != 0) {
            map.put("currentCprNumber", this.currentCprNumber);
        }
        if (this.gender != null) {
            map.put("gender", this.gender);
        }
        return map;
    }
}
