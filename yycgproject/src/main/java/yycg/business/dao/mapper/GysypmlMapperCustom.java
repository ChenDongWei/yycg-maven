package yycg.business.dao.mapper;

import java.util.List;

import yycg.business.pojo.vo.GysypmlCustom;
import yycg.business.pojo.vo.GysypmlQueryVo;

public interface GysypmlMapperCustom {
	//供应商药品目录查询列表
	public List<GysypmlCustom> findGysypmlList(GysypmlQueryVo gysypmlQueryVo) throws Exception;
	//供应商药品总数
	public int findGysypmlCount(GysypmlQueryVo gysypmlQueryVo) throws Exception;
}
