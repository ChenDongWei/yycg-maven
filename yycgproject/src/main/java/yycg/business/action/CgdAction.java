package yycg.business.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
import yycg.business.pojo.vo.GysypmlQueryVo;
import yycg.business.pojo.vo.YpxxCustom;
import yycg.business.pojo.vo.YycgdCustom;
import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;
import yycg.business.service.CgdService;
import yycg.util.MyUtil;

/**
 * 采购单管理
 * 
 * @author dongwei
 * @date 2017年3月17日
 */
@Controller
@RequestMapping("/cgd")
public class CgdAction {
	@Autowired
	private CgdService cgdService;

	@Autowired
	private SystemConfigService systemConfigService;

	// 创建采购单的基本信息页面
	@RequestMapping("addcgd")
	public String addCgd(HttpSession session, Model model) throws Exception {
		ActiveUser activeUser = (ActiveUser) session
				.getAttribute(Config.ACTIVEUSER_KEY);
		// 用户所属单位名称(医院名称)
		String sysmc = activeUser.getSysmc();
		// 生成采购单名称：医院名称+当前时间+“采购单”
		String yycgdmc = sysmc + MyUtil.getDate() + "采购单";
		// 采购年份：当前年份
		String year = MyUtil.get_YYYY(MyUtil.getDate());

		model.addAttribute("yycgdmc", yycgdmc);
		model.addAttribute("year", year);
		return "/business/cgd/addcgd";
	}

	// 创建采购单基本信息保存方法
	@RequestMapping("/addcgdsubmit")
	public @ResponseBody SubmitResultInfo addCgdSubmit(HttpSession session,
			String year, YycgdQueryVo yycgdQueryVo) throws Exception {
		ActiveUser activeUser = (ActiveUser) session
				.getAttribute(Config.ACTIVEUSER_KEY);
		// 医院id
		String useryyid = activeUser.getSysid();
		// 获取采购单id
		String yycgdid = cgdService.insertYycgd(useryyid, year,
				yycgdQueryVo.getYycgdCustom());
		// 获取采购单的id，将id通过ResultInfo中的sysdate传到页面
		ResultInfo resultInfo = ResultUtil.createSuccess(Config.MESSAGE, 906,
				null);
		resultInfo.getSysdata().put("yycgdid", yycgdid);

		return ResultUtil.createSubmitResult(resultInfo);
	}

	// 采购单修改页面方法
	@RequestMapping("editcgd")
	public String editCgd(Model model, String id) throws Exception {
		// 采购状态
		List<Dictinfo> cgztlist = systemConfigService.findDictinfoByType("011");
		// 交易状态
		List<Dictinfo> jyztlist = systemConfigService.findDictinfoByType("003");
		// 药品类型
		List<Dictinfo> lblist = systemConfigService.findDictinfoByType("001");
		YycgdCustom yycgdCustom = cgdService.findYycgdById(id);
		model.addAttribute("yycgd", yycgdCustom);
		model.addAttribute("cgztlist", cgztlist);
		model.addAttribute("jyztlist", jyztlist);
		model.addAttribute("lblist", lblist);

		return "/business/cgd/editcgd";
	}

	// 采购单修改提交方法
	@RequestMapping("/editcgdsubmit")
	public @ResponseBody SubmitResultInfo editCgdSubmit(String id,
			YycgdQueryVo yycgdQueryVo) throws Exception {
		cgdService.updateYycgd(id, yycgdQueryVo.getYycgdCustom());
		return ResultUtil.createSubmitResult(ResultUtil.createSuccess(
				Config.MESSAGE, 906, null));
	}

