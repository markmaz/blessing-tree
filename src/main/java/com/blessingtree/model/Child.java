package com.blessingtree.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="children")
public class Child {
    @Id
    @Column(name="child_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="parent_id", nullable = false)
    private Parent parent;

    @OneToMany
    @JoinColumn(name="child_id")
    private List<Gift> gifts;
}
