package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A QuatationDetails.
 */
@Entity
@Table(name = "quatation_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuatationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "availabel_stock")
    private Double availabelStock;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "rats_per_unit")
    private Double ratsPerUnit;

    @Column(name = "totalprice")
    private Double totalprice;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @JsonIgnoreProperties(
        value = {
            "categories",
            "unit",
            "purchaseOrderDetails",
            "rawMaterialOrders",
            "quatationDetails",
            "securityUser",
            "productInventories",
            "productTransactions",
        },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private Product product;

    @JsonIgnoreProperties(value = { "purchaseOrderDetails", "product", "quatationDetails" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Unit unit;

    @JsonIgnoreProperties(value = { "product", "quatationDetails" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Categories categories;

    @JsonIgnoreProperties(value = { "securityUser", "quatationDetails" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ProductQuatation productQuatation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public QuatationDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAvailabelStock() {
        return this.availabelStock;
    }

    public QuatationDetails availabelStock(Double availabelStock) {
        this.setAvailabelStock(availabelStock);
        return this;
    }

    public void setAvailabelStock(Double availabelStock) {
        this.availabelStock = availabelStock;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public QuatationDetails quantity(Double quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRatsPerUnit() {
        return this.ratsPerUnit;
    }

    public QuatationDetails ratsPerUnit(Double ratsPerUnit) {
        this.setRatsPerUnit(ratsPerUnit);
        return this;
    }

    public void setRatsPerUnit(Double ratsPerUnit) {
        this.ratsPerUnit = ratsPerUnit;
    }

    public Double getTotalprice() {
        return this.totalprice;
    }

    public QuatationDetails totalprice(Double totalprice) {
        this.setTotalprice(totalprice);
        return this;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public QuatationDetails discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public QuatationDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public QuatationDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public QuatationDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public QuatationDetails freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public QuatationDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public QuatationDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public QuatationDetails product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public QuatationDetails unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    public Categories getCategories() {
        return this.categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public QuatationDetails categories(Categories categories) {
        this.setCategories(categories);
        return this;
    }

    public ProductQuatation getProductQuatation() {
        return this.productQuatation;
    }

    public void setProductQuatation(ProductQuatation productQuatation) {
        this.productQuatation = productQuatation;
    }

    public QuatationDetails productQuatation(ProductQuatation productQuatation) {
        this.setProductQuatation(productQuatation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuatationDetails)) {
            return false;
        }
        return id != null && id.equals(((QuatationDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuatationDetails{" +
            "id=" + getId() +
            ", availabelStock=" + getAvailabelStock() +
            ", quantity=" + getQuantity() +
            ", ratsPerUnit=" + getRatsPerUnit() +
            ", totalprice=" + getTotalprice() +
            ", discount=" + getDiscount() +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
