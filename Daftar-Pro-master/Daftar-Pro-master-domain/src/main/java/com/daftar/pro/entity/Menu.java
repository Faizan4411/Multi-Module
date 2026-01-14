package com.daftar.pro.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "menus")
public class Menu extends BaseEntity {

	@Column(nullable = false, unique = true, length = 100)
	private String code;

	@Column(nullable = false)
	private String label;

	private String path;

	private String icon;

	private Integer sortOrder = 0;

	private Long parentId;

	@Column(nullable = false)
	private Boolean enabled = Boolean.TRUE;

	@ManyToMany(mappedBy = "menus", fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Privilege> privileges = new HashSet<>();
}

