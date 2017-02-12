package yycg.base.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.pojo.vo.PageQuery;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;
import yycg.base.process.result.DataGridResultInfo;
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
	
	/**
	 * 用户查询页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryuser")
	public String queryUser(Model model) throws Exception{
		
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
	public @ResponseBody Map<String, Object> addSysuserSubmit(SysuserQueryVo sysuserQueryVo) throws Exception{
		//提示给用户的信息
		String message = "操作成功！";
		int type = 0;//成功
		try {
			//调用service执行用户添加
			userService.insertSysuser(sysuserQueryVo.getSysuserCustom());
		} catch (Exception e) {
			// 输出异常信息
			e.printStackTrace();
			//对异常信息进行解析
			message = e.getMessage();
			type = 1;//失败
		}
		//将执行的结果返回页面
		Map<String, Object> result_map = new HashMap<String, Object>();
		result_map.put("type", type);
		result_map.put("message", message);
		
		return result_map;
	}
}
