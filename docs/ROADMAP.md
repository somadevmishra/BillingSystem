# Roadmap

## CURRENT

There is no implemented roadmap mechanism, release plan, issue tracker, customer/vendor/inventory domain, or reporting/AI capability in this repository. The items below are planning direction, not delivered functionality or commitments.

## PLANNED direction

The intended product is a generic small-business platform, with Shibansh Scientific as the first real operating context. The architecture starts as a modular monolith.

## PROPOSED delivery sequence

### 1. Stabilize the prototype foundation

- Resolve the authoritative database schema and migration strategy.
- Align entities, the SQL reference, auditing/version columns, and required code-sequence initialization.
- Complete or remove unfinished public contracts such as bill update.
- Normalize API DTO boundaries and error behavior.
- Establish meaningful tests for calculations, lifecycle transitions, validation, and persistence.

### 2. Establish core master data

- Evolve catalog behavior for real product needs without prematurely abstracting it.
- Add approved customer and vendor capabilities.
- Confirm Shibansh Scientific-specific product, pricing, tax, and party-data needs.

### 3. Build inventory and purchasing workflows

- Define stock movement and availability rules.
- Add purchase documents, vendor obligations, and purchase-return behavior.
- Make inventory effects traceable and testable.

### 4. Build sales and financial workflows

- Define quotations, sales/invoices, sales returns, and their relation to the existing bill prototype.
- Add payments, allocations, receivables, payables, and approved GST workflows.
- Strengthen audit trails, numbering, document states, and reporting prerequisites.

### 5. Reporting and profitability

- Deliver operational and financial reports from validated transaction data.
- Define profitability calculations and their inputs before presenting them as authoritative.

### 6. Intelligence and automation

- Add demand/seasonal analysis and purchase recommendations only once sufficiently reliable historical data exists.
- Add automation and AI-assisted analysis behind explicit human review and auditability controls.

## Decision gates

Before advancing between these areas, confirm actual operating requirements, API and data contracts, migration impact, test coverage, and whether new abstractions or infrastructure are justified. Microservices remain out of scope unless explicitly approved.
