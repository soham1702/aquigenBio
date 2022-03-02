package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PurchaseOrder} entity.
 */
public class PurchaseOrderDTO implements Serializable {

    private Long id;

    private Double totalPOAmount;

    private Double totalGSTAmount;

    private Instant expectedDeliveryDate;

    private Instant poDate;

    private Status orderStatus;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private ProductInventoryDTO productInventory;

    private WarehouseDTO warehouse;

    private SecurityUserDTO securityUser;

    private RawMaterialOrderDTO rawMaterialOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPOAmount() {
        return totalPOAmount;
    }

    public void setTotalPOAmount(Double totalPOAmount) {
        this.totalPOAmount = totalPOAmount;
    }

    public Double getTotalGSTAmount() {
        return totalGSTAmount;
    }

    public void setTotalGSTAmount(Double totalGSTAmount) {
        this.totalGSTAmount = totalGSTAmount;
    }

    public Instant getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Instant expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Instant getPoDate() {
        return poDate;
    }

    public void setPoDate(Instant poDate) {
        this.poDate = poDate;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
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

    public ProductInventoryDTO getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(ProductInventoryDTO productInventory) {
        this.productInventory = productInventory;
    }

    public WarehouseDTO getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDTO warehouse) {
        this.warehouse = warehouse;
    }

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    public RawMaterialOrderDTO getRawMaterialOrder() {
        return rawMaterialOrder;
    }

    public void setRawMaterialOrder(RawMaterialOrderDTO rawMaterialOrder) {
        this.rawMaterialOrder = rawMaterialOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseOrderDTO)) {
            return false;
        }

        PurchaseOrderDTO purchaseOrderDTO = (PurchaseOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, purchaseOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseOrderDTO{" +
            "id=" + getId() +
            ", totalPOAmount=" + getTotalPOAmount() +
            ", totalGSTAmount=" + getTotalGSTAmount() +
            ", expectedDeliveryDate='" + getExpectedDeliveryDate() + "'" +
            ", poDate='" + getPoDate() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", productInventory=" + getProductInventory() +
            ", warehouse=" + getWarehouse() +
            ", securityUser=" + getSecurityUser() +
            ", rawMaterialOrder=" + getRawMaterialOrder() +
            "}";
    }
}
