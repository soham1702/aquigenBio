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
 * Criteria class for the {@link com.mycompany.myapp.domain.TranferDetailsApprovals} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TranferDetailsApprovalsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tranfer-details-approvals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class TranferDetailsApprovalsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter approvalDate;

    private DoubleFilter qtyRequested;

    private DoubleFilter qtyApproved;

    private StringFilter comment;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private BooleanFilter isDeleted;

    private BooleanFilter isActive;

    private LongFilter transferId;

    private Boolean distinct;

    public TranferDetailsApprovalsCriteria() {}

    public TranferDetailsApprovalsCriteria(TranferDetailsApprovalsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.approvalDate = other.approvalDate == null ? null : other.approvalDate.copy();
        this.qtyRequested = other.qtyRequested == null ? null : other.qtyRequested.copy();
        this.qtyApproved = other.qtyApproved == null ? null : other.qtyApproved.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.transferId = other.transferId == null ? null : other.transferId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TranferDetailsApprovalsCriteria copy() {
        return new TranferDetailsApprovalsCriteria(this);
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

    public InstantFilter getApprovalDate() {
        return approvalDate;
    }

    public InstantFilter approvalDate() {
        if (approvalDate == null) {
            approvalDate = new InstantFilter();
        }
        return approvalDate;
    }

    public void setApprovalDate(InstantFilter approvalDate) {
        this.approvalDate = approvalDate;
    }

    public DoubleFilter getQtyRequested() {
        return qtyRequested;
    }

    public DoubleFilter qtyRequested() {
        if (qtyRequested == null) {
            qtyRequested = new DoubleFilter();
        }
        return qtyRequested;
    }

    public void setQtyRequested(DoubleFilter qtyRequested) {
        this.qtyRequested = qtyRequested;
    }

    public DoubleFilter getQtyApproved() {
        return qtyApproved;
    }

    public DoubleFilter qtyApproved() {
        if (qtyApproved == null) {
            qtyApproved = new DoubleFilter();
        }
        return qtyApproved;
    }

    public void setQtyApproved(DoubleFilter qtyApproved) {
        this.qtyApproved = qtyApproved;
    }

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
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
        final TranferDetailsApprovalsCriteria that = (TranferDetailsApprovalsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(approvalDate, that.approvalDate) &&
            Objects.equals(qtyRequested, that.qtyRequested) &&
            Objects.equals(qtyApproved, that.qtyApproved) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(transferId, that.transferId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            approvalDate,
            qtyRequested,
            qtyApproved,
            comment,
            freeField1,
            freeField2,
            lastModified,
            lastModifiedBy,
            isDeleted,
            isActive,
            transferId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TranferDetailsApprovalsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (approvalDate != null ? "approvalDate=" + approvalDate + ", " : "") +
            (qtyRequested != null ? "qtyRequested=" + qtyRequested + ", " : "") +
            (qtyApproved != null ? "qtyApproved=" + qtyApproved + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (transferId != null ? "transferId=" + transferId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
