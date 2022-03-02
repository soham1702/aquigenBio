package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TranferRecieved.
 */
@Entity
@Table(name = "tranfer_recieved")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TranferRecieved implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transfer_date")
    private Instant transferDate;

    @Column(name = "qty_transfered")
    private Double qtyTransfered;

    @Column(name = "qty_received")
    private Double qtyReceived;

    @Column(name = "comment")
    private String comment;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "securityUsers", "transferDetails", "tranferDetailsApprovals", "tranferRecieveds", "productInventory" },
        allowSetters = true
    )
    private Transfer transfer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TranferRecieved id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTransferDate() {
        return this.transferDate;
    }

    public TranferRecieved transferDate(Instant transferDate) {
        this.setTransferDate(transferDate);
        return this;
    }

    public void setTransferDate(Instant transferDate) {
        this.transferDate = transferDate;
    }

    public Double getQtyTransfered() {
        return this.qtyTransfered;
    }

    public TranferRecieved qtyTransfered(Double qtyTransfered) {
        this.setQtyTransfered(qtyTransfered);
        return this;
    }

    public void setQtyTransfered(Double qtyTransfered) {
        this.qtyTransfered = qtyTransfered;
    }

    public Double getQtyReceived() {
        return this.qtyReceived;
    }

    public TranferRecieved qtyReceived(Double qtyReceived) {
        this.setQtyReceived(qtyReceived);
        return this;
    }

    public void setQtyReceived(Double qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public String getComment() {
        return this.comment;
    }

    public TranferRecieved comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public TranferRecieved freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public TranferRecieved freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public TranferRecieved lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public TranferRecieved lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public TranferRecieved isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public TranferRecieved isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Transfer getTransfer() {
        return this.transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public TranferRecieved transfer(Transfer transfer) {
        this.setTransfer(transfer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranferRecieved)) {
            return false;
        }
        return id != null && id.equals(((TranferRecieved) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TranferRecieved{" +
            "id=" + getId() +
            ", transferDate='" + getTransferDate() + "'" +
            ", qtyTransfered=" + getQtyTransfered() +
            ", qtyReceived=" + getQtyReceived() +
            ", comment='" + getComment() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
