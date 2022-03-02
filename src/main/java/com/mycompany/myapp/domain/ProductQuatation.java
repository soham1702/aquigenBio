package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductQuatation.
 */
@Entity
@Table(name = "product_quatation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductQuatation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quatationdate")
    private Instant quatationdate;

    @Column(name = "total_amt")
    private Double totalAmt;

    @Column(name = "gst")
    private Double gst;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "expected_delivery")
    private Instant expectedDelivery;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "quo_validity")
    private Instant quoValidity;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_mobile")
    private String clientMobile;

    @Column(name = "client_email")
    private String clientEmail;

    @Column(name = "terms_and_condition")
    private String termsAndCondition;

    @Column(name = "notes")
    private String notes;

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
    @OneToOne
    @JoinColumn(unique = true)
    private SecurityUser securityUser;

    @JsonIgnoreProperties(value = { "product", "unit", "categories", "productQuatation" }, allowSetters = true)
    @OneToOne(mappedBy = "productQuatation")
    private QuatationDetails quatationDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductQuatation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getQuatationdate() {
        return this.quatationdate;
    }

    public ProductQuatation quatationdate(Instant quatationdate) {
        this.setQuatationdate(quatationdate);
        return this;
    }

    public void setQuatationdate(Instant quatationdate) {
        this.quatationdate = quatationdate;
    }

    public Double getTotalAmt() {
        return this.totalAmt;
    }

    public ProductQuatation totalAmt(Double totalAmt) {
        this.setTotalAmt(totalAmt);
        return this;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Double getGst() {
        return this.gst;
    }

    public ProductQuatation gst(Double gst) {
        this.setGst(gst);
        return this;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public ProductQuatation discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Instant getExpectedDelivery() {
        return this.expectedDelivery;
    }

    public ProductQuatation expectedDelivery(Instant expectedDelivery) {
        this.setExpectedDelivery(expectedDelivery);
        return this;
    }

    public void setExpectedDelivery(Instant expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public ProductQuatation deliveryAddress(String deliveryAddress) {
        this.setDeliveryAddress(deliveryAddress);
        return this;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Instant getQuoValidity() {
        return this.quoValidity;
    }

    public ProductQuatation quoValidity(Instant quoValidity) {
        this.setQuoValidity(quoValidity);
        return this;
    }

    public void setQuoValidity(Instant quoValidity) {
        this.quoValidity = quoValidity;
    }

    public String getClientName() {
        return this.clientName;
    }

    public ProductQuatation clientName(String clientName) {
        this.setClientName(clientName);
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientMobile() {
        return this.clientMobile;
    }

    public ProductQuatation clientMobile(String clientMobile) {
        this.setClientMobile(clientMobile);
        return this;
    }

    public void setClientMobile(String clientMobile) {
        this.clientMobile = clientMobile;
    }

    public String getClientEmail() {
        return this.clientEmail;
    }

    public ProductQuatation clientEmail(String clientEmail) {
        this.setClientEmail(clientEmail);
        return this;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getTermsAndCondition() {
        return this.termsAndCondition;
    }

    public ProductQuatation termsAndCondition(String termsAndCondition) {
        this.setTermsAndCondition(termsAndCondition);
        return this;
    }

    public void setTermsAndCondition(String termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
    }

    public String getNotes() {
        return this.notes;
    }

    public ProductQuatation notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public ProductQuatation freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public ProductQuatation freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public ProductQuatation freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public ProductQuatation freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public ProductQuatation lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ProductQuatation lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public ProductQuatation securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public QuatationDetails getQuatationDetails() {
        return this.quatationDetails;
    }

    public void setQuatationDetails(QuatationDetails quatationDetails) {
        if (this.quatationDetails != null) {
            this.quatationDetails.setProductQuatation(null);
        }
        if (quatationDetails != null) {
            quatationDetails.setProductQuatation(this);
        }
        this.quatationDetails = quatationDetails;
    }

    public ProductQuatation quatationDetails(QuatationDetails quatationDetails) {
        this.setQuatationDetails(quatationDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductQuatation)) {
            return false;
        }
        return id != null && id.equals(((ProductQuatation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductQuatation{" +
            "id=" + getId() +
            ", quatationdate='" + getQuatationdate() + "'" +
            ", totalAmt=" + getTotalAmt() +
            ", gst=" + getGst() +
            ", discount=" + getDiscount() +
            ", expectedDelivery='" + getExpectedDelivery() + "'" +
            ", deliveryAddress='" + getDeliveryAddress() + "'" +
            ", quoValidity='" + getQuoValidity() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", clientMobile='" + getClientMobile() + "'" +
            ", clientEmail='" + getClientEmail() + "'" +
            ", termsAndCondition='" + getTermsAndCondition() + "'" +
            ", notes='" + getNotes() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
