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
 * Criteria class for the {@link com.mycompany.myapp.domain.RawMaterialOrder} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RawMaterialOrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /raw-material-orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class RawMaterialOrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter pricePerUnit;

    private StringFilter quantityUnit;

    private DoubleFilter quantity;

    private InstantFilter deliveryDate;

    private StringFilter quantityCheck;

    private InstantFilter orderedOn;

    private StringFilter orderStatus;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private LongFilter purchaseOrderId;

    private LongFilter productId;

    private Boolean distinct;

    public RawMaterialOrderCriteria() {}

    public RawMaterialOrderCriteria(RawMaterialOrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.pricePerUnit = other.pricePerUnit == null ? null : other.pricePerUnit.copy();
        this.quantityUnit = other.quantityUnit == null ? null : other.quantityUnit.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.deliveryDate = other.deliveryDate == null ? null : other.deliveryDate.copy();
        this.quantityCheck = other.quantityCheck == null ? null : other.quantityCheck.copy();
        this.orderedOn = other.orderedOn == null ? null : other.orderedOn.copy();
        this.orderStatus = other.orderStatus == null ? null : other.orderStatus.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.purchaseOrderId = other.purchaseOrderId == null ? null : other.purchaseOrderId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RawMaterialOrderCriteria copy() {
        return new RawMaterialOrderCriteria(this);
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

    public DoubleFilter getPricePerUnit() {
        return pricePerUnit;
    }

    public DoubleFilter pricePerUnit() {
        if (pricePerUnit == null) {
            pricePerUnit = new DoubleFilter();
        }
        return pricePerUnit;
    }

    public void setPricePerUnit(DoubleFilter pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public StringFilter getQuantityUnit() {
        return quantityUnit;
    }

    public StringFilter quantityUnit() {
        if (quantityUnit == null) {
            quantityUnit = new StringFilter();
        }
        return quantityUnit;
    }

    public void setQuantityUnit(StringFilter quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public DoubleFilter getQuantity() {
        return quantity;
    }

    public DoubleFilter quantity() {
        if (quantity == null) {
            quantity = new DoubleFilter();
        }
        return quantity;
    }

    public void setQuantity(DoubleFilter quantity) {
        this.quantity = quantity;
    }

    public InstantFilter getDeliveryDate() {
        return deliveryDate;
    }

    public InstantFilter deliveryDate() {
        if (deliveryDate == null) {
            deliveryDate = new InstantFilter();
        }
        return deliveryDate;
    }

    public void setDeliveryDate(InstantFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public StringFilter getQuantityCheck() {
        return quantityCheck;
    }

    public StringFilter quantityCheck() {
        if (quantityCheck == null) {
            quantityCheck = new StringFilter();
        }
        return quantityCheck;
    }

    public void setQuantityCheck(StringFilter quantityCheck) {
        this.quantityCheck = quantityCheck;
    }

    public InstantFilter getOrderedOn() {
        return orderedOn;
    }

    public InstantFilter orderedOn() {
        if (orderedOn == null) {
            orderedOn = new InstantFilter();
        }
        return orderedOn;
    }

    public void setOrderedOn(InstantFilter orderedOn) {
        this.orderedOn = orderedOn;
    }

    public StringFilter getOrderStatus() {
        return orderStatus;
    }

    public StringFilter orderStatus() {
        if (orderStatus == null) {
            orderStatus = new StringFilter();
        }
        return orderStatus;
    }

    public void setOrderStatus(StringFilter orderStatus) {
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
        final RawMaterialOrderCriteria that = (RawMaterialOrderCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(pricePerUnit, that.pricePerUnit) &&
            Objects.equals(quantityUnit, that.quantityUnit) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(quantityCheck, that.quantityCheck) &&
            Objects.equals(orderedOn, that.orderedOn) &&
            Objects.equals(orderStatus, that.orderStatus) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            pricePerUnit,
            quantityUnit,
            quantity,
            deliveryDate,
            quantityCheck,
            orderedOn,
            orderStatus,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            purchaseOrderId,
            productId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialOrderCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (pricePerUnit != null ? "pricePerUnit=" + pricePerUnit + ", " : "") +
            (quantityUnit != null ? "quantityUnit=" + quantityUnit + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
            (quantityCheck != null ? "quantityCheck=" + quantityCheck + ", " : "") +
            (orderedOn != null ? "orderedOn=" + orderedOn + ", " : "") +
            (orderStatus != null ? "orderStatus=" + orderStatus + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
