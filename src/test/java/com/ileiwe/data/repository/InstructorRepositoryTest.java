package com.ileiwe.data.repository;

import com.ileiwe.data.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
@Sql(scripts = ("/db/insert.sql"))
class InstructorRepositoryTest {

    @Autowired
    InstructorRepository instructorRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveInstructorAsLearningPartyTest(){
        //create a learning party
        LearningParty user =
                new LearningParty("trainer@ile.com",
                        "1234",
                        new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor and map with learning party
        Instructor instructor = Instructor.builder()
                    .firstname("Frank")
                    .lastname("Ala")
                    .learningParty(user).build();

        //save instructor
        instructorRepository.save(instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();
        log.info("instructor after saving ->{}", instructor);


    }

    @Test

    @Transactional
    @Rollback(value = false)
    void updateInstructorTableAfterCreate(){
        //create a learning party
        LearningParty user =
                new LearningParty("trainer@ile.com",
                        "1234",
                        new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor and map with learning party
        Instructor instructor = Instructor.builder()
                .firstname("Frank")
                .lastname("Ala")
                .learningParty(user).build();

        //save instructor
        instructorRepository.save(instructor);
        assertThat(instructor.getId()).isNotNull();
        assertThat(instructor.getLearningParty().getId()).isNotNull();
        log.info("instructor after saving ->{}", instructor);

        Instructor savedInstructor =
                instructorRepository.findById(instructor.getId()).orElse(null);

        log.info("Saved instructor ->{}", instructor);

        //assert that savedInstructor is not null
        assertThat(savedInstructor).isNotNull();
        //confirm that instructor's bio and gender is null
        assertThat(savedInstructor.getBio()).isNull();
        assertThat(savedInstructor.getGender()).isNull();
        //save bio and gender
        savedInstructor.setBio("I am a java instructor");
        savedInstructor.setGender(Gender.MALE);


        //save instructor into repo
        instructorRepository.save(savedInstructor);

        //assertThat instructor now has bio and gender
        assertThat(savedInstructor.getBio()).isNotNull();
        assertThat(savedInstructor.getGender()).isNotNull();


    }

    @Test
    void createInstructorWithNullValuesTest(){
        LearningParty user =
                new LearningParty("trainer@ile.com",
                        "1234",
                        new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor and map with learning party
        Instructor instructor = Instructor.builder()
                .firstname(null)
                .lastname(null)
                .learningParty(user).build();

        assertThrows(ConstraintViolationException.class,
                ()->instructorRepository.save(instructor));

    }
    @Test
    void createInstructorWithEmptyStringValuesTest(){
        LearningParty user =
                new LearningParty("trainer@ile.com",
                        "1234",
                        new Authority(Role.ROLE_INSTRUCTOR));
        //create instructor and map with learning party
        Instructor instructor = Instructor.builder()
                .firstname(" ")
                .lastname(" ")
                .learningParty(user).build();

        assertThrows(ConstraintViolationException.class,
                ()->instructorRepository.save(instructor));

    }



    @AfterEach
    void tearDown() {
    }
}