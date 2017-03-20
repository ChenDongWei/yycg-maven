package yycg.business.pojo.po;

public class Yycgdmx {
    private String id;

    private String yycgdid;

    private String ypxxid;

    private String usergysid;

    private Float zbjg;

    private Float jyjg;

    private Integer cgl;

    private Float cgje;

    private String cgzt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getYycgdid() {
        return yycgdid;
    }

    public void setYycgdid(String yycgdid) {
        this.yycgdid = yycgdid == null ? null : yycgdid.trim();
    }

    public String getYpxxid() {
        return ypxxid;
    }

    public void setYpxxid(String ypxxid) {
        this.ypxxid = ypxxid == null ? null : ypxxid.trim();
    }

    public String getUsergysid() {
        return usergysid;
    }

    public void setUsergysid(String usergysid) {
        this.usergysid = usergysid == null ? null : usergysid.trim();
    }

    public Float getZbjg() {
        return zbjg;
    }

    public void setZbjg(Float zbjg) {
        this.zbjg = zbjg;
    }

    public Float getJyjg() {
        return jyjg;
    }

    public void setJyjg(Float jyjg) {
        this.jyjg = jyjg;
    }

    public Integer getCgl() {
        return cgl;
    }

    public void setCgl(Integer cgl) {
        this.cgl = cgl;
    }

    public Float getCgje() {
        return cgje;
    }

    public void setCgje(Float cgje) {
        this.cgje = cgje;
    }

    public String getCgzt() {
        return cgzt;
    }

    public void setCgzt(String cgzt) {
        this.cgzt = cgzt == null ? null : cgzt.trim();
    }
}