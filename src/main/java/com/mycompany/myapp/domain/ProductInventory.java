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
 * A ProductInventory.
 */
@Entity
@Table(name = "product_inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "inward_outward_date")
    private Instant inwardOutwardDate;

    @Column(name = "inward_qty")
    private String inwardQty;

    @Column(name = "outward_qty")
    private String outwardQty;

    @Column(name = "total_quanity")
    private String totalQuanity;

    @Column(name = "price_per_unit")
    private Long pricePerUnit;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "expiry_date")
    private Instant expiryDate;

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

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "productInventory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "securityUsers", "transferDetails", "tranferDetailsApprovals", "tranferRecieveds", "productInventory" },
        allowSetters = true
    )
    private Set<Transfer> transfers = new HashSet<>();

    @OneToMany(mappedBy = "productInventory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "purchaseOrderDetails", "goodsReciveds", "productInventory", "warehouse", "securityUser", "rawMaterialOrder" },
        allowSetters = true
    )
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();

    @OneToMany(mappedBy = "productInventory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityUsers", "productInventory" }, allowSetters = true)
    private Set<ConsumptionDetails> consumptionDetails = new HashSet<>();

    @OneToMany(mappedBy = "productInventory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "warehouse", "products", "securityUser", "productInventory" }, allowSetters = true)
    private Set<ProductTransaction> productTransactions = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_product_inventory__product",
        joinColumns = @JoinColumn(name = "product_inventory_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
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

    @ManyToMany
    @JoinTable(
        name = "rel_product_inventory__warehouse",
        joinColumns = @JoinColumn(name = "product_inventory_id"),
        inverseJoinColumns = @JoinColumn(name = "warehouse_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "purchaseOrders", "securityUsers", "productTransaction", "productInventories" }, allowSetters = true)
    private Set<Warehouse> warehouses = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_product_inventory__security_user",
        joinColumns = @JoinColumn(name = "product_inventory_id"),
        inverseJoinColumns = @JoinColumn(name = "security_user_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<SecurityUser> securityUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductInventory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInwardOutwardDate() {
        return this.inwardOutwardDate;
    }

    public ProductInventory inwardOutwardDate(Instant inwardOutwardDate) {
        this.setInwardOutwardDate(inwardOutwardDate);
        return this;
    }

    public void setInwardOutwardDate(Instant inwardOutwardDate) {
        this.inwardOutwardDate = inwardOutwardDate;
    }

    public String getInwardQty() {
        return this.inwardQty;
    }

    public ProductInventory inwardQty(String inwardQty) {
        this.setInwardQty(inwardQty);
        return this;
    }

    public void setInwardQty(String inwardQty) {
        this.inwardQty = inwardQty;
    }

    public String getOutwardQty() {
        return this.outwardQty;
    }

    public ProductInventory outwardQty(String outwardQty) {
        this.setOutwardQty(outwardQty);
        return this;
    }

    public void setOutwardQty(String outwardQty) {
        this.outwardQty = outwardQty;
    }

    public String getTotalQuanity() {
        return this.totalQuanity;
    }

    public ProductInventory totalQuanity(String totalQuanity) {
        this.setTotalQuanity(totalQuanity);
        return this;
    }

    public void setTotalQuanity(String totalQuanity) {
        this.totalQuanity = totalQuanity;
    }

    public Long getPricePerUnit() {
        return this.pricePerUnit;
    }

    public ProductInventory pricePerUnit(Long pricePerUnit) {
        this.setPricePerUnit(pricePerUnit);
        return this;
    }

    public void setPricePerUnit(Long pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getLotNo() {
        return this.lotNo;
    }

    public ProductInventory lotNo(String lotNo) {
        this.setLotNo(lotNo);
        return this;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public ProductInventory expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public ProductInventory freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public ProductInventory freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public ProductInventory freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public ProductInventory freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public ProductInventory lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ProductInventory lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public ProductInventory isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public ProductInventory isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Transfer> getTransfers() {
        return this.transfers;
    }

    public void setTransfers(Set<Transfer> transfers) {
        if (this.transfers != null) {
            this.transfers.forEach(i -> i.setProductInventory(null));
        }
        if (transfers != null) {
            transfers.forEach(i -> i.setProductInventory(this));
        }
        this.transfers = transfers;
    }

    public ProductInventory transfers(Set<Transfer> transfers) {
        this.setTransfers(transfers);
        return this;
    }

    public ProductInventory addTransfer(Transfer transfer) {
        this.transfers.add(transfer);
        transfer.setProductInventory(this);
        return this;
    }

    public ProductInventory removeTransfer(Transfer transfer) {
        this.transfers.remove(transfer);
        transfer.setProductInventory(null);
        return this;
    }

    public Set<PurchaseOrder> getPurchaseOrders() {
        return this.purchaseOrders;
    }

    public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        if (this.purchaseOrders != null) {
            this.purchaseOrders.forEach(i -> i.setProductInventory(null));
        }
        if (purchaseOrders != null) {
            purchaseOrders.forEach(i -> i.setProductInventory(this));
        }
        this.purchaseOrders = purchaseOrders;
    }

    public ProductInventory purchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.setPurchaseOrders(purchaseOrders);
        return this;
    }

    public ProductInventory addPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.add(purchaseOrder);
        purchaseOrder.setProductInventory(this);
        return this;
    }

    public ProductInventory removePurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.remove(purchaseOrder);
        purchaseOrder.setProductInventory(null);
        return this;
    }

    public Set<ConsumptionDetails> getConsumptionDetails() {
        return this.consumptionDetails;
    }

    public void setConsumptionDetails(Set<ConsumptionDetails> consumptionDetails) {
        if (this.consumptionDetails != null) {
            this.consumptionDetails.forEach(i -> i.setProductInventory(null));
        }
        if (consumptionDetails != null) {
            consumptionDetails.forEach(i -> i.setProductInventory(this));
        }
        this.consumptionDetails = consumptionDetails;
    }

    public ProductInventory consumptionDetails(Set<ConsumptionDetails> consumptionDetails) {
        this.setConsumptionDetails(consumptionDetails);
        return this;
    }

    public ProductInventory addConsumptionDetails(ConsumptionDetails consumptionDetails) {
        this.consumptionDetails.add(consumptionDetails);
        consumptionDetails.setProductInventory(this);
        return this;
    }

    public ProductInventory removeConsumptionDetails(ConsumptionDetails consumptionDetails) {
        this.consumptionDetails.remove(consumptionDetails);
        consumptionDetails.setProductInventory(null);
        return this;
    }

    public Set<ProductTransaction> getProductTransactions() {
        return this.productTransactions;
    }

    public void setProductTransactions(Set<ProductTransaction> productTransactions) {
        if (this.productTransactions != null) {
            this.productTransactions.forEach(i -> i.setProductInventory(null));
        }
        if (productTransactions != null) {
            productTransactions.forEach(i -> i.setProductInventory(this));
        }
        this.productTransactions = productTransactions;
    }

    public ProductInventory productTransactions(Set<ProductTransaction> productTransactions) {
        this.setProductTransactions(productTransactions);
        return this;
    }

    public ProductInventory addProductTransaction(ProductTransaction productTransaction) {
        this.productTransactions.add(productTransaction);
        productTransaction.setProductInventory(this);
        return this;
    }

    public ProductInventory removeProductTransaction(ProductTransaction productTransaction) {
        this.productTransactions.remove(productTransaction);
        productTransaction.setProductInventory(null);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public ProductInventory products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public ProductInventory addProduct(Product product) {
        this.products.add(product);
        product.getProductInventories().add(this);
        return this;
    }

    public ProductInventory removeProduct(Product product) {
        this.products.remove(product);
        product.getProductInventories().remove(this);
        return this;
    }

    public Set<Warehouse> getWarehouses() {
        return this.warehouses;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public ProductInventory warehouses(Set<Warehouse> warehouses) {
        this.setWarehouses(warehouses);
        return this;
    }

    public ProductInventory addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
        warehouse.getProductInventories().add(this);
        return this;
    }

    public ProductInventory removeWarehouse(Warehouse warehouse) {
        this.warehouses.remove(warehouse);
        warehouse.getProductInventories().remove(this);
        return this;
    }

    public Set<SecurityUser> getSecurityUsers() {
        return this.securityUsers;
    }

    public void setSecurityUsers(Set<SecurityUser> securityUsers) {
        this.securityUsers = securityUsers;
    }

    public ProductInventory securityUsers(Set<SecurityUser> securityUsers) {
        this.setSecurityUsers(securityUsers);
        return this;
    }

    public ProductInventory addSecurityUser(SecurityUser securityUser) {
        this.securityUsers.add(securityUser);
        securityUser.getProductInventories().add(this);
        return this;
    }

    public ProductInventory removeSecurityUser(SecurityUser securityUser) {
        this.securityUsers.remove(securityUser);
        securityUser.getProductInventories().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductInventory)) {
            return false;
        }
        return id != null && id.equals(((ProductInventory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductInventory{" +
            "id=" + getId() +
            ", inwardOutwardDate='" + getInwardOutwardDate() + "'" +
            ", inwardQty='" + getInwardQty() + "'" +
            ", outwardQty='" + getOutwardQty() + "'" +
            ", totalQuanity='" + getTotalQuanity() + "'" +
            ", pricePerUnit=" + getPricePerUnit() +
            ", lotNo='" + getLotNo() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
