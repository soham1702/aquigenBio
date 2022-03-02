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
 * A SecurityUser.
 */
@Entity
@Table(name = "security_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecurityUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "business_title")
    private String businessTitle;

    @Column(name = "g_st_details")
    private String gSTDetails;

    @NotNull
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @Column(name = "lang_key")
    private String langKey;

    @Column(name = "activation_key")
    private String activationKey;

    @Column(name = "reset_key")
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "otp_expiry_time")
    private Instant otpExpiryTime;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "warehouse", "products", "securityUser", "productInventory" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ProductTransaction productTransaction;

    @OneToMany(mappedBy = "securityUser")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "purchaseOrderDetails", "goodsReciveds", "productInventory", "warehouse", "securityUser", "rawMaterialOrder" },
        allowSetters = true
    )
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();

    @OneToMany(mappedBy = "securityUser")
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
        name = "rel_security_user__security_permission",
        joinColumns = @JoinColumn(name = "security_user_id"),
        inverseJoinColumns = @JoinColumn(name = "security_permission_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityRoles", "securityUsers" }, allowSetters = true)
    private Set<SecurityPermission> securityPermissions = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_security_user__security_role",
        joinColumns = @JoinColumn(name = "security_user_id"),
        inverseJoinColumns = @JoinColumn(name = "security_role_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityPermissions", "securityUsers" }, allowSetters = true)
    private Set<SecurityRole> securityRoles = new HashSet<>();

    @JsonIgnoreProperties(value = { "securityUser", "quatationDetails" }, allowSetters = true)
    @OneToOne(mappedBy = "securityUser")
    private ProductQuatation productQuatation;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "securityUsers", "transferDetails", "tranferDetailsApprovals", "tranferRecieveds", "productInventory" },
        allowSetters = true
    )
    private Transfer transfer;

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityUsers", "productInventory" }, allowSetters = true)
    private ConsumptionDetails consumptionDetails;

    @ManyToMany(mappedBy = "securityUsers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "transfers", "purchaseOrders", "consumptionDetails", "productTransactions", "products", "warehouses", "securityUsers" },
        allowSetters = true
    )
    private Set<ProductInventory> productInventories = new HashSet<>();

    @ManyToMany(mappedBy = "securityUsers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "purchaseOrders", "securityUsers", "productTransaction", "productInventories" }, allowSetters = true)
    private Set<Warehouse> warehouses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SecurityUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public SecurityUser firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public SecurityUser lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return this.designation;
    }

    public SecurityUser designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBusinessTitle() {
        return this.businessTitle;
    }

    public SecurityUser businessTitle(String businessTitle) {
        this.setBusinessTitle(businessTitle);
        return this;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    public String getgSTDetails() {
        return this.gSTDetails;
    }

    public SecurityUser gSTDetails(String gSTDetails) {
        this.setgSTDetails(gSTDetails);
        return this;
    }

    public void setgSTDetails(String gSTDetails) {
        this.gSTDetails = gSTDetails;
    }

    public String getLogin() {
        return this.login;
    }

    public SecurityUser login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public SecurityUser passwordHash(String passwordHash) {
        this.setPasswordHash(passwordHash);
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return this.email;
    }

    public SecurityUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public SecurityUser imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public SecurityUser activated(Boolean activated) {
        this.setActivated(activated);
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return this.langKey;
    }

    public SecurityUser langKey(String langKey) {
        this.setLangKey(langKey);
        return this;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return this.activationKey;
    }

    public SecurityUser activationKey(String activationKey) {
        this.setActivationKey(activationKey);
        return this;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return this.resetKey;
    }

    public SecurityUser resetKey(String resetKey) {
        this.setResetKey(resetKey);
        return this;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return this.resetDate;
    }

    public SecurityUser resetDate(Instant resetDate) {
        this.setResetDate(resetDate);
        return this;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public SecurityUser mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOneTimePassword() {
        return this.oneTimePassword;
    }

    public SecurityUser oneTimePassword(String oneTimePassword) {
        this.setOneTimePassword(oneTimePassword);
        return this;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public Instant getOtpExpiryTime() {
        return this.otpExpiryTime;
    }

    public SecurityUser otpExpiryTime(Instant otpExpiryTime) {
        this.setOtpExpiryTime(otpExpiryTime);
        return this;
    }

    public void setOtpExpiryTime(Instant otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SecurityUser lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SecurityUser lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ProductTransaction getProductTransaction() {
        return this.productTransaction;
    }

    public void setProductTransaction(ProductTransaction productTransaction) {
        this.productTransaction = productTransaction;
    }

    public SecurityUser productTransaction(ProductTransaction productTransaction) {
        this.setProductTransaction(productTransaction);
        return this;
    }

    public Set<PurchaseOrder> getPurchaseOrders() {
        return this.purchaseOrders;
    }

    public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        if (this.purchaseOrders != null) {
            this.purchaseOrders.forEach(i -> i.setSecurityUser(null));
        }
        if (purchaseOrders != null) {
            purchaseOrders.forEach(i -> i.setSecurityUser(this));
        }
        this.purchaseOrders = purchaseOrders;
    }

    public SecurityUser purchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.setPurchaseOrders(purchaseOrders);
        return this;
    }

    public SecurityUser addPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.add(purchaseOrder);
        purchaseOrder.setSecurityUser(this);
        return this;
    }

    public SecurityUser removePurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.remove(purchaseOrder);
        purchaseOrder.setSecurityUser(null);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setSecurityUser(null));
        }
        if (products != null) {
            products.forEach(i -> i.setSecurityUser(this));
        }
        this.products = products;
    }

    public SecurityUser products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public SecurityUser addProduct(Product product) {
        this.products.add(product);
        product.setSecurityUser(this);
        return this;
    }

    public SecurityUser removeProduct(Product product) {
        this.products.remove(product);
        product.setSecurityUser(null);
        return this;
    }

    public Set<SecurityPermission> getSecurityPermissions() {
        return this.securityPermissions;
    }

    public void setSecurityPermissions(Set<SecurityPermission> securityPermissions) {
        this.securityPermissions = securityPermissions;
    }

    public SecurityUser securityPermissions(Set<SecurityPermission> securityPermissions) {
        this.setSecurityPermissions(securityPermissions);
        return this;
    }

    public SecurityUser addSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.add(securityPermission);
        securityPermission.getSecurityUsers().add(this);
        return this;
    }

    public SecurityUser removeSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.remove(securityPermission);
        securityPermission.getSecurityUsers().remove(this);
        return this;
    }

    public Set<SecurityRole> getSecurityRoles() {
        return this.securityRoles;
    }

    public void setSecurityRoles(Set<SecurityRole> securityRoles) {
        this.securityRoles = securityRoles;
    }

    public SecurityUser securityRoles(Set<SecurityRole> securityRoles) {
        this.setSecurityRoles(securityRoles);
        return this;
    }

    public SecurityUser addSecurityRole(SecurityRole securityRole) {
        this.securityRoles.add(securityRole);
        securityRole.getSecurityUsers().add(this);
        return this;
    }

    public SecurityUser removeSecurityRole(SecurityRole securityRole) {
        this.securityRoles.remove(securityRole);
        securityRole.getSecurityUsers().remove(this);
        return this;
    }

    public ProductQuatation getProductQuatation() {
        return this.productQuatation;
    }

    public void setProductQuatation(ProductQuatation productQuatation) {
        if (this.productQuatation != null) {
            this.productQuatation.setSecurityUser(null);
        }
        if (productQuatation != null) {
            productQuatation.setSecurityUser(this);
        }
        this.productQuatation = productQuatation;
    }

    public SecurityUser productQuatation(ProductQuatation productQuatation) {
        this.setProductQuatation(productQuatation);
        return this;
    }

    public Transfer getTransfer() {
        return this.transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public SecurityUser transfer(Transfer transfer) {
        this.setTransfer(transfer);
        return this;
    }

    public ConsumptionDetails getConsumptionDetails() {
        return this.consumptionDetails;
    }

    public void setConsumptionDetails(ConsumptionDetails consumptionDetails) {
        this.consumptionDetails = consumptionDetails;
    }

    public SecurityUser consumptionDetails(ConsumptionDetails consumptionDetails) {
        this.setConsumptionDetails(consumptionDetails);
        return this;
    }

    public Set<ProductInventory> getProductInventories() {
        return this.productInventories;
    }

    public void setProductInventories(Set<ProductInventory> productInventories) {
        if (this.productInventories != null) {
            this.productInventories.forEach(i -> i.removeSecurityUser(this));
        }
        if (productInventories != null) {
            productInventories.forEach(i -> i.addSecurityUser(this));
        }
        this.productInventories = productInventories;
    }

    public SecurityUser productInventories(Set<ProductInventory> productInventories) {
        this.setProductInventories(productInventories);
        return this;
    }

    public SecurityUser addProductInventory(ProductInventory productInventory) {
        this.productInventories.add(productInventory);
        productInventory.getSecurityUsers().add(this);
        return this;
    }

    public SecurityUser removeProductInventory(ProductInventory productInventory) {
        this.productInventories.remove(productInventory);
        productInventory.getSecurityUsers().remove(this);
        return this;
    }

    public Set<Warehouse> getWarehouses() {
        return this.warehouses;
    }

    public void setWarehouses(Set<Warehouse> warehouses) {
        if (this.warehouses != null) {
            this.warehouses.forEach(i -> i.removeSecurityUser(this));
        }
        if (warehouses != null) {
            warehouses.forEach(i -> i.addSecurityUser(this));
        }
        this.warehouses = warehouses;
    }

    public SecurityUser warehouses(Set<Warehouse> warehouses) {
        this.setWarehouses(warehouses);
        return this;
    }

    public SecurityUser addWarehouse(Warehouse warehouse) {
        this.warehouses.add(warehouse);
        warehouse.getSecurityUsers().add(this);
        return this;
    }

    public SecurityUser removeWarehouse(Warehouse warehouse) {
        this.warehouses.remove(warehouse);
        warehouse.getSecurityUsers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityUser)) {
            return false;
        }
        return id != null && id.equals(((SecurityUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", businessTitle='" + getBusinessTitle() + "'" +
            ", gSTDetails='" + getgSTDetails() + "'" +
            ", login='" + getLogin() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", email='" + getEmail() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", activated='" + getActivated() + "'" +
            ", langKey='" + getLangKey() + "'" +
            ", activationKey='" + getActivationKey() + "'" +
            ", resetKey='" + getResetKey() + "'" +
            ", resetDate='" + getResetDate() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", oneTimePassword='" + getOneTimePassword() + "'" +
            ", otpExpiryTime='" + getOtpExpiryTime() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
