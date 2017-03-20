package yycg.business.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.pojo.vo.ActiveUser;
import yycg.base.pojo.vo.PageQuery;
import yycg.base.process.context.Config;
import yycg.base.process.result.DataGridResultInfo;
import yycg.base.process.result.ResultInfo;
import yycg.base.process.result.ResultUtil;
import yycg.base.process.result.SubmitResultInfo;
import yycg.business.pojo.vo.YycgdCustom;
import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;
import yycg.business.service.CgdService;
import yycg.util.MyUtil;

/**
 * 采购单管理
 * @author dongwei
 * @date 2017年3月17日
 */
@Controller
@RequestMapping("/cgd")
public class CgdAction {
	@Autowired
	private CgdService cgdService;
	
	//创建采购单的基本信息页面
	@RequestMapping("addcgd")
	public String addCgd(HttpSession session, Model model) throws Exception{
		ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
		//用户所属单位名称(医院名称)
		String sysmc = activeUser.getSysmc();
		//生成采购单名称：医院名称+当前时间+“采购单”
		String yycgdmc = sysmc + MyUtil.getDate() + "采购单";
		//采购年份：当前年份
		String year = MyUtil.get_YYYY(MyUtil.getDate());
		
		model.addAttribute("yycgdmc", yycgdmc);
		model.addAttribute("year", year);
		return "/business/cgd/addcgd";
	}
	
	//创建采购单基本信息保存方法
	@RequestMapping("/addcgdsubmit")
	public @ResponseBody SubmitResultInfo addCgdSubmit(
			HttpSession session,
			String year,
			YycgdQueryVo yycgdQueryVo
			) throws Exception{
		ActiveUser activeUser = (ActiveUser) session.getAttribute(Config.ACTIVEUSER_KEY);
		//医院id
		String useryyid = activeUser.getUserid();
		//获取采购单id
		String yycgdid = cgdService.insertYycgd(useryyid, year, yycgdQueryVo.getYycgdCustom());
		//获取采购单的id，将id通过ResultInfo中的sysdate传到页面
		ResultInfo resultInfo = ResultUtil.createSuccess(Config.MESSAGE, 906,null);
		resultInfo.getSysdata().put("yycgdid", yycgdid);
		
		return ResultUtil.createSubmitResult(resultInfo);
	}
	
	//采购单修改页面方法
	@RequestMapping("editcgd")
	public String editCgd(Model model, String id) throws Exception{
		YycgdCustom yycgdCustom = cgdService.findYycgdById(id);
		model.addAttribute("yycgd", yycgdCustom);
		
		return "/business/cgd/editcgd";
	}
	
	//采购单修改提交方法
	@RequestMapping("/editcgdsubmit")
	public @ResponseBody SubmitResultInfo editCgdSubmit(
			String id,
			YycgdQueryVo yycgdQueryVo
			) throws Exception{
		cgdService.updateYycgd(id, yycgdQueryVo.getYycgdCustom());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
	}
	
	//采购单药品明细查询结果集
	@RequestMapping("/queryYycgdmx_result")
	public @ResponseBody DataGridResultInfo queryYycgdmx_result(
			String id,
			YycgdQueryVo yycgdQueryVo,
			int page,
			int rows
			) throws Exception {
		//查询数据总数
		int total = cgdService.findYycgdmxCountByYycgdid(id, yycgdQueryVo);
		//分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		//设置分页的参数
		yycgdQueryVo.setPageQuery(pageQuery);
		//分页查询
		List<YycgdmxCustom> list = cgdService.findYycgdmxListByYycgdid(id, yycgdQueryVo);
		
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		//dataGridResultInfo.setFooter(footer);
		return dataGridResultInfo;
	}
}
