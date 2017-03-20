package yycg.business.pojo.po;

import java.util.Date;

public class Yycgd extends BusinessBasePo {
    private String id;

    private String bm;

    private String mc;

    private String useryyid;

    private String lxr;

    private String lxdh;

    private String cjr;

    private Date cjtime;

    private Date tjtime;

    private String bz;

    private String zt;

    private String shyj;

    private Date shtime;

    private Date xgtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm == null ? null : bm.trim();
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc == null ? null : mc.trim();
    }

    public String getUseryyid() {
        return useryyid;
    }

    public void setUseryyid(String useryyid) {
        this.useryyid = useryyid == null ? null : useryyid.trim();
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr == null ? null : lxr.trim();
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh == null ? null : lxdh.trim();
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr == null ? null : cjr.trim();
    }

    public Date getCjtime() {
        return cjtime;
    }

    public void setCjtime(Date cjtime) {
        this.cjtime = cjtime;
    }

    public Date getTjtime() {
        return tjtime;
    }

    public void setTjtime(Date tjtime) {
        this.tjtime = tjtime;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt == null ? null : zt.trim();
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj == null ? null : shyj.trim();
    }

    public Date getShtime() {
        return shtime;
    }

    public void setShtime(Date shtime) {
        this.shtime = shtime;
    }

    public Date getXgtime() {
        return xgtime;
    }

    public void setXgtime(Date xgtime) {
        this.xgtime = xgtime;
    }
}