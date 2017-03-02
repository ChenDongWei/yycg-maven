package yycg.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import yycg.business.dao.mapper.GysypmlMapperCustom;
import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;
import yycg.business.service.YpmlService;

public class YpmlServiceImpl implements YpmlService {
	@Autowired
	private GysypmlMapperCustom gysypmlMapperCustom;

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

}
