# Development Status

## CURRENT implemented baseline

| Area | Status | Evidence |
| --- | --- | --- |
| Application bootstrap | Implemented | Spring Boot entry point and Gradle build |
| HTTP API | Implemented for categories, items, and bills | Three REST controllers under `/api` |
| Catalog categories | Partially implemented | CRUD-style API with soft delete and generated codes |
| Catalog items | Partially implemented | CRUD-style API with generated codes, optional category, price, and GST percentage |
| Bill lifecycle | Partially implemented | Create/read, add/update/remove lines, finalize, cancel |
| Billing calculations | Implemented baseline | Dedicated `BillCalculationEngine` |
| API error representation | Implemented baseline | Global REST exception handler and error DTOs |
| Auditing/concurrency annotations | Implemented in entities | JPA auditing and `@Version` in `BaseEntity` |
| Database migrations | Implemented baseline | Flyway V1 initial MySQL schema migration; Hibernate validates the schema |
| Automated tests | Minimal | One Spring context-load test |
| UI | Not implemented | No frontend/templates/static assets |

## CURRENT technical gaps and risks

- `BillService.update` is unimplemented and has no controller endpoint.
- Existing databases created before Flyway require inspection and explicit baselining before migration version `1` is recorded.
- Current test coverage does not exercise business rules, API behavior, or calculation edge cases.
- Item update exposes a persistence entity in an API request; category and item active-list semantics differ.
- Generic runtime exceptions in some bill paths become HTTP 500 instead of clear client errors.
- The current OpenAPI description retains cafe-prototype language, while product direction has moved to a generic business-management platform.

## PLANNED scope not implemented

Customers, vendors, inventory, purchases, purchase returns, sales, sales returns, quotations, complete invoicing flows, payment allocations, GST workflows, receivables/payables, reporting, profitability, business intelligence, seasonal/demand analysis, purchase recommendations, automation, and AI-assisted analysis are not implemented.

## PROPOSED near-term focus

Prioritize foundation correctness before scope expansion: schema ownership/migrations, API contract cleanup, missing bill behavior, domain-rule tests, and confirmation of the first Shibansh Scientific workflows. See `ROADMAP.md` for the proposed order and `DATABASE.md` for the persistence decision needed before broader schema development.
