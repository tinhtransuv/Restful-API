package org.java.restfulapi.repos;

import org.java.restfulapi.models.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepos extends JpaRepository<Work, Long> {
	
}