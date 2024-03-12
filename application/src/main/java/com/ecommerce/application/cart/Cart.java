package com.ecommerce.application.cart;

import com.ecommerce.application.item.Item;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
//@uildber
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> itemList;
    //@Transient
    private Double totalPrice;

    public void addItem(Item item){
        if(itemList==null){
            itemList = new ArrayList<>();
        }
        itemList.add(item);
    }
}
