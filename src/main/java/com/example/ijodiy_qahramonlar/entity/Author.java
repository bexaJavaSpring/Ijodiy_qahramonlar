package com.example.ijodiy_qahramonlar.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String regions;
    private String district;
    private String village;
    private String birthDate;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Region region;
    @OneToOne
    private Attachment attachment;

}
