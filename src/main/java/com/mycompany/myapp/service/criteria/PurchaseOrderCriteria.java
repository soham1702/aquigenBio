package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.Status;
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
 * Criteria class for the {@link com.mycompany.myapp.domain.PurchaseOrder} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PurchaseOrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /purchase-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PurchaseOrderCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter totalPOAmount;

    private DoubleFilter totalGSTAmount;

    private InstantFilter expectedDeliveryDate;

    private InstantFilter poDate;

    private StatusFilter orderStatus;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private LongFilter purchaseOrderDetailsId;

    private LongFilter goodsRecivedId;

    private LongFilter productInventoryId;

    private LongFilter warehouseId;

    private LongFilter securityUserId;

    private LongFilter rawMaterialOrderId;

    private Boolean distinct;

    public PurchaseOrderCriteria() {}

    public PurchaseOrderCriteria(PurchaseOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalPOAmount = other.totalPOAmount == null ? null : other.totalPOAmount.copy();
        this.totalGSTAmount = other.totalGSTAmount == null ? null : other.totalGSTAmount.copy();
        this.expectedDeliveryDate = other.expectedDeliveryDate == null ? null : other.expectedDeliveryDate.copy();
        this.poDate = other.poDate == null ? null : other.poDate.copy();
        this.orderStatus = other.orderStatus == null ? null : other.orderStatus.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.purchaseOrderDetailsId = other.purchaseOrderDetailsId == null ? null : other.purchaseOrderDetailsId.copy();
        this.goodsRecivedId = other.goodsRecivedId == null ? null : other.goodsRecivedId.copy();
        this.productInventoryId = other.productInventoryId == null ? null : other.productInventoryId.copy();
        this.warehouseId = other.warehouseId == null ? null : other.warehouseId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.rawMaterialOrderId = other.rawMaterialOrderId == null ? null : other.rawMaterialOrderId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PurchaseOrderCriteria copy() {
        return new PurchaseOrderCriteria(this);
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

    public DoubleFilter getTotalPOAmount() {
        return totalPOAmount;
    }

    public DoubleFilter totalPOAmount() {
        if (totalPOAmount == null) {
            totalPOAmount = new DoubleFilter();
        }
        return totalPOAmount;
    }

    public void setTotalPOAmount(DoubleFilter totalPOAmount) {
        this.totalPOAmount = totalPOAmount;
    }

    public DoubleFilter getTotalGSTAmount() {
        return totalGSTAmount;
    }

    public DoubleFilter totalGSTAmount() {
        if (totalGSTAmount == null) {
            totalGSTAmount = new DoubleFilter();
        }
        return totalGSTAmount;
    }

    public void setTotalGSTAmount(DoubleFilter totalGSTAmount) {
        this.totalGSTAmount = totalGSTAmount;
    }

    public InstantFilter getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public InstantFilter expectedDeliveryDate() {
        if (expectedDeliveryDate == null) {
            expectedDeliveryDate = new InstantFilter();
        }
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(InstantFilter expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public InstantFilter getPoDate() {
        return poDate;
    }

    public InstantFilter poDate() {
        if (poDate == null) {
            poDate = new InstantFilter();
        }
        return poDate;
    }

    public void setPoDate(InstantFilter poDate) {
        this.poDate = poDate;
    }

    public StatusFilter getOrderStatus() {
        return orderStatus;
    }

    public StatusFilter orderStatus() {
        if (orderStatus == null) {
            orderStatus = new StatusFilter();
        }
        return orderStatus;
    }

    public void setOrderStatus(StatusFilter orderStatus) {
        this.orderStatus = orderStatus;
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

    public LongFilter getPurchaseOrderDetailsId() {
        return purchaseOrderDetailsId;
    }

    public LongFilter purchaseOrderDetailsId() {
        if (purchaseOrderDetailsId == null) {
            purchaseOrderDetailsId = new LongFilter();
        }
        return purchaseOrderDetailsId;
    }

    public void setPurchaseOrderDetailsId(LongFilter purchaseOrderDetailsId) {
        this.purchaseOrderDetailsId = purchaseOrderDetailsId;
    }

    public LongFilter getGoodsRecivedId() {
        return goodsRecivedId;
    }

    public LongFilter goodsRecivedId() {
        if (goodsRecivedId == null) {
            goodsRecivedId = new LongFilter();
        }
        return goodsRecivedId;
    }

    public void setGoodsRecivedId(LongFilter goodsRecivedId) {
        this.goodsRecivedId = goodsRecivedId;
    }

    public LongFilter getProductInventoryId() {
        return productInventoryId;
    }

    public LongFilter productInventoryId() {
        if (productInventoryId == null) {
            productInventoryId = new LongFilter();
        }
        return productInventoryId;
    }

    public void setProductInventoryId(LongFilter productInventoryId) {
        this.productInventoryId = productInventoryId;
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

    public LongFilter getRawMaterialOrderId() {
        return rawMaterialOrderId;
    }

    public LongFilter rawMaterialOrderId() {
        if (rawMaterialOrderId == null) {
            rawMaterialOrderId = new LongFilter();
        }
        return rawMaterialOrderId;
    }

    public void setRawMaterialOrderId(LongFilter rawMaterialOrderId) {
        this.rawMaterialOrderId = rawMaterialOrderId;
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
        final PurchaseOrderCriteria that = (PurchaseOrderCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(totalPOAmount, that.totalPOAmount) &&
            Objects.equals(totalGSTAmount, that.totalGSTAmount) &&
            Objects.equals(expectedDeliveryDate, that.expectedDeliveryDate) &&
            Objects.equals(poDate, that.poDate) &&
            Objects.equals(orderStatus, that.orderStatus) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(purchaseOrderDetailsId, that.purchaseOrderDetailsId) &&
            Objects.equals(goodsRecivedId, that.goodsRecivedId) &&
            Objects.equals(productInventoryId, that.productInventoryId) &&
            Objects.equals(warehouseId, that.warehouseId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(rawMaterialOrderId, that.rawMaterialOrderId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            totalPOAmount,
            totalGSTAmount,
            expectedDeliveryDate,
            poDate,
            orderStatus,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            purchaseOrderDetailsId,
            goodsRecivedId,
            productInventoryId,
            warehouseId,
            securityUserId,
            rawMaterialOrderId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseOrderCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (totalPOAmount != null ? "totalPOAmount=" + totalPOAmount + ", " : "") +
            (totalGSTAmount != null ? "totalGSTAmount=" + totalGSTAmount + ", " : "") +
            (expectedDeliveryDate != null ? "expectedDeliveryDate=" + expectedDeliveryDate + ", " : "") +
            (poDate != null ? "poDate=" + poDate + ", " : "") +
            (orderStatus != null ? "orderStatus=" + orderStatus + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (purchaseOrderDetailsId != null ? "purchaseOrderDetailsId=" + purchaseOrderDetailsId + ", " : "") +
            (goodsRecivedId != null ? "goodsRecivedId=" + goodsRecivedId + ", " : "") +
            (productInventoryId != null ? "productInventoryId=" + productInventoryId + ", " : "") +
            (warehouseId != null ? "warehouseId=" + warehouseId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (rawMaterialOrderId != null ? "rawMaterialOrderId=" + rawMaterialOrderId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
