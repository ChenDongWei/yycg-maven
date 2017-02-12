package yycg.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import yycg.base.dao.mapper.SysuserMapper;
import yycg.base.dao.mapper.SysuserMapperCustom;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;
import yycg.base.service.UserService;

public class UserServiceImpl implements UserService {
	
	//注入mapper
	@Autowired
	private SysuserMapper sysuserMapper;
	
	@Autowired
	private SysuserMapperCustom sysuserMapperCustom;
	
	/**
	 * 根据条件查询用户信息
	 */
	@Override
	public List<SysuserCustom> findSysuserList(SysuserQueryVo sysuserQueryVo)
			throws Exception {
		return sysuserMapperCustom.findSysuserList(sysuserQueryVo);
	}

	/**
	 * 根据条件查询列表的总数
	 */
	@Override
	public int findSysuserCount(SysuserQueryVo sysuserQueryVo) throws Exception {
		return sysuserMapperCustom.findSysuserCount(sysuserQueryVo);
	}
	

}
