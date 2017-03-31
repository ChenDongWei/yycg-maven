package yycg.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yycg.base.dao.mapper.UserjdMapper;
import yycg.base.dao.mapper.UseryyMapper;
import yycg.base.pojo.po.Dictinfo;
import yycg.base.pojo.po.Userjd;
import yycg.base.pojo.po.Useryy;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.process.context.Config;
import yycg.base.process.result.DataGridResultInfo;
import yycg.base.process.result.ResultUtil;
import yycg.base.service.SystemConfigService;
import yycg.business.dao.mapper.YpxxMapper;
import yycg.business.dao.mapper.YycgdMapper;
import yycg.business.dao.mapper.YycgdMapperCustom;
import yycg.business.dao.mapper.YycgdmxMapper;
import yycg.business.pojo.po.Ypxx;
import yycg.business.pojo.po.Yycgd;
import yycg.business.pojo.po.YycgdExample;
import yycg.business.pojo.po.Yycgdmx;
import yycg.business.pojo.po.YycgdmxExample;
import yycg.business.pojo.vo.YycgdCustom;
import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;
import yycg.business.service.CgdService;
import yycg.util.MyUtil;
import yycg.util.UUIDBuild;

public class CgdServiceImpl implements CgdService {
	@Autowired
	private YycgdMapper yycgdMapper;
	
	@Autowired
	private YycgdMapperCustom yycgdMapperCustom;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private UseryyMapper useryyMapper;
	
	@Autowired
	private YpxxMapper ypxxMapper;
	
	@Autowired
	private YycgdmxMapper yycgdmxMapper;
	
	@Autowired
	private UserjdMapper userjdMapper;

	@Override
	public String insertYycgd(String useryyid, String year,
			YycgdCustom yycgdCustom) throws Exception {
		//获取采购单号
		String bm = yycgdMapperCustom.getYycgdBm(year);
		//采购单主键
		yycgdCustom.setId(bm);//采购单id主键和bm一致，目的是为了方便操作采购单
		//采购单号
		yycgdCustom.setBm(bm);
		//创建采购单医院
		yycgdCustom.setUseryyid(useryyid);
		//创建时间
		yycgdCustom.setCjtime(new Date());
		//采购单状态,默认未提交
		yycgdCustom.setZt("1");
		//设置年份，为了操作动态表
		yycgdCustom.setBusinessyear(year);
		yycgdMapper.insert(yycgdCustom);
		
		//返回采购单id
		return bm;
	}

