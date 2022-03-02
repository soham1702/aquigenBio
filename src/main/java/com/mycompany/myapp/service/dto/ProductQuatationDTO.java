package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProductQuatation} entity.
 */
public class ProductQuatationDTO implements Serializable {

    private Long id;

    private Instant quatationdate;

    private Double totalAmt;

    private Double gst;

    private Double discount;

    private Instant expectedDelivery;

    private String deliveryAddress;

    private Instant quoValidity;

    private String clientName;

    private String clientMobile;

    private String clientEmail;

    private String termsAndCondition;

    private String notes;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private Instant lastModified;

    private String lastModifiedBy;

    private SecurityUserDTO securityUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getQuatationdate() {
        return quatationdate;
    }

    public void setQuatationdate(Instant quatationdate) {
        this.quatationdate = quatationdate;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Instant getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(Instant expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Instant getQuoValidity() {
        return quoValidity;
    }

    public void setQuoValidity(Instant quoValidity) {
        this.quoValidity = quoValidity;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientMobile() {
        return clientMobile;
    }

    public void setClientMobile(String clientMobile) {
        this.clientMobile = clientMobile;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getTermsAndCondition() {
        return termsAndCondition;
    }

    public void setTermsAndCondition(String termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return freeField4;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductQuatationDTO)) {
            return false;
        }

        ProductQuatationDTO productQuatationDTO = (ProductQuatationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productQuatationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductQuatationDTO{" +
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
            ", securityUser=" + getSecurityUser() +
            "}";
    }
}
