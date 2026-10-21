# EventSphere 🎟️

A robust, enterprise-grade Event Ticketing and Management REST API built with **Java 22**, **Spring Boot 4**, **Spring Security (OAuth2/OIDC Resource Server)**, **Keycloak IAM**, and **PostgreSQL**.

EventSphere powers the entire event lifecycle: multi-role authentication (Organizers, Attendees, Staff), dynamic event scheduling, ticket inventory management, simulated transactions, and real-time QR code generation & validation for on-site staff.

---

## 🚀 Key Highlights & Architecture

- **OAuth2 / OIDC Role-Based Access Control (RBAC):** Integrated with Keycloak. JWT tokens are verified as a Spring Security Resource Server, extracting custom realm roles (`ROLE_ORGANIZER`, `ROLE_ATTENDEE`, `ROLE_STAFF`).
- **Just-in-Time (JIT) User Provisioning:** Custom `UserProvisioningFilter` intercepts authenticated requests and automatically synchronizes Keycloak user profiles into the PostgreSQL database.
- **Auditing & Entity Lifecycle:** Global JPA auditing via `AuditingEntityListener` managing `created_at` and `updated_at` timestamps across all persistent records.
- **Ticketing & QR Code Engine:** Built with Google ZXing to generate scannable 2D QR codes encoded with secure UUID payloads for fast entry scanning.
- **Dual Validation Pipeline:** Staff can validate tickets either via real-time camera QR scan or via manual ticket ID entry.
- **Clean Architecture & DTO Pattern:** Strict separation of concerns across Domain entities, Spring Data Repositories, Services, and REST Controllers using MapStruct-style mapping principles and Lombok.

---

## 🛠️ Tech Stack

### Backend
- **Language:** Java 22
- **Framework:** Spring Boot 4.1.x
- **Security:** Spring Security 6 (OAuth2 Resource Server with JWT)
- **Identity Provider (IAM):** Keycloak 26.x
- **Persistence & ORM:** Spring Data JPA, Hibernate ORM
- **Database:** PostgreSQL 18
- **Connection Pool:** HikariCP
- **QR Code Processing:** ZXing (`core` & `javase`)
- **Build Tool:** Maven

### Frontend (Client Interface)
- Modern React + TypeScript interface powered by Vite, Tailwind CSS, `react-oidc-context`, and `@yudiel/react-qr-scanner`.

---

## 🏗️ System Architecture & Workflow

Here is how requests and data flow through the platform:

```
[ User / Browser ] 
       │
       ▼ (1. Authenticate)
[ Keycloak IAM (OAuth2 / OIDC) ] ──▶ Issues Signed JWT Token with Realm Roles
       │
       ▼ (2. API Request with Bearer Token)
[ Spring Security Filter Chain ]
       │  ├── Validates JWT against Keycloak Public Keys
       │  └── JwtAuthenticationConverter (extracts ROLE_ORGANIZER / ATTENDEE / STAFF)
       ▼
[ UserProvisioningFilter ] ─────────▶ JIT sync: auto-saves user record to DB if first visit
       │
       ▼ (3. Authorized Request Dispatch)
┌────────────────────────────────────────────────────────┐
│                   REST Controllers                     │
├────────────────────┬──────────────────┬────────────────┤
│    EventController │ TicketController │ ValidationCtrl │
│    (Organizers)    │   (Attendees)    │    (Staff)     │
└─────────┬──────────┴────────┬─────────┴────────┬───────┘
          ▼                   ▼                  ▼
┌────────────────────────────────────────────────────────┐
│                    Service Layer                       │
│  - Event Lifecycle & Publishing Logic                  │
│  - Ticket Purchase & ZXing QR Generation Engine        │
│  - QR & Manual Entry Status Validation (PURCHASED->USED)│
└───────────────────────────┬────────────────────────────┘
                            ▼
┌────────────────────────────────────────────────────────┐
│                   Data Persistence                     │
│  - Spring Data JPA Repositories                        │
│  - Hibernate ORM + Global AuditingListener (orm.xml)   │
│  - PostgreSQL 18 Relational Storage                    │
└────────────────────────────────────────────────────────┘
```

---

## 👥 User Roles & Access Control

