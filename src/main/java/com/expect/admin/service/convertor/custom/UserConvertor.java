package com.expect.admin.service.convertor.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.expect.admin.data.dataobject.custom.Department;
import com.expect.admin.data.dataobject.custom.Role;
import com.expect.admin.data.dataobject.custom.User;
import com.expect.admin.service.vo.component.html.SelectOptionVo;
import com.expect.admin.service.vo.component.html.datatable.DataTableButtonFactory;
import com.expect.admin.service.vo.component.html.datatable.DataTableRowVo;
import com.expect.admin.service.vo.custom.UserVo;

public class UserConvertor {

	/**
	 * do To vo
	 */
	public static UserVo convert(User user) {
		UserVo userVo = new UserVo();
		if (user == null) {
			return userVo;
		}
		BeanUtils.copyProperties(user, userVo);
		// 设置角色
		Set<Role> roles = user.getRoles();
		StringBuilder roleSb = new StringBuilder();
		if (!CollectionUtils.isEmpty(roles)) {
			int index = 0;
			for (Role role : roles) {
				if (index != roles.size() - 1) {
					roleSb.append(role.getName() + ",");
				} else {
					roleSb.append(role.getName());
				}
				index++;
			}
		}
		userVo.setRoleName(roleSb.toString());
		// 设置部门
		Set<Department> departments = user.getDepartments();
		StringBuilder departmentSb = new StringBuilder();
		if (!CollectionUtils.isEmpty(departments)) {
			int index = 0;
			for (Department department : departments) {
				if (index != departments.size() - 1) {
					departmentSb.append(department.getName() + ",");
				} else {
					departmentSb.append(department.getName());
				}
				index++;
			}
		}
		userVo.setDepartmentName(departmentSb.toString());
		// 设置头像
		if (user.getAvatar() != null) {
			userVo.setAvatarId(user.getAvatar().getId());
		}

		return userVo;
	}

	/**
	 * dos To vos
	 */
	public static List<UserVo> convert(List<User> users) {
		List<UserVo> userVos = new ArrayList<>();
		if (!CollectionUtils.isEmpty(users)) {
			for (User user : users) {
				UserVo userVo = convert(user);
				userVos.add(userVo);
			}
		}
		return userVos;
	}

	/**
	 * vo to do
	 */
	public static User convert(UserVo userVo) {
		User user = new User();
		BeanUtils.copyProperties(userVo, user);
		return user;
	}

	/**
	 * vo to do
	 */
	public static void convert(User user, UserVo userVo) {
		user.setUsername(userVo.getUsername());
		user.setPassword(userVo.getPassword());
		user.setFullName(userVo.getFullName());
		user.setEmail(userVo.getEmail());
		user.setPhone(userVo.getPhone());
		user.setSex(userVo.getSex());
	}

	/**
	 * vos To dtrvs
	 */
	public static List<DataTableRowVo> convertDtrvs(List<User> users) {
		List<DataTableRowVo> dtrvs = new ArrayList<DataTableRowVo>();
		if (!CollectionUtils.isEmpty(users)) {
			for (User user : users) {
				DataTableRowVo dtrv = new DataTableRowVo();
				convertDtrv(dtrv, user);
				dtrvs.add(dtrv);
			}
		}
		return dtrvs;
	}

	/**
	 * do to dtrv
	 */
	public static void convertDtrv(DataTableRowVo dtrv, User user) {
		dtrv.setObj(user.getId());
		dtrv.setCheckbox(true);
		dtrv.addData(user.getUsername());
		dtrv.addData(user.getPassword());
		dtrv.addData(user.getFullName());
		dtrv.addData(user.getSex());
		dtrv.addData(user.getPhone());
		dtrv.addData(user.getEmail());
		StringBuilder buttonSb = new StringBuilder();
		buttonSb.append(DataTableButtonFactory.getGreenSharpButton("头像", "data-id='" + user.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getPurpleButton("角色", "data-id='" + user.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getYellowButton("部门", "data-id='" + user.getId() + "'"));
		buttonSb.append(DataTableButtonFactory.getDefaultButton(user.getId()));
		dtrv.addData(buttonSb.toString());
	}

	/**
	 * vos to sov
	 */
	public static SelectOptionVo convertSov(List<UserVo> userVos, String username) {
		SelectOptionVo sov = new SelectOptionVo();
		if (!CollectionUtils.isEmpty(userVos)) {
			sov.addOption("", "无");
			for (UserVo userVo : userVos) {
				if (StringUtils.isBlank(username)) {
					sov.addOption(userVo.getId(), userVo.getUsername());
					continue;
				}
				if (username.equals(userVo.getUsername())) {
					sov.addOption(userVo.getId(), userVo.getUsername(), true);
				} else {
					sov.addOption(userVo.getId(), userVo.getUsername());
				}
			}
		}
		return sov;
	}
}
