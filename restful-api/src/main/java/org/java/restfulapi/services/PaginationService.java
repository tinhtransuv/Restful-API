package org.java.restfulapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.java.restfulapi.repos.PaginationRepos;
import org.java.restfulapi.models.Work;

@Service
public class PaginationService {

	@Autowired
	private PaginationRepos paginationRepos;

	public Page<Work> findWorkByCondition(String orderBy, String direction, int page, int size) {
		Sort sort = null;
		if (direction.equals("ASC")) {
			sort = Sort.by(Sort.Direction.ASC, orderBy);
		}
		if (direction.equals("DESC")) {
			sort = Sort.by(Sort.Direction.DESC, orderBy);
		}
		
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Work> data = paginationRepos.findAll(pageable);
		return data;
	}
}
