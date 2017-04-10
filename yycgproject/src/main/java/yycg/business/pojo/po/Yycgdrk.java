package yycg.business.pojo.po;

import java.util.Date;

public class Yycgdrk extends BusinessBasePo{
    private String id;

    private String yycgdid;

    private String ypxxid;

    private Integer rkl;

    private Float rkje;

    private String rkdh;

    private String ypph;

    private Float ypyxq;

    private Date rktime;

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

    public Integer getRkl() {
        return rkl;
    }

    public void setRkl(Integer rkl) {
        this.rkl = rkl;
    }

    public Float getRkje() {
        return rkje;
    }

    public void setRkje(Float rkje) {
        this.rkje = rkje;
    }

    public String getRkdh() {
        return rkdh;
    }

    public void setRkdh(String rkdh) {
        this.rkdh = rkdh == null ? null : rkdh.trim();
    }

    public String getYpph() {
        return ypph;
    }

    public void setYpph(String ypph) {
        this.ypph = ypph == null ? null : ypph.trim();
    }

    public Float getYpyxq() {
        return ypyxq;
    }

    public void setYpyxq(Float ypyxq) {
        this.ypyxq = ypyxq;
    }

    public Date getRktime() {
        return rktime;
    }

    public void setRktime(Date rktime) {
        this.rktime = rktime;
    }

    public String getCgzt() {
        return cgzt;
    }

    public void setCgzt(String cgzt) {
        this.cgzt = cgzt == null ? null : cgzt.trim();
    }
}