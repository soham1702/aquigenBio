package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.ProductInventory} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProductInventoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-inventories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductInventoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter inwardOutwardDate;

    private StringFilter inwardQty;

    private StringFilter outwardQty;

    private StringFilter totalQuanity;

    private LongFilter pricePerUnit;

    private StringFilter lotNo;

    private InstantFilter expiryDate;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private BooleanFilter isDeleted;

    private BooleanFilter isActive;

    private LongFilter transferId;

    private LongFilter purchaseOrderId;

    private LongFilter consumptionDetailsId;

    private LongFilter productTransactionId;

    private LongFilter productId;

    private LongFilter warehouseId;

    private LongFilter securityUserId;

    private Boolean distinct;

    public ProductInventoryCriteria() {}

    public ProductInventoryCriteria(ProductInventoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.inwardOutwardDate = other.inwardOutwardDate == null ? null : other.inwardOutwardDate.copy();
        this.inwardQty = other.inwardQty == null ? null : other.inwardQty.copy();
        this.outwardQty = other.outwardQty == null ? null : other.outwardQty.copy();
        this.totalQuanity = other.totalQuanity == null ? null : other.totalQuanity.copy();
        this.pricePerUnit = other.pricePerUnit == null ? null : other.pricePerUnit.copy();
        this.lotNo = other.lotNo == null ? null : other.lotNo.copy();
        this.expiryDate = other.expiryDate == null ? null : other.expiryDate.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.transferId = other.transferId == null ? null : other.transferId.copy();
        this.purchaseOrderId = other.purchaseOrderId == null ? null : other.purchaseOrderId.copy();
        this.consumptionDetailsId = other.consumptionDetailsId == null ? null : other.consumptionDetailsId.copy();
        this.productTransactionId = other.productTransactionId == null ? null : other.productTransactionId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductInventoryCriteria copy() {
        return new ProductInventoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getInwardOutwardDate() {
        return inwardOutwardDate;
    }

    public InstantFilter inwardOutwardDate() {
        if (inwardOutwardDate == null) {
            inwardOutwardDate = new InstantFilter();
        }
        return inwardOutwardDate;
    }

    public void setInwardOutwardDate(InstantFilter inwardOutwardDate) {
        this.inwardOutwardDate = inwardOutwardDate;
    }

    public StringFilter getInwardQty() {
        return inwardQty;
    }

    public StringFilter inwardQty() {
        if (inwardQty == null) {
            inwardQty = new StringFilter();
        }
        return inwardQty;
    }

    public void setInwardQty(StringFilter inwardQty) {
        this.inwardQty = inwardQty;
    }

    public StringFilter getOutwardQty() {
        return outwardQty;
    }

    public StringFilter outwardQty() {
        if (outwardQty == null) {
            outwardQty = new StringFilter();
        }
        return outwardQty;
    }

    public void setOutwardQty(StringFilter outwardQty) {
        this.outwardQty = outwardQty;
    }

    public StringFilter getTotalQuanity() {
        return totalQuanity;
    }

    public StringFilter totalQuanity() {
        if (totalQuanity == null) {
            totalQuanity = new StringFilter();
        }
        return totalQuanity;
    }

    public void setTotalQuanity(StringFilter totalQuanity) {
        this.totalQuanity = totalQuanity;
    }

    public LongFilter getPricePerUnit() {
        return pricePerUnit;
    }

    public LongFilter pricePerUnit() {
        if (pricePerUnit == null) {
            pricePerUnit = new LongFilter();
        }
        return pricePerUnit;
    }

    public void setPricePerUnit(LongFilter pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public StringFilter getLotNo() {
        return lotNo;
    }

    public StringFilter lotNo() {
        if (lotNo == null) {
            lotNo = new StringFilter();
        }
        return lotNo;
    }

    public void setLotNo(StringFilter lotNo) {
        this.lotNo = lotNo;
    }

    public InstantFilter getExpiryDate() {
        return expiryDate;
    }

    public InstantFilter expiryDate() {
        if (expiryDate == null) {
            expiryDate = new InstantFilter();
        }
        return expiryDate;
    }

    public void setExpiryDate(InstantFilter expiryDate) {
        this.expiryDate = expiryDate;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public StringFilter getFreeField3() {
        return freeField3;
    }

    public StringFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new StringFilter();
        }
        return freeField3;
    }

    public void setFreeField3(StringFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public StringFilter getFreeField4() {
        return freeField4;
    }

    public StringFilter freeField4() {
        if (freeField4 == null) {
            freeField4 = new StringFilter();
        }
        return freeField4;
    }

    public void setFreeField4(StringFilter freeField4) {
        this.freeField4 = freeField4;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            isActive = new BooleanFilter();
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public LongFilter getTransferId() {
        return transferId;
    }

    public LongFilter transferId() {
        if (transferId == null) {
            transferId = new LongFilter();
        }
        return transferId;
    }

    public void setTransferId(LongFilter transferId) {
        this.transferId = transferId;
    }

    public LongFilter getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public LongFilter purchaseOrderId() {
        if (purchaseOrderId == null) {
            purchaseOrderId = new LongFilter();
        }
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(LongFilter purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public LongFilter getConsumptionDetailsId() {
        return consumptionDetailsId;
    }

    public LongFilter consumptionDetailsId() {
        if (consumptionDetailsId == null) {
            consumptionDetailsId = new LongFilter();
        }
        return consumptionDetailsId;
    }

    public void setConsumptionDetailsId(LongFilter consumptionDetailsId) {
        this.consumptionDetailsId = consumptionDetailsId;
    }

    public LongFilter getProductTransactionId() {
        return productTransactionId;
    }

    public LongFilter productTransactionId() {
        if (productTransactionId == null) {
            productTransactionId = new LongFilter();
        }
        return productTransactionId;
    }

    public void setProductTransactionId(LongFilter productTransactionId) {
        this.productTransactionId = productTransactionId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getWarehouseId() {
        return warehouseId;
    }

    public LongFilter warehouseId() {
        if (warehouseId == null) {
            warehouseId = new LongFilter();
        }
        return warehouseId;
    }

    public void setWarehouseId(LongFilter warehouseId) {
        this.warehouseId = warehouseId;
    }

    public LongFilter getSecurityUserId() {
        return securityUserId;
    }

    public LongFilter securityUserId() {
        if (securityUserId == null) {
            securityUserId = new LongFilter();
        }
        return securityUserId;
    }

    public void setSecurityUserId(LongFilter securityUserId) {
        this.securityUserId = securityUserId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductInventoryCriteria that = (ProductInventoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(inwardOutwardDate, that.inwardOutwardDate) &&
            Objects.equals(inwardQty, that.inwardQty) &&
            Objects.equals(outwardQty, that.outwardQty) &&
            Objects.equals(totalQuanity, that.totalQuanity) &&
            Objects.equals(pricePerUnit, that.pricePerUnit) &&
            Objects.equals(lotNo, that.lotNo) &&
            Objects.equals(expiryDate, that.expiryDate) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(transferId, that.transferId) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId) &&
            Objects.equals(consumptionDetailsId, that.consumptionDetailsId) &&
            Objects.equals(productTransactionId, that.productTransactionId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(warehouseId, that.warehouseId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            inwardOutwardDate,
            inwardQty,
            outwardQty,
            totalQuanity,
            pricePerUnit,
            lotNo,
            expiryDate,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            lastModified,
            lastModifiedBy,
            isDeleted,
            isActive,
            transferId,
            purchaseOrderId,
            consumptionDetailsId,
            productTransactionId,
            productId,
            warehouseId,
            securityUserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductInventoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (inwardOutwardDate != null ? "inwardOutwardDate=" + inwardOutwardDate + ", " : "") +
            (inwardQty != null ? "inwardQty=" + inwardQty + ", " : "") +
            (outwardQty != null ? "outwardQty=" + outwardQty + ", " : "") +
            (totalQuanity != null ? "totalQuanity=" + totalQuanity + ", " : "") +
            (pricePerUnit != null ? "pricePerUnit=" + pricePerUnit + ", " : "") +
            (lotNo != null ? "lotNo=" + lotNo + ", " : "") +
            (expiryDate != null ? "expiryDate=" + expiryDate + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (transferId != null ? "transferId=" + transferId + ", " : "") +
            (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
            (consumptionDetailsId != null ? "consumptionDetailsId=" + consumptionDetailsId + ", " : "") +
            (productTransactionId != null ? "productTransactionId=" + productTransactionId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
