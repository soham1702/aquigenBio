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
 * Criteria class for the {@link com.mycompany.myapp.domain.ProductQuatation} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProductQuatationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-quatations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductQuatationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter quatationdate;

    private DoubleFilter totalAmt;

    private DoubleFilter gst;

    private DoubleFilter discount;

    private InstantFilter expectedDelivery;

    private StringFilter deliveryAddress;

    private InstantFilter quoValidity;

    private StringFilter clientName;

    private StringFilter clientMobile;

    private StringFilter clientEmail;

    private StringFilter termsAndCondition;

    private StringFilter notes;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter securityUserId;

    private LongFilter quatationDetailsId;

    private Boolean distinct;

    public ProductQuatationCriteria() {}

    public ProductQuatationCriteria(ProductQuatationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quatationdate = other.quatationdate == null ? null : other.quatationdate.copy();
        this.totalAmt = other.totalAmt == null ? null : other.totalAmt.copy();
        this.gst = other.gst == null ? null : other.gst.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.expectedDelivery = other.expectedDelivery == null ? null : other.expectedDelivery.copy();
        this.deliveryAddress = other.deliveryAddress == null ? null : other.deliveryAddress.copy();
        this.quoValidity = other.quoValidity == null ? null : other.quoValidity.copy();
        this.clientName = other.clientName == null ? null : other.clientName.copy();
        this.clientMobile = other.clientMobile == null ? null : other.clientMobile.copy();
        this.clientEmail = other.clientEmail == null ? null : other.clientEmail.copy();
        this.termsAndCondition = other.termsAndCondition == null ? null : other.termsAndCondition.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.quatationDetailsId = other.quatationDetailsId == null ? null : other.quatationDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductQuatationCriteria copy() {
        return new ProductQuatationCriteria(this);
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

    public InstantFilter getQuatationdate() {
        return quatationdate;
    }

    public InstantFilter quatationdate() {
        if (quatationdate == null) {
            quatationdate = new InstantFilter();
        }
        return quatationdate;
    }

    public void setQuatationdate(InstantFilter quatationdate) {
        this.quatationdate = quatationdate;
    }

    public DoubleFilter getTotalAmt() {
        return totalAmt;
    }

    public DoubleFilter totalAmt() {
        if (totalAmt == null) {
            totalAmt = new DoubleFilter();
        }
        return totalAmt;
    }

    public void setTotalAmt(DoubleFilter totalAmt) {
        this.totalAmt = totalAmt;
    }

    public DoubleFilter getGst() {
        return gst;
    }

    public DoubleFilter gst() {
        if (gst == null) {
            gst = new DoubleFilter();
        }
        return gst;
    }

    public void setGst(DoubleFilter gst) {
        this.gst = gst;
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

    public InstantFilter getExpectedDelivery() {
        return expectedDelivery;
    }

    public InstantFilter expectedDelivery() {
        if (expectedDelivery == null) {
            expectedDelivery = new InstantFilter();
        }
        return expectedDelivery;
    }

    public void setExpectedDelivery(InstantFilter expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public StringFilter getDeliveryAddress() {
        return deliveryAddress;
    }

    public StringFilter deliveryAddress() {
        if (deliveryAddress == null) {
            deliveryAddress = new StringFilter();
        }
        return deliveryAddress;
    }

    public void setDeliveryAddress(StringFilter deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public InstantFilter getQuoValidity() {
        return quoValidity;
    }

    public InstantFilter quoValidity() {
        if (quoValidity == null) {
            quoValidity = new InstantFilter();
        }
        return quoValidity;
    }

    public void setQuoValidity(InstantFilter quoValidity) {
        this.quoValidity = quoValidity;
    }

    public StringFilter getClientName() {
        return clientName;
    }

    public StringFilter clientName() {
        if (clientName == null) {
            clientName = new StringFilter();
        }
        return clientName;
    }

    public void setClientName(StringFilter clientName) {
        this.clientName = clientName;
    }

    public StringFilter getClientMobile() {
        return clientMobile;
    }

    public StringFilter clientMobile() {
        if (clientMobile == null) {
            clientMobile = new StringFilter();
        }
        return clientMobile;
    }

    public void setClientMobile(StringFilter clientMobile) {
        this.clientMobile = clientMobile;
    }

    public StringFilter getClientEmail() {
        return clientEmail;
    }

    public StringFilter clientEmail() {
        if (clientEmail == null) {
            clientEmail = new StringFilter();
        }
        return clientEmail;
    }

    public void setClientEmail(StringFilter clientEmail) {
        this.clientEmail = clientEmail;
    }

    public StringFilter getTermsAndCondition() {
        return termsAndCondition;
    }

    public StringFilter termsAndCondition() {
        if (termsAndCondition == null) {
            termsAndCondition = new StringFilter();
        }
        return termsAndCondition;
    }

    public void setTermsAndCondition(StringFilter termsAndCondition) {
        this.termsAndCondition = termsAndCondition;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public StringFilter notes() {
        if (notes == null) {
            notes = new StringFilter();
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
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

    public LongFilter getQuatationDetailsId() {
        return quatationDetailsId;
    }

    public LongFilter quatationDetailsId() {
        if (quatationDetailsId == null) {
            quatationDetailsId = new LongFilter();
        }
        return quatationDetailsId;
    }

    public void setQuatationDetailsId(LongFilter quatationDetailsId) {
        this.quatationDetailsId = quatationDetailsId;
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
        final ProductQuatationCriteria that = (ProductQuatationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quatationdate, that.quatationdate) &&
            Objects.equals(totalAmt, that.totalAmt) &&
            Objects.equals(gst, that.gst) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(expectedDelivery, that.expectedDelivery) &&
            Objects.equals(deliveryAddress, that.deliveryAddress) &&
            Objects.equals(quoValidity, that.quoValidity) &&
            Objects.equals(clientName, that.clientName) &&
            Objects.equals(clientMobile, that.clientMobile) &&
            Objects.equals(clientEmail, that.clientEmail) &&
            Objects.equals(termsAndCondition, that.termsAndCondition) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(quatationDetailsId, that.quatationDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            quatationdate,
            totalAmt,
            gst,
            discount,
            expectedDelivery,
            deliveryAddress,
            quoValidity,
            clientName,
            clientMobile,
            clientEmail,
            termsAndCondition,
            notes,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            lastModified,
            lastModifiedBy,
            securityUserId,
            quatationDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductQuatationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (quatationdate != null ? "quatationdate=" + quatationdate + ", " : "") +
            (totalAmt != null ? "totalAmt=" + totalAmt + ", " : "") +
            (gst != null ? "gst=" + gst + ", " : "") +
            (discount != null ? "discount=" + discount + ", " : "") +
            (expectedDelivery != null ? "expectedDelivery=" + expectedDelivery + ", " : "") +
            (deliveryAddress != null ? "deliveryAddress=" + deliveryAddress + ", " : "") +
            (quoValidity != null ? "quoValidity=" + quoValidity + ", " : "") +
            (clientName != null ? "clientName=" + clientName + ", " : "") +
            (clientMobile != null ? "clientMobile=" + clientMobile + ", " : "") +
            (clientEmail != null ? "clientEmail=" + clientEmail + ", " : "") +
            (termsAndCondition != null ? "termsAndCondition=" + termsAndCondition + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (quatationDetailsId != null ? "quatationDetailsId=" + quatationDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
