package dk.magenta.datafordeler.cpr.data.person.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lars on 21-06-17.
 */
@Entity
@Table(name = "cpr_person_parent")
public class PersonParentData extends AuthorityDetailData {

    public static final String DB_FIELD_IS_MOTHER = "isMother";
    @Column(name = DB_FIELD_IS_MOTHER)
    @JsonIgnore
    @XmlTransient
    private boolean isMother;

    @JsonIgnore
    @XmlTransient
    public boolean isMother() {
        return this.isMother;
    }

    public void setMother(boolean mother) {
        isMother = mother;
    }


    public static final String DB_FIELD_CPR_NUMBER = "cprNumber";
    public static final String IO_FIELD_CPR_NUMBER = "personnummer";
    @Column(name = DB_FIELD_CPR_NUMBER)
    @JsonProperty(value = IO_FIELD_CPR_NUMBER)
    @XmlElement(name = IO_FIELD_CPR_NUMBER)
    private String cprNumber;

    public String getCprNumber() {
        return this.cprNumber;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }



    public static final String DB_FIELD_BIRTHDATE = "birthDate";
    public static final String IO_FIELD_BIRTHDATE = "foedselsdato";
    @Column(name = DB_FIELD_BIRTHDATE)
    @JsonProperty(value = IO_FIELD_BIRTHDATE)
    @XmlElement(name = IO_FIELD_BIRTHDATE)
    private LocalDate birthDate;

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }



    public static final String DB_FIELD_BIRTHDATE_UNCERTAIN = "birthDateUncertain";
    public static final String IO_FIELD_BIRTHDATE_UNCERTAIN = "foedselsdatoUsikker";
    @Column(name = DB_FIELD_BIRTHDATE_UNCERTAIN)
    @JsonProperty(value = IO_FIELD_BIRTHDATE_UNCERTAIN)
    @XmlElement(name = IO_FIELD_BIRTHDATE_UNCERTAIN)
    private boolean birthDateUncertain;

    public boolean isBirthDateUncertain() {
        return this.birthDateUncertain;
    }

    public void setBirthDateUncertain(boolean birthDateUncertain) {
        this.birthDateUncertain = birthDateUncertain;
    }



    @Column
    @JsonProperty(value = "navn")
    @XmlElement(name = "navn")
    private String navn;

    public String getNavn() {
        return this.navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }



    @Column
    @JsonProperty(value = "navneMarkering")
    @XmlElement(name = "navneMarkering")
    private boolean navneMarkering;

    public boolean isNavneMarkering() {
        return this.navneMarkering;
    }

    public void setNavneMarkering(boolean navneMarkering) {
        this.navneMarkering = navneMarkering;
    }



    @Override
    public Map<String, Object> asMap() {
        HashMap<String, Object> map = new HashMap<>(super.asMap());
        map.put("cprNumber", this.cprNumber);
        map.put("birthDate", this.birthDate);
        map.put("birthDateUncertain", this.birthDateUncertain);
        map.put("navn", this.navn);
        map.put("navneMarkering", this.navneMarkering);
        return map;
    }
}