	@Override
	public YycgdCustom findYycgdById(String id) throws Exception {
		//从采购单id获取年份
		String year = id.substring(0, 4);
		YycgdExample yycgdExample = new YycgdExample();
		YycgdExample.Criteria criteria = yycgdExample.createCriteria();
		criteria.andIdEqualTo(id);
		//通过YycgdExample传入年份
		yycgdExample.setBusinessyear(year);
		List<Yycgd> list = yycgdMapper.selectByExample(yycgdExample);
		Yycgd yycgd = null;
		YycgdCustom yycgdCustom = new YycgdCustom();
		if (list != null && list.size() == 1) {
			yycgd = list.get(0);
			//将yycgd属性的值拷贝到yycgdCustom
			BeanUtils.copyProperties(yycgd, yycgdCustom);
		}else {
			//系统没有该采购单
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 501, null));
		}
		//添加采购单状态名称
		String zt = yycgd.getZt();
		//根据状态代码查询状态对应的名称
		String yycgdztmc = systemConfigService.findDictinfoByDictcode("010", zt).getInfo();
		yycgdCustom.setYycgdztmc(yycgdztmc);
		return yycgdCustom;
	}

	@Override
	public void updateYycgd(String id, YycgdCustom yycgdCustom)
			throws Exception {
		//从采购单id获取年份
		String year = id.substring(0, 4);
		//从数据库查询采购单信息
		YycgdCustom yycgdCustom_old = this.findYycgdById(id);
		//向对象设置修改的值
		yycgdCustom_old.setLxr(yycgdCustom.getLxr());
		yycgdCustom_old.setLxdh(yycgdCustom.getLxdh());
		yycgdCustom_old.setMc(yycgdCustom.getMc());
		yycgdCustom_old.setBz(yycgdCustom.getBz());
		yycgdCustom_old.setBusinessyear(year);
		
		yycgdMapper.updateByPrimaryKey(yycgdCustom_old);
	}

	@Override
	public List<YycgdmxCustom> findYycgdmxListByYycgdid(String yycgdid,
			YycgdQueryVo yycgdQueryVo) throws Exception {
		yycgdQueryVo = yycgdQueryVo != null?yycgdQueryVo : new YycgdQueryVo();
		
		//通过采购单id获取年份
		String businessyear = yycgdid.substring(0, 4);
		//设置固定的业务参数
		YycgdmxCustom yycgdmxCustom = yycgdQueryVo.getYycgdmxCustom();
		if (yycgdmxCustom == null) {
			yycgdmxCustom = new YycgdmxCustom();
		}
		yycgdmxCustom.setYycgdid(yycgdid);
		yycgdQueryVo.setYycgdmxCustom(yycgdmxCustom);
		//设置年份
		yycgdQueryVo.setBusinessyear(businessyear);
		return yycgdMapperCustom.findYycgdmxList(yycgdQueryVo);
	}

	@Override
	public int findYycgdmxCountByYycgdid(String yycgdid,
			YycgdQueryVo yycgdQueryVo) throws Exception {
		// 非空判断
		yycgdQueryVo = yycgdQueryVo != null?yycgdQueryVo : new YycgdQueryVo();
		//通过采购单id得到年份
		String businessyear = yycgdid.substring(0, 4);
		YycgdmxCustom yycgdmxCustom = yycgdQueryVo.getYycgdmxCustom();
		if (yycgdmxCustom == null) {
			yycgdmxCustom = new YycgdmxCustom();
		}
		yycgdmxCustom.setYycgdid(yycgdid);
		yycgdQueryVo.setYycgdmxCustom(yycgdmxCustom);
		yycgdQueryVo.setBusinessyear(businessyear);
		return yycgdMapperCustom.findYycgdmxCount(yycgdQueryVo);
	}

	@Override
	public List<YycgdmxCustom> findAddYycgdmxList(String useryyid,
			String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception {
		//根据医院id得到医院的区域id
		Useryy useryy = useryyMapper.selectByPrimaryKey(useryyid);
		String dq = useryy.getDq();
		//向yycgdQueryVo设置业务参数
		Useryy useryy_1 = yycgdQueryVo.getUseryy();
		if (useryy_1 == null) {
			useryy_1 = new Useryy();
		}
		useryy_1.setDq(dq);
		yycgdQueryVo.setUseryy(useryy_1);
		//采购单id
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		if (yycgdCustom == null) {
			yycgdCustom = new YycgdCustom();
		}
		yycgdCustom.setId(yycgdid);
		yycgdQueryVo.setYycgdCustom(yycgdCustom);
		//设置年份
		String businessyear = yycgdid.substring(0, 4);
		yycgdQueryVo.setBusinessyear(businessyear);
		
		return yycgdMapperCustom.findAddYycgdmxList(yycgdQueryVo);
	}

	@Override
	public int findAddYycgdmxCount(String useryyid, String yycgdid,
			YycgdQueryVo yycgdQueryVo) throws Exception {
		//根据医院id得到医院的区域id
		Useryy useryy = useryyMapper.selectByPrimaryKey(useryyid);
		String dq = useryy.getDq();
		//向yycgdQueryVo设置业务参数
		Useryy useryy_1 = yycgdQueryVo.getUseryy();
		if (useryy_1 == null) {
			useryy_1 = new Useryy();
		}
		useryy_1.setDq(dq);
		yycgdQueryVo.setUseryy(useryy_1);
		//采购单id
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		if (yycgdCustom == null) {
			yycgdCustom = new YycgdCustom();
		}
		yycgdCustom.setId(yycgdid);
		yycgdQueryVo.setYycgdCustom(yycgdCustom);
		//设置年份
		String businessyear = yycgdid.substring(0, 4);
		yycgdQueryVo.setBusinessyear(businessyear);
		
		return yycgdMapperCustom.findAddYycgdmxCount(yycgdQueryVo);
	}
	
	//根据采购单id和药品id查询采购单明细
	public Yycgdmx findYycgdmxByYycgdidAndYpxxid(String yycgdid, String ypxxid) throws Exception{
		YycgdmxExample yycgdmxExample = new YycgdmxExample();
		YycgdmxExample.Criteria criteria = yycgdmxExample.createCriteria();
		criteria.andYycgdidEqualTo(yycgdid);
		criteria.andYpxxidEqualTo(ypxxid);
		//设置年份
		String businessyear = yycgdid.substring(0, 4);
		yycgdmxExample.setBusinessyear(businessyear);
		List<Yycgdmx> list = yycgdmxMapper.selectByExample(yycgdmxExample);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertYycgdmx(String yycgdid, String ypxxid, String usergysid)
			throws Exception {
		//药品信息
		Ypxx ypxx = ypxxMapper.selectByPrimaryKey(ypxxid);
		if (ypxx == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 316, null));
		}
		//校验采购单明细表唯一约束
		Yycgdmx yycgdmx_only = this.findYycgdmxByYycgdidAndYpxxid(yycgdid, ypxxid);
		if (yycgdmx_only != null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 508, null));
		}
		//年份
		String businessyear = yycgdid.substring(0, 4);
		//采购单明细
		Yycgdmx yycgdmx = new Yycgdmx();
		yycgdmx.setBusinessyear(businessyear);
		yycgdmx.setId(UUIDBuild.getUUID());
		yycgdmx.setYycgdid(yycgdid);
		yycgdmx.setYpxxid(ypxxid);
		yycgdmx.setUsergysid(usergysid);
		yycgdmx.setZbjg(ypxx.getZbjg());
		yycgdmx.setJyjg(ypxx.getZbjg());//交易价格等于中标价格
		yycgdmx.setCgzt("1");//默认为1,未确认送货
		
		yycgdmxMapper.insert(yycgdmx);
	}

	@Override
	public void updateYycgdmx(String yycgdid, String ypxxid, Integer cgl)
			throws Exception {
		String businessyear = yycgdid.substring(0, 4);
		//根据采购单id和药品id获得采购明细的记录
		Yycgdmx yycgdmx = this.findYycgdmxByYycgdidAndYpxxid(yycgdid, ypxxid);
		if (yycgdmx == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 509, null));
		}
		if (cgl == null || cgl <= 0) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 561, null));
		}
		
		Float jyjg = yycgdmx.getJyjg();
		Float cgje = jyjg * cgl;
		
		//定义一个更新的对象
		Yycgdmx yycgdmx_update = new Yycgdmx();
		yycgdmx_update.setId(yycgdmx.getId());
		yycgdmx_update.setCgl(cgl);
		yycgdmx_update.setCgje(cgje);
		//设置年份
		yycgdmx_update.setBusinessyear(businessyear);
		yycgdmxMapper.updateByPrimaryKeySelective(yycgdmx_update);
	}

	@Override
	public List<YycgdCustom> findYycgdList(String useryyid, String year,
			YycgdQueryVo yycgdQueryVo) throws Exception {
		yycgdQueryVo = yycgdQueryVo != null?yycgdQueryVo : new YycgdQueryVo();
		//设置查询年份
		yycgdQueryVo.setBusinessyear(year);
		//设置医院id
		Useryy useryy = yycgdQueryVo.getUseryy();
		if (useryy == null) {
			useryy = new Useryy();
		}
		useryy.setId(useryyid);
		yycgdQueryVo.setUseryy(useryy);
		
		return yycgdMapperCustom.findYycgdList(yycgdQueryVo);
	}

	@Override
	public int findYycgdCount(String useryyid, String year, YycgdQueryVo yycgdQueryVo)
			throws Exception {
		yycgdQueryVo = yycgdQueryVo != null?yycgdQueryVo : new YycgdQueryVo();
		//设置查询年份
		yycgdQueryVo.setBusinessyear(year);
		//设置医院id
		Useryy useryy = yycgdQueryVo.getUseryy();
		if (useryy == null) {
			useryy = new Useryy();
		}
		useryy.setId(useryyid);
		yycgdQueryVo.setUseryy(useryy);
		
		return yycgdMapperCustom.findYycgdCount(yycgdQueryVo);
	}

	@Override
	public void saveYycgdSubmitStatus(String yycgdid) throws Exception {
		//采购单状态为未提交或审核不通过方可提交
		Yycgd yycgd = this.findYycgdById(yycgdid);
		if (yycgd == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 501, null));
		}
		String zt = yycgd.getZt();
		if (!zt.equals("1") && !zt.endsWith("4")) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 502, null));
		}
		
		//采购单必须包含采购药品明细
		List<YycgdmxCustom> yycgdmxList = this.findYycgdmxListByYycgdid(yycgdid, null);
		if (yycgdmxList == null || yycgdmxList.size() <= 0) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 504, null));
		}
		
		//采购单的采购药品信息必须包含采购量和金额
		for (YycgdmxCustom yycgdmxCustom : yycgdmxList) {
			Integer cgl = yycgdmxCustom.getCgl();
			Float cgje = yycgdmxCustom.getCgje();
			if (cgl == null || cgje == null) {
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 505, null));
			}
		}
		
		String businessyear = yycgdid.substring(0, 4);
		//更新采购单状态及提交时间
		Yycgd yycgd_update = new Yycgd();
		yycgd_update.setId(yycgdid);
		yycgd_update.setZt("2");
		yycgd_update.setTjtime(MyUtil.getNowDate());
		yycgd_update.setBusinessyear(businessyear);
		
		yycgdMapper.updateByPrimaryKeySelective(yycgd_update);
	}

	@Override
	public List<YycgdCustom> findCheckYycgdList(String year, String userjdid, YycgdQueryVo yycgdQueryVo)
			throws Exception {
		yycgdQueryVo = yycgdQueryVo != null?yycgdQueryVo : new YycgdQueryVo();
		//采购单状态
		String zt = "2";//审核中
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		if (yycgdCustom == null) {
			yycgdCustom = new YycgdCustom();
		}
		yycgdCustom.setZt(zt);
		yycgdQueryVo.setYycgdCustom(yycgdCustom);
		//根据监管单位id查询监管单位
		Userjd userjd = userjdMapper.selectByPrimaryKey(userjdid);
		//管理地区
		String dq = userjd.getDq();
		
		Useryy useryy = yycgdQueryVo.getUseryy();
		useryy = useryy != null?useryy : new Useryy();
		useryy.setDq(dq);
		yycgdQueryVo.setUseryy(useryy);
		
		yycgdQueryVo.setBusinessyear(year);
		return yycgdMapperCustom.findYycgdList(yycgdQueryVo);
	}

	@Override
	public int findCheckYycgdCount(String year, String userjdid,
			YycgdQueryVo yycgdQueryVo) throws Exception {
		yycgdQueryVo = yycgdQueryVo != null?yycgdQueryVo : new YycgdQueryVo();
		//采购单状态
		String zt = "2";//审核中
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		if (yycgdCustom == null) {
			yycgdCustom = new YycgdCustom();
		}
		yycgdCustom.setZt(zt);
		yycgdQueryVo.setYycgdCustom(yycgdCustom);
		//根据监管单位id查询监管单位
		Userjd userjd = userjdMapper.selectByPrimaryKey(userjdid);
		//管理地区
		String dq = userjd.getDq();
		
		Useryy useryy = yycgdQueryVo.getUseryy();
		useryy = useryy != null?useryy : new Useryy();
		useryy.setDq(dq);
		yycgdQueryVo.setUseryy(useryy);
		
		yycgdQueryVo.setBusinessyear(year);
		return yycgdMapperCustom.findYycgdCount(yycgdQueryVo);
	}

}
