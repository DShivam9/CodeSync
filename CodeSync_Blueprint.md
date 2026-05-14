# CodeSync — Real-Time Collaborative Code Editor
## Complete Agent Build Blueprint

---

## 🤖 Agent Persona & Instructions

You are a **Senior Full-Stack Engineer** with 10+ years of experience building production-grade distributed systems. You have deep expertise in:
- Java / Spring Boot ecosystem
- Real-time systems (WebSockets, STOMP, OT algorithms)
- React + Monaco Editor
- PostgreSQL, Redis
- Docker, Railway deployment

**Your job is to build CodeSync from scratch — a real-time collaborative code editor powered by Operational Transformation (OT).** You will follow this blueprint phase by phase, writing production-quality code at every step. No shortcuts. No placeholders. No `// TODO` comments left unimplemented.

**Rules you must follow:**
1. Complete each phase fully before moving to the next
2. Write real, working code — not stubs
3. Every file must be complete — no partial implementations
4. Run and verify each phase works before proceeding
5. Follow the exact project structure defined below
6. Use the exact dependency versions specified
7. Write unit tests for all critical logic (especially OT engine)
8. Handle all edge cases and errors gracefully
9. Never leave hardcoded secrets — use environment variables always
10. The final product must be deployed and publicly accessible

---

## 📋 Project Overview

**CodeSync** is a browser-based collaborative code editor where multiple users can edit the same file simultaneously in real time. Conflicts are resolved using the **Operational Transformation (OT)** algorithm — the same algorithm that powers Google Docs.

### Core Capabilities
- Real-time multi-user code editing (same file, same room, no conflicts)
- Live cursor tracking — see every user's cursor position
- Room system — create and join rooms via shareable links
- File tree — create, rename, delete files per room
- Syntax highlighting for 15+ languages via Monaco Editor
- Document version history — replay any past state
- User presence — see who is online in a room
- Secure authentication with JWT
- Fully deployed and publicly accessible

---

## 🛠️ Complete Tech Stack

### Backend
| Technology | Version | Purpose |
|---|---|---|
| Java | 21 (LTS) | Core language |
| Spring Boot | 3.2.x | Application framework |
| Spring Web | 3.2.x | REST API |
| Spring WebSocket | 3.2.x | Real-time communication |
| Spring Security | 3.2.x | Auth + route protection |
| Spring Data JPA | 3.2.x | Database ORM |
| Spring Data Redis | 3.2.x | Redis integration |
| JJWT | 0.12.x | JWT creation + validation |
| PostgreSQL Driver | 42.7.x | DB connection |
| Lettuce | 6.3.x | Redis client |
| Lombok | 1.18.x | Boilerplate reduction |
| MapStruct | 1.5.x | DTO mapping |
| Flyway | 10.x | DB migrations |
| JUnit 5 | 5.10.x | Unit testing |
| Mockito | 5.x | Mocking in tests |
| Maven | 3.9.x | Build tool |

### Frontend
| Technology | Version | Purpose |
|---|---|---|
| React | 18.x | UI framework |
| Vite | 5.x | Build tool + dev server |
| Monaco Editor | 0.45.x | VS Code editor engine |
| @monaco-editor/react | 4.6.x | Monaco React wrapper |
| SockJS-client | 1.6.x | WebSocket fallback |
| @stomp/stompjs | 7.x | STOMP protocol client |
| React Router DOM | 6.x | Client-side routing |
| Zustand | 4.x | State management |
| Axios | 1.x | HTTP client |
| TailwindCSS | 3.x | Utility CSS framework |
| Lucide React | 0.x | Icons |

### Infrastructure
| Technology | Purpose |
|---|---|
| PostgreSQL 16 | Primary database |
| Redis 7 | Session state + cursor positions |
| Docker + Docker Compose | Local development |
| Railway | Cloud deployment |
| GitHub | Version control + CI |

---

## 📁 Complete Project Structure

```
codesync/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/codesync/
│   │   │   │   ├── CodeSyncApplication.java
│   │   │   │   ├── config/
│   │   │   │   │   ├── WebSocketConfig.java
│   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   ├── RedisConfig.java
│   │   │   │   │   └── CorsConfig.java
│   │   │   │   ├── controller/
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── RoomController.java
│   │   │   │   │   ├── DocumentController.java
│   │   │   │   │   └── UserController.java
│   │   │   │   ├── websocket/
│   │   │   │   │   ├── EditorWebSocketHandler.java
│   │   │   │   │   ├── CursorWebSocketHandler.java
│   │   │   │   │   └── PresenceWebSocketHandler.java
│   │   │   │   ├── ot/
│   │   │   │   │   ├── Operation.java
│   │   │   │   │   ├── OperationType.java
│   │   │   │   │   ├── OTEngine.java
│   │   │   │   │   ├── OTServer.java
│   │   │   │   │   └── DocumentState.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── AuthService.java
│   │   │   │   │   ├── RoomService.java
│   │   │   │   │   ├── DocumentService.java
│   │   │   │   │   ├── UserService.java
│   │   │   │   │   └── PresenceService.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   ├── RoomRepository.java
│   │   │   │   │   ├── DocumentRepository.java
│   │   │   │   │   └── OperationRepository.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Room.java
│   │   │   │   │   ├── Document.java
│   │   │   │   │   └── OperationLog.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── request/
│   │   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   │   ├── RegisterRequest.java
│   │   │   │   │   │   ├── CreateRoomRequest.java
│   │   │   │   │   │   ├── OperationRequest.java
│   │   │   │   │   │   └── CursorRequest.java
│   │   │   │   │   └── response/
│   │   │   │   │       ├── AuthResponse.java
│   │   │   │   │       ├── RoomResponse.java
│   │   │   │   │       ├── DocumentResponse.java
│   │   │   │   │       └── OperationResponse.java
│   │   │   │   ├── security/
│   │   │   │   │   ├── JwtUtil.java
│   │   │   │   │   ├── JwtAuthFilter.java
│   │   │   │   │   └── UserDetailsServiceImpl.java
│   │   │   │   └── exception/
│   │   │   │       ├── GlobalExceptionHandler.java
│   │   │   │       ├── RoomNotFoundException.java
│   │   │   │       ├── UnauthorizedException.java
│   │   │   │       └── OTConflictException.java
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       ├── application-dev.yml
│   │   │       ├── application-prod.yml
│   │   │       └── db/migration/
│   │   │           ├── V1__create_users.sql
│   │   │           ├── V2__create_rooms.sql
│   │   │           ├── V3__create_documents.sql
│   │   │           └── V4__create_operation_logs.sql
│   │   └── test/
│   │       └── java/com/codesync/
│   │           ├── ot/
│   │           │   ├── OTEngineTest.java
│   │           │   └── OTServerTest.java
│   │           └── service/
│   │               ├── RoomServiceTest.java
│   │               └── AuthServiceTest.java
│   ├── pom.xml
│   └── Dockerfile
├── frontend/
│   ├── src/
│   │   ├── main.jsx
│   │   ├── App.jsx
│   │   ├── components/
│   │   │   ├── editor/
│   │   │   │   ├── EditorPane.jsx
│   │   │   │   ├── CursorOverlay.jsx
│   │   │   │   ├── LanguageSelector.jsx
│   │   │   │   └── EditorToolbar.jsx
│   │   │   ├── room/
│   │   │   │   ├── RoomSidebar.jsx
│   │   │   │   ├── FileTree.jsx
│   │   │   │   ├── PresenceBar.jsx
│   │   │   │   └── ShareModal.jsx
│   │   │   ├── auth/
│   │   │   │   ├── LoginForm.jsx
│   │   │   │   └── RegisterForm.jsx
│   │   │   └── common/
│   │   │       ├── Navbar.jsx
│   │   │       ├── LoadingSpinner.jsx
│   │   │       └── ErrorBoundary.jsx
│   │   ├── pages/
│   │   │   ├── HomePage.jsx
│   │   │   ├── EditorPage.jsx
│   │   │   ├── LoginPage.jsx
│   │   │   ├── RegisterPage.jsx
│   │   │   └── DashboardPage.jsx
│   │   ├── hooks/
│   │   │   ├── useWebSocket.js
│   │   │   ├── useOTClient.js
│   │   │   ├── usePresence.js
│   │   │   └── useAuth.js
│   │   ├── store/
│   │   │   ├── authStore.js
│   │   │   ├── editorStore.js
│   │   │   └── roomStore.js
│   │   ├── services/
│   │   │   ├── api.js
│   │   │   ├── authService.js
│   │   │   ├── roomService.js
│   │   │   └── websocketService.js
│   │   └── utils/
│   │       ├── otClient.js
│   │       └── languageDetect.js
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   ├── tailwind.config.js
│   └── Dockerfile
├── docker-compose.yml
├── docker-compose.prod.yml
├── .env.example
├── .gitignore
└── README.md
```

