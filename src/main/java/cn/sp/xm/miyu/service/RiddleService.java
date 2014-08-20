package cn.sp.xm.miyu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import cn.sp.service.IBaseService;
import cn.sp.xm.miyu.dao.RiddleDao;
import cn.sp.xm.miyu.entity.Riddle;

/**
* @author 陈嘉镇
* @version 创建时间：2014-8-19 下午2:17:25
* @email benjaminchen555@gmail.com
*/
@Service
@Transactional
public class RiddleService implements IBaseService<Riddle,Long>{
	@Autowired
	private RiddleDao dao;

	public Riddle getById(Long id) {
		return dao.getById(id);
	}

	public void save(Riddle entity) {
		dao.save(entity);		
	}

	public void delete(Riddle entityObject) {
		// TODO Auto-generated method stub
		
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	public List<Riddle> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void zan(Long id) {
		Riddle riddle = getById(id);
		riddle.setZan(riddle.getZan()+1);
		save(riddle);
	}


	public Page<Riddle> getPage(int start, int size, Map<String, String> searchMap) {
		return dao.findPage(start, size,searchMap);
	}


}
