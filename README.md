# CodeSync — Real-Time Collaborative Code Editor

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.2-green?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/React-18-blue?style=for-the-badge&logo=react" />
  <img src="https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql" />
  <img src="https://img.shields.io/badge/Redis-7-red?style=for-the-badge&logo=redis" />
  <img src="https://img.shields.io/badge/Docker-Compose-blue?style=for-the-badge&logo=docker" />
</p>

**CodeSync** is a browser-based collaborative code editor where multiple users can edit the same file simultaneously in real time. Conflicts are resolved using the **Operational Transformation (OT)** algorithm — the same algorithm that powers Google Docs.

## ✨ Features

- 🔄 **Real-time multi-user editing** — same file, same room, zero conflicts
- 🎯 **Live cursor tracking** — see every user's cursor position with colored labels
- 🏠 **Room system** — create and join rooms via shareable links
- 📁 **File tree** — create, rename, delete files per room
- 🎨 **Syntax highlighting** — 15+ languages via Monaco Editor (VS Code engine)
- 📜 **Version history** — replay any past document state
- 👥 **User presence** — see who is online in a room
- 🔐 **JWT authentication** — secure register/login flow
- 🚀 **Fully deployed** — accessible via public URL

## 🛠️ Tech Stack

| Layer | Technologies |
|-------|-------------|
| **Backend** | Java 21, Spring Boot 3.2, Spring WebSocket, Spring Security, Spring Data JPA |
| **Frontend** | React 18, Vite 5, Monaco Editor, Zustand, TailwindCSS |
| **Database** | PostgreSQL 16 (primary), Redis 7 (sessions + cursors) |
| **Real-time** | WebSocket + STOMP protocol, Operational Transformation |
| **Infra** | Docker Compose, Railway (backend), Vercel (frontend) |

## 📁 Project Structure

```
codesync/
├── backend/          # Spring Boot API + WebSocket server
│   ├── src/main/java/com/codesync/
│   │   ├── config/       # WebSocket, Security, Redis, CORS configs
│   │   ├── controller/   # REST API controllers
│   │   ├── websocket/    # WebSocket message handlers
│   │   ├── ot/           # Operational Transformation engine
│   │   ├── service/      # Business logic layer
│   │   ├── repository/   # JPA repositories
│   │   ├── model/        # JPA entities
│   │   ├── dto/          # Request/Response DTOs
│   │   ├── security/     # JWT auth components
│   │   └── exception/    # Global error handling
│   └── src/main/resources/
│       └── db/migration/ # Flyway SQL migrations
├── frontend/         # React + Vite SPA
│   └── src/
│       ├── components/   # Reusable UI components
│       ├── pages/        # Route pages
│       ├── hooks/        # Custom React hooks
│       ├── store/        # Zustand state stores
│       ├── services/     # API + WebSocket clients
│       └── utils/        # OT client + helpers
├── docker-compose.yml    # Local dev environment
└── README.md
```

## 🚀 Getting Started

> Full setup instructions coming soon.

## 📄 License

MIT
