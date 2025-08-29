package com.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "rating")
public class Rating implements BaseEntity<Rating>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Size(max = 125)
    @NotBlank(message = "moodys is mandatory")
    @Column(name = "moodysRating", length = 125)
    private String moodysRating;

    @Size(max = 125)
    @NotBlank(message = "sandp is mandatory")
    @Column(name = "sandPRating", length = 125)
    private String sandPRating;

    @Size(max = 125)
    @NotBlank(message = "fitch is mandatory")
    @Column(name = "fitchRating", length = 125)
    private String fitchRating;

    @Min(value = 1, message = "order number must be greater than 0")
    @Column(name = "orderNumber")
    private int orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    @Override
    public void update(Rating rating) {
        this.moodysRating = rating.getMoodysRating();
        this.sandPRating = rating.getSandPRating();
        this.fitchRating = rating.getFitchRating();
        this.orderNumber = rating.getOrderNumber();
    }
}