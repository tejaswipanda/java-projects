package com.jts.multi.datasource.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.jts.multi.datasource.db1.model.User;
import com.jts.multi.datasource.db1.repository.UserRepository;
import com.jts.multi.datasource.listener.JobCompletionNotificationListener;

import jakarta.persistence.EntityManager;

@Configuration
public class SpringBatchConfig {
	private String input = "Users.txt";
	@Autowired
	UserRepository userRepository;

	@Bean
	FlatFileItemReader<User> reader() {
		FlatFileItemReader<User> fileItemReader = new FlatFileItemReader<>();
		fileItemReader.setResource(new ClassPathResource(input));
		fileItemReader.setName("userItemReader");
		fileItemReader.setLinesToSkip(0);// This will skip the line from top
		fileItemReader.setLineMapper(defaultLinemapper());
		return fileItemReader;/*
								 * new FlatFileItemReaderBuilder<>().name("userItemReader").resource(new
								 * ClassPathResource(input)) .delimited().names(new String[] { "userId", "age",
								 * "name" }) .fieldSetMapper(new BeanWrapperFieldSetMapper() { {
								 * setTargetType(User.class); } }).build();
								 */
	}

	private LineMapper<User> defaultLinemapper() {
		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		delimitedLineTokenizer.setNames("userId", "age", "name");

		BeanWrapperFieldSetMapper<User> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(User.class);

		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		return defaultLineMapper;
	}

	/*
	 * @Bean JdbcBatchItemWriter<User> writer(DataSource dataSource) { return new
	 * JdbcBatchItemWriterBuilder() .itemSqlParameterSourceProvider(new
	 * BeanPropertyItemSqlParameterSourceProvider<>())
	 * .sql("INSERT INTO user (userId, age, name) VALUES (:userId, :age, :name)").
	 * dataSource(dataSource) .build(); }
	 */

	@Bean
	RepositoryItemWriter<User> writer() {
		RepositoryItemWriter<User> repositoryItemWriter = new RepositoryItemWriter<>();
		repositoryItemWriter.setRepository(userRepository);
		repositoryItemWriter.setMethodName("save");
		return repositoryItemWriter;
	}

	@Bean
	ItemProcessor<User, User> processor(EntityManager entityManager) {
		return new ItemProcessor<User, User>() {
			@Override
			public User process(User user) throws Exception {
				// Check if the entity exists in the database
				Long count = (Long) entityManager.createQuery(
						"SELECT COUNT(e) FROM User e WHERE e.userId = :userId AND e.age = :age AND e.name = :name")
						.setParameter("userId", user.getUserId()).setParameter("age", user.getAge())
						.setParameter("name", user.getName()).getSingleResult();

				if (count == 0) {
					// Entity doesn't exist, return it for insertion
					user.setName(user.getName().toUpperCase());
					return user;
				} else {
					User databaseUser = (User) entityManager
							.createQuery(
									"SELECT u FROM User u WHERE u.userId = :userId AND u.age = :age AND u.name = :name")
							.setParameter("userId", user.getUserId()).setParameter("age", user.getAge())
							.setParameter("name", user.getName()).getSingleResult();
					System.out.println(databaseUser);
				}
				// Return null if the entity exists, so it will be skipped
				return null;
			}
		};
	}

	@Bean
	Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			EntityManager entityManager) {
		return new StepBuilder("step1", jobRepository).<User, User>chunk(10, transactionManager).reader(reader())
				.processor(processor(entityManager)).writer(writer()).taskExecutor(taskExecutor()).build();
	}

	@Bean
	Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
		return new JobBuilder("importUserJob", jobRepository).incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1).end().build();
	}

	@Bean
	TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
		asyncTaskExecutor.setConcurrencyLimit(2);// 10 thread will get executed parallely
		return asyncTaskExecutor;
	}

}
