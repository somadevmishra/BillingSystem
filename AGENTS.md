# Engineering Guide for Coding Agents

## Read first

Before changing code, inspect the documentation relevant to the task:

- `docs/PROJECT.md` for the verified repository baseline.
- `docs/ARCHITECTURE.md` and `docs/decisions/` for architectural constraints.
- `docs/DOMAIN-MODEL.md` and `docs/BUSINESS-RULES.md` for domain terminology and implemented behavior.
- `docs/DATABASE.md` before changing entities, repositories, or persistence configuration.
- `docs/DEVELOPMENT-STATUS.md` and `docs/ROADMAP.md` to avoid duplicating or bypassing planned work.

Treat `CURRENT` sections as repository-backed facts. `PLANNED` and `PROPOSED` sections are direction, not implemented behavior. Update the affected documentation in the same change when code changes make it stale.

## Architecture rules

- The approved starting architecture is a **modular monolith**. Do not introduce microservices, distributed messaging, separate deployables, or external infrastructure without explicit approval.
- Keep Kotlin, Spring Boot, Java 21, Spring MVC, Spring Data JPA, validation, and the current Gradle build unless an explicit decision changes them.
- Organize new capabilities around business domains, not technical utility layers. Current code is organized in shared technical packages; evolve deliberately toward bounded domain modules rather than doing broad package moves incidentally.
- Keep controllers thin: HTTP binding and response status only. Put use-case orchestration in services/application code and deterministic calculations in focused domain components.
- Do not expose JPA entities in public API request or response contracts. Use request/response DTOs and explicit mapping. Existing exceptions to this rule are technical debt, not precedent.
- Keep domain rules close to the domain that owns them. Do not add catch-all `util`, `common`, or cross-domain service classes merely to share speculative abstractions.
- Preserve bill/item historical snapshots where a transaction requires them; do not reinterpret historical transaction values from mutable catalog data.

## Package and domain rules

- Use `com.manual.billing` as the application root package.
- Current implemented concepts are catalog (`CategoryEntity`, `ItemEntity`), billing (`BillEntity`, `BillItemEntity`), sequence generation, and shared configuration/exception handling.
- Candidate future domains include catalog, parties (customers/vendors), inventory, purchasing, sales, finance/payments, reporting, and intelligence. Add a domain only when a concrete approved use case needs it.
- A module may depend on another module through a narrow public application/domain contract. Avoid repository/entity access across domains unless explicitly designed and documented.
- New externally observable behavior needs an API contract, validation, error handling, tests, and documentation.

## Kotlin and Spring conventions

- Follow idiomatic Kotlin: concise expressions where clear, immutable `val` by default, nullable types only when absence is meaningful, and explicit names for money and state transitions.
- Use `BigDecimal` for money, tax, quantity, balances, and persisted monetary calculations; never use `Double` or `Float`.
- Use Jakarta Bean Validation on external requests. Validate business invariants in the owning service/domain component.
- Make transactional boundaries explicit. Read-only operations should use `@Transactional(readOnly = true)` where they load lazy relationships or require a consistent read.
- Surface expected absence and business conflicts through the established exception mechanism; do not throw generic `RuntimeException` for expected client-facing cases.
- Do not add manual timestamp writes where configured Spring Data auditing owns the field, unless the change is intentional and documented.

## Database rules

- Do not rely on Hibernate `ddl-auto: update` as the long-term schema change mechanism. Before schema-affecting production work, obtain approval for a managed migration approach and document it.
- Every entity-to-schema change must account for existing data, indexes, foreign keys, nullability, precision/scale, audit fields, and optimistic-lock versioning.
- `src/main/resources/entity-details.sql` is a reference SQL file in the current repository; it is not configured as an application migration. Keep it aligned only if it remains part of the chosen schema strategy.
- Sequence rows (`CATEGORY`, `ITEM`, `BILL`) are required by the current code-generation service. Never change their names or behavior without considering transactional concurrency and deployed data.
- Do not hard-code credentials or environment-specific connection strings. Preserve environment-based datasource configuration.

## Testing expectations

- Add focused tests for every changed business rule and bug fix. The current test suite is only a context-load smoke test and is insufficient as a quality bar for new behavior.
- Unit-test pure calculations and state transitions; use repository/service tests for persistence and transactional behavior; add controller/API tests for request validation and HTTP errors when contracts change.
- Test money values, GST rounding/precision, lifecycle transitions, inactive/soft-deleted records, and concurrent code generation where relevant.
- Run the relevant Gradle tests before handoff. Do not claim tests passed unless they were run successfully in the current workspace.

## Change-control rules

- Do not make architectural changes, introduce dependencies, alter the persistence strategy, change public endpoints, or expand the product scope without explicit approval.
- Prefer small, reversible, domain-scoped changes. Do not mix refactors with feature work unless necessary and approved.
- Preserve user changes in a dirty worktree. Inspect `git status` before editing and do not overwrite unrelated files.
- Do not invent current functionality. Document and communicate whether a statement is `CURRENT`, `PLANNED`, or `PROPOSED`.
