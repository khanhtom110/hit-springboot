# Buổi 8: Testing trong Spring Boot — Unit Test, MockMvc, Repository Test & Integration Test với H2

---

## Mục tiêu buổi học

- Hiểu **Testing Pyramid** và biết khi nào dùng Unit Test, Slice Test, Integration Test.
- Viết **Unit Test** cho Service bằng JUnit 5, Mockito, AssertJ.
- Viết **Repository Test** bằng `@DataJpaTest` với database H2.
- Viết **Controller Test** bằng `MockMvc` và `@WebMvcTest`.
- Viết **Integration Test** bằng `@SpringBootTest` + `@AutoConfigureMockMvc`.
- Tách cấu hình test bằng `application-test.properties`, không phụ thuộc MySQL thật.
- Biết cách test API có Security/JWT mà không cần gọi hệ thống thật.
- Chạy toàn bộ test bằng Maven và đọc kết quả test report.

> **Lưu ý:** Buổi 7 đã xây dựng base project có Spring Security, JWT, Controller, Service, Repository. Buổi 8 dùng lại project đó và bổ sung test mẫu trong `lession 8/base`.

---

## I. Testing Pyramid — Test cái gì và test ở tầng nào?

Trong Spring Boot, không phải test nào cũng cần chạy toàn bộ application. Test càng nhỏ thì càng nhanh, dễ debug, ít phụ thuộc môi trường.

```
        Integration Test
      /                  \
     /   Controller Test  \
    /----------------------\
   / Repository / Slice Test \
  /--------------------------\
 /        Unit Test           \
```

| Loại test | Annotation thường dùng | Mục tiêu | Tốc độ |
|---|---|---|---|
| Unit Test | `@ExtendWith(MockitoExtension.class)` | Test 1 class độc lập | Nhanh nhất |
| Controller Test | `@WebMvcTest`, `MockMvc` | Test request/response, validation, status code | Nhanh |
| Repository Test | `@DataJpaTest` | Test JPA query, mapping Entity, constraint DB | Trung bình |
| Integration Test | `@SpringBootTest` | Test nhiều tầng chạy cùng nhau | Chậm nhất |

**Nguyên tắc chọn test:**

| Cần kiểm tra | Nên dùng |
|---|---|
| Logic if/else trong Service | Unit Test |
| Controller validate request đúng không | MockMvc + `@WebMvcTest` |
| Repository method có query đúng không | `@DataJpaTest` + H2 |
| Login thật đi qua Controller → Service → Repository → H2 | `@SpringBootTest` |
| Security rule cho endpoint | MockMvc + Spring Security Test |

> **Sai lầm thường gặp:** Chỉ dùng `@SpringBootTest` cho mọi thứ. Cách này chạy chậm, khó tìm lỗi và dễ phụ thuộc database thật.

---

## II. Dependencies cho Testing

Trong `pom.xml`, cần có các dependency test sau:

```xml
<!-- JUnit 5, Mockito, AssertJ, Spring Test, MockMvc... -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Test các API có Spring Security -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- In-memory database cho repository/integration test -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
```

### Vì sao dùng H2?

H2 là database chạy trong memory, phù hợp cho test:

- Không cần cài MySQL/PostgreSQL.
- Mỗi lần test có thể tạo database sạch.
- Chạy nhanh, độc lập với dữ liệu local.
- Dễ dùng trong CI/CD.

> **Lưu ý thực tế:** H2 không thay thế hoàn toàn MySQL/PostgreSQL. Với query phức tạp hoặc native SQL đặc thù DB, vẫn cần test trên database thật ở môi trường integration/staging.

---

## III. Cấu hình profile test với H2

Tạo file:

```text
src/test/resources/application-test.properties
```

Ví dụ cấu hình trong `lession 8/base`:

```properties
spring.config.import=

spring.datasource.url=jdbc:h2:mem:lesson8_test;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;NON_KEYWORDS=USER
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect

jwt.secret=lesson8-test-secret-key-lesson8-test-secret-key
jwt.access.expiration_time=3600000
jwt.refresh.expiration_time=259200000
```

Giải thích nhanh:

