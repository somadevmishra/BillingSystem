# Domain Model

## CURRENT model

The current persistence model is limited to the following concepts.

| Concept | Current fields/relationships | Current role |
| --- | --- | --- |
| Category | Generated ID, unique code, name, description, display order, active flag | Optional catalog grouping; items may reference it |
| Item | Generated ID, unique item code, name, description, optional category, unit price, GST percentage, display order, active flag | Sellable catalog record |
| Bill | Bill number, date, status, totals, customer name/mobile text, payment fields, cancellation/finalization fields | An open, finalized, or cancelled transaction document |
| Bill item | Parent bill, referenced item, item-code/name snapshots, quantity, unit price, GST, discount, and line totals | Historical line on a bill |
| Code sequence | Named next numeric value | Generates `CAT-n`, `ITM-n`, and `BIL-n` codes |
| Base entity | Created/updated timestamps, created/updated by, optimistic-lock version | Inherited audit/concurrency fields for most entities |

The category-to-item relationship is optional many-to-one. Bill-to-bill-item is one-to-many with cascade and orphan removal. A bill item also references an item, while storing the item’s code and name and monetary details as a snapshot.

`BillStatus` is `OPEN`, `FINALIZED`, or `CANCELLED`. `PaymentMode` is `CASH`, `CARD`, `UPI`, or `WALLET`; `PaymentStatus` is `PENDING`, `PARTIAL`, or `PAID`.

Customer information is currently copied as optional text fields on a bill; there is no customer entity. There is no vendor, inventory, purchase, return, quotation, GST registration, payment allocation, ledger, report, or analytics model.

## PLANNED model scope

The platform is expected to add first-class business concepts for products, categories, customers, vendors, inventory, purchase documents and returns, sales documents and returns, quotations, invoices/bills, payments, GST, receivables/payables, reporting, profitability, and business intelligence.

## PROPOSED modelling principles

- Separate reusable master data (for example, product and party) from immutable or snapshot-bearing transaction lines.
- Model inventory changes as traceable movements when inventory is introduced; do not derive stock solely from a mutable item record.
- Keep purchase, sale, and return semantics explicit; do not collapse them into one document merely because their fields overlap.
- Define how payments allocate to receivables/payables before treating a payment amount as a settled document balance.
- Treat GST rules, registrations, document numbering, tax rates, and rounding as domain rules that need explicit approved requirements.
- Introduce the minimum fields and relationships needed for a real Shibansh Scientific workflow before attempting cross-business generalization.
