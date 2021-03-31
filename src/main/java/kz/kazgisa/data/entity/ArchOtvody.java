package kz.kazgisa.data.entity;

public class ArchOtvody {
    private Long oid;
    private String wkt;
    private Long objectid;
    private Boolean isLocalProj;

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public Long getObjectid() {
        return objectid;
    }

    public void setObjectid(Long objectid) {
        this.objectid = objectid;
    }

    public Boolean getLocalProj() {
        return isLocalProj;
    }

    public void setLocalProj(Boolean localProj) {
        isLocalProj = localProj;
    }
}
