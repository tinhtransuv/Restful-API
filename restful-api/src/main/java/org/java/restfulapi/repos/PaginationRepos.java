package org.java.restfulapi.repos;

import org.java.restfulapi.models.Work;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaginationRepos extends PagingAndSortingRepository<Work, Long> {
	
}