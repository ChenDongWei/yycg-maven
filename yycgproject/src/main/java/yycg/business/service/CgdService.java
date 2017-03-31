package yycg.business.service;

import java.util.List;

import yycg.business.pojo.po.Yycgdmx;
import yycg.business.pojo.vo.YycgdCustom;
import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;

/**
 * 采购单管理
 * @author dongwei
 * @date 2017年3月16日
 */
public interface CgdService {
	//创建采购单的基本信息
	public String insertYycgd(String useryyid, String year, YycgdCustom yycgdCustom) throws Exception;
	
	//根据采购单id查询采购单信息
	public YycgdCustom findYycgdById(String id) throws Exception;
	
	//更新采购单的基本信息
	public void updateYycgd(String id, YycgdCustom yycgdCustom) throws Exception;
	
	//查询采购单下明细信息
	public List<YycgdmxCustom> findYycgdmxListByYycgdid(String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception;
	
	//查询采购单总数
	public int findYycgdmxCountByYycgdid(String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception;
	
	//查询药品添加目录
	public List<YycgdmxCustom> findAddYycgdmxList(String useryyid, String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception;
	public int findAddYycgdmxCount(String useryyid, String yycgdid, YycgdQueryVo yycgdQueryVo) throws Exception;
	
	//采购药品添加
	public void insertYycgdmx(String yycgdid, String ypxxid, String usergysid) throws Exception;
	
	//根据采购单id和药品id查询采购单明细
	public Yycgdmx findYycgdmxByYycgdidAndYpxxid(String yycgdid, String ypxxid) throws Exception;
	
	//根据采购单id和药品id更新采购单明细表的采购量和采购金额
	public void updateYycgdmx(String yycgdid, String ypxxid, Integer cgl) throws Exception;
	
	//采购单列表
	public List<YycgdCustom> findYycgdList(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;
	
	//采购单列表总数
	public int findYycgdCount(String useryyid, String year, YycgdQueryVo yycgdQueryVo) throws Exception;
	
	//采购单提交
	public void saveYycgdSubmitStatus(String yycgdid) throws Exception;
	
	//采购单审批列表
	public List<YycgdCustom> findCheckYycgdList(String year, String userjdid, YycgdQueryVo yycgdQueryVo) throws Exception;
	
	//采购单审批列表总数
	public int findCheckYycgdCount(String year, String userjdid, YycgdQueryVo yycgdQueryVo) throws Exception;
}
