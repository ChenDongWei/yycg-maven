package yycg.business.pojo.vo;

import java.util.List;

import yycg.base.pojo.po.Usergys;
import yycg.base.pojo.po.Useryy;
import yycg.base.pojo.vo.PageQuery;
import yycg.business.pojo.po.BusinessBasePo;

public class YycgdQueryVo extends BusinessBasePo {
	
	private PageQuery pageQuery;
	
	private Useryy useryy;//医院信息
	private Usergys usergys;
	private YycgdCustom yycgdCustom;// 采购单基本信息
	private YycgdmxCustom yycgdmxCustom;// 采购单明细信息
	private YpxxCustom ypxxCustom;//药品信息
	
	private GysypmlCustom gysypmlCustom;//供药商药品目录
	private List<YycgdmxCustom> yycgdmxCustoms;//药品明细
	
	private List<YycgdCustom> yycgdCustoms;//采购单批量信息
	private List<YycgdrkCustom> yycgdrkCustoms;//入库信息

	public YycgdCustom getYycgdCustom() {
		return yycgdCustom;
	}

	public void setYycgdCustom(YycgdCustom yycgdCustom) {
		this.yycgdCustom = yycgdCustom;
	}

	public YycgdmxCustom getYycgdmxCustom() {
		return yycgdmxCustom;
	}

	public void setYycgdmxCustom(YycgdmxCustom yycgdmxCustom) {
		this.yycgdmxCustom = yycgdmxCustom;
	}

	public PageQuery getPageQuery() {
		return pageQuery;
	}

	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}

	public YpxxCustom getYpxxCustom() {
		return ypxxCustom;
	}

	public void setYpxxCustom(YpxxCustom ypxxCustom) {
		this.ypxxCustom = ypxxCustom;
	}

	public GysypmlCustom getGysypmlCustom() {
		return gysypmlCustom;
	}

	public void setGysypmlCustom(GysypmlCustom gysypmlCustom) {
		this.gysypmlCustom = gysypmlCustom;
	}

	public Useryy getUseryy() {
		return useryy;
	}

	public void setUseryy(Useryy useryy) {
		this.useryy = useryy;
	}

	public List<YycgdmxCustom> getYycgdmxCustoms() {
		return yycgdmxCustoms;
	}

	public void setYycgdmxCustoms(List<YycgdmxCustom> yycgdmxCustoms) {
		this.yycgdmxCustoms = yycgdmxCustoms;
	}

	public List<YycgdCustom> getYycgdCustoms() {
		return yycgdCustoms;
	}

	public void setYycgdCustoms(List<YycgdCustom> yycgdCustoms) {
		this.yycgdCustoms = yycgdCustoms;
	}

	public List<YycgdrkCustom> getYycgdrkCustoms() {
		return yycgdrkCustoms;
	}

	public void setYycgdrkCustoms(List<YycgdrkCustom> yycgdrkCustoms) {
		this.yycgdrkCustoms = yycgdrkCustoms;
	}

	public Usergys getUsergys() {
		return usergys;
	}

	public void setUsergys(Usergys usergys) {
		this.usergys = usergys;
	}

}
