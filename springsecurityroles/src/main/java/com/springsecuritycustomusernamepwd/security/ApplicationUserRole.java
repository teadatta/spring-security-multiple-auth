package com.springsecuritycustomusernamepwd.security;

import java.util.Set;

import com.google.common.collect.Sets;
import static com.springsecuritycustomusernamepwd.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

	STUDENT(Sets.newHashSet()), 
	ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_READ, COURSE_WRITE)),
	ADMINTRAINEE(Sets.newHashSet(STUDENT_READ, COURSE_READ));
	
	private Set<ApplicationUserPermission> permissions;

	private ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<ApplicationUserPermission> getPermissions() {
		return permissions;
	}

}
