package com.daftar.pro.bootstrap;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.daftar.pro.entity.Menu;
import com.daftar.pro.entity.Privilege;
import com.daftar.pro.entity.Role;
import com.daftar.pro.entity.User;
import com.daftar.pro.entity.enums.RoleType;
import com.daftar.pro.repository.MenuRepository;
import com.daftar.pro.repository.PrivilegeRepository;
import com.daftar.pro.repository.RoleRepository;
import com.daftar.pro.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

	private final RoleRepository roleRepository;
	private final MenuRepository menuRepository;
	private final PrivilegeRepository privilegeRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void run(String... args) {
		Role superAdmin = ensureRole(RoleType.SUPER_ADMIN, "Super Admin", "Full access");
		Role admin = ensureRole(RoleType.ADMIN, "Administrator", "Manage privileged operations");
		Role user = ensureRole(RoleType.USER, "User", "Standard access");

		Menu dashboard = ensureMenu("DASHBOARD", "Dashboard", "/dashboard", "home", 1);
		Menu usersMenu = ensureMenu("USERS", "Users", "/users", "users", 2);
		Menu menusMenu = ensureMenu("MENUS", "Menus", "/menus", "menu", 3);

		Privilege viewDashboard = ensurePrivilege("VIEW_DASHBOARD", "View Dashboard", "Access dashboard insights", dashboard);
		Privilege manageUsers = ensurePrivilege("MANAGE_USERS", "Manage Users", "CRUD on users", usersMenu);
		Privilege manageMenus = ensurePrivilege("MANAGE_MENUS", "Manage Menus", "CRUD on menus", menusMenu);
		Privilege managePrivileges = ensurePrivilege("MANAGE_PRIVILEGES", "Manage Privileges", "CRUD on privileges", menusMenu);

		assignMenus(superAdmin, Set.of(dashboard, usersMenu, menusMenu));
		assignMenus(admin, Set.of(dashboard, usersMenu));
		assignMenus(user, Set.of(dashboard));

		assignPrivileges(superAdmin, Set.of(viewDashboard, manageUsers, manageMenus, managePrivileges));
		assignPrivileges(admin, Set.of(viewDashboard, manageUsers));
		assignPrivileges(user, Set.of(viewDashboard));

		if (userRepository.count() == 0) {
			User seedUser = new User();
			seedUser.setUsername("superadmin");
			seedUser.setEmail("super@admin.local");
			seedUser.setPassword(passwordEncoder.encode("ChangeMe!123"));
			seedUser.setRoles(Set.of(superAdmin));
			userRepository.save(seedUser);
		}
	}

	private Role ensureRole(RoleType type, String name, String description) {
		return roleRepository.findByCode(type).orElseGet(() -> {
			Role role = new Role();
			role.setCode(type);
			role.setDisplayName(name);
			role.setDescription(description);
			return roleRepository.save(role);
		});
	}

	private Menu ensureMenu(String code, String label, String path, String icon, int sortOrder) {
		return menuRepository.findByCodeIgnoreCase(code)
				.orElseGet(() -> {
					Menu menu = new Menu();
					menu.setCode(code);
					menu.setLabel(label);
					menu.setPath(path);
					menu.setIcon(icon);
					menu.setSortOrder(sortOrder);
					return menuRepository.save(menu);
				});
	}

	private void assignMenus(Role role, Set<Menu> menus) {
		role.getMenus().addAll(menus);
		roleRepository.save(role);
		menus.forEach(menu -> menu.getRoles().add(role));
		menuRepository.saveAll(menus);
	}

	private void assignPrivileges(Role role, Set<Privilege> privileges) {
		role.getPrivileges().addAll(privileges);
		roleRepository.save(role);
		privileges.forEach(privilege -> privilege.getRoles().add(role));
		privilegeRepository.saveAll(privileges);
	}

	private Privilege ensurePrivilege(String code, String name, String description, Menu menu) {
		return privilegeRepository.findByCodeIgnoreCase(code)
				.orElseGet(() -> {
					Privilege privilege = new Privilege();
					privilege.setCode(code);
					privilege.setDisplayName(name);
					privilege.setDescription(description);
					privilege.setMenu(menu);
					return privilegeRepository.save(privilege);
				});
	}
}

