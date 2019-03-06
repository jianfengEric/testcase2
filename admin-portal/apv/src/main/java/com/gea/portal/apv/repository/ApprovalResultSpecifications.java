package com.gea.portal.apv.repository;

import com.gea.portal.apv.entity.ApprovalResult;
import com.tng.portal.ana.entity.AnaAccount;
import com.tng.portal.common.enumeration.ApprovalType;
import com.tng.portal.common.enumeration.Instance;
import com.tng.portal.common.enumeration.RequestApprovalStatus;
import com.tng.portal.common.util.JpaUtil;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Created by Dell on 2018/11/29.
 */
public class ApprovalResultSpecifications {

    public static Specifications<ApprovalResult> base() {
       return Specifications.where((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id")));
    }

    public static Specification<ApprovalResult> approvalUserNameLike(String approvalUserName) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<ApprovalResult, AnaAccount> join = root.join("approvalBy", JoinType.LEFT);
            return criteriaBuilder.like(criteriaBuilder.function("CONCAT", String.class, join.get("firstName"), join.get("lastName")), "%" + approvalUserName.replace(" ", "") + "%");
        };
    }

    public static Specification<ApprovalResult> geaParticipantRefIdLike(String geaParticipantRefId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("geaParticipantRefId"), "%" + geaParticipantRefId + "%");
    }

    public static Specification<ApprovalResult> requestUserNameLike(String requestUserName) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("requestUserName"), "%" + requestUserName + "%");
    }

    public static Specification<ApprovalResult> approvalStatusEqual(RequestApprovalStatus approvalStatus) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("approvalStatus"), approvalStatus);
    }

    public static Specification<ApprovalResult> currentEnvirEqual(Instance instance) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("currentEnvir"), instance);
    }

    public static Specification<ApprovalResult> approvalTypeEqual(ApprovalType approvalType) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join("approvalCategoryItem", JoinType.LEFT).get("code"), approvalType);
    }

    public static Specification<ApprovalResult> approvalRemarkLike(String approvalRemark) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("approvalRemark"), JpaUtil.genLikeStr(approvalRemark));
    }

    public static Specification<ApprovalResult> approvalDateLike(String approvalDate) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("approvalDate").as(String.class), "%" + approvalDate + "%");
    }

    public static Specification<ApprovalResult> requestDateLike(String requestDate) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("requestDate").as(String.class), "%" + requestDate + "%");
    }

    public static Specification<ApprovalResult> geaParticipantRefIdIn(List<String> geaParticipantRefIds) {
        return (root, criteriaQuery, criteriaBuilder) -> root.get("geaParticipantRefId").in(geaParticipantRefIds);
    }

    public static Specification<ApprovalResult> deployRefIdIn(List<String> deployRefIds) {
        return (root, criteriaQuery, criteriaBuilder) ->  root.get("deployRefId").in(deployRefIds);
    }
}
