package com.ileiwe.data.repository;

import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/insert.sql"})
class LearningPartyRepositoryTest {

    @Autowired
    LearningPartyRepository learningPartyRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    @Rollback(value=false)
    void createLearningPartyWithStudentRoleTest(){
        LearningParty learningUser =
                new LearningParty("frank@gmail.com",
                        "frank1234",
                        new Authority(Role.ROLE_STUDENT));
        log.info("Before saving ->{}", learningUser);

        learningPartyRepository.save(learningUser);

        assertThat(learningUser.getId()).isNotNull();
        assertThat(learningUser.getPassword()).isEqualTo("frank1234");
        assertThat(learningUser.getEmail()).isEqualTo("frank@gmail.com");
        assertThat(learningUser.getAuthorities()
                .get(0).getAuthority()).isEqualTo(Role.ROLE_STUDENT);

        assertThat(learningUser.getAuthorities()
                .get(0).getId()).isNotNull();


        log.info("After saving ->{}", learningUser);




    }

    @Test
    void createLearningPartyUniqueEmailsTest(){
        //create a learning party

        LearningParty user =
                new LearningParty("Rabbi@gmail.com",
                        "rabbi1234",
                        new Authority(Role.ROLE_STUDENT));
        //save to db

        learningPartyRepository.save(user);
        assertThat(user.getEmail()).isEqualTo("Rabbi@gmail.com");
        assertThat(user.getId()).isNotNull();

        //create another learning with same email
        LearningParty user2 = new LearningParty("Rabbi@gmail.com",
                "rabbi1234",
                new Authority(Role.ROLE_STUDENT));

        //save and catch exception
        assertThrows(DataIntegrityViolationException.class, ()->
        learningPartyRepository.save(user2));
        //assert that exception is thrown

        assertThat(DataIntegrityViolationException.class).isNotNull();



    }


    @Test
    void LearningPartyWithEmptyStringValueTest(){


        LearningParty user2 = new LearningParty(" ",
                        " ",
                        new Authority(Role.ROLE_STUDENT));
        assertThrows(ConstraintViolationException.class, ()->
                learningPartyRepository.save(user2));

    }

    @Test
    void LearningPartyWithNullValueTest(){
        //create another learning with same email
        LearningParty user2 = new LearningParty(null,
                null,
                new Authority(Role.ROLE_STUDENT));

        //save and catch exception
        assertThrows(ConstraintViolationException.class, ()->
                learningPartyRepository.save(user2));
    }

    @Test
    @Transactional
    void findByUserNameTest(){
        LearningParty learningParty =
                learningPartyRepository.findByEmail("tomi@gmail.com");

        assertThat(learningParty).isNotNull();
        assertThat(learningParty.getEmail()).isEqualTo("tomi@gmail.com");

        log.info("learning party object ->{}", learningParty);
    }

    @AfterEach
    void tearDown() {
    }


}