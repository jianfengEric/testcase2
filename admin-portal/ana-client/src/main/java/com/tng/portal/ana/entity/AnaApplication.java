package com.tng.portal.ana.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Zero on 2016/11/4.
 */
@Entity
@Table(name = "ana_application")
public class AnaApplication {
    @Id
    @Column(name = "code", columnDefinition="CHAR")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "is_displayed")
    private Boolean isDisplay;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "anaApplication")
    private List<AnaRole>anaRoles;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "anaApplication")
    private List<AnaFunction> anaFunctions;

    @Column(name = "URL_ENDPOINT")
    private String urlEnpoin;

    @Version
    @Column(name = "optimisticLockVersion")
    private long optimisticLockVersion; //throws OptimisticLockException

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AnaRole> getAnaRoles() {
        return anaRoles;
    }

    public void setAnaRoles(List<AnaRole> anaRoles) {
        this.anaRoles = anaRoles;
    }

    public List<AnaFunction> getAnaFunctions() {
        return anaFunctions;
    }

    public void setAnaFunctions(List<AnaFunction> appFunctions) {
        this.anaFunctions = appFunctions;
    }

    public Boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getUrlEnpoin() {
        return urlEnpoin;
    }

    public void setUrlEnpoin(String urlEnpoin) {
        this.urlEnpoin = urlEnpoin;
    }

    public Boolean getDisplay() {
        return isDisplay;
    }

    public void setDisplay(Boolean display) {
        isDisplay = display;
    }

    public long getOptimisticLockVersion() {
        return optimisticLockVersion;
    }

    public void setOptimisticLockVersion(long optimisticLockVersion) {
        this.optimisticLockVersion = optimisticLockVersion;
    }
}
