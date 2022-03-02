package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ConsumptionDetails} entity.
 */
public class ConsumptionDetailsDTO implements Serializable {

    private Long id;

    private Instant comsumptionDate;

    private Double qtyConsumed;

    private String freeField1;

    private String freeField2;

    private Instant lastModified;

    private String lastModifiedBy;

    private ProductInventoryDTO productInventory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getComsumptionDate() {
        return comsumptionDate;
    }

    public void setComsumptionDate(Instant comsumptionDate) {
        this.comsumptionDate = comsumptionDate;
    }

    public Double getQtyConsumed() {
        return qtyConsumed;
    }

    public void setQtyConsumed(Double qtyConsumed) {
        this.qtyConsumed = qtyConsumed;
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

    public ProductInventoryDTO getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(ProductInventoryDTO productInventory) {
        this.productInventory = productInventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsumptionDetailsDTO)) {
            return false;
        }

        ConsumptionDetailsDTO consumptionDetailsDTO = (ConsumptionDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, consumptionDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsumptionDetailsDTO{" +
            "id=" + getId() +
            ", comsumptionDate='" + getComsumptionDate() + "'" +
            ", qtyConsumed=" + getQtyConsumed() +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", productInventory=" + getProductInventory() +
            "}";
    }
}
