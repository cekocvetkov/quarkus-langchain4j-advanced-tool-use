package com.tsvetkov.assistants;

import com.tsvetkov.tools.OrderServiceCodeExecution;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;
import jakarta.enterprise.context.ApplicationScoped;

@RegisterAiService
@ApplicationScoped
public interface AICodeExecutionAssistent {
    @SystemMessage("""
            You are a helpful assistant with access to tools.
            Answer questions clearly and use tools when needed.
            Convert tool responses into natural, human-readable answers.
            Ask for clarification if questions are unclear.
        """
    )
    @ToolBox(OrderServiceCodeExecution.class)
    String chat(@UserMessage String question);
}