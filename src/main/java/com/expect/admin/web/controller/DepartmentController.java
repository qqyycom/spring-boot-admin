package com.expect.admin.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.expect.admin.service.convertor.DepartmentConvertor;
import com.expect.admin.service.impl.DepartmentService;
import com.expect.admin.service.vo.DepartmentVo;
import com.expect.admin.service.vo.component.ResultVo;
import com.expect.admin.service.vo.component.html.CheckboxsVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.web.exception.AjaxException;
import com.expect.admin.web.exception.AjaxRequestException;

/**
 * 部门管理Controller
 */
@Controller
@RequestMapping("/admin/department")
public class DepartmentController {

	private final String viewName = "admin/system/department/";

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 部门-管理页面
	 */
	@RequestMapping(value = "/departmentManagePage", method = RequestMethod.GET)
	public ModelAndView userManagePage() {
		List<DepartmentVo> departments = departmentService.getDepartments();
		List<DataTableRowVo> dtrvs = DepartmentConvertor.convertDtrv(departments);
		ModelAndView modelAndView = new ModelAndView(viewName + "manage");
		modelAndView.addObject("departments", dtrvs);
		return modelAndView;
	}

	/**
	 * 部门-表单页面
	 */
	@RequestMapping(value = "/departmentFormPage", method = RequestMethod.GET)
	public ModelAndView departmentFormPage(String id) {
		DepartmentVo department = departmentService.getDepartmentById(id);
		ModelAndView modelAndView = new ModelAndView(viewName + "form/departmentForm");
		modelAndView.addObject("department", department);
		return modelAndView;
	}

	/**
	 * 部门-保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo save(DepartmentVo departmentVo) throws AjaxRequestException {
		return departmentService.save(departmentVo);
	}

	/**
	 * 部门-更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public DataTableRowVo update(DepartmentVo departmentVo) throws AjaxRequestException {
		return departmentService.update(departmentVo);
	}

	/**
	 * 部门-删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo delete(String id) throws AjaxRequestException {
		return departmentService.delete(id);
	}

	/**
	 * 部门-批量删除
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public ResultVo deleteBatch(String ids) throws AjaxRequestException {
		return departmentService.deleteBatch(ids);
	}

	/**
	 * 获取departmentCheckbox的html
	 */
	@RequestMapping(value = "/getDepartmentCheckboxHtml", method = RequestMethod.POST)
	@ResponseBody
	@AjaxException
	public CheckboxsVo getDepartmentCheckboxHtml(String userId) throws AjaxRequestException {
		List<DepartmentVo> departments = departmentService.getAllBottomDepartments();
		List<String> ids = new ArrayList<>();
		List<DepartmentVo> userDepartments = departmentService.getDepartmentsByUserId(userId);
		if (!CollectionUtils.isEmpty(userDepartments)) {
			for (DepartmentVo department : userDepartments) {
				ids.add(department.getId());
			}
		}
		CheckboxsVo cbv = DepartmentConvertor.convertCbv(departments, ids);
		return cbv;
	}

}
