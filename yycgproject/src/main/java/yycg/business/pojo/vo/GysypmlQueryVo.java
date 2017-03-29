package yycg.business.pojo.vo;

import java.util.List;

import yycg.base.pojo.po.Usergys;
import yycg.base.pojo.vo.PageQuery;
import yycg.business.pojo.po.GysypmlControl;
/**
 * 供应商药品目录查询VO
 * @author dongwei
 * @date 2017年2月26日
 */
public class GysypmlQueryVo {
	private Usergys usergys;
	//添加页面批量提交
	private List<YpxxCustom> ypxxCustoms;
	
	//接收药品控制提交参数
	private List<GysypmlControl> gysypmlControls;
	
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

	public List<YpxxCustom> getYpxxCustoms() {
		return ypxxCustoms;
	}

	public void setYpxxCustoms(List<YpxxCustom> ypxxCustoms) {
		this.ypxxCustoms = ypxxCustoms;
	}

	public Usergys getUsergys() {
		return usergys;
	}

	public void setUsergys(Usergys usergys) {
		this.usergys = usergys;
	}

	public List<GysypmlControl> getGysypmlControls() {
		return gysypmlControls;
	}

	public void setGysypmlControls(List<GysypmlControl> gysypmlControls) {
		this.gysypmlControls = gysypmlControls;
	}

}
