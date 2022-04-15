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
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String regions;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String village;
    private String birthDate;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Region region;
    @OneToOne
    private Attachment attachment;

}
