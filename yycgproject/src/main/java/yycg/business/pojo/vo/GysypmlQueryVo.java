package yycg.business.pojo.vo;

import yycg.base.pojo.vo.PageQuery;
/**
 * 供应商药品目录查询VO
 * @author dongwei
 * @date 2017年2月26日
 */
public class GysypmlQueryVo {
	private PageQuery pageQuery;
	
	private YpxxCustom ypxxCustom;
	
	private GysypmlCustom gysypmlCustom;

	public PageQuery getPageQuery() {
		return pageQuery;
	}

	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}

	public GysypmlCustom getGysypmlCustom() {
		return gysypmlCustom;
	}

	public void setGysypmlCustom(GysypmlCustom gysypmlCustom) {
		this.gysypmlCustom = gysypmlCustom;
	}

	public YpxxCustom getYpxxCustom() {
		return ypxxCustom;
	}

	public void setYpxxCustom(YpxxCustom ypxxCustom) {
		this.ypxxCustom = ypxxCustom;
	}

}
