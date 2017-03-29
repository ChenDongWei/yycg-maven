package yycg.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import yycg.base.process.context.Config;
import yycg.base.process.result.ResultUtil;
import yycg.base.service.SystemConfigService;
import yycg.business.dao.mapper.GysypmlControlMapper;
import yycg.business.dao.mapper.GysypmlMapper;
import yycg.business.dao.mapper.GysypmlMapperCustom;
import yycg.business.dao.mapper.YpxxMapper;
import yycg.business.pojo.po.Gysypml;
import yycg.business.pojo.po.GysypmlControl;
import yycg.business.pojo.po.GysypmlControlExample;
import yycg.business.pojo.po.GysypmlExample;
import yycg.business.pojo.po.Ypxx;
import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;
import yycg.business.service.YpmlService;
import yycg.util.UUIDBuild;

public class YpmlServiceImpl implements YpmlService {
	@Autowired
	private GysypmlMapperCustom gysypmlMapperCustom;
	
	@Autowired
	private GysypmlMapper gysypmlMapper;
	
	@Autowired
	private YpxxMapper ypxxMapper;
	
	@Autowired
	private GysypmlControlMapper gysypmlControlMapper;
	
	@Autowired
	private SystemConfigService systemConfigService;

	@Override
	public List<GysypmlCustom> findGysypmlList(String usergysId,
			GysypmlQueryVo gysypmlQueryVo) throws Exception {
		// 非空判断
		gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo
				: new GysypmlQueryVo();

		GysypmlCustom gysypmlCustom = gysypmlQueryVo.getGysypmlCustom();
		if (gysypmlCustom == null) {
			gysypmlCustom = new GysypmlCustom();
		}
		// 设置数据范围权限
		gysypmlCustom.setUsergysid(usergysId);
		gysypmlQueryVo.setGysypmlCustom(gysypmlCustom);

		return gysypmlMapperCustom.findGysypmlList(gysypmlQueryVo);
	}

	@Override
	public int findGysypmlCount(String usergysId, GysypmlQueryVo gysypmlQueryVo)
			throws Exception {
		// 非空判断
		gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo
				: new GysypmlQueryVo();

		GysypmlCustom gysypmlCustom = gysypmlQueryVo.getGysypmlCustom();
		if (gysypmlCustom == null) {
			gysypmlCustom = new GysypmlCustom();
		}
		// 设置数据范围权限
		gysypmlCustom.setUsergysid(usergysId);
		gysypmlQueryVo.setGysypmlCustom(gysypmlCustom);

		return gysypmlMapperCustom.findGysypmlCount(gysypmlQueryVo);
	}

	@Override
	public List<GysypmlCustom> findAddGysypmlList(String usergysId,
			GysypmlQueryVo gysypmlQueryVo) throws Exception {
		// 非空判断
		gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo
				: new GysypmlQueryVo();

		GysypmlCustom gysypmlCustom = gysypmlQueryVo.getGysypmlCustom();
		if (gysypmlCustom == null) {
			gysypmlCustom = new GysypmlCustom();
		}
		// 设置数据范围权限
		gysypmlCustom.setUsergysid(usergysId);
		gysypmlQueryVo.setGysypmlCustom(gysypmlCustom);
		return gysypmlMapperCustom.findAddGysypmlList(gysypmlQueryVo);
	}

	@Override
	public int findAddGysypmlCount(String usergysId,
			GysypmlQueryVo gysypmlQueryVo) throws Exception {
		// 非空判断
		gysypmlQueryVo = gysypmlQueryVo != null ? gysypmlQueryVo
				: new GysypmlQueryVo();

		GysypmlCustom gysypmlCustom = gysypmlQueryVo.getGysypmlCustom();
		if (gysypmlCustom == null) {
			gysypmlCustom = new GysypmlCustom();
		}
		// 设置数据范围权限
		gysypmlCustom.setUsergysid(usergysId);
		gysypmlQueryVo.setGysypmlCustom(gysypmlCustom);
		return gysypmlMapperCustom.findAddGysypmlCount(gysypmlQueryVo);
	}

