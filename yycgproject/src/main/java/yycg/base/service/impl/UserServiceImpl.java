package yycg.base.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import yycg.base.dao.mapper.SysuserMapper;
import yycg.base.dao.mapper.SysuserMapperCustom;
import yycg.base.dao.mapper.UsergysMapper;
import yycg.base.dao.mapper.UserjdMapper;
import yycg.base.dao.mapper.UseryyMapper;
import yycg.base.pojo.po.Sysuser;
import yycg.base.pojo.po.SysuserExample;
import yycg.base.pojo.po.Usergys;
import yycg.base.pojo.po.UsergysExample;
import yycg.base.pojo.po.Userjd;
import yycg.base.pojo.po.UserjdExample;
import yycg.base.pojo.po.Useryy;
import yycg.base.pojo.po.UseryyExample;
import yycg.base.pojo.vo.ActiveUser;
import yycg.base.pojo.vo.SysuserCustom;
import yycg.base.pojo.vo.SysuserQueryVo;
import yycg.base.process.context.Config;
import yycg.base.process.result.ExceptionResultInfo;
import yycg.base.process.result.ResultInfo;
import yycg.base.process.result.ResultUtil;
import yycg.base.service.UserService;
import yycg.util.MD5;
import yycg.util.ResourcesUtil;
import yycg.util.UUIDBuild;

public class UserServiceImpl implements UserService {

	// 注入mapper
	@Autowired
	private SysuserMapper sysuserMapper;

	@Autowired
	private UserjdMapper userjdMapper;

	@Autowired
	private UseryyMapper useryyMapper;

	@Autowired
	private UsergysMapper usergysMapper;

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

