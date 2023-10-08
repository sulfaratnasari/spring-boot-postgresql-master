package com.demo.postgres.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "carpark")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Carpark extends AuditModel {
    @Id
    @GeneratedValue(generator = "carpark_generator")
    @SequenceGenerator(
            name = "carpark_generator",
            sequenceName = "carpark_sequence",
            initialValue = 1
    )
    private Long id;

    @Column(name="carpark_location_id")
    private Long carparkLocationID;

     @Column(name="total_lots")
    private int totalLots;

    @Column(name="lots_available")
    private int lotsAvailable;
}