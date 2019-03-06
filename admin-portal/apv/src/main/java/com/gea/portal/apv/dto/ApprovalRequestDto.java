package com.gea.portal.apv.dto;

import com.tng.portal.common.enumeration.ApprovalType;
import com.tng.portal.common.enumeration.Instance;
import com.tng.portal.common.enumeration.RequestApprovalStatus;
import com.tng.portal.common.vo.PageRequestDto;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by Owen on 2018/9/13.
 */
public class ApprovalRequestDto extends PageRequestDto implements Serializable{
    private String requestUserId;
    private String participantRefId;
    private RequestApprovalStatus approvalStatus;
    private String approvalUserId;
    private ApprovalType approvalType;
    @ApiModelProperty("Instance PRE-PROD or PROD")
    private Instance instance;
    private String deploymentStatus;
    private String approvalRemark;
    private String approvalDate;
    private String deploymentDate;
    private String participantName;
    private String requestDate;
    private String requestUserName;
    private String approvalUserName;

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getApprovalRemark() {
        return approvalRemark;
    }

    public void setApprovalRemark(String approvalRemark) {
        this.approvalRemark = approvalRemark;
    }

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

    public String getParticipantRefId() {
        return participantRefId;
    }

    public void setParticipantRefId(String participantRefId) {
        this.participantRefId = participantRefId;
    }

    public RequestApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(RequestApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalUserId() {
        return approvalUserId;
    }

    public void setApprovalUserId(String approvalUserId) {
        this.approvalUserId = approvalUserId;
    }

    public ApprovalType getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(ApprovalType approvalType) {
        this.approvalType = approvalType;
    }

    public String getDeploymentStatus() {
        return deploymentStatus;
    }

    public void setDeploymentStatus(String deploymentStatus) {
        this.deploymentStatus = deploymentStatus;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public String getDeploymentDate() {
        return deploymentDate;
    }

    public void setDeploymentDate(String deploymentDate) {
        this.deploymentDate = deploymentDate;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getRequestUserName() {
        return requestUserName;
    }

    public void setRequestUserName(String requestUserName) {
        this.requestUserName = requestUserName;
    }

    public String getApprovalUserName() {
        return approvalUserName;
    }

    public void setApprovalUserName(String approvalUserName) {
        this.approvalUserName = approvalUserName;
    }
}
