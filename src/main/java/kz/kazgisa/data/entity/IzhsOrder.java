package kz.kazgisa.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "izhs_orders")
public class IzhsOrder implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String fullName;
    private String letterNumber;
    private String iin;

    public IzhsOrder() {
    }
    public IzhsOrder(Long id, String fullName, String letterNumber, String iin ) {
        this.id = id;
        this.fullName = fullName;
        this.letterNumber = letterNumber;
        this.iin = iin;

    }
    public String getLetterNumber() {
        return letterNumber;
    }

    public void setLetterNumber(String letterNumber) {
        this.letterNumber = letterNumber;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    @Override
    public String toString() {
        return "IzhsOrder{" + "id=" + id + ", fullName=" + fullName + ", iin=" + iin
                + ", letterNumber=" + letterNumber + '}';
    }

}