	// 采购单药品明细查询结果集
	@RequestMapping("/queryYycgdmx_result")
	public @ResponseBody DataGridResultInfo queryYycgdmx_result(String id,
			YycgdQueryVo yycgdQueryVo, int page, int rows) throws Exception {
		// 查询数据总数
		int total = cgdService.findYycgdmxCountByYycgdid(id, yycgdQueryVo);
		// 分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		// 设置分页的参数
		yycgdQueryVo.setPageQuery(pageQuery);
		// 分页查询
		List<YycgdmxCustom> list = cgdService.findYycgdmxListByYycgdid(id,
				yycgdQueryVo);

		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);
		// dataGridResultInfo.setFooter(footer);
		return dataGridResultInfo;
	}

	// 采购药品添加查询页面
	@RequestMapping("/queryaddyycgdmx")
	public String queryAddYycgdmx(Model model, String yycgdid) throws Exception {
		// 药品类别
		List<Dictinfo> yplblist = systemConfigService.findDictinfoByType("001");
		model.addAttribute("yplblist", yplblist);
		model.addAttribute("yycgdid", yycgdid);

		return "/business/cgd/queryaddyycgdmx";
	}

	// 采购药品添加查询列表结果集
	@RequestMapping("/queryaddyycgdmx_result")
	public @ResponseBody DataGridResultInfo queryAddYycgdmx_result(
			HttpSession session, String yycgdid, YycgdQueryVo yycgdQueryVo,
			int page, int rows) throws Exception {
		// 当前用户
		ActiveUser activeUser = (ActiveUser) session
				.getAttribute(Config.ACTIVEUSER_KEY);
		// 用户所属单位
		String useryyid = activeUser.getSysid();

		// 列表总数
		int total = cgdService.findAddYycgdmxCount(useryyid, yycgdid,
				yycgdQueryVo);

		// 分页参数
		PageQuery pageQuery = new PageQuery();
		pageQuery.setPageParams(total, rows, page);
		yycgdQueryVo.setPageQuery(pageQuery);

		// 分页查询列表
		List<YycgdmxCustom> list = cgdService.findAddYycgdmxList(useryyid,
				yycgdid, yycgdQueryVo);

		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setTotal(total);
		dataGridResultInfo.setRows(list);

		return dataGridResultInfo;
	}

	// 采购单药品提交
	@RequestMapping("/addyycgdmxsubmit")
	public @ResponseBody SubmitResultInfo addYycgdmxSubmit(String yycgdid,
			YycgdQueryVo yycgdQueryVo, int[] indexs) throws Exception {
		// 页面提交的业务数据
		List<YycgdmxCustom> list = yycgdQueryVo.getYycgdmxCustoms();
		// 处理的数据总数
		int count = indexs.length;
		// 处理成功的数量
		int count_success = 0;
		// 处理失败的数量
		int count_error = 0;
		// 处理失败的原因
		List<ResultInfo> msgs_error = new ArrayList<ResultInfo>();

		for (int i = 0; i < count; i++) {
			ResultInfo resultInfo = null;
			// 根据选中行的序号获取要处理的业务数据(单个)
			YycgdmxCustom yycgdmxCustom = list.get(indexs[i]);
			String ypxxid = yycgdmxCustom.getYpxxid();
			String usergysid = yycgdmxCustom.getUsergysid();
			try {
				cgdService.insertYycgdmx(yycgdid, ypxxid, usergysid);
			} catch (Exception e) {
				e.printStackTrace();

				if (e instanceof ExceptionResultInfo) {
					resultInfo = ((ExceptionResultInfo) e).getResultInfo();
				} else {
					// 构造未知错误异常
					resultInfo = ResultUtil.createFail(Config.MESSAGE, 900,
							null);
				}
			}
			if (resultInfo == null) {
				// 说明成功
				count_success++;
			} else {
				count_error++;
				// 记录失败原因
				msgs_error.add(resultInfo);
			}
		}
		// 提示用户成功数量、失败数量、失败原因
		return ResultUtil.createSubmitResult(
				ResultUtil.createSuccess(Config.MESSAGE, 907, new Object[] {
						count_success, count_error }), msgs_error);
	}
}
