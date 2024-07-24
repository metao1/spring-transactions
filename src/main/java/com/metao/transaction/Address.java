package com.metao.transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Embeddable
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private Long id;

    private String street;
    private String city;
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public Address(String street, String city, String number) {
        this.street = street;
        this.city = city;
        this.number = number;
    }
}
