package com.xib.assessment.controller;

import com.xib.assessment.exception.InternalServerException;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import com.xib.assessment.repository.ManagerRepository;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class ManagerController {

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    TeamRepository teamRepository;

    @PostMapping("/manager")
    public Manager createManager(@RequestBody Manager manager){
        Manager manager1 = new Manager();

     try{
         manager1 = managerRepository.save(manager);

     }catch(InternalServerException e){
        throw new InternalServerException(e.getMessage());
     }
        return manager1;
    }

}
