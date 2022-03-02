package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Transfer} entity.
 */
public class TransferDTO implements Serializable {

    private Long id;

    private Instant tranferDate;

    private String comment;

    private Boolean isApproved;

    private Boolean isRecieved;

    private Status status;

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

    public Instant getTranferDate() {
        return tranferDate;
    }

    public void setTranferDate(Instant tranferDate) {
        this.tranferDate = tranferDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsRecieved() {
        return isRecieved;
    }

    public void setIsRecieved(Boolean isRecieved) {
        this.isRecieved = isRecieved;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        if (!(o instanceof TransferDTO)) {
            return false;
        }

        TransferDTO transferDTO = (TransferDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transferDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDTO{" +
            "id=" + getId() +
            ", tranferDate='" + getTranferDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", isRecieved='" + getIsRecieved() + "'" +
            ", status='" + getStatus() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", productInventory=" + getProductInventory() +
            "}";
    }
}
