package com.example.ijodiy_qahramonlar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Category category;
    @OneToOne
    private Attachment attachment;
}