| Cấu hình | Ý nghĩa |
|---|---|
| `jdbc:h2:mem:lesson8_test` | Tạo DB H2 trong RAM |
| `MODE=MySQL` | H2 cố gắng tương thích cú pháp MySQL |
| `NON_KEYWORDS=USER` | Tránh lỗi vì bảng tên `user` có thể trùng keyword |
| `ddl-auto=create-drop` | Tạo schema khi test start, xóa khi test kết thúc |
| `spring.config.import=` | Không phụ thuộc file `.env` khi chạy test |

Khi test class dùng profile test:

```java
@ActiveProfiles("test")
class SomeTest {
}
```

---

## IV. Unit Test Service với JUnit 5 + Mockito

Unit test không khởi động Spring context. Ta mock các dependency và chỉ test logic của class cần kiểm tra.

Ví dụ: `UserServiceImplTest`

```java
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  UserRepository userRepository;

  @Mock
  UserMapper userMapper;

  @Mock
  PasswordEncoder passwordEncoder;

  @InjectMocks
  UserServiceImpl userService;

  @Test
  void createUser_shouldEncodePasswordAndSaveUser() {
    // given
    CreateUserRequestDto request = new CreateUserRequestDto(
        "lesson8", "lesson8@example.com", "Password123",
        "Lesson", "Eight", Role.USER);

    User mappedUser = User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(request.getPassword())
        .role(request.getRole())
        .build();

    when(userRepository.existsUserByEmail(request.getEmail())).thenReturn(false);
    when(userRepository.existsUserByUsername(request.getUsername())).thenReturn(false);
    when(userMapper.createUserRequestDtoToUser(request)).thenReturn(mappedUser);
    when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");

    // when
    userService.createUser(request);

    // then
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCaptor.capture());
    assertThat(userCaptor.getValue().getPassword()).isEqualTo("encoded-password");
  }
}
```

### Given — When — Then

Một test nên đọc theo 3 phần:

| Phần | Ý nghĩa |
|---|---|
| Given | Chuẩn bị dữ liệu, mock behavior |
| When | Gọi method cần test |
| Then | Assert kết quả, verify interaction |

### Mockito hay dùng

| Cú pháp | Ý nghĩa |
|---|---|
| `@Mock` | Tạo object giả |
| `@InjectMocks` | Inject mock vào class cần test |
| `when(...).thenReturn(...)` | Định nghĩa behavior của mock |
| `verify(mock).method(...)` | Kiểm tra method đã được gọi |
| `never()` | Kiểm tra method không được gọi |
| `ArgumentCaptor` | Bắt object truyền vào mock để assert |

---

## V. Repository Test với `@DataJpaTest`

Repository test chỉ load phần JPA: Entity, Repository, DataSource, EntityManager.

Ví dụ: `UserRepositoryTest`

```java
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  UserRepository userRepository;

  @Test
  void findByUsername_shouldReturnUser() {
    User user = User.builder()
        .username("repository-test")
        .email("repository-test@example.com")
        .password("encoded-password")
        .role(Role.USER)
        .build();

    entityManager.persistAndFlush(user);

    assertThat(userRepository.findByUsername("repository-test"))
        .isPresent()
        .get()
        .extracting(User::getEmail)
        .isEqualTo("repository-test@example.com");
  }
}
```

### Vì sao dùng `@AutoConfigureTestDatabase(replace = NONE)`?

Mặc định `@DataJpaTest` có thể tự thay datasource bằng embedded database. Trong project này ta muốn dùng đúng URL H2 đã cấu hình trong `application-test.properties`, nên dùng:

```java
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
```

### TestEntityManager dùng để làm gì?

`TestEntityManager` giúp chuẩn bị dữ liệu test rõ ràng hơn:

```java
entityManager.persistAndFlush(user);
```

Sau đó repository method sẽ query trên dữ liệu đã insert.

---

## VI. Controller Test với MockMvc và `@WebMvcTest`

Controller test kiểm tra HTTP layer:

- URL đúng chưa?
- HTTP method đúng chưa?
- Request body validate đúng chưa?
- Response status đúng chưa?
- Response JSON đúng format chưa?

Ví dụ: `AuthControllerMockMvcTest`

```java
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class AuthControllerMockMvcTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockitoBean
  AuthService authService;

  @Test
  void login_whenRequestValid_shouldReturnToken() throws Exception {
    LoginResponseDto response = LoginResponseDto.builder()
        .accessToken("access-token")
        .refreshToken("refresh-token")
        .build();

    when(authService.authentication(any(LoginRequestDto.class))).thenReturn(response);

    LoginRequestDto request = new LoginRequestDto("lesson8", "Password123");

    mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("SUCCESS"))
        .andExpect(jsonPath("$.data.accessToken").value("access-token"));
  }
}
```

