# Final Submission Checklist

## Code
- [ ] `Main.java` contains the executable entry point.
- [ ] Package structure matches the assignment.
- [ ] Patient and Doctor CRUD works.
- [ ] Appointment creation/view/cancel works.
- [ ] Billing works.
- [ ] Search overload is demonstrated.
- [ ] Enums are used for fixed states/categories.
- [ ] Cloneable/deep-copy requirement is demonstrated.
- [ ] `BillSummary` is immutable.
- [ ] Custom exceptions are used.
- [ ] Collections and generics are demonstrated.
- [ ] Streams/lambdas are demonstrated if claiming that bonus.
- [ ] Persistence is demonstrated if claiming the persistence bonus.
- [ ] `--loadData` works if claiming persistence support.
- [ ] Design patterns are actually used, not just declared.
- [ ] Thread-safe counter/concurrency example is functional if claiming it.
- [ ] Public APIs have meaningful Javadocs.

## Documentation
- [ ] Setup instructions included.
- [ ] JVM report included.
- [ ] Requirements traceability included.
- [ ] System architecture included.
- [ ] UML diagrams included.
- [ ] Application flowcharts included.
- [ ] Design decisions included.
- [ ] Test plan/results included.

## Repository hygiene
- [ ] No `.class` files or generated build folders unless intentionally required.
- [ ] No IDE-specific files that are not needed.
- [ ] No passwords, tokens or secrets.
- [ ] README explains how to compile/run.
- [ ] Git status is clean before final push.

## Viva preparation
Be able to explain:
1. Why `Person` is abstract.
2. Why `Doctor` and `Patient` inherit from `Person`.
3. Overloading vs overriding in this project.
4. Shallow vs deep cloning.
5. Why `BillSummary` is immutable.
6. Why `AtomicInteger` is used for IDs.
7. Factory vs Strategy Pattern.
8. Why `HashMap` is useful for ID lookup.
9. How streams/lambdas are used.
10. How JVM executes the compiled classes.
