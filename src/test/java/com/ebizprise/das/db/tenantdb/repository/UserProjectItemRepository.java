package com.ebizprise.das.db.tenantdb.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebizprise.das.db.tenantdb.entity.UserProjectItem;

@Repository
public interface UserProjectItemRepository extends
		JpaRepository<UserProjectItem, String> {

	public UserProjectItem save(UserProjectItem userProjectItem);

	public List<UserProjectItem> findByCustId(String cust_id, Sort sort);

}