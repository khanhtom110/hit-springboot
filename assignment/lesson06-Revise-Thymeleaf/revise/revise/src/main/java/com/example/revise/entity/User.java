package com.example.revise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name",nullable = false,length = 100)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(name = "phone_number",length = 10)
    private String phoneNumber;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean active=true;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @Builder.Default
    private List<Order> orders=new ArrayList<>();

}