### Vì sao Controller Test mock Service?

Controller test không kiểm tra business logic. Service đã có unit test riêng.

Controller test chỉ cần biết:

```text
Request hợp lệ → Controller gọi Service → Response đúng JSON
Request sai validation → trả 400
```

> **Ghi chú:** Với Spring Boot mới, dùng `@MockitoBean`. Nếu project đang dùng Spring Boot cũ hơn, bạn có thể gặp tài liệu dùng `@MockBean`.

### `addFilters = false`

```java
@AutoConfigureMockMvc(addFilters = false)
```

Dùng khi muốn test controller/validation mà chưa cần test security filter. Với API có JWT, nếu bật filter, test phải chuẩn bị token hợp lệ.

---

## VII. Integration Test với `@SpringBootTest`

Integration test load gần như toàn bộ Spring context:

```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthIntegrationTest {
}
```

Ví dụ test login thật qua nhiều tầng:

```java
@BeforeEach
void setUp() {
  userRepository.deleteAll();
  userRepository.save(User.builder()
      .username("admin-test")
      .email("admin-test@example.com")
      .password(passwordEncoder.encode("AdminTest123"))
      .role(Role.ADMIN)
      .build());
}

@Test
void login_withSeededAdmin_shouldReturnJwtTokens() throws Exception {
  LoginRequestDto request = new LoginRequestDto("admin-test", "AdminTest123");

  mockMvc.perform(post("/api/v1/auth/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status").value("SUCCESS"))
      .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
      .andExpect(jsonPath("$.data.refreshToken").isNotEmpty());
}
```

Luồng test này đi qua:

```text
MockMvc
  → AuthController
  → AuthServiceImpl
  → UserRepository
  → H2 Database
  → JwtProvider
  → JSON Response
```

> Integration test nên ít hơn unit test vì chạy chậm hơn. Dùng cho các flow quan trọng như login, tạo đơn hàng, thanh toán, phân quyền.

---

## VIII. Test với Spring Security

Khi test API có Security, có 3 hướng phổ biến:

| Cách | Khi nào dùng |
|---|---|
| `addFilters = false` | Chỉ test controller/validation, bỏ qua security |
| `@WithMockUser` | Test endpoint cần quyền nhưng không cần JWT thật |
| Login thật lấy token | Integration test flow xác thực thật |

Ví dụ test endpoint cần role:

```java
@Test
@WithMockUser(authorities = "ROLE_ADMIN")
void adminApi_whenAdmin_shouldReturnOk() throws Exception {
  mockMvc.perform(get("/api/v1/admin/user/user-1"))
      .andExpect(status().isOk());
}
```

Ví dụ test chưa đăng nhập:

```java
@Test
void profile_whenNoToken_shouldReturnUnauthorized() throws Exception {
  mockMvc.perform(get("/api/v1/user/profile"))
      .andExpect(status().isUnauthorized());
}
```

> Với JWT filter custom, nếu muốn test token thật thì nên dùng integration test, tạo user trong H2, gọi login lấy token rồi dùng token đó gọi API cần bảo vệ.

---

## IX. Cấu trúc thư mục Project (Base)

```text
base/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/example/base/
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── PasswordEncoderConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java
│   │   │   │   └── UserController.java
│   │   │   ├── domain/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/example/base/
│       │   ├── BaseApplicationTests.java
│       │   ├── controller/
│       │   │   └── AuthControllerMockMvcTest.java
│       │   ├── integration/
│       │   │   └── AuthIntegrationTest.java
│       │   ├── repository/
│       │   │   └── UserRepositoryTest.java
│       │   └── service/
│       │       └── UserServiceImplTest.java
│       └── resources/
│           └── application-test.properties
```

---

## X. Chạy test

Trên Windows:

```bash
cd "lession 8/base"
./mvnw.cmd test
```

Hoặc:

```bash
mvn test
```

Kết quả mong đợi:

