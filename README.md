# TASHKEELA · Oman Football Manager

A fantasy football web app for the Oman Professional League.
Users build an 11-player squad within a budget, follow AI player ratings, and compete on a leaderboard.

## Tech stack

- **Frontend:** HTML, CSS, JavaScript
- **Backend:** Java 17, Spring Boot 3.5, Spring Security (JWT)
- **Database:** MariaDB / MySQL, Spring Data JPA
- **Build:** Maven

## Architecture

```
Browser (HTML · CSS · JS)
        │  REST + JSON, JWT in the Authorization header
        ▼
Spring Security  →  checks the token and the role (USER / ADMIN)
        ▼
Controllers      →  REST endpoints
        ▼
Services         →  business logic
        ▼
Repositories     →  Spring Data JPA
        ▼
Database (tashkeeladb)
```

The frontend is served by Spring Boot from `src/main/resources/static`, so the whole app runs on one port.

```
src/main/java/com/omanfootball/manager/
├── config/        startup setup
├── controller/    REST endpoints
├── dto/           request and response objects
├── entity/        database tables
├── exception/     error handling
├── repository/    database access
├── security/      security rules and JWT filter
└── service/       business logic
```

## Getting started

### Prerequisites

- Java 17+
- Maven
- MariaDB or MySQL

### Steps

1. Clone the repository and open it in IntelliJ.
2. Import `tashkeeladb_backup.sql` into your database to create `tashkeeladb` with demo data.
3. Check the database URL in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mariadb://127.0.0.1:3307/tashkeeladb
   ```
4. Set the [environment variables](#environment-variables).
5. Run the app:
   ```bash
   mvn spring-boot:run
   ```
6. Open `http://localhost:8080`.

Login sends a 6-digit code to the user's email. The code is also printed in the console.

## Team

| Role | Member |
|---|---|
| `Team Leader` | `Salwa Said` |
| `Team Members` | `Almonther Alwaili - Mohammed Alhamrashdi - Rashid Alshibli - Salima Alsaidi ` |
| `Supervisor` | `Fatma Al-mamari & Is'haq Al-balushi` |


In IntelliJ: `Run` → `Edit Configurations` → `Environment variables`.

## API

All endpoints except `/auth/**` need the header `Authorization: Bearer <token>`.

### Standard endpoints

| Method | Path |
|---|---|
| `GET` | `/{resource}/all` |
| `GET` | `/{resource}/by-id/{id}` |
| `POST` | `/{resource}/create` |
| `PUT` | `/{resource}/update/{id}` |
| `DELETE` | `/{resource}/delete/{id}` |

Resources: `players`, `clubs`, `matches`, `users`, `fantasy-teams`, `fantasy-team-players`, `fantasy-points`, `ai-ratings`, `rating-history`, `ai-fantasy-points`, `ai-match-reports`, `match-statistics`, `performances`, `player-comparisons`, `starting-xi`, `challenges`, `user-challenges`, `club-dashboards`, `simulations`, `shared-teams`.

### Authentication

| Method | Path | Description |
|---|---|---|
| `POST` | `/auth/register` | Register |
| `POST` | `/auth/login` | Log in and send OTP |
| `POST` | `/auth/verify-otp` | Verify OTP and get a token |
| `POST` | `/auth/forgot-password` | Send a reset code |
| `POST` | `/auth/reset-password` | Reset the password |
| `GET` | `/users/me` | Current user |

### Fantasy team

| Method | Path | Description |
|---|---|---|
| `GET` | `/fantasy-teams/mine` | My squad |
| `POST` | `/fantasy-teams/mine` | Create my squad |
| `POST` | `/fantasy-teams/mine/players/{playerId}` | Add a player |
| `DELETE` | `/fantasy-teams/mine/players/{entryId}` | Remove a player |
| `PUT` | `/fantasy-teams/mine/formation` | Change formation |
| `GET` | `/leaderboard/all` | Leaderboard |

### AI and analysis

| Method | Path | Description |
|---|---|---|
| `GET` | `/ai/player/{id}` | Analyze a player |
| `GET` | `/rating-history/player/{playerId}` | Player rating history |
| `POST` | `/ai-match-reports/generate/{matchId}` | Match report |
| `POST` | `/starting-xi/generate/{matchId}` | Recommended starting XI |
| `POST` | `/player-comparisons/generate` | Compare two players |

### Other

| Method | Path | Description |
|---|---|---|
| `GET` `POST` | `/simulations/mine` | What-If simulator |
| `POST` | `/shared-teams/mine` | Share my squad |
| `GET` | `/shared-teams/by-code/{shareCode}` | View a shared squad |
| `PUT` | `/user-challenges/complete/{id}` | Complete a challenge |
| `POST` | `/performances/record` | Record a player performance |
| `PUT` | `/match-statistics/for-match/{matchId}` | Save match statistics |
| `POST` | `/admin/users` | Create a user (admin) |
| `DELETE` | `/admin/users/delete/{id}` | Delete a user (admin) |
| `POST` | `/admin/send-email` | Send an email (admin) |

## Tests

```bash
mvn test
```
