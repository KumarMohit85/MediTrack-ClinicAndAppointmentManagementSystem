# System Architecture

## 1. Architectural style
MediTrack uses a simple **layered object-oriented architecture** suitable for a console application.

```text
+------------------------------------------------+
|              Presentation / CLI                |
|                 Main / Menu                    |
+------------------------+-----------------------+
                         |
+------------------------v-----------------------+
|                    Services                    |
| PatientService | DoctorService | Appointment  |
| BillService                                  |
+------------------------+-----------------------+
                         |
+------------------------v-----------------------+
|                     Entities                   |
| Person | Patient | Doctor | Appointment | Bill |
+------------------------+-----------------------+
                         |
+------------------------v-----------------------+
| Utilities / Patterns / Persistence              |
| Validator | DateUtil | CSVUtil | DataStore     |
| IdGenerator | AIHelper | Factory | Strategy    |
+------------------------------------------------+
```

## 2. Package responsibilities

### `entity`
Represents domain objects and their state/behavior.

### `service`
Contains application operations and business coordination. Services use collections to manage entities.

### `interfaces`
Defines contracts such as `Searchable` and `Payable`.

### `pattern.factory`
Contains the factory used to choose the required billing strategy/type.

### `pattern.strategy`
Contains interchangeable billing algorithms.

### `util`
Cross-cutting helpers for validation, dates, persistence, generic storage, ID generation and recommendations.

### `exception`
Represents application-specific failure conditions.

### `constants`
Centralized application constants such as tax rate.

## 3. Dependency direction
The CLI should call services rather than directly implementing business rules. Services operate on entities and use utilities/patterns where required.

```text
User
 |
v
CLI
 |
v
Service
 | | +--> Validator / DateUtil / DataStore / CSVUtil
 |
 +----> Entity
 |
 +----> Factory ----> Strategy
```

## 4. Design principle
`Main` should remain an entry point, while menu/controller code handles input/output and service classes handle business operations. This keeps the code easier to test and explain.
