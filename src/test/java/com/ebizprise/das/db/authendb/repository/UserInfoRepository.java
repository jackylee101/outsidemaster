package com.ebizprise.das.db.authendb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebizprise.das.db.authendb.entity.UserInfo1;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo1, String> {

	public UserInfo1 save(UserInfo1 userInfo);

	public UserInfo1 findByEmail(String email);

	public UserInfo1 findByEmailLike(String string);
}