package yycg.base.pojo.vo;
/**
 * 包装类，用于页面向action传递参数，再将数据传到mybatis
 * @author dongwei
 * @date 2017年2月9日
 */
public class SysuserQueryVo {
	//分页的参数
	private PageQuery pageQuery;
	//用户查询条件
	private SysuserCustom sysuserCustom;

	public SysuserCustom getSysuserCustom() {
		return sysuserCustom;
	}

	public void setSysuserCustom(SysuserCustom sysuserCustom) {
		this.sysuserCustom = sysuserCustom;
	}

	public PageQuery getPageQuery() {
		return pageQuery;
	}

	public void setPageQuery(PageQuery pageQuery) {
		this.pageQuery = pageQuery;
	}
	
}
