package com.tianhang.adapp.domain;

/**
 * Created by student on 15/9/15.
 */
public class DepartmentBean {
    private String departmentId;
    private String deptName;
    private String contacName;
    private String photoNo;
    private String faxNo;
    private String collectionPointId;

    public DepartmentBean(String departmentId, String deptName, String contacName, String photoNo, String faxNo, String collectionPointId) {
        this.departmentId = departmentId;
        this.deptName = deptName;
        this.contacName = contacName;
        this.photoNo = photoNo;
        this.faxNo = faxNo;
        this.collectionPointId = collectionPointId;
    }

    public void setDepartmentId(String departmentId) {

        this.departmentId = departmentId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setContacName(String contacName) {
        this.contacName = contacName;
    }

    public void setPhotoNo(String photoNo) {
        this.photoNo = photoNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public void setCollectionPointId(String collectionPointId) {
        this.collectionPointId = collectionPointId;
    }

    public String getDepartmentId() {

        return departmentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getContacName() {
        return contacName;
    }

    public String getPhotoNo() {
        return photoNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public String getCollectionPointId() {
        return collectionPointId;
    }
}
