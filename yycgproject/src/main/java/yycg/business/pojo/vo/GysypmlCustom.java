package yycg.business.pojo.vo;

/**
 * 供应商药品目录
 * 
 * @author dongwei
 * @date 2017年2月26日
 */
public class GysypmlCustom extends YpxxCustom {
	private String gysypmlid;
	private String ypxxid;
	private String usergysid;
	private String usergysmc;
	// 控制状态名称
	private String controlmc;

	private String control;
	//监督单位意见
	private String advice;

	public String getGysypmlid() {
		return gysypmlid;
	}

	public void setGysypmlid(String gysypmlid) {
		this.gysypmlid = gysypmlid;
	}

	public String getControlmc() {
		return controlmc;
	}

	public void setControlmc(String controlmc) {
		this.controlmc = controlmc;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getYpxxid() {
		return ypxxid;
	}

	public void setYpxxid(String ypxxid) {
		this.ypxxid = ypxxid;
	}

	public String getUsergysid() {
		return usergysid;
	}

	public void setUsergysid(String usergysid) {
		this.usergysid = usergysid;
	}

	public String getUsergysmc() {
		return usergysmc;
	}

	public void setUsergysmc(String usergysmc) {
		this.usergysmc = usergysmc;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}
}
