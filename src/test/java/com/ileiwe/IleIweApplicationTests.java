package com.ileiwe;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
class IleIweApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private DataSource dataSource;

	@Test

	public void connectToDatabaseTest(){
		assertThat(dataSource).isNotNull();
		log.info("DataSource properties ->{}", dataSource);
		try{
			Connection connection = dataSource.getConnection();
			assertThat(connection).isNotNull();
			assertThat(connection.getCatalog()).isEqualTo("ilewedb");
			log.info("DataSource->{}", connection.getCatalog());
		}catch (SQLException exception){
			log.info("An exception occurred ->{}", exception.getMessage());

		}
	}

}
