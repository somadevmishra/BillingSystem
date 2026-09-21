# ADR 0001: Start with a Modular Monolith

- **Status:** Accepted
- **Date:** 2026-09-12
- **Decision owners:** Project stakeholders (product direction supplied for this repository)

## Context

The existing application is one Kotlin/Spring Boot deployable containing catalog and billing prototype functionality. The product is planned to grow into a small-business business-management platform, first for Shibansh Scientific, with future domains such as inventory, purchasing, sales, payments, GST, reporting, and analysis.

The product needs clear domain boundaries as it grows, but the current repository contains no demonstrated need for independently deployable services, distributed data ownership, asynchronous integration infrastructure, or operational complexity associated with microservices.

## Decision

Start and continue as a **modular monolith**: one deployable Kotlin/Spring Boot application with domain-oriented internal boundaries. Keep the current primary stack (Kotlin, Spring Boot, Java 21, Spring MVC, Spring Data JPA) unless a future approved decision changes it.

Domains will be introduced incrementally around approved use cases. Boundaries should make ownership and dependencies explicit, while allowing local transactions and simpler deployment. This decision does not prescribe a speculative package migration; it governs the direction of new and materially changed code.

Microservices, external messaging, independently deployed modules, and distributed transactions require explicit approval and a replacement or follow-on ADR.

## Consequences

### Positive

- One build, deployment, database integration point, and operational surface at the current stage.
- Straightforward local transactions for catalog, inventory, documents, and financial rules as they emerge.
- Domain boundaries can be improved without prematurely paying distributed-system costs.
- The team can focus on correct business workflows and data quality for the first customer context.

### Constraints and trade-offs

- Module boundaries must be actively maintained in code; a monolith does not enforce them automatically.
- A poorly designed shared data model could create coupling, so cross-domain entity/repository access must be deliberate.
- Future scaling or team autonomy needs may justify a different architecture, but only when supported by concrete evidence and an approved decision.

## Alternatives considered

1. **Microservices from the outset:** rejected for now because the repository and approved requirements do not establish independent deployment, scale, or ownership needs that justify their cost.
2. **Keep an unstructured layered monolith indefinitely:** rejected as the target because the planned capability scope needs clearer domain ownership than broad shared technical packages provide.

## Related documents

- [Architecture](../ARCHITECTURE.md)
- [Project Baseline](../PROJECT.md)
- [Roadmap](../ROADMAP.md)
