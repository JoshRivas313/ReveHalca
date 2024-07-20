
package com.ravehalcajpa.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "categoriaproducto")
public class CategoriaProducto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(name = "nombrecategoria", nullable = false)
    private String productcategory;
    

    public CategoriaProducto() {
    }

    public CategoriaProducto(Long id, String productcategory) {
        this.id = id;
        this.productcategory = productcategory;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCategory() {
        return productcategory;
    }

    public void setProductCategory(String productcategory) {
        this.productcategory = productcategory;
    }

    @Override
    public String toString() {
        return "CategoriaProducto{" + "id=" + id + ", nombrecategoria=" + productcategory + '}';
    }

}
