# Project Baseline

## Purpose and status labels

This document records the repository baseline as of 2026-09-12. It is intentionally separate from the product direction.

- **CURRENT** means verified from tracked application files and configuration.
- **PLANNED** means approved product direction supplied for this project, but not implemented in the repository.
- **PROPOSED** means an engineering recommendation that requires a future decision or implementation.

## CURRENT

The repository is a Kotlin Spring Boot backend named `billing` in `application.yaml`. The application root is `com.manual.billing` and `BillingApplication.kt` starts Spring Boot.

Build and runtime baseline:

| Concern | Repository evidence |
| --- | --- |
| Language/runtime | Kotlin 2.1.21 and Java toolchain 21 |
| Framework | Spring Boot 3.5.0 |
| Build | Gradle Kotlin DSL; wrapper 9.4.1 |
| Web/API | Spring MVC, Jakarta Validation, Jackson Kotlin |
| Persistence | Spring Data JPA, MySQL connector at runtime |
| API documentation | springdoc OpenAPI Web MVC UI 2.8.9 and custom API metadata |
| Operations | Spring Boot Actuator dependency |
| Tests | Spring Boot test, JUnit 5, MockK, Kotlin test, H2, Kover |
| Server | Port 9080 |

The source tree has controllers, services and implementations, repositories, JPA entities, DTOs, mappers, a calculation engine, enums, configuration, and exception handling. Its implemented REST resources are `/api/categories`, `/api/items`, and `/api/bills`.

The current application covers a small catalog and bill lifecycle: categories, items, open bills, bill lines, finalization/payment status, and cancellation. The repository has no frontend source, static web assets, or server-side templates.

## PLANNED

The product is evolving from the original Cafe Billing prototype into a generic small-business business-management platform. The first real business target is **Shibansh Scientific**, a scientific and laboratory supplies distribution business.

The eventual capability scope is products/items, categories, customers, vendors, inventory, purchases and returns, sales and returns, quotations, billing/invoicing, payments, GST, receivables/payables, reporting, profitability, business intelligence, demand/seasonal analysis, purchase recommendations, automation, and AI-assisted business analysis.

## PROPOSED

Use the current billing prototype as a learning baseline rather than as proof that the broader product exists. Deliver concrete operational domains in increments, preserving generic concepts where they are naturally shared across small businesses and avoiding abstractions that do not yet serve an approved use case.

See `ARCHITECTURE.md`, `DOMAIN-MODEL.md`, and `ROADMAP.md` for the intended evolution.
