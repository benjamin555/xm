package cn.sp.xm.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import cn.sp.xm.core.entity.Node;

/**
* @author 陈嘉镇
* @version 创建时间：2014-8-9 上午9:20:26
* @email benjaminchen555@gmail.com
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class NodeServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private NodeService nodeService;
	
	@Autowired
	private UserService userService;
	
	
	@Test
	public void testContribute() throws Exception {
		String nodeId = "402881e847b8a2960147b8a29d9f0001";
		String content = "fasdfasd.";
		String operator =null;
		nodeService.addContribution(nodeId,content,operator);
	}
	
	@Test
	public void testZan() throws Exception {
		String pnId = null;
		Node n = nodeService.findByParent(pnId ).get(0);
		Node n2 = nodeService.zan(n.getId(),null);
		Assert.isTrue(n2.getZan()==n.getZan()+1);
		Assert.isTrue(n2.getSumZan()==n.getSumZan()+1);
	}
	/**
	 * 获取下一级节点，票数最多的子节点
	 * @throws Exception
	 */
	@Test
	public void testReadNext() throws Exception {
		String pnId = null;
		Node n = nodeService.findByParent(pnId ).get(0);
		Node n2 = nodeService.addContribution(n.getId(),"hello",null);
		Node n3 = nodeService.addContribution(n.getId(),"hello2",null);
		
		
		nodeService.zan(n2.getId(),null);
		nodeService.zan(n2.getId(),null);
		
		nodeService.zan(n3.getId(),null);
		
		nodeService.readNext(n.getId(),null);
//		Assert.isTrue(n4.getId().equals(n2.getId()));
		
	}
	
	@Test
	public void testReadSiblings() throws Exception {
		String pnId = null;
		String userId = "8ac21e9147c915500147c91560410001";
		Node n = nodeService.findByParent(pnId ).get(0);
		
		nodeService.deleteByParent(n.getId());
		
		Node n2 = nodeService.addContribution(n.getId(),"hello",null);
		Node n3 = nodeService.addContribution(n.getId(),"hello2",null);
		
		nodeService.zan(n2.getId(),null);
		nodeService.zan(n2.getId(),null);
		nodeService.zan(n2.getId(),null);
		nodeService.zan(n2.getId(),null);
		
		nodeService.zan(n3.getId(),null);
		nodeService.zan(n3.getId(),null);
		nodeService.zan(n3.getId(),null);
		
		Node n4 = nodeService.readNext(n.getId(),userId);
		Assert.isTrue(n4.getId().equals(n2.getId()));
		
		Node n5 =nodeService.readNextSibling(n4.getId(),userId);
		Assert.isTrue(n5.getId().equals(n3.getId()));
		
		Node n6 =nodeService.readPrevSibling(n5.getId(),userId);
		Assert.isTrue(n6.getId().equals(n2.getId()));
	}
	
	/**
	 * 登录后读最近读的节点
	 * @throws Exception
	 */
	@Test
	public void testReadLatestNode() throws Exception {
		String pnId = null;
		String userId = "8ac21e9147c915500147c91560410001";
		Node n = nodeService.findByParent(pnId ).get(0);
		Node n4 = nodeService.readNext(n.getId(),userId);
		
		Node ln = nodeService.findLatestNode(userId,null);
		
		Assert.isTrue(n4.getId().equals(ln.getId()));
	}

}
