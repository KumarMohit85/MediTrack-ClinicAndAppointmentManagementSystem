# Test Plan

## 1. Objective
Verify that core CRUD, search, appointment status, billing, cloning, validation, persistence, patterns and analytics behave as required.

## 2. Functional test cases
| ID | Test | Expected result |
|---|---|---|
| T01 | Add valid patient | Patient stored successfully |
| T02 | Add duplicate patient ID | Operation rejected |
| T03 | Search patient by ID | Matching patient returned |
| T04 | Search patient by name | Matching patient(s) returned |
| T05 | Search patient by age | Matching patient(s) returned |
| T06 | Update patient | Stored record updated |
| T07 | Delete patient | Patient removed |
| T08 | Add doctor | Doctor stored |
| T09 | Filter doctor by specialization | Correct doctors returned |
| T10 | Sort doctors by fee | Ascending fee order |
| T11 | Create appointment | Pending appointment created |
| T12 | Confirm pending appointment | Status becomes confirmed |
| T13 | Cancel pending appointment | Status becomes cancelled |
| T14 | Generate standard bill | Correct amount + tax |
| T15 | Generate premium bill | Premium strategy used |
| T16 | Generate insurance bill | Insurance strategy used |
| T17 | Patient clone | Clone has independent mutable nested state |
| T18 | Appointment clone | Clone does not unintentionally share mutable nested state |
| T19 | Invalid email/phone | Validation rejects bad data |
| T20 | Missing appointment | `AppointmentNotFoundException` path |
| T21 | Missing bill | `BillNotFoundException` path |
| T22 | Load persisted data | Saved records restored |
| T23 | Recommendation | Appropriate specialization/doctors suggested |
| T24 | Analytics | Stream/lambda result matches expected calculation |

## 3. Smoke test
`TestRun.java` should exercise a representative path across entities, services, patterns and utilities. A successful run should print a clear pass/fail summary and terminate without uncaught exceptions.

## 4. Regression test
After changes:
1. Delete the old `out` directory.
2. Recompile all source files.
3. Run the smoke test.
4. Start the CLI and manually exercise each top-level menu.
5. If persistence is implemented, run once to save data and again with `--loadData` to verify restoration.

## 5. Boundary tests
Test empty search, duplicate IDs, invalid ages, invalid dates, missing records, invalid bill types and cancelling/confirming appointments in invalid states.
