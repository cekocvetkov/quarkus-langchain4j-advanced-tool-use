package com.tsvetkov;

import com.tsvetkov.assistants.AICodeExecutionAssistent;
import com.tsvetkov.assistants.AIInputExampleAssistent;
import com.tsvetkov.assistants.AIToolSearchAssistent;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/ai")
public class AIResource {

    @Inject
    AICodeExecutionAssistent aiCodeExecutionAssistent;
    @Inject
    AIToolSearchAssistent aiToolSearchAssistent;
    @Inject
    AIInputExampleAssistent aiInputExampleAssistent;

    @GET
    @Path("/code-execution")
    @Produces(MediaType.TEXT_PLAIN)
    public String checkOrderStatusCodeExecution() {
        return aiCodeExecutionAssistent.chat("""
                    Hi, I didn't get a confirmation that my order is already in delivery.
                    Can you tell me if it has already been sent?
                    The order number I got in my mail is 1002.
                """);
    }

    @GET
    @Path("/tool-search")
    @Produces(MediaType.TEXT_PLAIN)
    public String checkOrderStatusToolSearch() {
        return aiToolSearchAssistent.chat("""
                    Hi, I didn't get a confirmation that my order is already in delivery.
                    Can you tell me if it has already been sent?
                    The order number I got in my mail is 1002.
                """);
    }

    @GET
    @Path("/input-examples")
    @Produces(MediaType.TEXT_PLAIN)
    public String testSupportTicket() {
        return aiInputExampleAssistent.chat("""
                Create a support ticket for a critical login failure that returns a 500 error.
                """);
    }
}
