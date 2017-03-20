package yycg.business.pojo.vo;

import yycg.base.pojo.vo.PageQuery;
import yycg.business.pojo.po.BusinessBasePo;

public class YycgdQueryVo extends BusinessBasePo {
	
	private PageQuery pageQuery;
	
	private YycgdCustom yycgdCustom;// 采购单基本信息
	private YycgdmxCustom yycgdmxCustom;// 采购单明细信息
	private YpxxCustom ypxxCustom;//药品信息

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

}
