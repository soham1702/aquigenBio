package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.QuatationDetails} entity.
 */
public class QuatationDetailsDTO implements Serializable {

    private Long id;

    private Double availabelStock;

    private Double quantity;

    private Double ratsPerUnit;

    private Double totalprice;

    private Double discount;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private Instant lastModified;

    private String lastModifiedBy;

    private ProductDTO product;

    private UnitDTO unit;

    private CategoriesDTO categories;

    private ProductQuatationDTO productQuatation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAvailabelStock() {
        return availabelStock;
    }

    public void setAvailabelStock(Double availabelStock) {
        this.availabelStock = availabelStock;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRatsPerUnit() {
        return ratsPerUnit;
    }

    public void setRatsPerUnit(Double ratsPerUnit) {
        this.ratsPerUnit = ratsPerUnit;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
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

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public CategoriesDTO getCategories() {
        return categories;
    }

    public void setCategories(CategoriesDTO categories) {
        this.categories = categories;
    }

    public ProductQuatationDTO getProductQuatation() {
        return productQuatation;
    }

    public void setProductQuatation(ProductQuatationDTO productQuatation) {
        this.productQuatation = productQuatation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuatationDetailsDTO)) {
            return false;
        }

        QuatationDetailsDTO quatationDetailsDTO = (QuatationDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quatationDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuatationDetailsDTO{" +
            "id=" + getId() +
            ", availabelStock=" + getAvailabelStock() +
            ", quantity=" + getQuantity() +
            ", ratsPerUnit=" + getRatsPerUnit() +
            ", totalprice=" + getTotalprice() +
            ", discount=" + getDiscount() +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", product=" + getProduct() +
            ", unit=" + getUnit() +
            ", categories=" + getCategories() +
            ", productQuatation=" + getProductQuatation() +
            "}";
    }
}
