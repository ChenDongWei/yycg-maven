package yycg.base.service;

import java.util.List;

import yycg.base.pojo.po.Sysuser;
import yycg.base.pojo.po.Usergys;
import yycg.base.pojo.po.Userjd;
import yycg.base.pojo.po.Useryy;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;

public interface UserService {
	//校验用户信息
	public ActiveUser checkUserInfo(String userid, String pwd)throws Exception;
	
	//根据条件查询用户信息
	public List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	//根据条件查询列表的总数
	public int findSysuserCount(SysuserQueryVo sysuserQueryVo) throws Exception;
	
	//根据用户帐号查询用户信息
	public Sysuser findSysuserByUserid(String userId) throws Exception;
	
	// 根据单位名称查询单位信息(监督单位)
	public Userjd findUserjdByMc(String mc) throws Exception;
		
	// 根据单位名称查询单位信息(医院)
	public Useryy findUseryyByMc(String mc) throws Exception;
	
	// 根据单位名称查询单位信息(供应商)
	public Usergys findUsergysByMc(String mc) throws Exception;
	
	//添加用户
	public void insertSysuser(SysuserCustom sysuserCustom) throws Exception;
	
	//删除用户
	public void deleteSysuser(String id) throws Exception;
	
	//修改用户
	public void updateSysuser(String id, SysuserCustom sysuserCustom) throws Exception;
	
	//根据用户id获取用户信息
	public SysuserCustom findSysuserById(String id) throws Exception;
}
