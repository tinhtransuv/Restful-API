package org.java.restfulapi.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.java.restfulapi.exceptions.PaginationSortingException;
import org.java.restfulapi.exceptions.PagingSortingErrorResponse;
import org.java.restfulapi.exceptions.ResourceNotFoundException;
import org.java.restfulapi.models.Work;
import org.java.restfulapi.repos.WorkRepos;
import org.java.restfulapi.services.PaginationService;
import org.java.restfulapi.utils.Direction;
import org.java.restfulapi.utils.OrderBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class WorkController {

	@Autowired
	private WorkRepos workRepos;

	@Autowired
	private PaginationService paginationService;

	@GetMapping("works")
	public List<Work> getAllEmployees() {
		return workRepos.findAll();
	}

	@GetMapping("works/{id}")
	public ResponseEntity<Work> getEmployeeById(@PathVariable(value = "id") Long workId)
			throws ResourceNotFoundException {
		
		Work employee = workRepos.findById(workId)
				.orElseThrow(() -> new ResourceNotFoundException("This work does not exist: " + workId));
		
		return ResponseEntity.ok().body(employee);
	}

	@PostMapping("works")
	public Work createWork(@Valid @RequestBody Work work) {
		return workRepos.save(work);
	}

	@PutMapping("works/{id}")
	public ResponseEntity<Work> updateWork(@PathVariable(value = "id") Long workId,
			@Valid @RequestBody Work workDetails) throws ResourceNotFoundException {
		Work work = workRepos.findById(workId)
				.orElseThrow(() -> new ResourceNotFoundException("This work does not exist: " + workId));

		work.setWorkName(workDetails.getWorkName());
		work.setStartingDate(workDetails.getStartingDate());
		work.setEndingDate(workDetails.getEndingDate());
		work.setStatus(workDetails.getStatus());
		
		final Work updatedEmployee = workRepos.save(work);
		
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/works/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long workId)
			throws ResourceNotFoundException {
		
		Work employee = workRepos.findById(workId)
				.orElseThrow(() -> new ResourceNotFoundException("This work does not exist: " + workId));

		workRepos.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}

	@RequestMapping(value = "/pagination/conditionalPagination", params = { "orderBy", "direction", "page",
			"size" }, method = RequestMethod.GET)
	@ResponseBody
	public Page<Work> findJsonDataByPageAndSize(@RequestParam("orderBy") String orderBy,
			@RequestParam("direction") String direction, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		if (!(direction.equals(Direction.ASCENDING.getDirectionCode())
				|| direction.equals(Direction.DESCENDING.getDirectionCode()))) {
			throw new PaginationSortingException("Invalid sort direction");
		}
		if (!(orderBy.equals(OrderBy.ID.getOrderByCode()))) {
			throw new PaginationSortingException("Invalid orderBy condition");
		}
		Page<Work> list = paginationService.findWorkByCondition(orderBy, direction, page, size);
		return list;
	}

	@ExceptionHandler(PaginationSortingException.class)
	public ResponseEntity<PagingSortingErrorResponse> exceptionHandler(Exception ex) {
		PagingSortingErrorResponse pagingSortingErrorResponse = new PagingSortingErrorResponse();
		pagingSortingErrorResponse.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		pagingSortingErrorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<PagingSortingErrorResponse>(pagingSortingErrorResponse, HttpStatus.OK);
	}
}