---

## 🗄️ Database Schema

Run these Flyway migrations in order. Create them as files under `src/main/resources/db/migration/`.

### V1__create_users.sql
```sql
CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    display_name VARCHAR(100),
    avatar_color VARCHAR(7) DEFAULT '#6366f1',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    is_active BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
```

### V2__create_rooms.sql
```sql
CREATE TABLE rooms (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(20) NOT NULL UNIQUE,
    owner_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    is_public BOOLEAN DEFAULT TRUE,
    password_hash VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE TABLE room_members (
    room_id UUID REFERENCES rooms(id) ON DELETE CASCADE,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    joined_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    role VARCHAR(20) DEFAULT 'EDITOR',
    PRIMARY KEY (room_id, user_id)
);

CREATE INDEX idx_rooms_slug ON rooms(slug);
CREATE INDEX idx_rooms_owner ON rooms(owner_id);
```

### V3__create_documents.sql
```sql
CREATE TABLE documents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    room_id UUID NOT NULL REFERENCES rooms(id) ON DELETE CASCADE,
    filename VARCHAR(255) NOT NULL,
    content TEXT DEFAULT '',
    language VARCHAR(50) DEFAULT 'plaintext',
    version INTEGER DEFAULT 0,
    created_by UUID REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    UNIQUE(room_id, filename)
);

CREATE INDEX idx_documents_room ON documents(room_id);
```

### V4__create_operation_logs.sql
```sql
CREATE TABLE operation_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    document_id UUID NOT NULL REFERENCES documents(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id),
    operation_type VARCHAR(10) NOT NULL CHECK (operation_type IN ('INSERT', 'DELETE', 'RETAIN')),
    position INTEGER NOT NULL,
    content TEXT,
    length INTEGER,
    revision INTEGER NOT NULL,
    applied_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX idx_oplog_document ON operation_logs(document_id);
CREATE INDEX idx_oplog_revision ON operation_logs(document_id, revision);
```

---

## ⚙️ Backend Implementation

### pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.3</version>
        <relativePath/>
    </parent>
    <groupId>com.codesync</groupId>
    <artifactId>codesync-backend</artifactId>
    <version>1.0.0</version>
    <name>codesync-backend</name>
    <description>CodeSync Real-Time Collaborative Editor Backend</description>

    <properties>
        <java.version>21</java.version>
        <jjwt.version>0.12.5</jjwt.version>
        <mapstruct.version>1.5.5.Final</mapstruct.version>
        <lombok.version>1.18.30</lombok.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Core -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- PostgreSQL -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Flyway -->
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
        </dependency>
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-database-postgresql</artifactId>
        </dependency>

        <!-- JWT -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>${jjwt.version}</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- MapStruct -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>

        <!-- Lettuce Redis Client -->
        <dependency>
            <groupId>io.lettuce</groupId>
            <artifactId>lettuce-core</artifactId>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </path>
                        <path>
                            <groupId>org.mapstruct</groupId>
                            <artifactId>mapstruct-processor</artifactId>
                            <version>${mapstruct.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### application.yml
```yaml
spring:
  application:
    name: codesync

  datasource:
    url: ${DATABASE_URL:jdbc:postgresql://localhost:5432/codesync}
    username: ${DATABASE_USER:codesync}
    password: ${DATABASE_PASSWORD:codesync}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true

  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true

  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}
      password: ${REDIS_PASSWORD:}
      timeout: 2000ms

  jackson:
    serialization:
      write-dates-as-timestamps: false
    time-zone: UTC

server:
  port: ${PORT:8080}
  error:
    include-message: always
    include-binding-errors: always

jwt:
  secret: ${JWT_SECRET:your-256-bit-secret-key-change-this-in-production-immediately}
  expiration: 86400000
  refresh-expiration: 604800000

cors:
  allowed-origins: ${CORS_ORIGINS:http://localhost:5173,http://localhost:3000}

logging:
  level:
    com.codesync: DEBUG
    org.springframework.web.socket: DEBUG
```

### application-prod.yml
```yaml
spring:
  jpa:
    show-sql: false

logging:
  level:
    com.codesync: INFO
    org.springframework.web.socket: WARN

server:
  error:
    include-message: never
    include-binding-errors: never
```

---

### 🧠 OT Engine — The Core Algorithm

This is the most critical part of the project. Implement every class exactly as specified.

#### OperationType.java
```java
package com.codesync.ot;

public enum OperationType {
    INSERT,
    DELETE,
    RETAIN
}
```

#### Operation.java
```java
package com.codesync.ot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    private String id;
    private String userId;
    private String documentId;
    private OperationType type;
    private int position;       // character position in document
    private String content;     // for INSERT: text to insert
    private int length;         // for DELETE: number of chars to delete
    private int revision;       // document version this op was based on
    private long timestamp;

    public static Operation insert(String userId, String documentId, int position, String content, int revision) {
        return Operation.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .documentId(documentId)
                .type(OperationType.INSERT)
                .position(position)
                .content(content)
                .length(content.length())
                .revision(revision)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static Operation delete(String userId, String documentId, int position, int length, int revision) {
        return Operation.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .documentId(documentId)
                .type(OperationType.DELETE)
                .position(position)
                .length(length)
                .revision(revision)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
```

#### DocumentState.java
```java
package com.codesync.ot;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Data
public class DocumentState {
    private final String documentId;
    private StringBuilder content;
    private int revision;
    private final List<Operation> history;
    private final ReadWriteLock lock;

    public DocumentState(String documentId, String initialContent) {
        this.documentId = documentId;
        this.content = new StringBuilder(initialContent);
        this.revision = 0;
        this.history = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public String getContent() {
        lock.readLock().lock();
        try {
            return content.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getRevision() {
        lock.readLock().lock();
        try {
            return revision;
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<Operation> getOperationsSince(int revision) {
        lock.readLock().lock();
        try {
            return history.stream()
                    .filter(op -> op.getRevision() >= revision)
                    .toList();
        } finally {
            lock.readLock().unlock();
        }
    }
}
```

#### OTEngine.java
```java
package com.codesync.ot;

import org.springframework.stereotype.Component;

/**
 * Core Operational Transformation Engine.
 *
 * Implements the Jupiter OT algorithm for transforming concurrent operations.
 *
 * Key invariant: transform(op1, op2) returns op1' such that
 * applying op2 then op1' yields the same result as
 * applying op1 then op2'.
 */
@Component
public class OTEngine {

    /**
     * Transform operation 'incoming' against 'concurrent' operation.
     * Returns a new operation that can be safely applied after 'concurrent'.
     */
    public Operation transform(Operation incoming, Operation concurrent) {
        if (incoming.getType() == OperationType.INSERT && concurrent.getType() == OperationType.INSERT) {
            return transformInsertInsert(incoming, concurrent);
        } else if (incoming.getType() == OperationType.INSERT && concurrent.getType() == OperationType.DELETE) {
            return transformInsertDelete(incoming, concurrent);
        } else if (incoming.getType() == OperationType.DELETE && concurrent.getType() == OperationType.INSERT) {
            return transformDeleteInsert(incoming, concurrent);
        } else if (incoming.getType() == OperationType.DELETE && concurrent.getType() == OperationType.DELETE) {
            return transformDeleteDelete(incoming, concurrent);
        }
        return incoming;
    }

    /**
     * Apply a list of concurrent operations to transform incoming.
     * Called when client is behind multiple revisions.
     */
    public Operation transformAgainstAll(Operation incoming, java.util.List<Operation> concurrentOps) {
        Operation transformed = incoming;
        for (Operation concurrent : concurrentOps) {
            transformed = transform(transformed, concurrent);
        }
        return transformed;
    }

    /**
     * Apply an operation to document content and return new content.
     */
    public String applyOperation(String content, Operation op) {
        StringBuilder sb = new StringBuilder(content);
        switch (op.getType()) {
            case INSERT -> {
                int pos = Math.min(op.getPosition(), sb.length());
                sb.insert(pos, op.getContent());
            }
            case DELETE -> {
                int start = Math.min(op.getPosition(), sb.length());
                int end = Math.min(start + op.getLength(), sb.length());
                if (start < end) {
                    sb.delete(start, end);
                }
            }
            default -> { /* RETAIN: no-op */ }
        }
        return sb.toString();
    }

    // --- Private transformation methods ---

    private Operation transformInsertInsert(Operation incoming, Operation concurrent) {
        // If incoming insert position is before concurrent insert, no change needed
        if (incoming.getPosition() < concurrent.getPosition()) {
            return incoming;
        }
        // If same position, break tie by userId (lexicographic) for determinism
        if (incoming.getPosition() == concurrent.getPosition()) {
            if (incoming.getUserId().compareTo(concurrent.getUserId()) < 0) {
                return incoming;
            }
        }
        // incoming position is after concurrent insert — shift right by concurrent length
        return Operation.builder()
                .id(incoming.getId())
                .userId(incoming.getUserId())
                .documentId(incoming.getDocumentId())
                .type(OperationType.INSERT)
                .position(incoming.getPosition() + concurrent.getContent().length())
                .content(incoming.getContent())
                .length(incoming.getLength())
                .revision(incoming.getRevision())
                .timestamp(incoming.getTimestamp())
                .build();
    }

    private Operation transformInsertDelete(Operation incoming, Operation concurrent) {
        // Concurrent deleted before incoming insert position
        if (concurrent.getPosition() + concurrent.getLength() <= incoming.getPosition()) {
            return Operation.builder()
                    .id(incoming.getId())
                    .userId(incoming.getUserId())
                    .documentId(incoming.getDocumentId())
                    .type(OperationType.INSERT)
                    .position(incoming.getPosition() - concurrent.getLength())
                    .content(incoming.getContent())
                    .length(incoming.getLength())
                    .revision(incoming.getRevision())
                    .timestamp(incoming.getTimestamp())
                    .build();
        }
        // Concurrent deleted after incoming insert position — no change
        if (concurrent.getPosition() >= incoming.getPosition()) {
            return incoming;
        }
        // incoming insert is inside the deleted range — move to start of deletion
        return Operation.builder()
                .id(incoming.getId())
                .userId(incoming.getUserId())
                .documentId(incoming.getDocumentId())
                .type(OperationType.INSERT)
                .position(concurrent.getPosition())
                .content(incoming.getContent())
                .length(incoming.getLength())
                .revision(incoming.getRevision())
                .timestamp(incoming.getTimestamp())
                .build();
    }

    private Operation transformDeleteInsert(Operation incoming, Operation concurrent) {
        // Concurrent insert is after the entire delete range — no change
        if (concurrent.getPosition() >= incoming.getPosition() + incoming.getLength()) {
            return incoming;
        }
        // Concurrent insert is before delete start — shift delete right
        if (concurrent.getPosition() <= incoming.getPosition()) {
            return Operation.builder()
                    .id(incoming.getId())
                    .userId(incoming.getUserId())
                    .documentId(incoming.getDocumentId())
                    .type(OperationType.DELETE)
                    .position(incoming.getPosition() + concurrent.getContent().length())
                    .length(incoming.getLength())
                    .revision(incoming.getRevision())
                    .timestamp(incoming.getTimestamp())
                    .build();
        }
        // Concurrent insert is inside delete range — extend delete to cover inserted text
        return Operation.builder()
                .id(incoming.getId())
                .userId(incoming.getUserId())
                .documentId(incoming.getDocumentId())
                .type(OperationType.DELETE)
                .position(incoming.getPosition())
                .length(incoming.getLength() + concurrent.getContent().length())
                .revision(incoming.getRevision())
                .timestamp(incoming.getTimestamp())
                .build();
    }

    private Operation transformDeleteDelete(Operation incoming, Operation concurrent) {
        int inStart = incoming.getPosition();
        int inEnd = inStart + incoming.getLength();
        int conStart = concurrent.getPosition();
        int conEnd = conStart + concurrent.getLength();

        // No overlap — concurrent is entirely after incoming
        if (conStart >= inEnd) {
            return incoming;
        }
        // No overlap — concurrent is entirely before incoming
        if (conEnd <= inStart) {
            return Operation.builder()
                    .id(incoming.getId())
                    .userId(incoming.getUserId())
                    .documentId(incoming.getDocumentId())
                    .type(OperationType.DELETE)
                    .position(incoming.getPosition() - concurrent.getLength())
                    .length(incoming.getLength())
                    .revision(incoming.getRevision())
                    .timestamp(incoming.getTimestamp())
                    .build();
        }
        // Overlapping deletes — compute remaining delete range
        int newStart = Math.min(inStart, conStart);
        int overlapStart = Math.max(inStart, conStart);
        int overlapEnd = Math.min(inEnd, conEnd);
        int newLength = incoming.getLength() - (overlapEnd - overlapStart);
        int adjustedStart = inStart <= conStart ? inStart : Math.max(0, inStart - concurrent.getLength());

        if (newLength <= 0) {
            // Entire incoming delete was already deleted by concurrent — no-op
            return Operation.builder()
                    .id(incoming.getId())
                    .userId(incoming.getUserId())
                    .documentId(incoming.getDocumentId())
                    .type(OperationType.RETAIN)
                    .position(0)
                    .length(0)
                    .revision(incoming.getRevision())
                    .timestamp(incoming.getTimestamp())
                    .build();
        }

        return Operation.builder()
                .id(incoming.getId())
                .userId(incoming.getUserId())
                .documentId(incoming.getDocumentId())
                .type(OperationType.DELETE)
                .position(adjustedStart)
                .length(newLength)
                .revision(incoming.getRevision())
                .timestamp(incoming.getTimestamp())
                .build();
    }
}
```

#### OTServer.java
```java
package com.codesync.ot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OT Server manages in-memory document states and applies/broadcasts operations.
 * Acts as the central authority for all document revisions.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OTServer {

    private final OTEngine otEngine;
    private final Map<String, DocumentState> documentStates = new ConcurrentHashMap<>();

    /**
     * Load or create document state in memory.
     */
    public DocumentState getOrCreateState(String documentId, String content, int revision) {
        return documentStates.computeIfAbsent(documentId, id -> {
            DocumentState state = new DocumentState(id, content);
            return state;
        });
    }

    /**
     * Process an incoming operation from a client.
     * Transforms it against all concurrent operations the client hasn't seen,
     * applies it to the document, and returns the transformed operation.
     */
    public synchronized Operation processOperation(Operation incoming) {
        DocumentState state = documentStates.get(incoming.getDocumentId());
        if (state == null) {
            throw new IllegalStateException("Document state not found: " + incoming.getDocumentId());
        }

        state.getLock().writeLock().lock();
        try {
            // Get operations the client hasn't seen yet
            List<Operation> missedOps = state.getOperationsSince(incoming.getRevision());

            // Transform incoming against all missed operations
            Operation transformed = otEngine.transformAgainstAll(incoming, missedOps);
            transformed = Operation.builder()
                    .id(transformed.getId())
                    .userId(transformed.getUserId())
                    .documentId(transformed.getDocumentId())
                    .type(transformed.getType())
                    .position(transformed.getPosition())
                    .content(transformed.getContent())
                    .length(transformed.getLength())
                    .revision(state.getRevision())
                    .timestamp(transformed.getTimestamp())
                    .build();

            // Apply to document content
            if (transformed.getType() != OperationType.RETAIN) {
                String newContent = otEngine.applyOperation(state.getContent().toString(), transformed);
                state.setContent(new StringBuilder(newContent));
            }

            // Increment revision and record
            state.setRevision(state.getRevision() + 1);
            state.getHistory().add(transformed);

            log.debug("Applied op {} to doc {} -> revision {}", 
                transformed.getId(), incoming.getDocumentId(), state.getRevision());

            return transformed;

        } finally {
            state.getLock().writeLock().unlock();
        }
    }

    public void removeDocument(String documentId) {
        documentStates.remove(documentId);
    }
}
```

---

### WebSocket Configuration

#### WebSocketConfig.java
```java
package com.codesync.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable in-memory broker for /topic and /queue destinations
        config.enableSimpleBroker("/topic", "/queue");
        // Prefix for messages bound for @MessageMapping methods
        config.setApplicationDestinationPrefixes("/app");
        // Prefix for user-specific messages
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
```

#### WebSocket Message Types (define these as constants)
```java
package com.codesync.websocket;

public class WebSocketDestinations {
    // Client sends operations to:
    public static final String OPERATION_SEND = "/app/document/{docId}/operation";
    public static final String CURSOR_SEND = "/app/document/{docId}/cursor";
    public static final String JOIN_ROOM = "/app/room/{roomId}/join";
    public static final String LEAVE_ROOM = "/app/room/{roomId}/leave";

    // Server broadcasts to:
    public static final String OPERATION_BROADCAST = "/topic/document/{docId}/operations";
    public static final String CURSOR_BROADCAST = "/topic/document/{docId}/cursors";
    public static final String PRESENCE_BROADCAST = "/topic/room/{roomId}/presence";
    public static final String ERROR_USER = "/user/queue/errors";
}
```

#### EditorWebSocketHandler.java
```java
package com.codesync.websocket;

import com.codesync.dto.request.OperationRequest;
import com.codesync.dto.response.OperationResponse;
import com.codesync.ot.Operation;
import com.codesync.ot.OTServer;
import com.codesync.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EditorWebSocketHandler {

    private final OTServer otServer;
    private final DocumentService documentService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/document/{docId}/operation")
    public void handleOperation(
            @DestinationVariable String docId,
            @Payload OperationRequest request,
            SimpMessageHeaderAccessor headerAccessor) {

        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        if (userId == null) {
            messagingTemplate.convertAndSendToUser(
                headerAccessor.getUser().getName(),
                "/queue/errors",
                "Unauthorized: not authenticated"
            );
            return;
        }

        try {
            // Build operation
            Operation incoming = Operation.builder()
                    .id(request.getId())
                    .userId(userId)
                    .documentId(docId)
                    .type(request.getType())
                    .position(request.getPosition())
                    .content(request.getContent())
                    .length(request.getLength())
                    .revision(request.getRevision())
                    .timestamp(System.currentTimeMillis())
                    .build();

            // Process through OT engine
            Operation transformed = otServer.processOperation(incoming);

            // Persist async
            documentService.persistOperation(transformed);

            // Broadcast to all clients in this document's topic
            OperationResponse response = OperationResponse.builder()
                    .id(transformed.getId())
                    .userId(transformed.getUserId())
                    .documentId(transformed.getDocumentId())
                    .type(transformed.getType())
                    .position(transformed.getPosition())
                    .content(transformed.getContent())
                    .length(transformed.getLength())
                    .revision(transformed.getRevision())
                    .timestamp(transformed.getTimestamp())
                    .build();

            messagingTemplate.convertAndSend(
                "/topic/document/" + docId + "/operations",
                response
            );

        } catch (Exception e) {
            log.error("Error processing operation for doc {}: {}", docId, e.getMessage());
            messagingTemplate.convertAndSendToUser(
                headerAccessor.getUser().getName(),
                "/queue/errors",
                "Failed to process operation: " + e.getMessage()
            );
        }
    }
}
```

---

### Security Configuration

#### SecurityConfig.java
```java
package com.codesync.config;

import com.codesync.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configure(http))
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/ws/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
```

#### JwtUtil.java
```java
package com.codesync.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
```

---

### REST Controllers

#### AuthController.java
```java
package com.codesync.controller;

import com.codesync.dto.request.LoginRequest;
import com.codesync.dto.request.RegisterRequest;
import com.codesync.dto.response.AuthResponse;
import com.codesync.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.refresh(token));
    }
}
```

#### RoomController.java
```java
package com.codesync.controller;

import com.codesync.dto.request.CreateRoomRequest;
import com.codesync.dto.response.RoomResponse;
import com.codesync.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @Valid @RequestBody CreateRoomRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(roomService.createRoom(request, userDetails.getUsername()));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable String slug) {
        return ResponseEntity.ok(roomService.getRoomBySlug(slug));
    }

    @GetMapping("/my")
    public ResponseEntity<List<RoomResponse>> getMyRooms(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(roomService.getRoomsForUser(userDetails.getUsername()));
    }

    @PostMapping("/{slug}/join")
    public ResponseEntity<RoomResponse> joinRoom(
            @PathVariable String slug,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(roomService.joinRoom(slug, userDetails.getUsername()));
    }

    @DeleteMapping("/{slug}/leave")
    public ResponseEntity<Void> leaveRoom(
            @PathVariable String slug,
            @AuthenticationPrincipal UserDetails userDetails) {
        roomService.leaveRoom(slug, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
```

#### DocumentController.java
```java
package com.codesync.controller;

import com.codesync.dto.response.DocumentResponse;
import com.codesync.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByRoom(@PathVariable String roomId) {
        return ResponseEntity.ok(documentService.getDocumentsByRoom(roomId));
    }

    @GetMapping("/{docId}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable String docId) {
        return ResponseEntity.ok(documentService.getDocument(docId));
    }

    @PostMapping("/room/{roomId}")
    public ResponseEntity<DocumentResponse> createDocument(
            @PathVariable String roomId,
            @RequestParam String filename) {
        return ResponseEntity.ok(documentService.createDocument(roomId, filename));
    }

    @DeleteMapping("/{docId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String docId) {
        documentService.deleteDocument(docId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{docId}/history")
    public ResponseEntity<?> getDocumentHistory(
            @PathVariable String docId,
            @RequestParam(defaultValue = "0") int fromRevision) {
        return ResponseEntity.ok(documentService.getHistory(docId, fromRevision));
    }
}
```

---

### Global Exception Handler

#### GlobalExceptionHandler.java
```java
package com.codesync.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ProblemDetail handleRoomNotFound(RoomNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Room Not Found");
        return pd;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ProblemDetail handleUnauthorized(UnauthorizedException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        pd.setTitle("Forbidden");
        return pd;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthException(AuthenticationException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        pd.setTitle("Unauthorized");
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        pd.setTitle("Validation Error");
        pd.setProperty("errors", errors);
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneral(Exception ex) {
        log.error("Unhandled exception: ", ex);
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        pd.setTitle("Internal Server Error");
        return pd;
    }
}
```

---

## 🎨 Frontend Implementation

### package.json
```json
{
  "name": "codesync-frontend",
  "private": true,
  "version": "1.0.0",
  "type": "module",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview"
  },
  "dependencies": {
    "react": "^18.3.1",
    "react-dom": "^18.3.1",
    "react-router-dom": "^6.22.3",
    "@monaco-editor/react": "^4.6.0",
    "monaco-editor": "^0.45.0",
    "@stomp/stompjs": "^7.0.0",
    "sockjs-client": "^1.6.1",
    "axios": "^1.6.7",
    "zustand": "^4.5.2",
    "lucide-react": "^0.363.0",
    "clsx": "^2.1.0",
    "nanoid": "^5.0.6"
  },
  "devDependencies": {
    "@types/react": "^18.3.1",
    "@types/react-dom": "^18.3.1",
    "@vitejs/plugin-react": "^4.2.1",
    "autoprefixer": "^10.4.18",
    "postcss": "^8.4.35",
    "tailwindcss": "^3.4.1",
    "vite": "^5.1.6"
  }
}
```

### vite.config.js
```javascript
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      '/ws': {
        target: 'ws://localhost:8080',
        ws: true,
        changeOrigin: true
      }
    }
  },
  build: {
    outDir: 'dist',
    sourcemap: false,
    rollupOptions: {
      output: {
        manualChunks: {
          monaco: ['monaco-editor'],
          vendor: ['react', 'react-dom', 'react-router-dom']
        }
      }
    }
  }
})
```

### OT Client (Frontend)

#### src/utils/otClient.js
```javascript
/**
 * OT Client — mirrors the server's OT engine on the frontend.
 * Manages local operation buffering and transformation
 * so the editor always stays in sync.
 */
export class OTClient {
  constructor(documentId, userId, initialContent, initialRevision) {
    this.documentId = documentId;
    this.userId = userId;
    this.content = initialContent;
    this.revision = initialRevision;
    this.pendingOps = [];   // ops sent but not yet acknowledged
    this.buffer = [];       // ops not yet sent
  }

  /**
   * Create an INSERT operation from Monaco change event.
   */
  createInsert(position, text) {
    return {
      id: crypto.randomUUID(),
      userId: this.userId,
      documentId: this.documentId,
      type: 'INSERT',
      position,
      content: text,
      length: text.length,
      revision: this.revision,
      timestamp: Date.now()
    };
  }

  /**
   * Create a DELETE operation from Monaco change event.
   */
  createDelete(position, length) {
    return {
      id: crypto.randomUUID(),
      userId: this.userId,
      documentId: this.documentId,
      type: 'DELETE',
      position,
      length,
      revision: this.revision,
      timestamp: Date.now()
    };
  }

  /**
   * Apply a remote operation received from server to local content.
   * Returns the updated content string.
   */
  applyRemoteOperation(op) {
    // Transform against pending local ops
    let transformed = op;
    for (const pending of this.pendingOps) {
      transformed = this.transformOperation(transformed, pending);
    }

    this.content = this.applyToContent(this.content, transformed);
    this.revision = op.revision + 1;
    return { content: this.content, operation: transformed };
  }

  /**
   * Record a local operation and return it ready for sending.
   */
  localOperation(op) {
    this.content = this.applyToContent(this.content, op);
    this.pendingOps.push(op);
    return op;
  }

  /**
   * Server acknowledged our operation.
   */
  acknowledge(opId) {
    this.pendingOps = this.pendingOps.filter(op => op.id !== opId);
    this.revision++;
  }

  // --- Internal transform (mirrors server logic) ---

  transformOperation(incoming, concurrent) {
    if (incoming.type === 'INSERT' && concurrent.type === 'INSERT') {
      if (incoming.position > concurrent.position ||
         (incoming.position === concurrent.position && incoming.userId > concurrent.userId)) {
        return { ...incoming, position: incoming.position + concurrent.content.length };
      }
      return incoming;
    }
    if (incoming.type === 'DELETE' && concurrent.type === 'INSERT') {
      if (concurrent.position <= incoming.position) {
        return { ...incoming, position: incoming.position + concurrent.content.length };
      }
      return incoming;
    }
    if (incoming.type === 'INSERT' && concurrent.type === 'DELETE') {
      if (concurrent.position + concurrent.length <= incoming.position) {
        return { ...incoming, position: incoming.position - concurrent.length };
      }
      if (concurrent.position >= incoming.position) {
        return incoming;
      }
      return { ...incoming, position: concurrent.position };
    }
    if (incoming.type === 'DELETE' && concurrent.type === 'DELETE') {
      const inEnd = incoming.position + incoming.length;
      const conEnd = concurrent.position + concurrent.length;
      if (concurrent.position >= inEnd) return incoming;
      if (conEnd <= incoming.position) {
        return { ...incoming, position: incoming.position - concurrent.length };
      }
      const overlap = Math.min(inEnd, conEnd) - Math.max(incoming.position, concurrent.position);
      const newLength = Math.max(0, incoming.length - overlap);
      const newPos = incoming.position <= concurrent.position
        ? incoming.position
        : Math.max(0, incoming.position - concurrent.length);
      return newLength === 0
        ? { ...incoming, type: 'RETAIN', length: 0 }
        : { ...incoming, position: newPos, length: newLength };
    }
    return incoming;
  }

  applyToContent(content, op) {
    if (op.type === 'INSERT') {
      const pos = Math.min(op.position, content.length);
      return content.slice(0, pos) + op.content + content.slice(pos);
    }
    if (op.type === 'DELETE') {
      const start = Math.min(op.position, content.length);
      const end = Math.min(start + op.length, content.length);
      return content.slice(0, start) + content.slice(end);
    }
    return content;
  }
}
```

### WebSocket Service

#### src/services/websocketService.js
```javascript
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

class WebSocketService {
  constructor() {
    this.client = null;
    this.subscriptions = new Map();
    this.connectionPromise = null;
  }

  connect(token) {
    if (this.connectionPromise) return this.connectionPromise;

    this.connectionPromise = new Promise((resolve, reject) => {
      this.client = new Client({
        webSocketFactory: () => new SockJS(`${import.meta.env.VITE_API_URL || ''}/ws`),
        connectHeaders: {
          Authorization: `Bearer ${token}`
        },
        reconnectDelay: 3000,
        onConnect: () => {
          console.log('[WS] Connected');
          resolve();
        },
        onDisconnect: () => {
          console.log('[WS] Disconnected');
          this.connectionPromise = null;
        },
        onStompError: (frame) => {
          console.error('[WS] STOMP error:', frame);
          reject(frame);
        }
      });

      this.client.activate();
    });

    return this.connectionPromise;
  }

  subscribe(destination, callback) {
    if (!this.client?.connected) {
      throw new Error('WebSocket not connected');
    }
    const sub = this.client.subscribe(destination, (message) => {
      callback(JSON.parse(message.body));
    });
    this.subscriptions.set(destination, sub);
    return sub;
  }

  unsubscribe(destination) {
    const sub = this.subscriptions.get(destination);
    if (sub) {
      sub.unsubscribe();
      this.subscriptions.delete(destination);
    }
  }

  send(destination, body) {
    if (!this.client?.connected) {
      throw new Error('WebSocket not connected');
    }
    this.client.publish({
      destination,
      body: JSON.stringify(body)
    });
  }

  disconnect() {
    if (this.client) {
      this.client.deactivate();
      this.client = null;
      this.connectionPromise = null;
    }
  }
}

export const wsService = new WebSocketService();
```

### Zustand Stores

#### src/store/authStore.js
```javascript
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export const useAuthStore = create(
  persist(
    (set) => ({
      token: null,
      user: null,
      setAuth: (token, user) => set({ token, user }),
      clearAuth: () => set({ token: null, user: null }),
      isAuthenticated: () => !!useAuthStore.getState().token,
    }),
    { name: 'codesync-auth' }
  )
);
```

#### src/store/editorStore.js
```javascript
import { create } from 'zustand';

export const useEditorStore = create((set, get) => ({
  currentDocument: null,
  content: '',
  revision: 0,
  cursors: {},       // { userId: { line, column, color, username } }
  presence: [],      // [{ userId, username, color }]
  isSynced: true,

  setDocument: (doc) => set({
    currentDocument: doc,
    content: doc.content,
    revision: doc.version
  }),

  setContent: (content) => set({ content }),
  setRevision: (revision) => set({ revision }),

  updateCursor: (userId, cursorData) => set((state) => ({
    cursors: { ...state.cursors, [userId]: cursorData }
  })),

  removeCursor: (userId) => set((state) => {
    const cursors = { ...state.cursors };
    delete cursors[userId];
    return { cursors };
  }),

  setPresence: (presence) => set({ presence }),
  setSynced: (isSynced) => set({ isSynced }),
}));
```

### Core Editor Component

#### src/components/editor/EditorPane.jsx
```jsx
import { useRef, useEffect, useCallback } from 'react';
import Editor from '@monaco-editor/react';
import { useEditorStore } from '../../store/editorStore';
import { useAuthStore } from '../../store/authStore';
import { OTClient } from '../../utils/otClient';
import { wsService } from '../../services/websocketService';
import CursorOverlay from './CursorOverlay';

export default function EditorPane({ documentId, roomId }) {
  const editorRef = useRef(null);
  const monacoRef = useRef(null);
  const otClientRef = useRef(null);
  const isRemoteChange = useRef(false);

  const { content, setContent, revision, currentDocument, updateCursor, cursors } = useEditorStore();
  const { user, token } = useAuthStore();

  useEffect(() => {
    if (!currentDocument || !user) return;

    otClientRef.current = new OTClient(
      documentId,
      user.id,
      currentDocument.content,
      currentDocument.version
    );

    // Subscribe to operations
    wsService.subscribe(
      `/topic/document/${documentId}/operations`,
      handleRemoteOperation
    );

    // Subscribe to cursors
    wsService.subscribe(
      `/topic/document/${documentId}/cursors`,
      handleRemoteCursor
    );

    return () => {
      wsService.unsubscribe(`/topic/document/${documentId}/operations`);
      wsService.unsubscribe(`/topic/document/${documentId}/cursors`);
    };
  }, [documentId, currentDocument, user]);

  const handleRemoteOperation = useCallback((op) => {
    if (op.userId === user.id) {
      otClientRef.current?.acknowledge(op.id);
      return;
    }

    const result = otClientRef.current?.applyRemoteOperation(op);
    if (!result) return;

    isRemoteChange.current = true;
    const editor = editorRef.current;
    if (editor) {
      const currentPos = editor.getPosition();
      const model = editor.getModel();

      // Apply the operation to Monaco model
      if (op.type === 'INSERT') {
        const position = model.getPositionAt(result.operation.position);
        editor.executeEdits('remote', [{
          range: {
            startLineNumber: position.lineNumber,
            startColumn: position.column,
            endLineNumber: position.lineNumber,
            endColumn: position.column
          },
          text: result.operation.content
        }]);
      } else if (op.type === 'DELETE') {
        const startPos = model.getPositionAt(result.operation.position);
        const endPos = model.getPositionAt(result.operation.position + result.operation.length);
        editor.executeEdits('remote', [{
          range: {
            startLineNumber: startPos.lineNumber,
            startColumn: startPos.column,
            endLineNumber: endPos.lineNumber,
            endColumn: endPos.column
          },
          text: ''
        }]);
      }

      editor.setPosition(currentPos);
    }
    isRemoteChange.current = false;
  }, [user]);

  const handleRemoteCursor = useCallback((data) => {
    if (data.userId !== user.id) {
      updateCursor(data.userId, data);
    }
  }, [user, updateCursor]);

  const handleEditorChange = useCallback((value, event) => {
    if (isRemoteChange.current || !otClientRef.current) return;

    for (const change of event.changes) {
      let op;
      if (change.text.length > 0 && change.rangeLength === 0) {
        // Pure insert
        op = otClientRef.current.createInsert(change.rangeOffset, change.text);
      } else if (change.rangeLength > 0 && change.text.length === 0) {
        // Pure delete
        op = otClientRef.current.createDelete(change.rangeOffset, change.rangeLength);
      } else if (change.text.length > 0 && change.rangeLength > 0) {
        // Replace = delete + insert
        const delOp = otClientRef.current.createDelete(change.rangeOffset, change.rangeLength);
        wsService.send(`/app/document/${documentId}/operation`, delOp);
        otClientRef.current.localOperation(delOp);
        op = otClientRef.current.createInsert(change.rangeOffset, change.text);
      }

      if (op) {
        otClientRef.current.localOperation(op);
        wsService.send(`/app/document/${documentId}/operation`, op);
      }
    }

    setContent(value);
  }, [documentId, setContent]);

  const handleCursorChange = useCallback((e) => {
    const position = e.position;
    wsService.send(`/app/document/${documentId}/cursor`, {
      userId: user.id,
      username: user.displayName,
      color: user.avatarColor,
      line: position.lineNumber,
      column: position.column
    });
  }, [documentId, user]);

  const handleEditorMount = (editor, monaco) => {
    editorRef.current = editor;
    monacoRef.current = monaco;
    editor.onDidChangeCursorPosition(handleCursorChange);
  };

  const language = currentDocument?.language || 'plaintext';

  return (
    <div className="relative flex-1 h-full">
      <Editor
        height="100%"
        language={language}
        value={content}
        onChange={handleEditorChange}
        onMount={handleEditorMount}
        theme="vs-dark"
        options={{
          fontSize: 14,
          fontFamily: "'JetBrains Mono', 'Fira Code', monospace",
          fontLigatures: true,
          minimap: { enabled: true },
          scrollBeyondLastLine: false,
          automaticLayout: true,
          tabSize: 2,
          wordWrap: 'on',
          renderWhitespace: 'selection',
          cursorSmoothCaretAnimation: 'on',
          smoothScrolling: true,
          padding: { top: 16 }
        }}
      />
      <CursorOverlay
        cursors={cursors}
        editor={editorRef.current}
        monaco={monacoRef.current}
      />
    </div>
  );
}
```

---

## 🔴 Redis Configuration

#### RedisConfig.java
```java
package com.codesync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
```

**Use Redis for:**
- `presence:{roomId}` → Set of online user IDs
- `cursor:{docId}:{userId}` → Cursor position (TTL: 30s, refreshed on each move)
- `doc:state:{docId}` → Serialized document content cache (TTL: 10min)

---

## 🐳 Docker Setup

### docker-compose.yml (Local Development)
```yaml
version: '3.9'

services:
  postgres:
    image: postgres:16-alpine
    container_name: codesync-postgres
    environment:
      POSTGRES_DB: codesync
      POSTGRES_USER: codesync
      POSTGRES_PASSWORD: codesync
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U codesync"]
      interval: 10s
      timeout: 5s
      retries: 5

  redis:
    image: redis:7-alpine
    container_name: codesync-redis
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    command: redis-server --save 20 1 --loglevel warning
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 10s
      timeout: 5s
      retries: 5

  backend:
    build:
      context: ./backend
      dockerfile: Dockerfile
    container_name: codesync-backend
    environment:
      DATABASE_URL: jdbc:postgresql://postgres:5432/codesync
      DATABASE_USER: codesync
      DATABASE_PASSWORD: codesync
      REDIS_HOST: redis
      REDIS_PORT: 6379
      JWT_SECRET: ${JWT_SECRET}
      CORS_ORIGINS: http://localhost:5173
    ports:
      - "8080:8080"
    depends_on:
      postgres:
        condition: service_healthy
      redis:
        condition: service_healthy

  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    container_name: codesync-frontend
    environment:
      VITE_API_URL: http://localhost:8080
    ports:
      - "5173:80"
    depends_on:
      - backend

volumes:
  postgres_data:
  redis_data:
```

### backend/Dockerfile
```dockerfile
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-jar", "app.jar", \
  "--spring.profiles.active=prod"]
```

### frontend/Dockerfile
```dockerfile
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### frontend/nginx.conf
```nginx
server {
    listen 80;
    server_name _;
    root /usr/share/nginx/html;
    index index.html;

    gzip on;
    gzip_types text/plain text/css application/json application/javascript;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location /api {
        proxy_pass http://backend:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    location /ws {
        proxy_pass http://backend:8080;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
        proxy_set_header Host $host;
        proxy_read_timeout 86400;
    }
}
```

---

## 🧪 Unit Tests

### OTEngineTest.java
```java
package com.codesync.ot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

class OTEngineTest {

    private OTEngine engine;

    @BeforeEach
    void setUp() {
        engine = new OTEngine();
    }

    @Test
    @DisplayName("Insert before insert: incoming position unchanged")
    void insertBeforeInsert() {
        Operation incoming = Operation.insert("userA", "doc1", 3, "hello", 0);
        Operation concurrent = Operation.insert("userB", "doc1", 7, "world", 0);
        Operation result = engine.transform(incoming, concurrent);
        assertThat(result.getPosition()).isEqualTo(3);
    }

    @Test
    @DisplayName("Insert after insert: incoming position shifts right")
    void insertAfterInsert() {
        Operation incoming = Operation.insert("userA", "doc1", 10, "hello", 0);
        Operation concurrent = Operation.insert("userB", "doc1", 3, "world", 0);
        Operation result = engine.transform(incoming, concurrent);
        assertThat(result.getPosition()).isEqualTo(10 + "world".length());
    }

    @Test
    @DisplayName("Apply insert operation: content updated correctly")
    void applyInsert() {
        Operation op = Operation.insert("userA", "doc1", 5, " World", 0);
        String result = engine.applyOperation("Hello", op);
        assertThat(result).isEqualTo("Hello World");
    }

    @Test
    @DisplayName("Apply delete operation: content updated correctly")
    void applyDelete() {
        Operation op = Operation.delete("userA", "doc1", 5, 6, 0);
        String result = engine.applyOperation("Hello World", op);
        assertThat(result).isEqualTo("Hello");
    }

    @Test
    @DisplayName("Concurrent inserts produce same document regardless of order")
    void convergence() {
        String initial = "Hello";
        Operation opA = Operation.insert("userA", "doc1", 5, " World", 0);
        Operation opB = Operation.insert("userB", "doc1", 5, "!!!", 0);

        // Path 1: apply A then transformed B
        String afterA = engine.applyOperation(initial, opA);
        Operation bPrime = engine.transform(opB, opA);
        String path1 = engine.applyOperation(afterA, bPrime);

        // Path 2: apply B then transformed A
        String afterB = engine.applyOperation(initial, opB);
        Operation aPrime = engine.transform(opA, opB);
        String path2 = engine.applyOperation(afterB, aPrime);

        // Both paths must converge to same content
        assertThat(path1).isEqualTo(path2);
    }

    @Test
    @DisplayName("Delete overlapping with concurrent delete: no double deletion")
    void deleteDeleteOverlap() {
        String content = "Hello World";
        Operation incoming = Operation.delete("userA", "doc1", 0, 5, 0);   // delete "Hello"
        Operation concurrent = Operation.delete("userB", "doc1", 3, 5, 0); // delete "lo Wo"

        Operation transformed = engine.transform(incoming, concurrent);
        String afterConcurrent = engine.applyOperation(content, concurrent);
        String result = engine.applyOperation(afterConcurrent, transformed);

        assertThat(result).doesNotContain("Hello");
    }
}
```

---

## 🔒 Security Checklist

Implement every item on this list before deployment:

- [ ] JWT secret is minimum 256-bit, stored as environment variable, never hardcoded
- [ ] Passwords hashed with BCrypt strength 12
- [ ] All API endpoints except `/api/auth/**` and `/ws/**` require authentication
- [ ] Input validation on all `@RequestBody` fields using `@Valid` + Bean Validation
- [ ] Rate limiting on `/api/auth/login` — max 10 requests per minute per IP
- [ ] CORS restricted to known frontend origin in production
- [ ] SQL injection prevented by JPA/Hibernate parameterized queries only
- [ ] WebSocket connections authenticated via JWT in STOMP connect headers
- [ ] Room access control — verify user is a member before processing operations
- [ ] File upload size limits if any file features are added
- [ ] Sensitive data never logged (passwords, tokens)
- [ ] HTTP security headers: `X-Content-Type-Options`, `X-Frame-Options`, `Strict-Transport-Security`
- [ ] PostgreSQL user has least privilege (no superuser in production)
- [ ] Redis protected with password in production
- [ ] All environment variables validated at startup — fail fast if missing

---

## 🚀 Deployment — Vercel (Frontend) + Railway (Backend)

### Architecture
```
Browser → Vercel (React frontend)
             ↓ API calls + WebSocket
          Railway (Spring Boot backend)
             ↓              ↓
        Railway           Railway
       PostgreSQL          Redis
```

---

### Step 1 — Generate JWT Secret
```bash
openssl rand -base64 32
```
Save this — you'll need it in both Railway and Vercel env vars.

---

### Step 2 — Push to GitHub
```bash
git init
git add .
git commit -m "feat: initial CodeSync implementation"
git remote add origin https://github.com/yourusername/codesync
git push -u origin main
```

---

### Step 3 — Deploy Backend on Railway

1. Go to [railway.app](https://railway.app) → **New Project**
2. Click **Add Plugin** → select **PostgreSQL** → Railway provisions it automatically
3. Click **Add Plugin** → select **Redis** → Railway provisions it automatically
4. Click **New Service** → **Deploy from GitHub** → select your `codesync` repo
5. Set **Root Directory** to `backend/`
6. Go to **Variables** tab → add the following:

```env
DATABASE_URL=jdbc:postgresql://${{Postgres.PGHOST}}:${{Postgres.PGPORT}}/${{Postgres.PGDATABASE}}
DATABASE_USER=${{Postgres.PGUSER}}
DATABASE_PASSWORD=${{Postgres.PGPASSWORD}}
REDIS_HOST=${{Redis.REDIS_HOST}}
REDIS_PORT=${{Redis.REDIS_PORT}}
REDIS_PASSWORD=${{Redis.REDIS_PASSWORD}}
JWT_SECRET=your-generated-secret-here
CORS_ORIGINS=https://your-app.vercel.app
SPRING_PROFILES_ACTIVE=prod
```

> ⚠️ Note: `CORS_ORIGINS` must point to your Vercel frontend URL. You can update this after Vercel deployment.

7. Railway auto-detects the Dockerfile — click **Deploy**
8. Wait for health check to pass
9. Copy your Railway backend public URL — e.g. `https://codesync-backend.up.railway.app`

---

### Step 4 — Deploy Frontend on Vercel

1. Go to [vercel.com](https://vercel.com) → **New Project**
2. Import your GitHub repo → select `codesync`
3. Set **Root Directory** to `frontend/`
4. Set **Framework Preset** to `Vite`
5. Add Environment Variables:

```env
VITE_API_URL=https://codesync-backend.up.railway.app
VITE_WS_URL=wss://codesync-backend.up.railway.app
```

6. Click **Deploy** — Vercel builds and deploys automatically
7. Copy your Vercel public URL — e.g. `https://codesync.vercel.app`

---

### Step 5 — Update CORS on Railway

Go back to Railway → your backend service → Variables → update:
```env
CORS_ORIGINS=https://codesync.vercel.app
```
Redeploy the backend.

---

### Step 6 — WebSocket on Vercel (Important)

Vercel does **not** support WebSocket connections directly — it's a serverless platform. Your WebSocket traffic must go directly to Railway. Update your frontend WebSocket service:

```javascript
// src/services/websocketService.js
const WS_URL = import.meta.env.VITE_WS_URL || 'wss://codesync-backend.up.railway.app';

this.client = new Client({
  webSocketFactory: () => new SockJS(`${WS_URL}/ws`),
  // ...
});
```

All REST API calls go through Vercel's proxy → Railway.
All WebSocket connections go directly to Railway.

---

### Step 7 — Verify Deployment

```bash
# Backend health check
curl https://codesync-backend.up.railway.app/actuator/health

# Register a user
curl -X POST https://codesync-backend.up.railway.app/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@test.com","password":"Test1234!"}'

# Open frontend
open https://codesync.vercel.app
```

---

### Step 8 — Automatic Deployments (CI/CD)

Both platforms auto-deploy on every `git push` to `main`:
- **Vercel** → rebuilds and redeploys frontend in ~30 seconds
- **Railway** → rebuilds Docker image and redeploys backend in ~2 minutes

No manual steps needed after initial setup.

---

### Environment Variables Summary

| Variable | Where | Value |
|---|---|---|
| `DATABASE_URL` | Railway backend | Auto from PostgreSQL plugin |
| `DATABASE_USER` | Railway backend | Auto from PostgreSQL plugin |
| `DATABASE_PASSWORD` | Railway backend | Auto from PostgreSQL plugin |
| `REDIS_HOST` | Railway backend | Auto from Redis plugin |
| `REDIS_PORT` | Railway backend | Auto from Redis plugin |
| `REDIS_PASSWORD` | Railway backend | Auto from Redis plugin |
| `JWT_SECRET` | Railway backend | Your generated secret |
| `CORS_ORIGINS` | Railway backend | Your Vercel frontend URL |
| `VITE_API_URL` | Vercel frontend | Your Railway backend URL |
| `VITE_WS_URL` | Vercel frontend | Your Railway backend URL (wss://) |

---

## 📋 Implementation Order — Phase by Phase

Follow this order exactly. Do not skip ahead.

### Phase 1 — Backend Foundation (Week 1)
1. Initialize Spring Boot project with all dependencies
2. Set up Docker Compose with PostgreSQL + Redis
3. Run Flyway migrations — verify all 4 tables created
4. Implement User entity + repository
5. Implement JWT security (JwtUtil + JwtAuthFilter + SecurityConfig)
6. Implement AuthService (register + login)
7. Implement AuthController
8. Test: register a user, login, get JWT back ✅

### Phase 2 — OT Engine (Week 1–2)
1. Implement `OperationType` enum
2. Implement `Operation` model
3. Implement `DocumentState`
4. Implement `OTEngine` — all 4 transform methods
5. Write `OTEngineTest` — all tests must pass before proceeding
6. Implement `OTServer`
7. Test: two concurrent inserts transform correctly in unit test ✅

### Phase 3 — WebSocket + Real-Time (Week 2)
1. Configure `WebSocketConfig` with STOMP
2. Implement `EditorWebSocketHandler` — receives operations, calls OTServer, broadcasts
3. Implement `CursorWebSocketHandler` — receives + broadcasts cursor positions
4. Implement `PresenceWebSocketHandler` — join/leave room presence
5. Store presence in Redis with TTL
6. Test: two browser tabs, type in one, see update in other ✅

### Phase 4 — Room + Document APIs (Week 3)
1. Implement Room entity + repository + service + controller
2. Implement Document entity + repository + service + controller
3. Implement OperationLog persistence (async via `@Async`)
4. Load document state into OTServer memory on first access
5. Implement history endpoint — return ops since revision N
6. Test: create room, create document, open document, get content ✅

### Phase 5 — React Frontend (Week 4)
1. Initialize Vite + React project, install all dependencies
2. Set up TailwindCSS
3. Implement auth store (Zustand) + auth service + login/register pages
4. Implement protected routing
5. Implement dashboard page — list rooms, create room
6. Implement OTClient class
7. Implement WebSocket service
8. Integrate Monaco Editor in EditorPane
9. Connect Monaco change events → OTClient → WebSocket send
10. Handle incoming operations → OTClient → Monaco apply
11. Test: two browser windows, edit simultaneously, no conflicts ✅

### Phase 6 — Cursors, Presence, File Tree (Week 5)
1. Implement CursorOverlay component using Monaco decorations
2. Send cursor positions via WebSocket on every position change
3. Render other users' cursors with their color + username label
4. Implement PresenceBar showing online users with colored dots
5. Implement FileTree sidebar — list files in room, click to switch
6. Implement file create/rename/delete in FileTree
7. Test: open room in 3 browsers, see all cursors, switch files ✅

### Phase 7 — Polish + Security + Deploy (Week 6–7)
1. Implement rate limiting on auth endpoints
2. Add HTTP security headers
3. Implement GlobalExceptionHandler
4. Add input validation to all request DTOs
5. Write integration tests for auth + room flows
6. Build Docker images for both services
7. Deploy to Railway (follow deployment steps above)
8. Smoke test all features on live deployment
9. Write README with architecture diagram, setup guide, and live demo link ✅

---

## 🔁 WebSocket Message Schemas

### Client → Server: Operation
```json
{
  "id": "uuid",
  "type": "INSERT | DELETE",
  "position": 42,
  "content": "hello",
  "length": 5,
  "revision": 7
}
```

### Server → Client: Operation (Broadcast)
```json
{
  "id": "uuid",
  "userId": "user-uuid",
  "documentId": "doc-uuid",
  "type": "INSERT | DELETE",
  "position": 42,
  "content": "hello",
  "length": 5,
  "revision": 8,
  "timestamp": 1712345678
}
```

### Client → Server: Cursor
```json
{
  "userId": "user-uuid",
  "line": 10,
  "column": 5
}
```

### Server → Client: Cursor (Broadcast)
```json
{
  "userId": "user-uuid",
  "username": "johndoe",
  "color": "#f59e0b",
  "line": 10,
  "column": 5
}
```

### Server → Client: Presence
```json
{
  "type": "JOIN | LEAVE",
  "userId": "user-uuid",
  "username": "johndoe",
  "color": "#f59e0b",
  "onlineUsers": [...]
}
```

---

## 🎨 UI Design Direction

The frontend must feel like a **professional developer tool** — dark, precise, minimal. Reference VS Code, Linear, and Vercel for aesthetic inspiration.

- **Theme:** Dark (background `#0d0d0d`, surface `#1a1a1a`, border `#2a2a2a`)
- **Accent:** Electric indigo `#6366f1` for primary actions
- **Font:** `JetBrains Mono` for editor, `Inter` for UI
- **Cursors:** Each user gets a unique bright color (assign from a palette of 8 distinct colors)
- **Cursor label:** Small pill above cursor showing username, same color as cursor
- **Presence bar:** Top-right corner, circular avatar dots with user initials + color
- **File tree:** Left sidebar, VS Code-style icons per language
- **Status bar:** Bottom bar showing: room name | document name | language | revision | sync status (green dot = synced, yellow = syncing)
- **Share button:** Top-right, copies room URL to clipboard with animation

---

## ✅ Definition of Done

The project is complete when ALL of the following are true:

- [ ] Two users in different browsers can edit the same document simultaneously with zero conflicts
- [ ] All OT unit tests pass
- [ ] Live cursors visible with correct positions across clients
- [ ] Presence bar shows/hides users on join/leave in real time
- [ ] File tree allows creating, switching, deleting documents
- [ ] Auth — register, login, protected routes all work
- [ ] JWT tokens expire and refresh correctly
- [ ] App is deployed on Railway and accessible via public URL
- [ ] Backend health endpoint returns 200
- [ ] README contains: project description, tech stack, architecture diagram, local setup instructions, live demo link
- [ ] No hardcoded secrets anywhere in the codebase
- [ ] Code is committed to GitHub with clean, meaningful commit history

---

*Blueprint version 1.0 — Built for CodeSync, a real-time collaborative code editor.*
*Agent: act as a Senior Full-Stack Engineer throughout. Write production code. Ship it.*
