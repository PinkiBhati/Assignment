package com.project.Ecommerce.Entities;

import com.project.Ecommerce.Utilities.HashMapConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    @Positive
    private Integer quantityAvailable;

    @Column(nullable = false)
    @Positive
    private double price;
    private String metadata;

    @Transient
    @Convert(converter = HashMapConverter.class)
    private Map<String,Object> infoAttributes;
    boolean isActive;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "productVariation",cascade = CascadeType.ALL)
    private Set<Cart> carts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean getisActive(){
        return  isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Map<String, Object> getInfoAttributes() {
        return infoAttributes;
    }

    public void setInfoAttributes(Map<String, Object> infoAttributes) {
        this.infoAttributes = infoAttributes;
    }

    public void addCarts(Cart cart)
    {
        if (carts==null)
        {
            carts = new HashSet<>();
        }
        carts.add(cart);
    }


}