| Role | Permissions & Flow |
| :--- | :--- |
| **`ROLE_ORGANIZER`** | Create, edit, publish, and delete events; configure ticket types with custom pricing and descriptions; manage event lifecycles (`DRAFT`, `PUBLISHED`, `CANCELLED`). |
| **`ROLE_ATTENDEE`** | Discover public published events; purchase tickets; access personal ticket wallet with scannable QR code images generated on demand. |
| **`ROLE_STAFF`** | On-site entrance verification: scan attendee QR codes via device camera or manually input ticket IDs to validate and redeem tickets in real time. |

---

## 🔌 API Endpoints Reference

### 1. Events API
| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/events` | `ROLE_ORGANIZER` | List all events created by the logged-in organizer (paginated). |
| `POST` | `/api/v1/events` | `ROLE_ORGANIZER` | Create a new event with ticket tiers. |
| `GET` | `/api/v1/events/{id}` | `ROLE_ORGANIZER` | Get full event details for editing. |
| `PUT` | `/api/v1/events/{id}` | `ROLE_ORGANIZER` | Update an existing event. |
| `DELETE` | `/api/v1/events/{id}` | `ROLE_ORGANIZER` | Delete an event. |
| `GET` | `/api/v1/published-events` | `Public` | List all public events with `PUBLISHED` status. |
| `GET` | `/api/v1/published-events/{id}` | `Public` | View public event details and available ticket types. |

### 2. Tickets API
| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/tickets` | `ROLE_ATTENDEE` | List all tickets purchased by the authenticated user. |
| `POST` | `/api/v1/events/{eventId}/ticket-types/{ticketTypeId}/tickets` | `ROLE_ATTENDEE` | Purchase a ticket for an active event. |
| `GET` | `/api/v1/tickets/{id}` | `ROLE_ATTENDEE` | View ticket details and QR code image payload. |

### 3. Staff Validation API
| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/ticket-validations` | `ROLE_STAFF` | Validate and redeem a ticket via QR payload or manual ID entry. |

---

## ⚙️ Getting Started

### Prerequisites
- **JDK 22** or higher installed.
- **Docker & Docker Compose** (for PostgreSQL and Keycloak).
- **Node.js 18+** (for frontend UI).

### 1. Clone the Repository
```bash
git clone https://github.com/<your-username>/EventSphere.git
cd EventSphere
```

### 2. Start PostgreSQL & Keycloak (Docker)
Ensure your Docker containers for PostgreSQL and Keycloak are up and running:
- **PostgreSQL**: Port `5433` (Database: `postgres`)
- **Keycloak**: Port `9090` (Realm: `event-ticket-platform`)

> **Keycloak Setup Checklist:**
> - Realm: `event-ticket-platform`
> - Client: `event-ticket-platform-app` (Public client with PKCE enabled)
> - Realm Roles: `ROLE_ORGANIZER`, `ROLE_ATTENDEE`, `ROLE_STAFF`
> - Set `ROLE_ATTENDEE` as a Default Role for self-registered users.

### 3. Run Backend (Spring Boot)
Navigate to the backend directory and launch the application:
```powershell
cd EventSphere
.\mvnw.cmd spring-boot:run
```
The Spring Boot server will initialize on `http://localhost:8080`.

### 4. Run Frontend (Client App)
In a separate terminal:
```powershell
cd frontend
npm install
npm run dev
```
Access the client application at `http://localhost:5173`.

---

## 🔒 Security Configuration Details

The backend utilizes a specialized `SecurityConfig` configuring Spring Security's `oauth2ResourceServer`:
- **Realm Role Mapping:** Standard Keycloak tokens store roles under `realm_access.roles`. A custom `JwtAuthenticationConverter` maps these into Spring Security `GrantedAuthority` records prefixed with `ROLE_`.
- **Stateless Sessions:** CSRF is disabled and `SessionCreationPolicy` is set to `STATELESS`, ensuring true RESTful token-based authentication.
- **Auditing Integration:** Spring Data JPA's `AuditingEntityListener` is configured globally through `META-INF/orm.xml` to populate audit timestamps across all entity mutations seamlessly.

---

## 👨‍💻 Author

**Nitin S Shastri**
