package com.poseidon.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "curvepoint", schema = "demo")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "CurveId")
    private int curveId;

    @Column(name = "asOfDate")
    private Date asOfDate;

    @Column(name = "term")
    private Double term;

    @Column(name = "value")
    private Double value;

    @Column(name = "creationDate")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date creationDate;

    public CurvePoint(int i, double v, double v1) {
        this.id = i;
        this.value = v;
        this.term = v1;
    }
}