package SpringSecurity.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Person implements Serializable {

    @EmbeddedId
    private PersonPrimaryKey personPrimaryKey;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "city_of_living")
    private String city;

    @Column(name = "role")
    private PRole role;
}

