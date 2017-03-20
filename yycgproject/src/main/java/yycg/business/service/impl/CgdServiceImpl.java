package yycg.business.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.base.service.SystemConfigService;
import yycg.business.dao.mapper.YycgdMapper;
import yycg.business.dao.mapper.YycgdMapperCustom;
import yycg.business.pojo.po.Yycgd;
import yycg.business.pojo.po.YycgdExample;
import yycg.business.pojo.vo.YycgdCustom;
import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;
import yycg.business.service.CgdService;
import yycg.util.UUIDBuild;

public class CgdServiceImpl implements CgdService {
	@Autowired
	private YycgdMapper yycgdMapper;
	
	@Autowired
	private YycgdMapperCustom yycgdMapperCustom;
	
	@Autowired
	private SystemConfigService systemConfigService;

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
		return 0;
	}

}
