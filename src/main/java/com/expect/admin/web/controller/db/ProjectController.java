package com.expect.admin.web.controller.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.impl.db.ProjectService;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.db.ProjectVo;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

/**
 * 项目Controller
 */
@Controller
@RequestMapping(value = "/admin/db/project")
public class ProjectController {

	private final String viewName = "admin/system/db/project/";

	@Autowired
	private ProjectService projectService;

	/**
	 * 项目-管理页面
	 */
	@RequestMapping(value = "/managePage", method = RequestMethod.GET)
	public ModelAndView managePage() {
		List<DataTableRowVo> dtrvs = projectService.getProjectDtrvs();
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("projects", dtrvs);
		return modelAndView;
	}

	/**
	 * 项目-表单页面
	 */
	@RequestMapping(value = "/formPage", method = RequestMethod.GET)
	public ModelAndView formPage(String id) {
		ProjectVo project = projectService.getProjectById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form");
		modelAndView.addObject("project", project);
		return modelAndView;
	}

	/**
	 * 项目-详细页面
	 */
	@RequestMapping(value = "/detailPage", method = RequestMethod.GET)
	public ModelAndView detailPage(String id) {
		ProjectVo project = projectService.getProjectById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "detail");
		modelAndView.addObject("project", project);
		return modelAndView;
	}

	/**
	 * 项目-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo save(ProjectVo projectVo) throws AjaxRequestException {
		return projectService.save(projectVo);
	}

	/**
	 * 项目-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo update(ProjectVo projectVo) throws AjaxRequestException {
		return projectService.update(projectVo);
	}

	/**
	 * 项目-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException {
		return projectService.delete(id);
	}

	/**
	 * 项目-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return projectService.deleteBatch(ids);
	}

	/**
	 * 下载代码
	 * 
	 * @param ids
	 *            项目id(使用,分隔)
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(String ids) {
//		RequestUtil.downloadFile(buffer, fileName, response);
	}
}
