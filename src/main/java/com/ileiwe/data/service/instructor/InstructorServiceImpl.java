package com.ileiwe.data.service.instructor;

import com.ileiwe.data.dto.InstructorPartyDto;
import com.ileiwe.data.model.Authority;
import com.ileiwe.data.model.Instructor;
import com.ileiwe.data.model.LearningParty;
import com.ileiwe.data.model.Role;
import com.ileiwe.data.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements  InstructorService{

    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public Instructor save(InstructorPartyDto instructorPartyDto) {

        if(instructorPartyDto == null){
            throw new IllegalArgumentException("Instructor cannot be null");
        }
        LearningParty learningParty = new LearningParty(instructorPartyDto.getEmail()
                                ,instructorPartyDto.getPassword(), new Authority(Role.ROLE_INSTRUCTOR));

        Instructor instructor = Instructor.builder()
                .lastname(instructorPartyDto.getLastname())
                .firstname(instructorPartyDto.getFirstname())
                .learningParty(learningParty).build();

        return instructorRepository.save(instructor);
    }
}
