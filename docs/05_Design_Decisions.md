# Design Decisions

## 1. Encapsulation
Entity fields are private. Access occurs through methods. Validation is centralized in `Validator` so invalid input does not need to be checked independently in every menu branch.

## 2. Inheritance
`Person` contains common identity/contact data. `Doctor` and `Patient` extend it because both are people but have different domain-specific fields.

## 3. Abstraction
`MedicalEntity` provides a common abstraction for medical-domain objects. `Searchable` and `Payable` define behavior contracts.

## 4. Polymorphism
Patient search demonstrates method overloading:

```text
searchPatient(String)  -> ID/name search
searchPatient(int)     -> age search
```

Billing demonstrates runtime polymorphism because `BillService` can operate through the `BillingStrategy` interface while the concrete strategy determines the calculation.

## 5. Deep vs shallow copy
`super.clone()` performs field-wise copying and is therefore shallow for object references. If an object contains a mutable nested collection, the clone must receive a new collection to satisfy deep-copy semantics.

For example:

```text
Shallow copy:
Patient A ----> MedicalHistory List <---- Patient B

Deep copy:
Patient A ----> MedicalHistory List A
Patient B ----> MedicalHistory List B
```

## 6. Immutable BillSummary
`BillSummary` represents a snapshot of billing results. It is designed with final state and no setters so callers cannot mutate an already-created summary. Immutable snapshots are easier to reason about and naturally safer to share across threads.

## 7. Enums
Enums prevent arbitrary strings from representing fixed states/categories:
- `AppointmentStatus`: lifecycle state.
- `Specialization`: doctor specialty.
- `BillType`: billing strategy selection.

## 8. Collections
Maps provide efficient ID-based lookup; lists support ordered collections and iteration. Streams are used where they make filtering, sorting and aggregation clearer.

## 9. Generic DataStore
`DataStore<T>` demonstrates reusable generic storage rather than writing separate storage classes for Patient, Doctor and Appointment.

## 10. Factory Pattern
`BillFactory` centralizes the choice of concrete billing strategy. Client code does not need a chain of object-construction conditionals throughout the application.

## 11. Strategy Pattern
Billing algorithms vary by billing type. Strategy allows the algorithm to change independently of the billing service.

```text
BillService
    |
    v
BillingStrategy
  /   |    Std Premium Insurance
```

## 12. Thread-safe ID generation
`AtomicInteger` provides atomic counter updates. This matters if multiple threads request IDs concurrently because an ordinary read-increment-write sequence can suffer from race conditions.

## 13. Persistence
CSV is intentionally simple for a teaching project. It demonstrates file I/O, conversion between objects and text, and try-with-resources. A production system would require stronger serialization rules, escaping/quoting support, database transactions and concurrency controls.

## 14. Rule-based AI helper
The recommendation feature is deliberately rule-based: symptoms are mapped to likely specializations and matching doctors are returned. This demonstrates an “AI-style” decision feature without adding an unnecessary ML dependency.
