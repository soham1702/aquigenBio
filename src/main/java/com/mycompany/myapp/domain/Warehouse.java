package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Warehouse.
 */
@Entity
@Table(name = "warehouse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "wh_name")
    private String whName;

    @Column(name = "address")
    private String address;

    @Column(name = "pincode")
    private Integer pincode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "g_st_details")
    private String gSTDetails;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "manager_email")
    private String managerEmail;

    @Column(name = "manager_contact")
    private String managerContact;

    @Column(name = "contact")
    private String contact;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @OneToMany(mappedBy = "warehouse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "purchaseOrderDetails", "goodsReciveds", "productInventory", "warehouse", "securityUser", "rawMaterialOrder" },
        allowSetters = true
    )
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_warehouse__security_user",
        joinColumns = @JoinColumn(name = "warehouse_id"),
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

    @JsonIgnoreProperties(value = { "warehouse", "products", "securityUser", "productInventory" }, allowSetters = true)
    @OneToOne(mappedBy = "warehouse")
    private ProductTransaction productTransaction;

    @ManyToMany(mappedBy = "warehouses")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "transfers", "purchaseOrders", "consumptionDetails", "productTransactions", "products", "warehouses", "securityUsers" },
        allowSetters = true
    )
    private Set<ProductInventory> productInventories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Warehouse id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWarehouseId() {
        return this.warehouseId;
    }

    public Warehouse warehouseId(Long warehouseId) {
        this.setWarehouseId(warehouseId);
        return this;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWhName() {
        return this.whName;
    }

    public Warehouse whName(String whName) {
        this.setWhName(whName);
        return this;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public String getAddress() {
        return this.address;
    }

    public Warehouse address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPincode() {
        return this.pincode;
    }

    public Warehouse pincode(Integer pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return this.city;
    }

    public Warehouse city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Warehouse state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public Warehouse country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getgSTDetails() {
        return this.gSTDetails;
    }

    public Warehouse gSTDetails(String gSTDetails) {
        this.setgSTDetails(gSTDetails);
        return this;
    }

    public void setgSTDetails(String gSTDetails) {
        this.gSTDetails = gSTDetails;
    }

    public String getManagerName() {
        return this.managerName;
    }

    public Warehouse managerName(String managerName) {
        this.setManagerName(managerName);
        return this;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerEmail() {
        return this.managerEmail;
    }

    public Warehouse managerEmail(String managerEmail) {
        this.setManagerEmail(managerEmail);
        return this;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getManagerContact() {
        return this.managerContact;
    }

    public Warehouse managerContact(String managerContact) {
        this.setManagerContact(managerContact);
        return this;
    }

    public void setManagerContact(String managerContact) {
        this.managerContact = managerContact;
    }

    public String getContact() {
        return this.contact;
    }

    public Warehouse contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Warehouse isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Warehouse isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Warehouse lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Warehouse lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<PurchaseOrder> getPurchaseOrders() {
        return this.purchaseOrders;
    }

    public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        if (this.purchaseOrders != null) {
            this.purchaseOrders.forEach(i -> i.setWarehouse(null));
        }
        if (purchaseOrders != null) {
            purchaseOrders.forEach(i -> i.setWarehouse(this));
        }
        this.purchaseOrders = purchaseOrders;
    }

    public Warehouse purchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.setPurchaseOrders(purchaseOrders);
        return this;
    }

    public Warehouse addPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.add(purchaseOrder);
        purchaseOrder.setWarehouse(this);
        return this;
    }

    public Warehouse removePurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.remove(purchaseOrder);
        purchaseOrder.setWarehouse(null);
        return this;
    }

    public Set<SecurityUser> getSecurityUsers() {
        return this.securityUsers;
    }

    public void setSecurityUsers(Set<SecurityUser> securityUsers) {
        this.securityUsers = securityUsers;
    }

    public Warehouse securityUsers(Set<SecurityUser> securityUsers) {
        this.setSecurityUsers(securityUsers);
        return this;
    }

    public Warehouse addSecurityUser(SecurityUser securityUser) {
        this.securityUsers.add(securityUser);
        securityUser.getWarehouses().add(this);
        return this;
    }

    public Warehouse removeSecurityUser(SecurityUser securityUser) {
        this.securityUsers.remove(securityUser);
        securityUser.getWarehouses().remove(this);
        return this;
    }

    public ProductTransaction getProductTransaction() {
        return this.productTransaction;
    }

    public void setProductTransaction(ProductTransaction productTransaction) {
        if (this.productTransaction != null) {
            this.productTransaction.setWarehouse(null);
        }
        if (productTransaction != null) {
            productTransaction.setWarehouse(this);
        }
        this.productTransaction = productTransaction;
    }

    public Warehouse productTransaction(ProductTransaction productTransaction) {
        this.setProductTransaction(productTransaction);
        return this;
    }

    public Set<ProductInventory> getProductInventories() {
        return this.productInventories;
    }

    public void setProductInventories(Set<ProductInventory> productInventories) {
        if (this.productInventories != null) {
            this.productInventories.forEach(i -> i.removeWarehouse(this));
        }
        if (productInventories != null) {
            productInventories.forEach(i -> i.addWarehouse(this));
        }
        this.productInventories = productInventories;
    }

    public Warehouse productInventories(Set<ProductInventory> productInventories) {
        this.setProductInventories(productInventories);
        return this;
    }

    public Warehouse addProductInventory(ProductInventory productInventory) {
        this.productInventories.add(productInventory);
        productInventory.getWarehouses().add(this);
        return this;
    }

    public Warehouse removeProductInventory(ProductInventory productInventory) {
        this.productInventories.remove(productInventory);
        productInventory.getWarehouses().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Warehouse)) {
            return false;
        }
        return id != null && id.equals(((Warehouse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Warehouse{" +
            "id=" + getId() +
            ", warehouseId=" + getWarehouseId() +
            ", whName='" + getWhName() + "'" +
            ", address='" + getAddress() + "'" +
            ", pincode=" + getPincode() +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", gSTDetails='" + getgSTDetails() + "'" +
            ", managerName='" + getManagerName() + "'" +
            ", managerEmail='" + getManagerEmail() + "'" +
            ", managerContact='" + getManagerContact() + "'" +
            ", contact='" + getContact() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
