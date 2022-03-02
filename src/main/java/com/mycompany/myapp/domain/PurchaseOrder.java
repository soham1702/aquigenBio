package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_po_amount")
    private Double totalPOAmount;

    @Column(name = "total_gst_amount")
    private Double totalGSTAmount;

    @Column(name = "expected_delivery_date")
    private Instant expectedDeliveryDate;

    @Column(name = "po_date")
    private Instant poDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private Status orderStatus;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @OneToMany(mappedBy = "purchaseOrder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "purchaseOrder", "product", "unit" }, allowSetters = true)
    private Set<PurchaseOrderDetails> purchaseOrderDetails = new HashSet<>();

    @OneToMany(mappedBy = "purchaseOrder")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "purchaseOrder" }, allowSetters = true)
    private Set<GoodsRecived> goodsReciveds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "transfers", "purchaseOrders", "consumptionDetails", "productTransactions", "products", "warehouses", "securityUsers" },
        allowSetters = true
    )
    private ProductInventory productInventory;

    @ManyToOne
    @JsonIgnoreProperties(value = { "purchaseOrders", "securityUsers", "productTransaction", "productInventories" }, allowSetters = true)
    private Warehouse warehouse;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "productTransaction",
            "purchaseOrders",
            "products",
            "securityPermissions",
            "securityRoles",
            "productQuatation",
            "transfer",
            "consumptionDetails",
            "productInventories",
            "warehouses",
        },
        allowSetters = true
    )
    private SecurityUser securityUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "purchaseOrders", "products" }, allowSetters = true)
    private RawMaterialOrder rawMaterialOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PurchaseOrder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPOAmount() {
        return this.totalPOAmount;
    }

    public PurchaseOrder totalPOAmount(Double totalPOAmount) {
        this.setTotalPOAmount(totalPOAmount);
        return this;
    }

    public void setTotalPOAmount(Double totalPOAmount) {
        this.totalPOAmount = totalPOAmount;
    }

    public Double getTotalGSTAmount() {
        return this.totalGSTAmount;
    }

    public PurchaseOrder totalGSTAmount(Double totalGSTAmount) {
        this.setTotalGSTAmount(totalGSTAmount);
        return this;
    }

    public void setTotalGSTAmount(Double totalGSTAmount) {
        this.totalGSTAmount = totalGSTAmount;
    }

    public Instant getExpectedDeliveryDate() {
        return this.expectedDeliveryDate;
    }

    public PurchaseOrder expectedDeliveryDate(Instant expectedDeliveryDate) {
        this.setExpectedDeliveryDate(expectedDeliveryDate);
        return this;
    }

    public void setExpectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Instant getPoDate() {
        return this.poDate;
    }

    public PurchaseOrder poDate(Instant poDate) {
        this.setPoDate(poDate);
        return this;
    }

    public void setPoDate(Instant poDate) {
        this.poDate = poDate;
    }

    public Status getOrderStatus() {
        return this.orderStatus;
    }

    public PurchaseOrder orderStatus(Status orderStatus) {
        this.setOrderStatus(orderStatus);
        return this;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public PurchaseOrder lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PurchaseOrder lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public PurchaseOrder freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public PurchaseOrder freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public PurchaseOrder freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public PurchaseOrder freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Set<PurchaseOrderDetails> getPurchaseOrderDetails() {
        return this.purchaseOrderDetails;
    }

    public void setPurchaseOrderDetails(Set<PurchaseOrderDetails> purchaseOrderDetails) {
        if (this.purchaseOrderDetails != null) {
            this.purchaseOrderDetails.forEach(i -> i.setPurchaseOrder(null));
        }
        if (purchaseOrderDetails != null) {
            purchaseOrderDetails.forEach(i -> i.setPurchaseOrder(this));
        }
        this.purchaseOrderDetails = purchaseOrderDetails;
    }

    public PurchaseOrder purchaseOrderDetails(Set<PurchaseOrderDetails> purchaseOrderDetails) {
        this.setPurchaseOrderDetails(purchaseOrderDetails);
        return this;
    }

    public PurchaseOrder addPurchaseOrderDetails(PurchaseOrderDetails purchaseOrderDetails) {
        this.purchaseOrderDetails.add(purchaseOrderDetails);
        purchaseOrderDetails.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrder removePurchaseOrderDetails(PurchaseOrderDetails purchaseOrderDetails) {
        this.purchaseOrderDetails.remove(purchaseOrderDetails);
        purchaseOrderDetails.setPurchaseOrder(null);
        return this;
    }

    public Set<GoodsRecived> getGoodsReciveds() {
        return this.goodsReciveds;
    }

    public void setGoodsReciveds(Set<GoodsRecived> goodsReciveds) {
        if (this.goodsReciveds != null) {
            this.goodsReciveds.forEach(i -> i.setPurchaseOrder(null));
        }
        if (goodsReciveds != null) {
            goodsReciveds.forEach(i -> i.setPurchaseOrder(this));
        }
        this.goodsReciveds = goodsReciveds;
    }

    public PurchaseOrder goodsReciveds(Set<GoodsRecived> goodsReciveds) {
        this.setGoodsReciveds(goodsReciveds);
        return this;
    }

    public PurchaseOrder addGoodsRecived(GoodsRecived goodsRecived) {
        this.goodsReciveds.add(goodsRecived);
        goodsRecived.setPurchaseOrder(this);
        return this;
    }

    public PurchaseOrder removeGoodsRecived(GoodsRecived goodsRecived) {
        this.goodsReciveds.remove(goodsRecived);
        goodsRecived.setPurchaseOrder(null);
        return this;
    }

    public ProductInventory getProductInventory() {
        return this.productInventory;
    }

    public void setProductInventory(ProductInventory productInventory) {
        this.productInventory = productInventory;
    }

    public PurchaseOrder productInventory(ProductInventory productInventory) {
        this.setProductInventory(productInventory);
        return this;
    }

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public PurchaseOrder warehouse(Warehouse warehouse) {
        this.setWarehouse(warehouse);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public PurchaseOrder securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public RawMaterialOrder getRawMaterialOrder() {
        return this.rawMaterialOrder;
    }

    public void setRawMaterialOrder(RawMaterialOrder rawMaterialOrder) {
        this.rawMaterialOrder = rawMaterialOrder;
    }

    public PurchaseOrder rawMaterialOrder(RawMaterialOrder rawMaterialOrder) {
        this.setRawMaterialOrder(rawMaterialOrder);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseOrder)) {
            return false;
        }
        return id != null && id.equals(((PurchaseOrder) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseOrder{" +
            "id=" + getId() +
            ", totalPOAmount=" + getTotalPOAmount() +
            ", totalGSTAmount=" + getTotalGSTAmount() +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", poDate='" + getPoDate() + "'" +
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
