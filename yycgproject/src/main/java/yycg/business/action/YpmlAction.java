package yycg.business.action;

import java.util.ArrayList;
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
import yycg.base.process.result.ExceptionResultInfo;
import yycg.base.process.result.ResultInfo;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.base.service.SystemConfigService;
import yycg.business.pojo.po.GysypmlControl;
import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;
import yycg.business.pojo.vo.YpxxCustom;
import yycg.business.service.YpmlService;

@Controller
@RequestMapping("/ypml")
public class YpmlAction {
	@Autowired
	private SystemConfigService systemConfigService;

	@Autowired
	private YpmlService ypmlService;

	// 查询页面
	@RequestMapping("/querygysypml")
	public String queryGysypml(Model model) throws Exception {
		// 药品类型
		List<Dictinfo> yplblist = systemConfigService.findDictinfoByType("001");
		// 交易状态
		List<Dictinfo> jyztlist = systemConfigService.findDictinfoByType("003");
		// 质量层次
		List<Dictinfo> zlcclist = systemConfigService.findDictinfoByType("004");
		// 供货状态
		List<Dictinfo> controllist = systemConfigService.findDictinfoByType("008");
		model.addAttribute("yplblist", yplblist);
		model.addAttribute("jyztlist", jyztlist);
		model.addAttribute("zlcclist", zlcclist);
		model.addAttribute("controllist", controllist);
		
		return "/business/ypml/querygysypml";
	}

	// 查询列表结果集
	@RequestMapping("/querygysypml_result")
	public @ResponseBody DataGridResultInfo queryGysypml_result(
			HttpSession session, GysypmlQueryVo gysypmlQueryVo, int page,
			int rows) throws Exception {
		// 当前用户
		ActiveUser activeUser = (ActiveUser) session
				.getAttribute(Config.ACTIVEUSER_KEY);
		// 用户所属单位
		String usergysId = activeUser.getSysid();// 单位id

		// 列表总数
		int total = ypmlService.findGysypmlCount(usergysId, gysypmlQueryVo);

		// 分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		gysypmlQueryVo.setPageQuery(pageQuery);

		// 分页查询列表
		List<GysypmlCustom> list = ypmlService.findGysypmlList(usergysId,
				gysypmlQueryVo);

		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);

