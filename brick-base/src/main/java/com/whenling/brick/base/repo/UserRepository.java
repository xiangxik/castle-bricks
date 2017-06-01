package com.whenling.brick.base.repo;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.whenling.brick.base.entity.QUser;
import com.whenling.brick.base.entity.User;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface UserRepository extends BaseJpaRepository<User, Long>, QuerydslBinderCustomizer<QUser> {

	User findByUsername(String username);

	@Override
	default void customize(QuerydslBindings bindings, QUser root) {
		bindings.bind(root.username, root.name, root.email, root.mobile).first((path, value) -> path.contains(value));
		bindings.excluding(root.password);
	}

}
