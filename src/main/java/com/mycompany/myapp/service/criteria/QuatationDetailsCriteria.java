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
 * Criteria class for the {@link com.mycompany.myapp.domain.QuatationDetails} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.QuatationDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /quatation-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class QuatationDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter availabelStock;

    private DoubleFilter quantity;

    private DoubleFilter ratsPerUnit;

    private DoubleFilter totalprice;

    private DoubleFilter discount;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter productId;

    private LongFilter unitId;

    private LongFilter categoriesId;

    private LongFilter productQuatationId;

    private Boolean distinct;

    public QuatationDetailsCriteria() {}

    public QuatationDetailsCriteria(QuatationDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.availabelStock = other.availabelStock == null ? null : other.availabelStock.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.ratsPerUnit = other.ratsPerUnit == null ? null : other.ratsPerUnit.copy();
        this.totalprice = other.totalprice == null ? null : other.totalprice.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.categoriesId = other.categoriesId == null ? null : other.categoriesId.copy();
        this.productQuatationId = other.productQuatationId == null ? null : other.productQuatationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public QuatationDetailsCriteria copy() {
        return new QuatationDetailsCriteria(this);
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

    public DoubleFilter getAvailabelStock() {
        return availabelStock;
    }

    public DoubleFilter availabelStock() {
        if (availabelStock == null) {
            availabelStock = new DoubleFilter();
        }
        return availabelStock;
    }

    public void setAvailabelStock(DoubleFilter availabelStock) {
        this.availabelStock = availabelStock;
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

    public DoubleFilter getRatsPerUnit() {
        return ratsPerUnit;
    }

    public DoubleFilter ratsPerUnit() {
        if (ratsPerUnit == null) {
            ratsPerUnit = new DoubleFilter();
        }
        return ratsPerUnit;
    }

    public void setRatsPerUnit(DoubleFilter ratsPerUnit) {
        this.ratsPerUnit = ratsPerUnit;
    }

    public DoubleFilter getTotalprice() {
        return totalprice;
    }

    public DoubleFilter totalprice() {
        if (totalprice == null) {
            totalprice = new DoubleFilter();
        }
        return totalprice;
    }

    public void setTotalprice(DoubleFilter totalprice) {
        this.totalprice = totalprice;
    }

    public DoubleFilter getDiscount() {
        return discount;
    }

    public DoubleFilter discount() {
        if (discount == null) {
            discount = new DoubleFilter();
        }
        return discount;
    }

    public void setDiscount(DoubleFilter discount) {
        this.discount = discount;
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

    public LongFilter getUnitId() {
        return unitId;
    }

    public LongFilter unitId() {
        if (unitId == null) {
            unitId = new LongFilter();
        }
        return unitId;
    }

    public void setUnitId(LongFilter unitId) {
        this.unitId = unitId;
    }

    public LongFilter getCategoriesId() {
        return categoriesId;
    }

    public LongFilter categoriesId() {
        if (categoriesId == null) {
            categoriesId = new LongFilter();
        }
        return categoriesId;
    }

    public void setCategoriesId(LongFilter categoriesId) {
        this.categoriesId = categoriesId;
    }

    public LongFilter getProductQuatationId() {
        return productQuatationId;
    }

    public LongFilter productQuatationId() {
        if (productQuatationId == null) {
            productQuatationId = new LongFilter();
        }
        return productQuatationId;
    }

    public void setProductQuatationId(LongFilter productQuatationId) {
        this.productQuatationId = productQuatationId;
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
        final QuatationDetailsCriteria that = (QuatationDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(availabelStock, that.availabelStock) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(ratsPerUnit, that.ratsPerUnit) &&
            Objects.equals(totalprice, that.totalprice) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(categoriesId, that.categoriesId) &&
            Objects.equals(productQuatationId, that.productQuatationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            availabelStock,
            quantity,
            ratsPerUnit,
            totalprice,
            discount,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            lastModified,
            lastModifiedBy,
            productId,
            unitId,
            categoriesId,
            productQuatationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuatationDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (availabelStock != null ? "availabelStock=" + availabelStock + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (ratsPerUnit != null ? "ratsPerUnit=" + ratsPerUnit + ", " : "") +
            (totalprice != null ? "totalprice=" + totalprice + ", " : "") +
            (discount != null ? "discount=" + discount + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (unitId != null ? "unitId=" + unitId + ", " : "") +
            (categoriesId != null ? "categoriesId=" + categoriesId + ", " : "") +
            (productQuatationId != null ? "productQuatationId=" + productQuatationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
