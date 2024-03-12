package com.ecommerce.application.user.model;

import com.ecommerce.application.cart.Cart;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @ElementCollection
    private List<Authority> authorities;
    private boolean enabled;

    @Column(length = 500)
    private String address;
    @CreationTimestamp
    private LocalDateTime dateCreated;
    @OneToOne(cascade = CascadeType.PERSIST)
    @Getter
    private final Cart myCart;

    public AppUser(){
        this.myCart = new Cart();
        myCart.setTotalPrice(0.0);
    }


}
