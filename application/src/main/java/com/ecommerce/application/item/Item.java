package com.ecommerce.application.item;

import com.ecommerce.application.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor

@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private Product product;
    private int quantityAdded;

    public Item (Product product, int i) {
        this.product = product;
        quantityAdded = i;
    }

    public void setQuantityAddedToCart(int quantityAdded){
        if (quantityAdded<= product.getQuantity()){
            this.quantityAdded = quantityAdded;
        }
        else {
            this.quantityAdded =0;
        }
    }
}
