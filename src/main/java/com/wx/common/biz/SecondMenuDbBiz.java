package com.wx.common.biz;


import java.util.List;


import com.wx.common.bean.SecondMenuDb;

public interface SecondMenuDbBiz {
	
	//插入二级菜单
	public void addSecondMenu( SecondMenuDb secondMenuDb);
	
	//查找所有二级菜单
	public List<SecondMenuDb> findAllSecondMenuByFid(SecondMenuDb secondMenuDb);
	
	//查询二级菜单数量
	public int findAllSecondMenuCountByFid(SecondMenuDb secondMenuDb);
	
	//根据sid删除二级菜单
	public void deleteSecondBySid(int sid);
	
	//查询二级菜单数量根据分组fid的数量
	public List<SecondMenuDb> findSecondCountGroupBy();
	
	
	
}
