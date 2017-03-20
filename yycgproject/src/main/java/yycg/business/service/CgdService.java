package yycg.business.service;

import java.util.List;

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
}
