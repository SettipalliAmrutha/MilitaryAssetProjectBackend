package com.militaryasset.repository;

import com.militaryasset.model.Assignment;
import com.militaryasset.model.Base;
import com.militaryasset.model.Asset;
import com.militaryasset.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
	List<Assignment> findByAssignedToAndDateBetween(User user, LocalDate start, LocalDate end);
	List<Assignment> findByAsset_Base(Base base);
	List<Assignment> findByAsset(Asset asset);
	List<Assignment> findByAssignedTo(User user);
	List<Assignment> findByAssignedTo_Base_Id(Long baseId);

}

