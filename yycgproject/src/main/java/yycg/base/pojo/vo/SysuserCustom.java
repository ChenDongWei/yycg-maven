package yycg.base.pojo.vo;

import yycg.base.pojo.po.Sysuser;
/**
 * 扩展类，用于提交信息，查询条件
 * @author dongwei
 * @date 2017年2月9日
 */
public class SysuserCustom extends Sysuser {
	
	private String sysmc;//单位名称

	public String getSysmc() {
		return sysmc;
	}

	public void setSysmc(String sysmc) {
		this.sysmc = sysmc;
	}
	
}
