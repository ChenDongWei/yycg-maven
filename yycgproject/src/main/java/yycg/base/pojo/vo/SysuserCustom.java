package yycg.base.pojo.vo;

import yycg.base.pojo.po.Sysuser;
/**
 * 扩展类，用于提交信息，查询条件
 * @author dongwei
 * @date 2017年2月9日
 */
public class SysuserCustom extends Sysuser {
	//单位名称
	private String sysmc;
	
	//用户类型名称
	private String groupname;

	public String getSysmc() {
		return sysmc;
	}

	public void setSysmc(String sysmc) {
		this.sysmc = sysmc;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
}
