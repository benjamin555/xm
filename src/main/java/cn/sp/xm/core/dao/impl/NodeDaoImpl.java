package cn.sp.xm.core.dao.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.hibernate.HibernateDao;

import cn.sp.persistent.utils.HqlUtil;
import cn.sp.xm.core.dao.NodeDao;
import cn.sp.xm.core.entity.Node;

/**
* @author 陈嘉镇
* @version 创建时间：2014-8-9 上午10:19:49
* @email benjaminchen555@gmail.com
*/
@Repository
public class NodeDaoImpl extends HibernateDao<Node, String> implements NodeDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public void create(Node node) {
		this.save(node);
	}

	public List<Node> find(Map<String, String> searchMap) {
		Map<String, Object> values = new HashMap<String, Object>();
		String append = HqlUtil.buildHqlAppend(searchMap, values);
		String hql = "select n from Node n join n.parentNode p where n.isUse='Y' ";
		hql += append;
		return this.find(hql, values);
	}

	public Node findRoot() {
		return this.findUnique(Restrictions.isNull("parentNode"));
	}

	public Node findById(String id) {
		return this.getById(id);
	}

	public void update(Node node) {
		this.save(node);
	}

	public List<Node> findChildrenBySize(String pId, int size, Map<String, String> orderMap) {
		Map<String, Object> values = new HashMap<String, Object>();
		Map<String, String> searchMap = new LinkedHashMap<String, String>();
		searchMap.put("flt_p_and_eqS_id", pId);
		searchMap.putAll(orderMap);
		String append = HqlUtil.buildHqlAppend(searchMap, values);
		String hql = "select n from Node n join n.parentNode p where n.isUse='Y' ";
		hql += append;
		Page<Node> page = new Page<Node>();
		page.setPageSize(size);
		logger.info(hql);
		Page<Node> nodes = this.findPage(page, hql, values);
		return nodes.getResult();
	}

	public Node findNextSibling(String id, boolean b) {
		Node n = this.getById(id);
		String pId = n.getParentNode().getId();
		String hql = "select n from Node n  where n.parentNode.id =:pId and n.id <>:id and n.isUse='Y'  ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("pId", pId);
		values.put("id", n.getId());
		values.put("zan", n.getZan());
		values.put("createTime", n.getCreateTime());
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		if (b) {
			orderMap.put("sort_n_zan", "desc");
			orderMap.put("sort_n_createTime", "desc");
			hql += " and n.zan<=:zan and n.createTime<=:createTime ";
		} else {
			orderMap.put("sort_n_zan", "asc");
			orderMap.put("sort_n_createTime", "asc");
			hql += " and n.zan>=:zan and n.createTime>=:createTime ";
		}
		String append = HqlUtil.buildHqlAppend(orderMap, values);
		hql += append;

		logger.info("hql:{},values:{}", hql, values);
		Page<Node> page = new Page<Node>();
		page.setPageSize(1);
		Page<Node> nodes  = this.findPage(page, hql, values);
		List<Node> result = nodes.getResult();
		
		if (result.isEmpty()) {
			return null;
		}
		
		return result.get(0);
	}

	public void deleteByParentId(String pnId) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("pnId", pnId);
		String hql = "update Node n set n.isUse='N' where n.parentNode.id=:pnId and n.isUse='Y' ";
		this.batchExecute(hql , values);
	}

}
