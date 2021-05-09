package com.xib.assessment.controller;

import com.xib.assessment.exception.InternalServerException;
import com.xib.assessment.exception.ResourceNotFoundException;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Team;
import com.xib.assessment.repository.ManagerRepository;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ManagerRepository managerRepository;

    //GET /teams/ - Returns a list of teams in the database in JSON format
    @GetMapping("/teams")
    public List<Team> getAllTeams(){

        List<Team> teams = new ArrayList<>();
           try{
               teams = teamRepository.findAll();

               if(teams.size() < 0){

                   throw new ResourceNotFoundException("No team registered");
               }
           }catch(InternalServerException | ResourceNotFoundException e){
               throw new InternalServerException(e.getMessage());
           }

        return teams;
    }

    //GET /team/{id}/ - Returns a detail view of the specified team in JSON format
    @GetMapping("team/{id}")
    public Team findTeam(@PathVariable("id") Long id) throws ResourceNotFoundException {

        Team team = new Team();
      try{

          team = teamRepository.findById(id).get();

        }catch(Exception e){
          throw  new ResourceNotFoundException("There is no such team at XIB");
      }
        return team;
    }

   // POST /team/ - Creates a new team with the specified details - Expects a JSON body
    @PostMapping("/team")
    public Team createTeam(@RequestBody Team team )  {
        Team team1 = new Team();

        try{
            team1 = teamRepository.save(team);

        }catch(InternalServerException e){

           throw new InternalServerException(e.getMessage());
        }

        return team1;
    }

}
