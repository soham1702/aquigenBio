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
 * A RawMaterialOrder.
 */
@Entity
@Table(name = "raw_material_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RawMaterialOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price_per_unit")
    private Double pricePerUnit;

    @Column(name = "quantity_unit")
    private String quantityUnit;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "delivery_date")
    private Instant deliveryDate;

    @Column(name = "quantity_check")
    private String quantityCheck;

    @Column(name = "ordered_on")
    private Instant orderedOn;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @OneToMany(mappedBy = "rawMaterialOrder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "purchaseOrderDetails", "goodsReciveds", "productInventory", "warehouse", "securityUser", "rawMaterialOrder" },
        allowSetters = true
    )
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();

    @ManyToMany(mappedBy = "rawMaterialOrders")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RawMaterialOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPricePerUnit() {
        return this.pricePerUnit;
    }

    public RawMaterialOrder pricePerUnit(Double pricePerUnit) {
        this.setPricePerUnit(pricePerUnit);
        return this;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getQuantityUnit() {
        return this.quantityUnit;
    }

    public RawMaterialOrder quantityUnit(String quantityUnit) {
        this.setQuantityUnit(quantityUnit);
        return this;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public RawMaterialOrder quantity(Double quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Instant getDeliveryDate() {
        return this.deliveryDate;
    }

    public RawMaterialOrder deliveryDate(Instant deliveryDate) {
        this.setDeliveryDate(deliveryDate);
        return this;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getQuantityCheck() {
        return this.quantityCheck;
    }

    public RawMaterialOrder quantityCheck(String quantityCheck) {
        this.setQuantityCheck(quantityCheck);
        return this;
    }

    public void setQuantityCheck(String quantityCheck) {
        this.quantityCheck = quantityCheck;
    }

    public Instant getOrderedOn() {
        return this.orderedOn;
    }

    public RawMaterialOrder orderedOn(Instant orderedOn) {
        this.setOrderedOn(orderedOn);
        return this;
    }

    public void setOrderedOn(Instant orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public RawMaterialOrder orderStatus(String orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public RawMaterialOrder lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public RawMaterialOrder lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public RawMaterialOrder freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public RawMaterialOrder freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public RawMaterialOrder freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public RawMaterialOrder freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Set<PurchaseOrder> getPurchaseOrders() {
        return this.purchaseOrders;
    }

    public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        if (this.purchaseOrders != null) {
            this.purchaseOrders.forEach(i -> i.setRawMaterialOrder(null));
        }
        if (purchaseOrders != null) {
            purchaseOrders.forEach(i -> i.setRawMaterialOrder(this));
        }
        this.purchaseOrders = purchaseOrders;
    }

    public RawMaterialOrder purchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.setPurchaseOrders(purchaseOrders);
        return this;
    }

    public RawMaterialOrder addPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.add(purchaseOrder);
        purchaseOrder.setRawMaterialOrder(this);
        return this;
    }

    public RawMaterialOrder removePurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.remove(purchaseOrder);
        purchaseOrder.setRawMaterialOrder(null);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.removeRawMaterialOrder(this));
        }
        if (products != null) {
            products.forEach(i -> i.addRawMaterialOrder(this));
        }
        this.products = products;
    }

    public RawMaterialOrder products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public RawMaterialOrder addProduct(Product product) {
        this.products.add(product);
        product.getRawMaterialOrders().add(this);
        return this;
    }

    public RawMaterialOrder removeProduct(Product product) {
        this.products.remove(product);
        product.getRawMaterialOrders().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterialOrder)) {
            return false;
        }
        return id != null && id.equals(((RawMaterialOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialOrder{" +
            "id=" + getId() +
            ", pricePerUnit=" + getPricePerUnit() +
            ", quantityUnit='" + getQuantityUnit() + "'" +
            ", quantity=" + getQuantity() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", quantityCheck='" + getQuantityCheck() + "'" +
            ", orderedOn='" + getOrderedOn() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            "}";
    }
}
