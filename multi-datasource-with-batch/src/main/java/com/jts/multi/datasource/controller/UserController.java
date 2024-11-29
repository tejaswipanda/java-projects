package com.jts.multi.datasource.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jts.multi.datasource.db1.model.User;
import com.jts.multi.datasource.service.UserService;

@RestController
@RequestMapping("/multiDataSource")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	@PostMapping("/saveUser")
	public ResponseEntity<User> save(@RequestBody User user) {
		User myuser = userService.saveUser(user);
		return new ResponseEntity<>(myuser, HttpStatus.OK);
	}

	@PostMapping("/addEntriesInUserTable")
	public void addEntriesInUserTable() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters jobParameter = new JobParametersBuilder().addLong("stratAt", System.currentTimeMillis())
				.toJobParameters();
		jobLauncher.run(job, jobParameter);
	}

}
