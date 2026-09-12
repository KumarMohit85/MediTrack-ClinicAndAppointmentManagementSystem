# JVM Report

## 1. Introduction
Java follows the **Write Once, Run Anywhere (WORA)** model. Java source code is compiled into platform-independent bytecode (`.class`). The JVM for a particular operating system executes that bytecode.

```text
Java Source (.java)
       |
       | javac
       v
Bytecode (.class)
       |
       | JVM
       v
Native machine instructions
       |
       v
Operating System / Hardware
```

## 2. Class Loader Subsystem
The Class Loader loads required classes into JVM memory.

### Main stages
1. **Loading** – finds and loads the class bytecode.
2. **Linking**
   - Verification checks bytecode validity and safety.
   - Preparation allocates memory for static fields and prepares default values.
   - Resolution converts symbolic references into direct references when required.
3. **Initialization** – executes class initialization logic, including static initialization blocks and static field initializers.

For MediTrack, classes such as `Main`, `Patient`, `Doctor`, `Appointment`, service classes and utility classes are loaded as they become required.

## 3. JVM Runtime Data Areas

### Method Area / Metaspace
Stores class-level information such as class metadata, method information and runtime constant-pool information. Modern HotSpot JVMs use native-memory Metaspace for class metadata.

### Heap
Stores objects created with `new`, for example `Patient`, `Doctor`, `Appointment` and `Bill` instances. Garbage collection reclaims objects that are no longer reachable.

### Java Stack
Each thread has its own stack. Method calls create stack frames containing local variables, operand-stack data and return information.

### PC Register
Each JVM thread has a program counter register indicating the current instruction position for that thread.

### Native Method Stack
Supports execution of native methods implemented outside Java.

## 4. Execution Engine
The execution engine runs bytecode.

```text
             Bytecode
                |
       +--------+--------+
       |                 |
 Interpreter          JIT Compiler
       |                 |
 Executes quickly     Compiles hot code
 one instruction      to optimized native
 at a time            machine code
       |                 |
       +--------+--------+
                |
          CPU execution
```

### Interpreter
Starts executing bytecode quickly, but repeatedly interpreting hot code can be slower.

### JIT Compiler
The **Just-In-Time compiler** identifies frequently executed (“hot”) code and compiles it into optimized native machine code. Subsequent executions can run much faster.

## 5. Garbage Collection
Java automatically manages heap memory. When objects are no longer reachable, a garbage collector can reclaim their memory. This reduces manual memory-management errors compared with languages requiring explicit deallocation.

## 6. JIT vs Interpreter

| Aspect | Interpreter | JIT |
|---|---|---|
| Execution | Bytecode instruction by instruction | Compiles frequently used bytecode |
| Startup | Usually fast | Compilation adds runtime work |
| Long-running performance | Lower for hot code | Usually higher for hot code |
| Optimization | Limited | Runtime/profile-guided optimization |

## 7. Why WORA works
Java bytecode is designed to run on a JVM specification rather than directly on one operating system's CPU instruction set. Therefore the same compiled Java application can run on Windows, Linux or macOS when a compatible JVM is installed.

The practical chain is:

```text
Same .class bytecode
       |
 +-----+------+------+
 |            |      |
JVM/Win     JVM/Linux JVM/macOS
 |            |      |
Windows     Linux   macOS
```

## 8. MediTrack relevance
The application uses standard Java APIs such as collections, `java.time`, exceptions, streams/lambdas and concurrency utilities. These are executed by the JVM and therefore benefit from Java's portability and managed runtime model.
