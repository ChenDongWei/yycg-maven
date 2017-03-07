package yycg.business.service;

import java.util.List;

import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;

public interface YpmlService {
	/*
	 * 供应商药品目录查询列表
	 * 
	 * @param usergysId 供应商id
	 * 
	 * @param gysypmlQueryVo 查询条件
	 */
	public List<GysypmlCustom> findGysypmlList(String usergysId,
			GysypmlQueryVo gysypmlQueryVo) throws Exception;

	// 供应商药品总数
	public int findGysypmlCount(String usergysId, GysypmlQueryVo gysypmlQueryVo)
			throws Exception;

	// 供应商药品目录添加查询
	public List<GysypmlCustom> findAddGysypmlList(String usergysId, GysypmlQueryVo gysypmlQueryVo)
			throws Exception;

	// 供应商添加目录总数
	public int findAddGysypmlCount(String usergysId, GysypmlQueryVo gysypmlQueryVo)
			throws Exception;
}
