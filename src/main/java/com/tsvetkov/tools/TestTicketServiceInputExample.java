package com.tsvetkov.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TestTicketServiceInputExample {
    private static final String METADATA = """
            {
              "input_examples": [
                {
                  "title": "Login page returns 500 error",
                  "priority": "critical",
                  "labels": ["bug", "authentication"],
                  "due_date": "2024-11-06"
                },
                {
                  "title": "Update API documentation",
                  "due_date": "2024-12-01"
                },
                {
                  "title": "Brainstorming session"
                }
              ]
            }
            """;

    @Tool(name = "create_ticket", value = "Create a support ticket", metadata = METADATA)
    public String createTicket(String title, @P(value =  "priority", required = false) String priority, @P(value =  "due_date", required = false) String dueDate ) {
        return "TICKET-123";
    }
}