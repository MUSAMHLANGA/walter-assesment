package com.xib.assessment.controller;

import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import com.xib.assessment.repository.AgentRepository;
import com.xib.assessment.repository.ManagerRepository;
import com.xib.assessment.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.xib.assessment.exception.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AgentController {

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("agent/{id}")
    public Agent findAgent(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Agent agent = new Agent();

        try{
            agent = agentRepository.findById(id).get();
            agent.setId(id);

        }catch(Exception e){
           throw new ResourceNotFoundException("Agent not registerd");
        }

        return agent;
    }

     @PostMapping("/agent")
     public Agent createAgent(@RequestBody Agent agent){

         Agent agent1 = new Agent();
         try{

             agent1 = agentRepository.save(agent);

         }catch(InvalidInputException e){
            throw new InvalidInputException(e.getMessage());
         }

        return agent1;
     }

    //GET /agents/ - Returns a list of all agents in the database in JSON format
     /*@GetMapping("/agents")
    public List<Agent> getAllAgents(){

       List<Agent> agents = new ArrayList<>();
           try{
               agents = agentRepository.findAll();
           }catch(Exception e){

           }
        return agents;
     }*/

    //GET /agents/ - Implement pagination and query parameters on this request. The agents identity number should no longer be returned in this request.
    @GetMapping("/agents")
    public Page<Agent> getAllPageableAgents(Pageable pageable) throws ResourceNotFoundException {
        Page<Agent> agents = null;
        try{
            agents  = agentRepository.findAll(pageable);

        }catch(Exception e){
            throw new ResourceNotFoundException("No agent added");
        }
        catch(InternalServerException e){
            throw new InternalServerException(e.getMessage());
        }

        return agents;
    }


  @PutMapping("/team/{id}")
    public Team updateTeam(@PathVariable("id") Long id,@RequestBody Agent agent) throws ResourceNotFoundException {

        Team team1 = new Team();
       try{

           if(teamRepository.findById(id).isPresent()){

               team1 = teamRepository.findById(id).get();

               team1.setAgent(agent);

               teamRepository.save(team1);

           }
       }catch(Exception e){
          throw new InternalServerException(e.getMessage());
       }

        return team1;
  }
  @GetMapping("/emptyTeams")
    public List<Team> getEmptyTeams() throws ResourceNotFoundException {

      List<Team> myTeam = new ArrayList<>();

        try{
            List<Team> teams = teamRepository.findAll();

            for(int x = 0;x < teams.size();x++){

                if(teams.get(x).getManager() == null || teams.get(x).getAgent() == null){

                    myTeam.add(teams.get(x));
                }
            }
        }catch(Exception e){
            throw new ResourceNotFoundException("No empty teams found");
        }

        return  myTeam;
  }




}
