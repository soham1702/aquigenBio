package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.RawMaterialOrder} entity.
 */
public class RawMaterialOrderDTO implements Serializable {

    private Long id;

    private Double pricePerUnit;

    private String quantityUnit;

    private Double quantity;

    private Instant deliveryDate;

    private String quantityCheck;

    private Instant orderedOn;

    private String orderStatus;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Instant getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getQuantityCheck() {
        return quantityCheck;
    }

    public void setQuantityCheck(String quantityCheck) {
        this.quantityCheck = quantityCheck;
    }

    public Instant getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(Instant orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterialOrderDTO)) {
            return false;
        }

        RawMaterialOrderDTO rawMaterialOrderDTO = (RawMaterialOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rawMaterialOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialOrderDTO{" +
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
