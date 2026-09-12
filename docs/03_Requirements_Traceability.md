# Requirements Traceability Matrix

This document maps the assignment requirements to the expected project artifacts. It should be used as the final submission checklist.

| Requirement | Evidence / Artifact |
|---|---|
| Java setup and JVM understanding | `docs/01_Setup_Instructions.md`, `docs/02_JVM_Report.md` |
| Base package `com.airtribe.meditrack` | `src/main/java/com/airtribe/meditrack` |
| Person, Doctor, Patient hierarchy | `entity/Person.java`, `Doctor.java`, `Patient.java` |
| Appointment and status | `entity/Appointment.java`, `AppointmentStatus.java` |
| Bill and immutable BillSummary | `entity/Bill.java`, `BillSummary.java` |
| Encapsulation | Private fields + public methods in entities/services |
| Inheritance | `Person` → `Doctor`, `Patient` |
| Polymorphism / overloading | Patient search by `String` and `int`; interface-based strategy use |
| Abstraction | Abstract `MedicalEntity`, interfaces `Searchable` and `Payable` |
| Deep vs shallow cloning | `Patient` and `Appointment` cloning implementation + design documentation |
| Immutability | `BillSummary` final state/no setters |
| Enums | `Specialization`, `AppointmentStatus`, `BillType` |
| Static members / initialization | `Constants`, `IdGenerator`, static configuration/counters |
| Collections | `ArrayList`, `HashMap`, lists/maps in services |
| Generics | `DataStore<T>` |
| Iterator | Service deletion / collection traversal where applicable |
| equals/hashCode | Entity identity/value semantics |
| Custom exceptions | Appointment/Bill not-found and invalid-data exceptions |
| Patient CRUD | `PatientService` + CLI |
| Doctor CRUD | `DoctorService` + CLI |
| Appointment CRUD/status flow | `AppointmentService` + CLI |
| Billing | `BillService`, `BillFactory`, billing strategies |
| Strategy Pattern | `BillingStrategy` + Standard/Premium/Insurance implementations |
| Factory Pattern | `BillFactory` |
| Singleton requirement | Shared configuration/ID-generation component as documented |
| File I/O / persistence bonus | `CSVUtil`, persistence coordinator and `--loadData` flow when enabled |
| AI bonus | `AIHelper` rule-based doctor recommendation |
| Streams/Lambdas bonus | Filtering, sorting, averaging and analytics in services |
| Concurrency | `AtomicInteger` for thread-safe ID generation |
| CLI | `Main` and menu/controller layer |
| Manual testing | `TestRun.java` / test plan |
| Javadocs | Public classes/methods should include meaningful Javadoc in final source |
| Git | `.gitignore`, commits and repository history |

## Bonus selection
The project is designed to support all four optional areas where implemented:
1. File I/O & Persistence
2. Design Patterns
3. AI feature
4. Streams + Lambdas

This exceeds the minimum “choose any two” bonus selection. The final code should be checked against the exact rubric before claiming a bonus item as complete.
