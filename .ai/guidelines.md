# 🍃 Spring Boot & Java Development Guidelines

This document defines the architectural standards and coding patterns for this repository. Follow these guidelines for
all code generation, refactoring, and explanations.

---

## 🏗️ Architecture & Project Structure

### Layered Architecture

Controller → Service → Repository

- **Controllers**
    - Handle HTTP mapping and request validation only
    - ❌ No business logic
- **Services**
    - Contain all business logic
    - Define transaction boundaries using `@Transactional`
- **Repositories**
    - Use Spring Data JPA / Mongo interfaces
    - Avoid custom implementations unless absolutely necessary

---

### Package Structure

- Prefer **feature-based packaging**:

```
com.example.feature.user
```

instead of:

```
com.example.controller
```

---

### DTOs

- Use **Java Records** for DTOs
- ❌ Never expose Entities directly to the API layer

---

## ☕ Java & Spring Standards

- **Version Requirements**
    - Java 25+
    - Spring Boot 4.0.x

### Language Features

- Use **Records** for immutable data
- Use **Pattern Matching** (`switch`, `instanceof`)
- Use **Sequenced Collections** where applicable

### Dependency Injection

- ✅ Always use **Constructor Injection**
- ❌ Never use `@Autowired` on fields
- Mark injected dependencies as `final`

### Lombok Policy

- ✅ Allowed:
- `@Data`
- `@Builder`
- `@Slf4j`
- ❌ Avoid:
- `@AllArgsConstructor` on Entities
    - Prefer manual constructors or `@Builder`

---

## 🌐 REST API Design

### Naming

- Use plural nouns:

```
/api/orders
```

### Response Handling

- Always return:

```java
ResponseEntity<T>
```

### Validation

- Use `jakarta.validation`:
    - `@NotNull`
    - `@Size`
    - `@Email`
- Ensure `@Valid` is present in Controller methods

### Global Exception Handling

- Use:

    ```
    @RestControllerAdvice
    ```

- Maintain a consistent error response schema

---

## 💾 Persistence (JPA / Hibernate)

### Fetch Type

- Use `FetchType.LAZY` for:
    - `@OneToMany`
    - `@ManyToMany`

### Querying

- Prefer **Derived Query Methods**
- Use `@Query` (JPQL) for complex queries

### Auditing

- Use:
    - `@CreatedDate`
    - `@LastModifiedDate`
- Via Spring Data Envers or JPA Auditing

---

## 🧪 Testing Strategy

### Unit Tests

- JUnit 5
- Mockito

### Slice Tests

- Controller:

    ```
    @WebMvcTest
    ```

- Repository:

    ```
    @DataJpaTest
    ```

  (H2 or Testcontainers)

### Integration Tests

```
@SpringBootTest
@ActiveProfiles("test")
```

### Assertions

- Use AssertJ:

    ```
    assertThat(...)
    ```

---

## ⚙️ Configuration & Security

### Configuration

- Use:

    ```
    application.yml
    ```

### Secrets

- ❌ Never hardcode credentials
- Use:

    ```
    ${ENVIRONMENT_VARIABLE}
    ```

### Security

- Default: **Deny All**
- Use:

    ```
    @PreAuthorize
    ```

---

## 🧠 Junie Tip

- Flag violations as **Legacy Pattern**
- Suggest refactoring to align with these guidelines