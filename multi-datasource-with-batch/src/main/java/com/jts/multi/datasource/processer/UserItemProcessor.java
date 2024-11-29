package com.jts.multi.datasource.processer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.jts.multi.datasource.db1.model.User;

public class UserItemProcessor implements ItemProcessor<User, User> {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserItemProcessor.class);

	@Override
	public User process(final User user) throws Exception {
		int userid = user.getUserId();
		int age = user.getAge();
		String name = user.getName().toUpperCase();

		User transformedUser = new User(userid, name, age);
		LOGGER.info("Converting ( {} ) into ( {} )", user, transformedUser);

		return transformedUser;
	}

}
