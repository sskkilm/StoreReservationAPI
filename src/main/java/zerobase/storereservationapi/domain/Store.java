package zerobase.storereservationapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.storereservationapi.embedded.Location;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "store")
    private List<Review> reviewList = new ArrayList<>();

    private String description;

    public void updateStore(String name, Location location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
    }

    public void addReview(Review review) {
        this.reviewList.add(review);
    }
}
