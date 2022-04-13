package com.example.ijodiy_qahramonlar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private Integer age;
    private String street;
    private String city;
    private Integer birthDate;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Region region;
    @OneToOne
    private Attachment attachment;

}
