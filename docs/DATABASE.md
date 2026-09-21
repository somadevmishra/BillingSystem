# Database and Persistence

## CURRENT configuration

Production datasource values are read from environment placeholders in `src/main/resources/application.yaml`:

```yaml
spring.datasource.url: ${DB_URL}
spring.datasource.username: ${DB_USER}
spring.datasource.password: ${DB_PASS}
```

The runtime JDBC driver is MySQL Connector/J. Spring Data JPA is enabled through dependencies, Flyway manages the MySQL schema, and Hibernate is configured with `ddl-auto: validate` and `show-sql: true`.

Tests use an in-memory H2 datasource (`jdbc:h2:mem:billing-test`) with `ddl-auto: create-drop` and SQL logging disabled. Flyway is disabled for this existing H2 test configuration because the authoritative migration targets the MySQL runtime.

## CURRENT schema sources

JPA entity classes remain the runtime persistence mapping. Flyway migration `src/main/resources/db/migration/V1__initial_current_schema.sql` is the authoritative initial schema for MySQL and creates `categories`, `items`, `code_sequence`, `bills`, and `bill_items`, including audit fields, optimistic-lock columns, bill lifecycle fields, constraints, and indexes. It also seeds the `CATEGORY`, `ITEM`, and `BILL` sequence rows at `1`.

`src/main/resources/entity-details.sql` remains an unconfigured reference SQL file. There is no `schema.sql`, `data.sql`, or Spring SQL-initialization setting that executes it at startup.

Current repository/entity details:

- `CategoryEntity`, `ItemEntity`, `BillEntity`, and `BillItemEntity` inherit audit fields and an optimistic-lock `version` from `BaseEntity`.
- `CodeSequenceEntity` stores named sequences and does not inherit `BaseEntity`.
- `CodeSequenceRepository.findForUpdate` applies a pessimistic write lock. `SequenceService` requires the exact names `CATEGORY`, `ITEM`, and `BILL` to exist.
- Current entity annotations define the core uniqueness and foreign-key intent; `entity-details.sql` also declares indexes.

## CURRENT migration approach

Flyway is the authoritative schema-history mechanism. New MySQL databases apply `V1__initial_current_schema.sql` before Hibernate validates the mapping. Existing databases must be inspected and explicitly baselined at version `1` only after confirming they match the migration; any verified drift must be corrected with a forward-only migration that preserves data and current sequence values.

## PLANNED

The product will need reliable relational persistence for catalog, parties, inventory, transactional documents, payments, tax, and reporting. This is not yet a defined target schema.

## PROPOSED persistence direction

Each future migration should be reviewed for backward compatibility, data preservation, constraints, indexes, decimal precision/scale, and rollback/forward remediation. Keep Hibernate validation explicit per environment; do not return to `ddl-auto: update` as a production schema-change mechanism.

Use database transactions and locking/versioning for the invariants they actually protect. Maintain sequence data and document-number uniqueness as deployment concerns, not just development fixtures.
