# Architecture

## CURRENT

The application is a single Spring Boot deployable with a conventional layered package layout:

```text
com.manual.billing
├── controller        HTTP endpoints for categories, items, and bills
├── service / impl    use-case orchestration and transactions
├── engine            bill calculation logic
├── entity            JPA persistence model
├── repository        Spring Data JPA repositories
├── dto               request and response contracts
├── mapper            entity-to-response mapping
├── config            JPA auditing and OpenAPI configuration
├── exception         REST exception handling
└── enums             bill and payment states
```

`BillCalculationEngine` is a Spring component that creates bill-line snapshots and calculates line and bill totals. `BillServiceImpl` owns the current bill lifecycle orchestration. Category and item services own their corresponding CRUD operations. Spring Data JPA persists entities to the configured relational database.

The application has no internal module boundaries beyond packages, no message broker, no external service clients, no distributed components, and no frontend module.

## PLANNED

The approved starting architecture is a **modular monolith**: one deployable Spring Boot application with explicit, domain-oriented module boundaries. Microservices are not part of the planned architecture unless explicitly approved.

Likely business modules, introduced only as concrete use cases require them, are:

| Module | Responsibility |
| --- | --- |
| Catalog | Products/items and categories |
| Parties | Customers and vendors |
| Inventory | Stock records, movements, and availability |
| Purchasing | Purchases, purchase returns, and vendor obligations |
| Sales | Quotations, sales, sales returns, and invoicing/billing |
| Finance | Payments, allocations, receivables, payables, and GST accounting support |
| Reporting | Operational, profitability, and financial reports |
| Intelligence | Approved analysis, demand/seasonality, recommendations, automation, and AI assistance |

## PROPOSED module shape

As domains are added or materially changed, keep each one cohesive under a domain root rather than expanding the shared technical folders indefinitely. A module can contain a small API layer, application services/use cases, domain rules, and persistence adapters. Exact package names should be chosen during an approved implementation, not imposed through a speculative mass refactor.

Cross-domain collaboration should use explicit application/domain contracts and transactional consistency within the monolith. Direct cross-domain use of another domain’s repositories or mutable entities should be treated as an exception requiring documentation.

HTTP controllers remain adapters: bind and validate requests, call an application use case, and return a DTO. JPA entities remain persistence concerns; they should not become public request contracts. Calculation components should remain deterministic and independently testable.

## Architectural constraints

- Kotlin + Spring Boot + Java 21 remain the primary stack.
- Preserve existing technology choices unless an explicit decision approves change.
- Do not introduce microservices or distributed infrastructure without explicit approval.
- Do not add generic framework layers, event buses, or abstractions without an immediate use case and owner.

The governing architecture decision is [ADR 0001](decisions/0001-modular-monolith.md).
