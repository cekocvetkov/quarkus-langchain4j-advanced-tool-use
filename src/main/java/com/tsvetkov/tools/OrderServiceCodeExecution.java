package com.tsvetkov.tools;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class OrderServiceCodeExecution {

    private static final Map<String, String> ORDERS = Map.of(
            "1001", "PENDING",
            "1002", "SHIPPED",
            "1003", "DELIVERED",
            "1004", "CANCELLED"
    );

    private static final String CODE_EXECUTION_METADATA = """
                {
                  "allowed_callers": ["code_execution_20250825"]
                }
                """;

    @Tool(  name = "get_order_status_by_order_id",
            value = "Get order status by order id",
            metadata = CODE_EXECUTION_METADATA)
    public String getOrderStatus(String orderId) {
        return ORDERS.get(orderId);
    }

}
