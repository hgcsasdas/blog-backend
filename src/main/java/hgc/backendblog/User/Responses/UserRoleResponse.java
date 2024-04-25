package hgc.backendblog.User.Responses;

import hgc.backendblog.User.Entitys.Role;

public class UserRoleResponse {

	Role rol = Role.ROLE_USER;

	public UserRoleResponse() {
		super();
	}

	public UserRoleResponse(Role rol) {
		super();
		this.rol = rol;
	}

	public Role getRol() {
		return rol;
	}

	public void setRol(Role rol) {
		this.rol = rol;
	}
	
	
	
}
