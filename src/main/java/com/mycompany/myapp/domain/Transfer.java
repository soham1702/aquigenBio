package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transfer.
 */
@Entity
@Table(name = "transfer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tranfer_date")
    private Instant tranferDate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "is_recieved")
    private Boolean isRecieved;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "transfer")
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

    @OneToMany(mappedBy = "transfer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transfer" }, allowSetters = true)
    private Set<TransferDetails> transferDetails = new HashSet<>();

    @OneToMany(mappedBy = "transfer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transfer" }, allowSetters = true)
    private Set<TranferDetailsApprovals> tranferDetailsApprovals = new HashSet<>();

    @OneToMany(mappedBy = "transfer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transfer" }, allowSetters = true)
    private Set<TranferRecieved> tranferRecieveds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "transfers", "purchaseOrders", "consumptionDetails", "productTransactions", "products", "warehouses", "securityUsers" },
        allowSetters = true
    )
    private ProductInventory productInventory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transfer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTranferDate() {
        return this.tranferDate;
    }

    public Transfer tranferDate(Instant tranferDate) {
        this.setTranferDate(tranferDate);
        return this;
    }

    public void setTranferDate(Instant tranferDate) {
        this.tranferDate = tranferDate;
    }

    public String getComment() {
        return this.comment;
    }

    public Transfer comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public Transfer isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Boolean getIsRecieved() {
        return this.isRecieved;
    }

    public Transfer isRecieved(Boolean isRecieved) {
        this.setIsRecieved(isRecieved);
        return this;
    }

    public void setIsRecieved(Boolean isRecieved) {
        this.isRecieved = isRecieved;
    }

    public Status getStatus() {
        return this.status;
    }

    public Transfer status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Transfer freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Transfer freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Transfer lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Transfer lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<SecurityUser> getSecurityUsers() {
        return this.securityUsers;
    }

    public void setSecurityUsers(Set<SecurityUser> securityUsers) {
        if (this.securityUsers != null) {
            this.securityUsers.forEach(i -> i.setTransfer(null));
        }
        if (securityUsers != null) {
            securityUsers.forEach(i -> i.setTransfer(this));
        }
        this.securityUsers = securityUsers;
    }

    public Transfer securityUsers(Set<SecurityUser> securityUsers) {
        this.setSecurityUsers(securityUsers);
        return this;
    }

    public Transfer addSecurityUser(SecurityUser securityUser) {
        this.securityUsers.add(securityUser);
        securityUser.setTransfer(this);
        return this;
    }

    public Transfer removeSecurityUser(SecurityUser securityUser) {
        this.securityUsers.remove(securityUser);
        securityUser.setTransfer(null);
        return this;
    }

    public Set<TransferDetails> getTransferDetails() {
        return this.transferDetails;
    }

    public void setTransferDetails(Set<TransferDetails> transferDetails) {
        if (this.transferDetails != null) {
            this.transferDetails.forEach(i -> i.setTransfer(null));
        }
        if (transferDetails != null) {
            transferDetails.forEach(i -> i.setTransfer(this));
        }
        this.transferDetails = transferDetails;
    }

    public Transfer transferDetails(Set<TransferDetails> transferDetails) {
        this.setTransferDetails(transferDetails);
        return this;
    }

    public Transfer addTransferDetails(TransferDetails transferDetails) {
        this.transferDetails.add(transferDetails);
        transferDetails.setTransfer(this);
        return this;
    }

    public Transfer removeTransferDetails(TransferDetails transferDetails) {
        this.transferDetails.remove(transferDetails);
        transferDetails.setTransfer(null);
        return this;
    }

    public Set<TranferDetailsApprovals> getTranferDetailsApprovals() {
        return this.tranferDetailsApprovals;
    }

    public void setTranferDetailsApprovals(Set<TranferDetailsApprovals> tranferDetailsApprovals) {
        if (this.tranferDetailsApprovals != null) {
            this.tranferDetailsApprovals.forEach(i -> i.setTransfer(null));
        }
        if (tranferDetailsApprovals != null) {
            tranferDetailsApprovals.forEach(i -> i.setTransfer(this));
        }
        this.tranferDetailsApprovals = tranferDetailsApprovals;
    }

    public Transfer tranferDetailsApprovals(Set<TranferDetailsApprovals> tranferDetailsApprovals) {
        this.setTranferDetailsApprovals(tranferDetailsApprovals);
        return this;
    }

    public Transfer addTranferDetailsApprovals(TranferDetailsApprovals tranferDetailsApprovals) {
        this.tranferDetailsApprovals.add(tranferDetailsApprovals);
        tranferDetailsApprovals.setTransfer(this);
        return this;
    }

    public Transfer removeTranferDetailsApprovals(TranferDetailsApprovals tranferDetailsApprovals) {
        this.tranferDetailsApprovals.remove(tranferDetailsApprovals);
        tranferDetailsApprovals.setTransfer(null);
        return this;
    }

    public Set<TranferRecieved> getTranferRecieveds() {
        return this.tranferRecieveds;
    }

    public void setTranferRecieveds(Set<TranferRecieved> tranferRecieveds) {
        if (this.tranferRecieveds != null) {
            this.tranferRecieveds.forEach(i -> i.setTransfer(null));
        }
        if (tranferRecieveds != null) {
            tranferRecieveds.forEach(i -> i.setTransfer(this));
        }
        this.tranferRecieveds = tranferRecieveds;
    }

    public Transfer tranferRecieveds(Set<TranferRecieved> tranferRecieveds) {
        this.setTranferRecieveds(tranferRecieveds);
        return this;
    }

    public Transfer addTranferRecieved(TranferRecieved tranferRecieved) {
        this.tranferRecieveds.add(tranferRecieved);
        tranferRecieved.setTransfer(this);
        return this;
    }

    public Transfer removeTranferRecieved(TranferRecieved tranferRecieved) {
        this.tranferRecieveds.remove(tranferRecieved);
        tranferRecieved.setTransfer(null);
        return this;
    }

    public ProductInventory getProductInventory() {
        return this.productInventory;
    }

    public void setProductInventory(ProductInventory productInventory) {
        this.productInventory = productInventory;
    }

    public Transfer productInventory(ProductInventory productInventory) {
        this.setProductInventory(productInventory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transfer)) {
            return false;
        }
        return id != null && id.equals(((Transfer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transfer{" +
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
            "}";
    }
}
