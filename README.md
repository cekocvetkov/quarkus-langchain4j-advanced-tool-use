# Advanced Tool Use Features

These features enable more efficient tool orchestration, improved accuracy, and reduced token consumption.

All features are optional and can be enabled independently.

---

## Tool Search

Anthropic's Tool Search Tool allows Claude to use search tools to access thousands of tools without consuming its context window.

The Tool Search Tool lets Claude dynamically discover tools instead of loading all definitions upfront. You provide all your tool definitions to the API, but mark tools with `defer_loading: true` to make them discoverable on-demand. Deferred tools aren't loaded into Claude's context initially. Claude only sees the Tool Search Tool itself plus any tools with `defer_loading: false` (your most critical, frequently-used tools).

When Claude needs specific capabilities, it searches for relevant tools. The Tool Search Tool returns references to matching tools, which get expanded into full definitions in Claude's context.

### Enable Tool Search

```properties
quarkus.langchain4j.anthropic.chat-model.tool-search.enabled=true
```

You can optionally specify the search type (`regex` is the default, or `bm25`):

```properties
quarkus.langchain4j.anthropic.chat-model.tool-search.type=bm25
```

### Notes

When Tool Search is enabled, the Quarkus extension automatically:

- Adds the `AnthropicServerTool` (either `regex` or `bm25` variant) to the model request
- Configures `toolMetadataKeysToSend` to include `defer_loading`
- Includes the required `advanced-tool-use-2025-11-20` beta header in all requests

### Example

```java
@Tool(
    name = "get_weather",
    value = "Get the weather for a city",
    metadata = "{\"defer_loading\": true}"
)
public String getWeather(String city) {
    // ...
}
```

---

## Programmatic Tool Calling

Anthropic's Programmatic Tool Calling allows Claude to invoke tools in a code execution environment, reducing the impact on the model’s context window.

Instead of invoking tools one-by-one through API round-trips, Claude writes code that calls multiple tools, processes their outputs, and controls what information enters its context.

This allows:

- Explicit loops and conditionals
- Better data transformations
- Clear error handling
- More reliable control flow

### Enable Programmatic Tool Calling

```properties
quarkus.langchain4j.anthropic.chat-model.programmatic-tool-calling.enabled=true
```

### Notes

When enabled, the extension automatically:

- Adds the Code Execution server tool
- Sends the `"allowed_callers"` metadata key with tool definitions
- Includes the required beta header in all requests

### Example

```java
@Tool(
    name = "get_weather",
    value = "Get the weather for a city",
    metadata = "{\"allowed_callers\": [\"code_execution_20250825\"]}"
)
public String getWeather(String city) {
    // ...
}
```

---

## Tool Use Examples

This feature provides a universal standard for demonstrating how to effectively use a given tool.

Tool Use Examples let you provide sample tool calls directly in your tool definitions. Instead of relying on schema alone, you show Claude concrete usage patterns.

### Example Use Case

For example, when a date is required:

- Should it be `"2024-11-06"`?
- `"Nov 6, 2024"`?
- `"2024-11-06T00:00:00Z"`?

Providing examples removes ambiguity.

### Enable Tool Use Examples

```properties
quarkus.langchain4j.anthropic.chat-model.tool-use-examples.enabled=true
```

### Notes

When enabled, the extension automatically:

- Sends the `"input_examples"` metadata key
- Includes the required beta header in all requests

### Example

```java
private static final String METADATA = """
{
  "input_examples": [
    {
      "title": "Login page returns 500 error",
      "priority": "critical",
      "labels": ["bug", "authentication"],
      "date": "2024-11-06"
    },
    {
      "title": "Update API documentation",
      "date": "2024-12-01"
    },
    {
      "title": "Brainstorming session"
    }
  ]
}
""";

@Tool(
    name = "create_ticket",
    value = "Create a support ticket",
    metadata = METADATA
)
public String createTicket(String title, String priority, String date) {
    return "TICKET-123";
}
```

---

## References

- https://www.anthropic.com/engineering/advanced-tool-use
- https://github.com/langchain4j/langchain4j/blob/main/docs/docs/integrations/language-models/anthropic.md#tool-search-tool
- https://github.com/langchain4j/langchain4j/blob/main/docs/docs/integrations/language-models/anthropic.md#programmatic-tool-calling
- https://github.com/langchain4j/langchain4j/blob/main/docs/docs/integrations/language-models/anthropic.md#tool-use-examples
- https://github.com/langchain4j/langchain4j/blob/main/langchain4j-anthropic/src/main/java/dev/langchain4j/model/anthropic/AnthropicChatModel.java#L321

# quarkus-langchain4j-advanced-tool-use

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./gradlew build
```

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./gradlew build -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./gradlew build -Dquarkus.native.enabled=true
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./gradlew build -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/quarkus-langchain4j-advanced-tool-use-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/gradle-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
