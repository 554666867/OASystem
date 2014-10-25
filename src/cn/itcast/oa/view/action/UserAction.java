package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.ModelDrivenBaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.util.DepartmentUtils;
import cn.itcast.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends ModelDrivenBaseAction<User> {

	private Long departmentId;
	private Long[] roleIds;

	/** 鍒楄〃 */
	public String list() throws Exception {
		new QueryHelper(User.class, "u")//
		.preparePageBean(userService, pageNum, pageSize);
		return "list";
	}

	/** 鍒犻櫎 */
	public String delete() throws Exception {
		userService.delete(model.getId());
		return "toList";
	}

	/** 娣诲姞椤甸潰 */
	public String addUI() throws Exception {
		// 鍑嗗鏁版嵁, departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);

		// 鍑嗗鏁版嵁, roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);

		return "saveUI";
	}

	/** 娣诲姞 */
	public String add() throws Exception {
		// 灏佽鍒板璞′腑锛堝綋model鏄疄浣撶被鍨嬫椂锛屼篃鍙互浣跨敤model锛屼絾瑕佽缃湭灏佽鐨勫睘鎬э級
		// >> 璁剧疆鎵�灞為儴闂�
		model.setDepartment(departmentService.getById(departmentId));
		// >> 璁剧疆鍏宠仈鐨勫矖浣�
		List<Role> roleList = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roleList));
		// >> 璁剧疆榛樿瀵嗙爜涓�1234锛堣浣跨敤MD5鎽樿锛�
		String md5Digest = DigestUtils.md5Hex("1234");
		model.setPassword(md5Digest);

		// 淇濆瓨鍒版暟鎹簱
		userService.save(model);

		return "toList";
	}

	/** 淇敼椤甸潰 */
	public String editUI() throws Exception {
		// 鍑嗗鏁版嵁, departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);

		// 鍑嗗鏁版嵁, roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);

		// 鍑嗗鍥炴樉鐨勬暟鎹�
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		if (user.getDepartment() != null) {
			departmentId = user.getDepartment().getId();
		}
		if (user.getRoles() != null) {
			roleIds = new Long[user.getRoles().size()];
			int index = 0;
			for (Role role : user.getRoles()) {
				roleIds[index++] = role.getId();
			}
		}

		return "saveUI";
	}

	/** 淇敼 */
	public String edit() throws Exception {
		// 1锛屼粠鏁版嵁搴撲腑鍙栧嚭鍘熷璞�
		User user = userService.getById(model.getId());

		// 2锛岃缃淇敼鐨勫睘鎬�
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		// >> 璁剧疆鎵�灞為儴闂�
		user.setDepartment(departmentService.getById(departmentId));
		// >> 璁剧疆鍏宠仈鐨勫矖浣�
		List<Role> roleList = roleService.getByIds(roleIds);
		user.setRoles(new HashSet<Role>(roleList));

		// 3锛屾洿鏂板埌鏁版嵁搴�
		userService.update(user);

		return "toList";
	}

	/** 鍒濆鍖栧瘑鐮佷负1234 */
	public String initPassword() throws Exception {
		// 1锛屼粠鏁版嵁搴撲腑鍙栧嚭鍘熷璞�
		User user = userService.getById(model.getId());

		// 2锛岃缃淇敼鐨勫睘鎬э紙瑕佷娇鐢∕D5鎽樿锛�
		String md5Digest = DigestUtils.md5Hex("1234");
		model.setPassword(md5Digest);

		// 3锛屾洿鏂板埌鏁版嵁搴�
		userService.update(user);

		return "toList";
	}

	// ---

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
	
	public String login() throws Exception{
		
		User user = userService.findByLoginNameAndPassword(model.getLoginName(),model.getPassword());
		if(user == null){
			addFieldError("login", "用户名或密码不正确！");
			return "loginUI";
		}else{
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
			
		}
	}
	
	/** 登录页面 */
	public String loginUI() throws Exception {
		return "loginUI";
	}
	
	public String logout() throws Exception {
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
