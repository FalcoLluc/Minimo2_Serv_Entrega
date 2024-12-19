package edu.upc.dsa.services;
import edu.upc.dsa.*;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.GameCharacter;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Question;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.reflections.Store;

import javax.naming.Name;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/question", description = "Endpoint to Question Service")
@Path("/question")

public class QuestionService {
    private SessionManager sesm; //per les galetes
    final static Logger logger = Logger.getLogger(QuestionService.class);
    public QuestionService(){
        this.sesm = SessionManager.getInstance();
    }
    @POST
    @ApiOperation(value = "submit a new Question", notes = "hello")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 506, message = "User Without An Active Session"),
    })
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(Question quest, @CookieParam("authToken") String authToken) {

        if (quest.getTitle()==null || quest.getMessage()==null || quest.getDate()==null)  return Response.status(500).build();
        try{
            User u=this.sesm.getSession(authToken);
            String user=u.getName();
            System.out.println("Date: "+quest.getDate());
            System.out.println("Title: "+quest.getTitle());
            System.out.println("Message: "+quest.getMessage());
            System.out.println("Sender: "+user);
            return Response.status(201).entity(quest).build();
        }
        catch (UserNotLoggedInException ex) {
            logger.warn("User Without An Active Session");
            return Response.status(506).build();
        }
    }
}
