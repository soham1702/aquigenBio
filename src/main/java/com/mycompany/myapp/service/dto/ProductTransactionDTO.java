package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProductTransaction} entity.
 */
public class ProductTransactionDTO implements Serializable {

    private Long id;

    private Double qtySold;

    private Double pricePerUnit;

    private String lotNumber;

    private Instant expirydate;

    private Double totalAmount;

    private Double gstAmount;

    private String description;

    private Instant lastModified;

    private String lastModifiedBy;

    private WarehouseDTO warehouse;

    private Set<ProductDTO> products = new HashSet<>();

    private ProductInventoryDTO productInventory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQtySold() {
        return qtySold;
    }

    public void setQtySold(Double qtySold) {
        this.qtySold = qtySold;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Instant getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Instant expirydate) {
        this.expirydate = expirydate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(Double gstAmount) {
        this.gstAmount = gstAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public WarehouseDTO getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseDTO warehouse) {
        this.warehouse = warehouse;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
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
        if (!(o instanceof ProductTransactionDTO)) {
            return false;
        }

        ProductTransactionDTO productTransactionDTO = (ProductTransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productTransactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTransactionDTO{" +
            "id=" + getId() +
            ", qtySold=" + getQtySold() +
            ", pricePerUnit=" + getPricePerUnit() +
            ", lotNumber='" + getLotNumber() + "'" +
            ", expirydate='" + getExpirydate() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", gstAmount=" + getGstAmount() +
            ", description='" + getDescription() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", warehouse=" + getWarehouse() +
            ", products=" + getProducts() +
            ", productInventory=" + getProductInventory() +
            "}";
    }
}
