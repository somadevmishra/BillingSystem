# Product Vision

## CURRENT

The repository describes itself only as a billing system in its README. Its implemented backend exposes categories, items, and bill operations. The OpenAPI configuration calls the API a “Cafe Billing System API” and refers to cafe billing and inventory management; this is prototype terminology, not evidence of implemented inventory or a generic platform.

No customer, vendor, inventory, purchasing, quotation, returns, reporting, forecasting, automation, or AI-assisted capability is implemented in the present codebase.

## PLANNED

Build a generic business-management platform for small businesses, first serving Shibansh Scientific, a scientific and laboratory supplies distribution business.

The product should enable an operator to manage a trustworthy catalog, trading partners, stock, purchase and sales flows, bills/invoices, payments, GST obligations, receivables/payables, and decision-support reporting. Over time it should help users understand profitability and demand, recommend purchases, automate routine work, and offer AI-assisted analysis.

The target is broad enough to support different small-business contexts, but the first workflows and language should be grounded in the needs of a scientific/laboratory supplies distributor. Generic design must be earned by real workflows, not assumed in advance.

## PROPOSED product principles

- **Operational accuracy first:** stock, tax, money, outstanding balances, and historical documents must be reliable before intelligence features are added.
- **Traceable transactions:** purchases, sales, returns, payment allocations, and changes to financial state should be auditable.
- **Generic core, contextual vocabulary:** use reusable concepts such as product, party, document, stock movement, and tax treatment only where they reduce genuine duplication; retain business-specific fields when required.
- **Incremental adoption:** a small business should gain value from catalog and transaction flows before using advanced reporting or AI.
- **Human-accountable assistance:** future automation and AI may recommend or summarize; they must not silently alter financial, inventory, or tax records without an approved human-controlled workflow.

## Non-goals for the current stage

Microservices, multi-tenant deployment, AI agents, forecasting, purchase recommendation engines, and a full frontend are not present and are not implied by this vision. They require separate approved design and implementation work.
