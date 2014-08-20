package cn.sp.xm.miyu.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springside.modules.orm.Page;

import cn.sp.persistent.BaseEntityDao;
import cn.sp.persistent.utils.HqlUtil;
import cn.sp.xm.miyu.entity.Riddle;

/**
* @author 陈嘉镇
* @version 创建时间：2014-8-19 下午2:22:41
* @email benjaminchen555@gmail.com
*/
@Repository
public class RiddleDao extends BaseEntityDao<Riddle, Long>{

	public Page<Riddle> findPage(int start, int size, Map<String, String> searchMap) {
		
		Page<Riddle> page = new Page<Riddle>();
		page.setPageSize(size);
		page.setStart(start);
		Map<String, Object> values = new HashMap<String, Object>();
		String append = HqlUtil.buildHqlAppend(searchMap, values);
		String hql = "select r from Riddle r  where r.isUse='Y' ";
		hql += append;
		logger.info(hql);
		return findPage(page, hql, values);
	}


}
