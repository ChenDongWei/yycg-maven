package yycg.business.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.pojo.po.Dictinfo;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.pojo.vo.PageQuery;
import yycg.base.process.context.Config;
import yycg.base.process.result.DataGridResultInfo;
import yycg.base.service.SystemConfigService;
import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;
import yycg.business.service.YpmlService;

@Controller
@RequestMapping("/ypml")
public class YpmlAction {
	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private YpmlService ypmlService;
	
	//查询页面
	@RequestMapping("/querygysypml")
	public String queryGysypml(Model model) throws Exception{
		//药品类型
		List<Dictinfo> yplblist = systemConfigService.findDictinfoByType("001");
		model.addAttribute("yplblist", yplblist);
		return "/business/ypml/querygysypml";
	}
	
	//查询列表结果集
	@RequestMapping("/querygysypml_result")
	public @ResponseBody DataGridResultInfo queryGysypml_result(
			HttpSession session,
			GysypmlQueryVo gysypmlQueryVo,
			int page,
			int rows
			) throws Exception{
		//当前用户
		ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
		//用户所属单位
		String usergysId = activeUser.getSysid();//单位id
		
		//列表总数
		int total = ypmlService.findGysypmlCount(usergysId, gysypmlQueryVo);
		
		//分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		gysypmlQueryVo.setPageQuery(pageQuery);
		
		//分页查询列表
		List<GysypmlCustom> list = ypmlService.findGysypmlList(usergysId, gysypmlQueryVo);
		
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		
		return dataGridResultInfo;
	}
}