	// 根据帐号查询用户
	public Sysuser findSysuserByUserid(String userId) throws Exception {
		SysuserExample sysuserExample = new SysuserExample();
		SysuserExample.Criteria criteria = sysuserExample.createCriteria();
		// 设置查询条件，根据帐号查询
		criteria.andUseridEqualTo(userId);
		List<Sysuser> list = sysuserMapper.selectByExample(sysuserExample);

		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	// 根据单位名称查询单位信息(监督单位)
	public Userjd findUserjdByMc(String mc) throws Exception {
		UserjdExample userjdExample = new UserjdExample();
		UserjdExample.Criteria criteria = userjdExample.createCriteria();
		// 设置查询条件，根据帐号查询
		criteria.andMcEqualTo(mc);
		List<Userjd> list = userjdMapper.selectByExample(userjdExample);

		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	// 根据单位名称查询单位信息(医院)
	public Useryy findUseryyByMc(String mc) throws Exception {

		UseryyExample useryyExample = new UseryyExample();
		UseryyExample.Criteria criteria = useryyExample.createCriteria();
		criteria.andMcEqualTo(mc);
		List<Useryy> list = useryyMapper.selectByExample(useryyExample);

		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	// 根据单位名称查询单位信息(供应商)
	public Usergys findUsergysByMc(String mc) throws Exception {

		UsergysExample usergysExample = new UsergysExample();
		UsergysExample.Criteria criteria = usergysExample.createCriteria();
		criteria.andMcEqualTo(mc);
		List<Usergys> list = usergysMapper.selectByExample(usergysExample);

		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 添加用户
	 */
	@Override
	public void insertSysuser(SysuserCustom sysuserCustom) throws Exception {
		// 参数校验
		// 通用的参数合法性校验，非空校验，长度校验

		// 数据业务合法性校验
		// 账号唯一性校验，查询数据库校验出来
		// 思路：根据用户账号查询sysuser表，如果查询到说明 账号重复

		Sysuser sysuser = this.findSysuserByUserid(sysuserCustom.getUserid());
		if (sysuser != null) {// 帐号重复
			//抛出异常
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 213, null));
		}

		// 根据用户类型，输入单位名称必须存在对应的单位表
		String groupid = sysuserCustom.getGroupid();// 用户类型
		String sysmc = sysuserCustom.getSysmc();//单位名称
		String sysid = null;//单位id
		if (groupid.equals("1") || groupid.equals("2")) {//监督单位
			//根据单位名称查询单位信息
			Userjd userjd = this.findUserjdByMc(sysmc);
			if (userjd == null) {
				//抛出异常
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
			}
			sysid = userjd.getId();
		}else if (groupid.equals("3")) {//卫生室
			//根据单位名称查询单位信息
			Useryy useryy = this.findUseryyByMc(sysmc);
			if (useryy == null) {
				//抛出异常
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
			}
			sysid = useryy.getId();
		}else if (groupid.equals("4")) {//供药商
			//根据单位名称查询单位信息
			Usergys usergys = this.findUsergysByMc(sysmc);
			if (usergys == null) {
				//抛出异常
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
			}
			sysid = usergys.getId();
		}
		//设置主键
		sysuserCustom.setId(UUIDBuild.getUUID());
		//设置单位id
		sysuserCustom.setSysid(sysid);
		sysuserCustom.setPwd(new MD5().getMD5ofStr(sysuserCustom.getPwd()));//明文密码转换成MD5密文存储
		sysuserMapper.insert(sysuserCustom);
	}

	@Override
	public void deleteSysuser(String id) throws Exception {
		//判断用户是否存在
		Sysuser sysuser = sysuserMapper.selectByPrimaryKey(id);
		if (sysuser == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 212, null));
		}
		//删除用户
		sysuserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateSysuser(String id, SysuserCustom sysuserCustom)
			throws Exception {
		//非空校验
		
		//获取页面传回来的userid
		String userid_page = sysuserCustom.getUserid();
		
		Sysuser sysuser = sysuserMapper.selectByPrimaryKey(id);
		if (sysuser == null) {//数据库找不到用户信息
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 215, null));
		}else {
			//根据页面提交的单位名称查询单位id
			String groupid = sysuserCustom.getGroupid();// 用户类型
			String sysmc = sysuserCustom.getSysmc();//单位名称
			String sysid = null;//单位id
			if (groupid.equals("1") || groupid.equals("2")) {//监督单位
				//根据单位名称查询单位信息
				Userjd userjd = this.findUserjdByMc(sysmc);
				if (userjd == null) {
					//抛出异常
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
				}
				sysid = userjd.getId();
			}else if (groupid.equals("3")) {//卫生室
				//根据单位名称查询单位信息
				Useryy useryy = this.findUseryyByMc(sysmc);
				if (useryy == null) {
					//抛出异常
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
				}
				sysid = useryy.getId();
			}else if (groupid.equals("4")) {//供药商
				//根据单位名称查询单位信息
				Usergys usergys = this.findUsergysByMc(sysmc);
				if (usergys == null) {
					//抛出异常
					ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
				}
				sysid = usergys.getId();
			}
			//更新用户信息
			sysuser.setUsername(sysuserCustom.getUsername());
			sysuser.setUserstate(sysuserCustom.getUserstate());
			sysuser.setGroupid(sysuserCustom.getGroupid());
			sysuser.setSysid(sysid);//单位id
			sysuserMapper.updateByPrimaryKey(sysuser);
		}
	}

	@Override
	public SysuserCustom findSysuserById(String id) throws Exception {
		// 从数据库查询用户
		Sysuser sysuser = sysuserMapper.selectByPrimaryKey(id);
		
		//根据id查询单位名称
		String groupid = sysuser.getGroupid();// 用户类型
		String sysid = sysuser.getSysid();//单位id
		String sysmc = null;//单位名称
		if (groupid.equals("1") || groupid.equals("2")) {//监督单位
			//根据单位名称查询单位信息
			Userjd userjd = userjdMapper.selectByPrimaryKey(sysid);
			if (userjd != null) {
				sysmc = userjd.getMc();
			}
			
		}else if (groupid.equals("3")) {//卫生室
			//根据单位名称查询单位信息
			Useryy useryy = useryyMapper.selectByPrimaryKey(sysid);
			if (useryy != null) {
				sysmc = useryy.getMc();
			}
			
		}else if (groupid.equals("4")) {//供药商
			//根据单位名称查询单位信息
			Usergys usergys = usergysMapper.selectByPrimaryKey(sysid);
			if (usergys != null) {
				sysmc = usergys.getMc();
			}
			
		}
		
		SysuserCustom sysuserCustom = new SysuserCustom();
		//将sysuser中数据设置到sysuserCustom
		BeanUtils.copyProperties(sysuser, sysuserCustom);
		sysuserCustom.setSysmc(sysmc);
		return sysuserCustom;
	}

	@Override
	public ActiveUser checkUserInfo(String userid, String pwd) throws Exception {
		// 校验用户是否存在
		Sysuser sysuser = this.findSysuserByUserid(userid);
		if (sysuser == null) {
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 101, null));
		}
		String pwd_db = sysuser.getPwd();//从数据库取出的md5密文密码
		String pwd_md5 = new MD5().getMD5ofStr(pwd);
		//校验用户密码是否合法
		if (!pwd_db.equalsIgnoreCase(pwd_md5)) {//用户名密码错误
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 114, null));
		}
		
		//构建用户身份信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(userid);
		activeUser.setUsername(sysuser.getUsername());
		activeUser.setGroupid(sysuser.getGroupid());
		activeUser.setSysid(sysuser.getSysid());// 单位id（重要）
		String sysmc = null;// 单位名称
		// 根据sysid查询单位名称
		String groupid = sysuser.getGroupid();
		String sysid = sysuser.getSysid();// 单位id
		if (groupid.equals("1") || groupid.equals("2")) {
			// 监督单位
			// 根据单位id查询单位信息
			Userjd userjd = userjdMapper.selectByPrimaryKey(sysid);
			if (userjd == null) {
				// 抛出异常，可预知异常
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,
						217, null));
			}
			sysmc = userjd.getMc();
		} else if (groupid.equals("3")) {
			// 卫生室
			// 根据单位id查询单位信息
			Useryy useryy = useryyMapper.selectByPrimaryKey(sysid);
			if (useryy == null) {
				// 抛出异常，可预知异常
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,
						217, null));
			}
			sysmc = useryy.getMc();
		} else if (groupid.equals("4")) {
			// 供货商
			// 根据单位id查询单位信息
			Usergys usergys = usergysMapper.selectByPrimaryKey(sysid);
			if (usergys == null) {
				// 抛出异常，可预知异常
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,
						217, null));
			}
			sysmc = usergys.getMc();
		}

		activeUser.setSysmc(sysmc);// 单位名称

		return activeUser;
	}

}
