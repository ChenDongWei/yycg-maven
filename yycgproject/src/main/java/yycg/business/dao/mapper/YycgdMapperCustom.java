package yycg.business.dao.mapper;

import java.util.List;

import yycg.business.pojo.vo.YycgdQueryVo;
import yycg.business.pojo.vo.YycgdmxCustom;

public interface YycgdMapperCustom {
    //采购单编号生成
	public String getYycgdBm(String year) throws Exception;
	
	//采购单明细查询
	public List<YycgdmxCustom> findYycgdmxList(YycgdQueryVo yycgdQueryVo) throws Exception;
	
	//采购单总数查询
	public int findYycgdmxCount(YycgdQueryVo yycgdQueryVo) throws Exception;
	
	
}