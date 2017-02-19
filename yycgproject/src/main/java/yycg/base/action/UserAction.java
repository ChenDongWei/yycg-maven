package yycg.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.pojo.po.Dictinfo;
import yycg.base.pojo.vo.PageQuery;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;
import yycg.base.process.context.Config;
import yycg.base.process.result.DataGridResultInfo;
import yycg.base.process.result.ExceptionResultInfo;
import yycg.base.process.result.ResultInfo;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.base.service.SystemConfigService;
import yycg.base.service.UserService;

/**
 * 系统用户管理
 * @author dongwei
 * @date 2017年2月9日
 */
@Controller
@RequestMapping("/user")
public class UserAction {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	/**
	 * 用户查询页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryuser")
	public String queryUser(Model model) throws Exception{
		//将页面所需的数据取出，传到页面
		//用户类型
		List<Dictinfo> groupList = systemConfigService.findDictinfoByType("s01");
		model.addAttribute("groupList", groupList);
		
		return "/base/user/queryuser";
	}
	
	/**
	 * 用户查询页面的结果集
	 * @return DataGridResultInfo通过@ResponseBody将java转成json
	 * @throws Exception
	 */
	@RequestMapping("/queryuser_result")
	public @ResponseBody DataGridResultInfo queryUser_result(
			SysuserQueryVo sysuserQueryVo,
			int page,//页码
			int rows//每页显示个数
			) throws Exception{
		//非空校验
		sysuserQueryVo = sysuserQueryVo != null?sysuserQueryVo : new SysuserQueryVo();
		
		//查询列表的总数
		int total = userService.findSysuserCount(sysuserQueryVo);
		
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		
		sysuserQueryVo.setPageQuery(pageQuery);
		//分页查询，向sysuserQueryVo中传入pageQuery
		List<SysuserCustom> list = userService.findSysuserList(sysuserQueryVo);
		
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		//填充total
		dataGridResultInfo.setTotal(total);
		//填充rows
		dataGridResultInfo.setRows(list);
		return dataGridResultInfo;
	}
	
	//添加用户页面
	@RequestMapping("/addsysuser")
	public String addSysuser(Model model) throws Exception{
		
		return "/base/user/addsysuser";
	}
	
	//添加用户提交，结果转json输出页面
	@RequestMapping("/addsysusersubmit")
	public @ResponseBody SubmitResultInfo addSysuserSubmit(SysuserQueryVo sysuserQueryVo) throws Exception{
		//调用service执行用户添加
		userService.insertSysuser(sysuserQueryVo.getSysuserCustom());

		//将执行的结果返回页面
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//删除用户
	@RequestMapping("deletesysuser")
	public @ResponseBody SubmitResultInfo deleteSysuser(String id) throws Exception{
		userService.deleteSysuser(id);
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//修改用户页面
	@RequestMapping("/editsysuser")
	public String editSysuser(Model model, String id) throws Exception{
		//通过id取出用户信息，转向页面
		SysuserCustom sysuserCustom = userService.findSysuserById(id);
		model.addAttribute("sysuserCustom", sysuserCustom);
		
		return "/base/user/editsysuser";
	}
	
	//修改用户提交
	@RequestMapping("/editsysusersubmit")
	public @ResponseBody SubmitResultInfo editSysuserSubmit(String id, SysuserQueryVo sysuserQueryVo) throws Exception{
		userService.updateSysuser(id, sysuserQueryVo.getSysuserCustom());
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
}
