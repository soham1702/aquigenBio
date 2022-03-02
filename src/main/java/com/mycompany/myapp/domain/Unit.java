package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Unit.
 */
@Entity
@Table(name = "unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "unit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "purchaseOrder", "product", "unit" }, allowSetters = true)
    private Set<PurchaseOrderDetails> purchaseOrderDetails = new HashSet<>();

    @OneToMany(mappedBy = "unit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "purchaseOrderDetails",
            "rawMaterialOrders",
            "quatationDetails",
            "categories",
            "unit",
            "securityUser",
            "productInventories",
            "productTransactions",
        },
        allowSetters = true
    )
    private Set<Product> products = new HashSet<>();

    @JsonIgnoreProperties(value = { "product", "unit", "categories", "productQuatation" }, allowSetters = true)
    @OneToOne(mappedBy = "unit")
    private QuatationDetails quatationDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Unit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public Unit unitName(String unitName) {
        this.setUnitName(unitName);
        return this;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public Unit shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Unit freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Unit freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Unit lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Unit lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Unit isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Unit isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<PurchaseOrderDetails> getPurchaseOrderDetails() {
        return this.purchaseOrderDetails;
    }

    public void setPurchaseOrderDetails(Set<PurchaseOrderDetails> purchaseOrderDetails) {
        if (this.purchaseOrderDetails != null) {
            this.purchaseOrderDetails.forEach(i -> i.setUnit(null));
        }
        if (purchaseOrderDetails != null) {
            purchaseOrderDetails.forEach(i -> i.setUnit(this));
        }
        this.purchaseOrderDetails = purchaseOrderDetails;
    }

    public Unit purchaseOrderDetails(Set<PurchaseOrderDetails> purchaseOrderDetails) {
        this.setPurchaseOrderDetails(purchaseOrderDetails);
        return this;
    }

    public Unit addPurchaseOrderDetails(PurchaseOrderDetails purchaseOrderDetails) {
        this.purchaseOrderDetails.add(purchaseOrderDetails);
        purchaseOrderDetails.setUnit(this);
        return this;
    }

    public Unit removePurchaseOrderDetails(PurchaseOrderDetails purchaseOrderDetails) {
        this.purchaseOrderDetails.remove(purchaseOrderDetails);
        purchaseOrderDetails.setUnit(null);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setUnit(null));
        }
        if (products != null) {
            products.forEach(i -> i.setUnit(this));
        }
        this.products = products;
    }

    public Unit products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Unit addProduct(Product product) {
        this.products.add(product);
        product.setUnit(this);
        return this;
    }

    public Unit removeProduct(Product product) {
        this.products.remove(product);
        product.setUnit(null);
        return this;
    }

    public QuatationDetails getQuatationDetails() {
        return this.quatationDetails;
    }

    public void setQuatationDetails(QuatationDetails quatationDetails) {
        if (this.quatationDetails != null) {
            this.quatationDetails.setUnit(null);
        }
        if (quatationDetails != null) {
            quatationDetails.setUnit(this);
        }
        this.quatationDetails = quatationDetails;
    }

    public Unit quatationDetails(QuatationDetails quatationDetails) {
        this.setQuatationDetails(quatationDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unit)) {
            return false;
        }
        return id != null && id.equals(((Unit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Unit{" +
            "id=" + getId() +
            ", unitName='" + getUnitName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