	// 根据供应商id和药品id查询供应商药品目录
	public Gysypml findGysypmlByUsergysidAndYpxxid(String usergysid, String ypxxid) throws Exception{
		GysypmlExample gysypmlExample = new GysypmlExample();
		GysypmlExample.Criteria criteria = gysypmlExample.createCriteria();
		// 设置查询条件
		criteria.andUsergysidEqualTo(usergysid);
		criteria.andYpxxidEqualTo(ypxxid);
		
		List<Gysypml> list = gysypmlMapper.selectByExample(gysypmlExample);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	// 根据供应商id和药品id查询供应商药品目录控制记录
	public GysypmlControl findGysypmlControlByUsergysidAndYpxxid(String usergysid, String ypxxid) throws Exception{
		GysypmlControlExample gysypmlControlExample = new GysypmlControlExample();
		GysypmlControlExample.Criteria criteria = gysypmlControlExample.createCriteria();
		// 设置查询条件
		criteria.andUsergysidEqualTo(usergysid);
		criteria.andYpxxidEqualTo(ypxxid);
		
		List<GysypmlControl> list = gysypmlControlMapper.selectByExample(gysypmlControlExample);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void insertGysypml(String usergysid, String ypxxid) throws Exception {
		// 只允许添加供应商药品目录没有的药品
		Gysypml gysypml = this.findGysypmlByUsergysidAndYpxxid(usergysid, ypxxid);
		if (gysypml != null) {//已存在
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 401, null));
		}
		//药品的交易状态为暂停时不允许添加
		Ypxx ypxx = ypxxMapper.selectByPrimaryKey(ypxxid);
		if (ypxx == null) {//找不到药品信息
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 316, null));
		}
		String jyzt = ypxx.getJyzt();
		if ("2".equals(jyzt)) {//药品状态为暂停
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 403, new Object[]{
				ypxx.getBm(),ypxx.getMc()	
			}));
		}
		
		//向供应商药品目录表插入一条数据
		Gysypml gysypml_insert = new Gysypml();
		gysypml_insert.setId(UUIDBuild.getUUID());
		gysypml_insert.setUsergysid(usergysid);
		gysypml_insert.setYpxxid(ypxxid);
		gysypmlMapper.insert(gysypml_insert);
		
		//如果控制表已经存在供应商控制表,就不需要插入
		GysypmlControl gysypmlControl = this.findGysypmlControlByUsergysidAndYpxxid(usergysid, ypxxid);
		if (gysypmlControl == null) {
			//从系统参数配置表获取控制状态的默认值
			String control = systemConfigService.findBasicinfoById("00101").getValue();
			//执行插入
			GysypmlControl gysypmlControl_insert = new GysypmlControl();
			gysypmlControl_insert.setId(UUIDBuild.getUUID());
			gysypmlControl_insert.setUsergysid(usergysid);
			gysypmlControl_insert.setYpxxid(ypxxid);
			gysypmlControl_insert.setControl(control);
			
			gysypmlControlMapper.insert(gysypmlControl_insert);
		}
	}

	@Override
	public void deleteGysypmlByUsergysidAndYpxxid(String usergysid,
			String ypxxid) throws Exception {
		//校验删除的药品是否在供应商药品目录
		Gysypml gysypml = this.findGysypmlByUsergysidAndYpxxid(usergysid, ypxxid);
		if (gysypml == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 316, null));
		}
		String id = gysypml.getId();
		gysypmlMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<GysypmlCustom> findGysypmlControlList(
			GysypmlQueryVo gysypmlQueryVo) throws Exception {
		
		return gysypmlMapperCustom.findGysypmlControlList(gysypmlQueryVo);
	}

	@Override
	public int findGysypmlControlCount(GysypmlQueryVo gysypmlQueryVo)
			throws Exception {
		
		return gysypmlMapperCustom.findGysypmlControlCount(gysypmlQueryVo);
	}

	@Override
	public void updateGysypmlControl(String usergysid, String ypxxid,
			String control, String advice) throws Exception {
		//根据供应商id和药品id校验是否存在控制记录
		GysypmlControl gysypmlControl = this.findGysypmlControlByUsergysidAndYpxxid(usergysid, ypxxid);
		if (gysypmlControl == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 316, null));
		}
		gysypmlControl.setControl(control);
		gysypmlControl.setAdvice(advice);
		
		gysypmlControlMapper.updateByPrimaryKey(gysypmlControl);
	}

}
