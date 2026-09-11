## Billing & Design Patterns

### Billing Design

The billing module is responsible for generating bills for completed appointments.

A Bill contains:
- Bill ID
- Appointment
- Amount
- Tax
- Total
- Generated timestamp

The Bill class stores the calculated billing information. The actual billing calculation is delegated to a BillingStrategy.

### Strategy Pattern

The Strategy Pattern is used because different billing types require different calculation rules.

The `BillingStrategy` interface defines the common billing operations:

- `calculateAmount(Appointment appointment)`
- `calculateTax(double amount)`
- `getStrategyName()`

The following strategies implement the interface:

- `StandardBillingStrategy`
- `InsuranceBillingStrategy`
- `PremiumBillingStrategy`

Each strategy provides its own implementation of `calculateAmount()`, while the common tax calculation is reused.

This allows the billing algorithm to change without modifying `BillService`.

#### Billing Assumptions

The assignment does not specify exact formulas for insurance and premium billing. Therefore, the project uses the following assumptions:

- Standard billing: consultation fee is used as the billing amount.
- Insurance billing: 20% of the consultation fee is assumed to be covered by insurance.
- Premium billing: a ₹500 premium service charge is added to the consultation fee.
- Tax is calculated at the configured tax rate.

These values are project assumptions and can be changed without modifying the overall billing architecture.

### Factory Pattern

`BillFactory` is responsible for creating the appropriate `BillingStrategy` based on the selected `BillType`.

The available bill types are:

- `STANDARD`
- `INSURANCE`
- `PREMIUM`

For example, when `BillType.INSURANCE` is provided, the factory returns an `InsuranceBillingStrategy`.

This keeps object creation separate from the billing service.

### Runtime Polymorphism

`BillService` works with the `BillingStrategy` interface rather than directly depending on a concrete strategy.

For example:

```java
BillingStrategy strategy = BillFactory.createStrategy(billType);