package com.example.ijodiy_qahramonlar.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(length = 10000)
    private String description;
    @ManyToOne
    private Author author;
    @OneToOne
    private Attachment attachment;
}