```text
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Test report nằm ở:

```text
target/surefire-reports/
```

---

## XI. Checklist viết test trong Spring Boot

| Checklist | Đã làm? |
|---|---|
| Unit test không load Spring context nếu không cần | ✅ |
| Service test mock Repository/Mapper/Encoder | ✅ |
| Repository test dùng H2, không dùng MySQL thật | ✅ |
| Controller test dùng MockMvc | ✅ |
| Validation lỗi trả đúng HTTP 400 | ✅ |
| Integration test dùng profile `test` | ✅ |
| Test không phụ thuộc `.env` thật | ✅ |
| Test data được tạo trong test, không phụ thuộc dữ liệu có sẵn | ✅ |

---

## Tổng Kết Buổi 8

| Chủ đề | Nội dung chính |
|---|---|
| Testing Pyramid | Test nhỏ nhiều, test lớn ít |
| Unit Test | JUnit 5 + Mockito, không cần Spring context |
| Service Test | Mock Repository/Mapper, assert business logic |
| Repository Test | `@DataJpaTest`, H2, `TestEntityManager` |
| Controller Test | `MockMvc`, `@WebMvcTest`, assert status + JSON |
| Integration Test | `@SpringBootTest`, chạy flow nhiều tầng |
| H2 Database | In-memory DB cho test, không phụ thuộc MySQL |
| Security Test | Bỏ filter, mock user, hoặc login thật tùy mục tiêu |
| Maven Test | `mvn test`, đọc report trong `target/surefire-reports` |

---

## Câu Hỏi Ôn Tập

1. Testing Pyramid là gì? Tại sao không nên dùng `@SpringBootTest` cho mọi test?
2. Unit test khác integration test ở điểm nào?
3. Mockito `@Mock` và `@InjectMocks` dùng để làm gì?
4. Vì sao Service test thường mock Repository?
5. `@DataJpaTest` load những thành phần nào của Spring?
6. Vì sao repository test nên dùng H2 thay vì MySQL local?
7. `MockMvc` dùng để test gì?
8. `@WebMvcTest` khác `@SpringBootTest` như thế nào?
9. Khi nào nên dùng `@AutoConfigureMockMvc(addFilters = false)`?
10. `@ActiveProfiles("test")` có tác dụng gì?
11. Vì sao test không nên phụ thuộc dữ liệu thật trong database?
12. Với API dùng JWT, có những cách nào để test endpoint cần đăng nhập?

---

## Bài Tập Thực Hành

### Bài 1: Unit Test cho `AuthService`

- Test login thành công:
  - Mock `UserRepository.findByUsername`
  - Mock `PasswordEncoder.matches`
  - Mock hoặc dùng thật `JwtProvider`
  - Assert response có `accessToken`, `refreshToken`
- Test login sai password:
  - `PasswordEncoder.matches` trả `false`
  - Assert throw `VsException`

### Bài 2: Repository Test cho `InvalidatedTokenRepository`

- Tạo token invalidated trong H2.
- Test `existsById(jwtId)` trả `true`.
- Test token chưa lưu trả `false`.

### Bài 3: MockMvc Test cho Validation

- Gửi request login thiếu `username`.
- Gửi request login thiếu `password`.
- Assert response status là `400`.
- Assert response body có `status = ERROR`.

### Bài 4: Integration Test cho Flow Login

- Tạo user trong H2 trước test.
- Gọi `POST /api/v1/auth/login`.
- Assert trả token.
- Dùng token gọi một endpoint cần đăng nhập.

### Bài 5 (Nâng cao): Test phân quyền ADMIN/USER

- Tạo user role `USER` và role `ADMIN`.
- Login lấy token cho từng user.
- Gọi API `/api/v1/admin/...`.
- Assert:
  - ADMIN được truy cập.
  - USER bị `403 Forbidden`.

---

## Tài Liệu Tham Khảo

### Spring Testing

- [Spring Boot Testing Reference](https://docs.spring.io/spring-boot/reference/testing/)
- [Spring Framework Testing](https://docs.spring.io/spring-framework/reference/testing.html)
- [Spring Boot Test Slices](https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.autoconfigured-tests)

### JUnit, Mockito, AssertJ

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [AssertJ Core](https://assertj.github.io/doc/)

### MockMvc & Security Test

- [Spring MockMvc](https://docs.spring.io/spring-framework/reference/testing/mockmvc.html)
- [Spring Security Test](https://docs.spring.io/spring-security/reference/servlet/test/index.html)

### H2 Database

- [H2 Database Documentation](https://www.h2database.com/html/main.html)
- [Spring Boot Database Testing with H2](https://www.baeldung.com/spring-boot-h2-database)
