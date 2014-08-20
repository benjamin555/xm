package cn.sp.xm.miyu.action;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.sp.action.CrudActionSupport;
import cn.sp.xm.miyu.entity.Riddle;
import cn.sp.xm.miyu.service.RiddleService;

/**
* @author 陈嘉镇
* @version 创建时间：2014-8-20 上午10:24:36
* @email benjaminchen555@gmail.com
*/
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
@Namespace("/miyu")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lang.Exception", result = "exception") })
@Results({ @Result(name = "exception", location = "/common/error.jsp") 
			,@Result(name="show",type="redirect",location="riddle!show.action",params={"id","${riddle.id}"})
			,@Result(name="showRight",type="redirect",location="riddle!showRight.action",params={"id","${riddle.id}"})
})
public class RiddleAction extends CrudActionSupport<Riddle> {
	private Riddle riddle;
	@Autowired
	private RiddleService riddleService;
	
	public Riddle getModel() {
		if (riddle==null) {
			riddle = new Riddle();
		}
		return riddle;
	}

	@Override
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		riddleService.save(riddle);
		return "show";
	}
	
	public String show() throws Exception {
		riddle = riddleService.getById(riddle.getId());
		return "detail";
		
	}
	
	public String showRight() throws Exception {
		riddle = riddleService.getById(riddle.getId());
		return "right";
		
	}
	
	
	public String check() throws Exception {
		Riddle r = riddleService.getById(riddle.getId());
		Collection<String> messages = new ArrayList<String>();
		if (riddle.getAnswer().trim().equals(r.getAnswer().trim())) {
			riddle =r;
			return "showRight";
		}else {
			
			messages.add("答错了！再想想。");
			setActionMessages(messages );
			riddle =r;
			return "detail";
		}
		
	}
	

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public Riddle getRiddle() {
		return riddle;
	}

	public void setRiddle(Riddle riddle) {
		this.riddle = riddle;
	}
	
	
	
}