		return dataGridResultInfo;
	}

	// 添加页面
	@RequestMapping("/gysypmladd")
	public String queryAddGysypml(Model model) throws Exception {
		// 药品类型
		List<Dictinfo> yplblist = systemConfigService.findDictinfoByType("001");
		// 交易状态
		List<Dictinfo> jyztlist = systemConfigService.findDictinfoByType("003");
		// 质量层次
		List<Dictinfo> zlcclist = systemConfigService.findDictinfoByType("004");
		// 供货状态
		List<Dictinfo> controllist = systemConfigService.findDictinfoByType("008");
		model.addAttribute("yplblist", yplblist);
		model.addAttribute("jyztlist", jyztlist);
		model.addAttribute("zlcclist", zlcclist);
		model.addAttribute("controllist", controllist);
		
		return "/business/ypml/querygysypmladd";
	}

	// 查询列表结果集
	@RequestMapping("/queryaddgysypml_result")
	public @ResponseBody DataGridResultInfo queryAddGysypml_result(
			HttpSession session, GysypmlQueryVo gysypmlQueryVo, int page,
			int rows) throws Exception {
		// 当前用户
		ActiveUser activeUser = (ActiveUser) session
				.getAttribute(Config.ACTIVEUSER_KEY);
		// 用户所属单位
		String usergysId = activeUser.getSysid();// 单位id

		// 列表总数
		int total = ypmlService.findAddGysypmlCount(usergysId, gysypmlQueryVo);

		// 分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		gysypmlQueryVo.setPageQuery(pageQuery);

		// 分页查询列表
		List<GysypmlCustom> list = ypmlService.findAddGysypmlList(usergysId,
				gysypmlQueryVo);

		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);

		return dataGridResultInfo;
	}
	
	//供应商药品目录添加提交
	@RequestMapping("addgysypmlsubmit")
	public @ResponseBody SubmitResultInfo addGysypmlSubmit(
			HttpSession session,
			int[] indexs,//接收页面的行序号
			GysypmlQueryVo gysypmlQueryVo
			) throws Exception{
		
		ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
		//当前用户所属单位
		String usergysid = activeUser.getSysid();
		
		//要处理的业务数据(页面提交多个业务数据)
		List<YpxxCustom> list = gysypmlQueryVo.getYpxxCustoms();
		//处理数据的总数
		int count = indexs.length;
		//处理成功的数量
		int count_success = 0;
		//处理失败的数量
		int count_error = 0;
		//处理失败的原因
		List<ResultInfo> msg_error = new ArrayList<ResultInfo>();
		
		for (int i = 0; i < count; i++) {
			ResultInfo resultInfo = null;
			
			//根据选中行的序号获取要处理的业务数据
			YpxxCustom ypxxCustom = list.get(indexs[i]);
			String ypxxid = ypxxCustom.getId();
			
			try {
				ypmlService.insertGysypml(usergysid, ypxxid);
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof ExceptionResultInfo) {
					resultInfo = ((ExceptionResultInfo) e).getResultInfo();
				}else {
					//构造未知错误异常
					resultInfo = ResultUtil.createFail(Config.MESSAGE, 900, null);
				}
			}
			if (resultInfo == null) {
				//成功
				count_success++;
			}else {
				count_error++;
				msg_error.add(resultInfo);
			}
		}
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
				count_success,count_error
		}), msg_error);
	}
	
	//供应商药品目录删除
	@RequestMapping("deletegysypmlsubmit")
	public @ResponseBody SubmitResultInfo deleteGysypmlSubmit(
			HttpSession session,
			int[] indexs,//接收页面的行序号
			GysypmlQueryVo gysypmlQueryVo
			) throws Exception{
		
		ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
		//当前用户所属单位
		String usergysid = activeUser.getSysid();
		
		//要处理的业务数据(页面提交多个业务数据)
		List<YpxxCustom> list = gysypmlQueryVo.getYpxxCustoms();
		//处理数据的总数
		int count = indexs.length;
		//处理成功的数量
		int count_success = 0;
		//处理失败的数量
		int count_error = 0;
		//处理失败的原因
		List<ResultInfo> msg_error = new ArrayList<ResultInfo>();
		
		for (int i = 0; i < count; i++) {
			ResultInfo resultInfo = null;
			
			//根据选中行的序号获取要处理的业务数据
			YpxxCustom ypxxCustom = list.get(indexs[i]);
			String ypxxid = ypxxCustom.getId();
			
			try {
				ypmlService.deleteGysypmlByUsergysidAndYpxxid(usergysid, ypxxid);
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof ExceptionResultInfo) {
					resultInfo = ((ExceptionResultInfo) e).getResultInfo();
				}else {
					//构造未知错误异常
					resultInfo = ResultUtil.createFail(Config.MESSAGE, 900, null);
				}
			}
			if (resultInfo == null) {
				//成功
				count_success++;
			}else {
				count_error++;
				msg_error.add(resultInfo);
			}
		}
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
				count_success,count_error
		}), msg_error);
	}
	
	//跳转到供应商药品目录控制查询页面
	@RequestMapping("/querygysypmlcontrol")
	public String queryGysypmlControl(Model model) throws Exception{
		//药品列表
		List<Dictinfo> yplblist = systemConfigService.findDictinfoByType("001");
		model.addAttribute("yplblist", yplblist);
		//交易状态
		List<Dictinfo> jyztlist = systemConfigService.findDictinfoByType("003");
		model.addAttribute("jyztlist", jyztlist);
		//供货状态
		List<Dictinfo> controllist = systemConfigService.findDictinfoByType("008");
		model.addAttribute("controllist", controllist);
		return "/business/ypml/querygysypmlcontrol";
	}
	
	//查询供应商药品目录控制结果集
	@RequestMapping("querygysypmlcontrol_result")
	public @ResponseBody DataGridResultInfo queryGysypmlControl_result(
			HttpSession session,
			GysypmlQueryVo gysypmlQueryVo,
			int page,
			int rows
			) throws Exception{
		//列表的总数
		int total = ypmlService.findGysypmlControlCount(gysypmlQueryVo);
		//分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		gysypmlQueryVo.setPageQuery(pageQuery);
		//分页查询列表
		List<GysypmlCustom> list = ypmlService.findGysypmlControlList(gysypmlQueryVo);
		
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		
		return dataGridResultInfo;
	}
	
	//供应商药品目录控制提交
	@RequestMapping("/gysypmlcontrolsubmit")
	public @ResponseBody SubmitResultInfo gysypmlControlSubmit(
			HttpSession session,
			int[] indexs,
			GysypmlQueryVo gysypmlQueryVo
			) throws Exception{
		//页面提交的业务数据
		List<GysypmlControl> list = gysypmlQueryVo.getGysypmlControls();
		//处理数据的总数
		int count = indexs.length;
		//处理成功的数量
		int count_success = 0;
		//处理失败的数量
		int count_error = 0;
		//处理失败的原因
		List<ResultInfo> msg_error = new ArrayList<ResultInfo>();
		
		for (int i = 0; i < count; i++) {
			ResultInfo resultInfo = null;
			//根据选中的序号获取要处理的业务数据
			GysypmlControl gysypmlControl = list.get(indexs[i]);
			String usergysid = gysypmlControl.getUsergysid();
			String ypxxid = gysypmlControl.getYpxxid();
			String control = gysypmlControl.getControl();
			String advice = gysypmlControl.getAdvice();
			
			try {
				ypmlService.updateGysypmlControl(usergysid, ypxxid, control, advice);
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof ExceptionResultInfo) {
					resultInfo = ((ExceptionResultInfo) e).getResultInfo();
				}else {
					//构造未知错误异常
					resultInfo = ResultUtil.createFail(Config.MESSAGE, 900, null);
				}
			}
			if (resultInfo == null) {
				//成功
				count_success++;
			}else {
				count_error++;
				msg_error.add(resultInfo);
			}
		}
		
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[]{
				count_success,count_error
		}), msg_error);
	}
}
