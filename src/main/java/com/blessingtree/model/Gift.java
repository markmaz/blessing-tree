package com.blessingtree.model;

import jakarta.persistence.*;

@Entity
@Table(name="gifts")
public class Gift {
    public Gift(){}

    @Id
    @Column(name="gift_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="size")
    private String size;

    @Column(name="status")
    private String status;
}
