package yycg.business.service;

import java.util.List;

import yycg.business.pojo.po.Gysypml;
import yycg.business.pojo.po.GysypmlControl;
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
	
	//供货商药品目录添加
	public void insertGysypml(String usergysid, String ypxxid) throws Exception;
	
	// 根据供应商id和药品id查询供应商药品目录
	public Gysypml findGysypmlByUsergysidAndYpxxid(String usergysid, String ypxxid) throws Exception;
	
	// 根据供应商id和药品id查询供应商药品目录控制记录
	public GysypmlControl findGysypmlControlByUsergysidAndYpxxid(String usergysid, String ypxxid) throws Exception;
	
	// 根据供应商id和药品id删除供应商药品目录记录
	public void deleteGysypmlByUsergysidAndYpxxid(String usergysid, String ypxxid) throws Exception;
	
	//供货商药品目录控制列表
	public List<GysypmlCustom> findGysypmlControlList(GysypmlQueryVo gysypmlQueryVo) throws Exception;
	
	//供货商药品目录控制总数
	public int findGysypmlControlCount(GysypmlQueryVo gysypmlQueryVo) throws Exception;
	
	//根据供应商id和药品信息id更新供应商药品目录控制表的控制状态及意见
	public void updateGysypmlControl(String usergysid, String ypxxid, String control, String advice) throws Exception;
}
