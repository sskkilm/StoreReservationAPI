package zerobase.storereservationapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.storereservationapi.embedded.Location;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Location location;

    private String description;

    public void updateStore(String name, Location location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
    }
}
