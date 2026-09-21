# AI Development Target

## Purpose

Extend the existing BillingSystem Spring Boot modular monolith with AI capabilities and use it as the learning and implementation project for AI application development.

The goal is to learn AI application development progressively without introducing unnecessary architecture or infrastructure.

## Current Architectural Direction

BillingSystem remains a **modular monolith**.

Do not introduce:
- AI microservices
- Separate deployables
- Vector databases
- RAG
- AI agents
- Model training
- Fine-tuning
- External AI infrastructure

unless there is a specific requirement and the change is agreed first.

AI functionality should initially live inside the existing Spring Boot application as an isolated AI module.

## First AI Feature

Build a small **Product Description Generator**.

### Target API

`POST /api/ai/product-description`

### Example Request

```json
{
  "productName": "Borosil Glass Beaker",
  "category": "Laboratory Glassware",
  "capacity": "500 ml"
}
```

### Expected Flow

```text
Client
  |
  v
AiController
  |
  v
AiService
  |
  v
Prompt Builder
  |
  v
AI Client
  |
  v
LLM API
  |
  v
AI Response
  |
  v
Client
```

## Initial AI Module

Suggested package structure:

```text
ai/
├── controller/
├── service/
├── client/
├── model/
└── prompt/
```

Keep the module small. Do not over-engineer it.

## Learning Objectives

Implement the feature progressively while learning:

1. What an LLM is
2. How a Spring Boot application communicates with an LLM
3. AI provider API authentication
4. Prompt construction
5. Request and response handling
6. Tokens and context limits
7. AI latency, timeout and failure handling
8. Structured AI output
9. Hallucination and why authoritative application data must remain the source of truth
10. Later: AI + application data, RAG and AI agents

## Important Principles

### 1. LLM is not the system of record

The AI model can generate text or reasoning, but it must not be treated as the authoritative source for business facts.

For example, product price, customer balance, invoice status and inventory should come from BillingSystem's database/business services.

### 2. Keep business logic outside the prompt

Business rules should remain in normal application code where possible.

The AI should assist with tasks such as:
- Natural-language generation
- Summarization
- Classification
- Recommendations
- Explanation

It should not silently replace deterministic business rules.

### 3. Prefer structured output when the application needs data

When AI output will be consumed by Java code, prefer a predictable JSON/structured response rather than arbitrary prose.

### 4. Keep provider integration isolated

The rest of the application should not depend directly on a specific AI provider's SDK/API details.

Use an internal `AiClient` abstraction so the provider can be changed later if required.

## Future Learning Path

### Level 1 - Basic LLM Integration

```text
Spring Boot
    |
    v
Prompt
    |
    v
LLM
    |
    v
Response
```

First deliverable: Product Description Generator.

### Level 2 - AI + Application Data

Example:

```text
Customer
   |
   v
BillingSystem Database
   |
   v
Relevant business data
   |
   v
LLM
   |
   v
Business summary
```

### Level 3 - Structured AI

```text
LLM
 |
 v
JSON
 |
 v
Java DTO
 |
 v
Application logic
```

### Level 4 - RAG

```text
Business Documents/Data
        |
        v
    Embeddings
        |
        v
 Vector Database
        |
        v
Relevant Context
        |
        v
       LLM
        |
        v
      Answer
```

### Level 5 - AI Agents

```text
User
 |
 v
LLM
 |
 +--> Database tool
 +--> Business API
 +--> Search tool
 +--> Other application services
 |
 v
Result / Action
```

## Development Rules for Codex

1. Read the existing project documentation before making changes.
2. Treat this document as the AI development target.
3. Preserve the existing modular-monolith architecture.
4. Do not change existing business behavior unless explicitly required.
5. Clearly distinguish CURRENT, PLANNED and PROPOSED functionality.
6. Keep the first AI implementation intentionally small.
7. Add tests for AI-related application logic.
8. Do not commit API keys or secrets.
9. Keep provider-specific code isolated behind the AI client boundary.
10. Update `docs/DEVELOPMENT-STATUS.md` when an AI milestone is completed.
11. Update relevant architecture/project documentation if an implementation decision changes the agreed architecture.

## First Milestone

The first milestone is complete when:

- AI module exists inside the existing Spring Boot application.
- Product description endpoint is available.
- Application can send a prompt to an LLM through the AI client.
- LLM response can be returned through the REST endpoint.
- Configuration/secrets are externalized.
- Basic success and failure tests exist.
- Existing BillingSystem tests continue to pass.
- Documentation is updated.

## Out of Scope for First Milestone

- RAG
- Vector database
- AI agents
- Fine-tuning
- Model training
- Streaming responses
- Multi-model orchestration
- AI microservice
- Autonomous actions
