# Business Rules

## CURRENT: catalog

- A category has a generated code in the form `CAT-<number>` and a unique database `code`.
- Category creation rejects a duplicate name case-insensitively. It trims the name, assigns the generated code, and persists display order and description.
- Category creation accepts an `active` request field but currently does not apply it; new categories use the entity default of `true`.
- Updating a category rejects a case-insensitive duplicate name except for the category’s unchanged name.
- Deleting a category is a soft delete: it sets `active` to `false`.
- The category list endpoint returns only active categories ordered by display order then name. Individual lookup can return an inactive category.
- An item has a generated `ITM-<number>` code, name, optional category, unit price, GST percentage, display order, and active flag.
- Item creation may resolve a category by category code. It does not verify that the category is active.
- Item update currently accepts a `CategoryEntity` in its request and assigns it directly. This is current behavior, not a recommended public API contract.
- Deleting an item is a soft delete. The item list currently returns all items, including inactive ones.

## CURRENT: bills and payments

- Creating a bill generates a `BIL-<number>` bill number, uses the current local date/time, starts in `OPEN`, and initializes all totals to zero.
- An open bill may add, update, or remove bill lines. Finalized and cancelled bills cannot be modified.
- Adding a bill line takes an item ID and a quantity of at least `0.001`. The line snapshots the item code, name, unit price, and GST percentage at creation.
- Line subtotal is `unitPrice × quantity`; line tax is `subtotal × gstPercentage / 100`; line total is `subtotal + tax − lineDiscount`.
- Bill subtotal and tax are sums of the corresponding line values. Grand total is `subtotal + tax − line discounts − bill discount`.
- Finalizing a bill recalculates totals, records payment mode and amount paid, derives pending/partial/paid status from payment compared to grand total, marks the bill `FINALIZED`, and records a local finalization time.
- The current implementation permits finalizing an empty bill and permits payment greater than the grand total, which produces a negative balance. It does not apply a rounding policy beyond `BigDecimal` arithmetic.
- A cancelled bill cannot be cancelled again. A finalized bill cannot be cancelled. Cancelling records the supplied reason and cancellation time; the `cancelledBy` field is not set by current code.
- `BillService.update` exists as an interface method but is explicitly unimplemented, and there is no HTTP endpoint for it.

## CURRENT: errors and auditing

- Missing categories/items in category/item services use `ResourceNotFoundException` and become HTTP 404.
- Missing bills use `EntityNotFoundException` and become HTTP 404.
- Missing bill items and a missing item while adding to a bill currently throw generic `RuntimeException`; the global handler treats unexpected exceptions as HTTP 500.
- The auditing configuration supplies `SYSTEM` as the auditor. Entities inherit created/updated audit fields and optimistic locking, subject to database schema alignment.

## PLANNED

Rules for customers, vendors, stock, purchases, returns, quotations, GST, payments/allocations, receivables/payables, reporting, profitability, and analysis are not yet defined in the codebase. They must be specified and approved before implementation.

## PROPOSED rule-design priorities

For each new transaction domain, define document states, permitted transitions, quantities, stock effects, taxes, rounding, numbering, cancellation/return behavior, auditability, and authorization before writing persistence or controller code. Ensure rules reflect confirmed Shibansh Scientific operating workflows rather than assumed retail behavior.
