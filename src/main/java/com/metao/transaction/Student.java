package com.metao.transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@Table(name = "student")
@NoArgsConstructor
@Entity(name = "student")
public class Student implements Persistable<Long> {

    @Id
    private Long id;

    @NaturalId
    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "student")
    private Collection<Address> addresses = new ArrayList<>();

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void updateAddress(Address address) {
        addresses.forEach(ad->{
            if (ad.getId().equals(address.getId())) {
                ad.setNumber(address.getNumber());
                ad.setCity(address.getCity());
                ad.setStreet(address.getStreet());
            }
        });
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
