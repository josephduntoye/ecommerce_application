package com.ecommerce.application.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private int quantity;
    private double price;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-ss-MM-mm-ss")
    private LocalDateTime datecreated;
    private String imageURL;
}
