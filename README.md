# ☕ Coffee Shop Telegram Bot

A Spring Boot web application for a coffee shop, featuring an online HTML template integrated with **MySQL** for data persistence and **Telegram Bot API** for real-time admin notifications.

---

## ✨ Features

This project includes four main feature modules, each with full client-side and server-side validation:

| Feature | Description |
|---|---|
| 🪑 **Table Reservation** | Customers can book a table with name, phone, email, date, time, and party size |
| 📬 **Contact Form** | Customers can send inquiries/messages directly to the admin |
| 📧 **Newsletter Subscription** | Email sign-up from both the homepage offer banner and the footer |
| 🤖 **Telegram Notifications** | Admin receives instant alerts for every reservation, contact message, and new subscriber |

All forms include:
- ✅ **Client-side validation** (JavaScript) — instant visual feedback, no page reload required for checks
- ✅ **Server-side validation** (Jakarta Bean Validation / `@Valid`) — cannot be bypassed, protects the database
- ✅ **Flash messages** — success/error feedback shown after form submission
- ✅ **MySQL persistence** — every submission is saved to the database
- ✅ **Telegram alerts** — admin is notified instantly via bot message

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.6 |
| Build Tool | Gradle |
| Template Engine | Thymeleaf |
| Database | MySQL 8.x |
| ORM | Spring Data JPA / Hibernate |
| Validation | Jakarta Bean Validation (`spring-boot-starter-validation`) |
| Bot Integration | [java-telegram-bot-api](https://github.com/pengrad/java-telegram-bot-api) (pengrad) |
| Frontend | HTML5, Bootstrap 4, jQuery, vanilla JavaScript |
| IDE | Spring Tool Suite (STS) / Eclipse / VS Code |

---

## 📁 Project Structure

```
coffee-shop-html-template/
├── src/
│   ├── main/
│   │   ├── java/com/setec/
│   │   │   ├── CoffeeShopHtmlTemplateApplication.java
│   │   │   ├── controller/
│   │   │   │   └── MyController.java          # All page + form endpoints
│   │   │   │   └── SubscriberController.java  # Newsletter REST endpoint
│   │   │   ├── entities/
│   │   │   │   ├── Booked.java                # Reservation entity
│   │   │   │   ├── Contact.java                # Contact message entity
│   │   │   │   └── Subscriber.java             # Newsletter subscriber entity
│   │   │   ├── repos/
│   │   │   │   ├── BookedRepo.java
│   │   │   │   ├── ContactRepo.java
│   │   │   │   └── SubscriberRepo.java
│   │   │   └── services/
│   │   │       ├── ContactService.java
│   │   │       ├── SubscriberService.java
│   │   │       └── MyTelegramBot.java          # Shared Telegram bot service
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/
│   │       │   ├── base.html        # Shared fragments: head, navbar, header, reservation, footer, scripts
│   │       │   ├── index.html       # Home page (carousel, offer, reservation)
│   │       │   ├── contact.html     # Contact page
│   │       │   ├── reservation.html # Reservation page
│   │       │   ├── success.html     # Booking confirmation page
│   │       │   ├── about.html
│   │       │   ├── service.html
│   │       │   ├── menu.html
│   │       │   └── testimonial.html
│   │       └── static/
│   │           ├── css/
│   │           ├── img/
│   │           │   └── favicon.ico
│   │           └── js/
├── build.gradle
└── README.md
```

---

## ✅ Prerequisites

Before running this project, make sure you have:

- **Java 21** (JDK) installed
- **MySQL Server** running locally (or accessible remotely)
- **Gradle** (wrapper included — no separate install needed)
- A **Telegram Bot Token** and **Chat ID** ([create one via @BotFather](https://t.me/BotFather))
- IDE: **Spring Tool Suite**, **Eclipse**, or **VS Code** with Java extensions

---

## 🚀 Setup & Installation

### 1. Clone the project
```bash
git clone <your-repo-url>
cd coffee-shop-html-template
```

### 2. Create the MySQL database
The app automatically creates the database on first run (`createDatabaseIfNotExist=true`), so you only need MySQL running — no manual `CREATE DATABASE` required.

### 3. Configure environment variables (recommended)
Instead of hardcoding credentials, set these environment variables:

```bash
DATABASE_URL=jdbc:mysql://localhost:3306/db_coffeeshop?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false
DATABASE_USERNAME=root
DATABASE_PASSWORD=your_password
```

### 4. Run the application

**Using Gradle wrapper:**
```bash
./gradlew bootRun
```

**Using your IDE:**
Right-click `CoffeeShopHtmlTemplateApplication.java` → **Run As → Spring Boot App**

### 5. Open in browser
```
http://localhost:8080/home
```

---

## ⚙️ Configuration

`src/main/resources/application.properties`:

```properties
spring.application.name=coffee-shop-html-template

# Telegram Bot
token=YOUR_TELEGRAM_BOT_TOKEN
chat_id=YOUR_TELEGRAM_CHAT_ID

# MySQL Database
spring.datasource.url=${DATABASE_URL:jdbc:mysql://localhost:3306/db_coffeeshop?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false}
spring.datasource.username=${DATABASE_USERNAME:root}
spring.datasource.password=${DATABASE_PASSWORD:}

# Hikari Connection Pool
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=1200000
spring.datasource.hikari.auto-commit=true

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=true
spring.jpa.properties.hibernate.format_sql=true
```

> ⚠️ **Security note:** Never commit real tokens/passwords to version control. Use environment variables or a `.env` file excluded via `.gitignore` in production.

---

## 🗄 Database Schema

### `booked` table

| Column | Type | Constraints |
|---|---|---|
| id | INT (IDENTITY) | Primary Key |
| name | VARCHAR | NOT NULL, 2–100 chars |
| phone_number | VARCHAR | NOT NULL, 9–12 digits |
| email | VARCHAR | NOT NULL, valid email format |
| date | VARCHAR | NOT NULL |
| time | VARCHAR | NOT NULL |
| person | INT | NOT NULL, min 1 |
| booked_at | DATETIME | Auto-set on insert |

### `contacts` table

| Column | Type | Constraints |
|---|---|---|
| id | BIGINT (AUTO) | Primary Key |
| name | VARCHAR | NOT NULL, 2–100 chars |
| email | VARCHAR | NOT NULL, valid email format |
| subject | VARCHAR | NOT NULL, 2–200 chars |
| message | TEXT | NOT NULL, 5–1000 chars |
| submitted_at | DATETIME | Auto-set on insert |

### `subscribers` table

| Column | Type | Constraints |
|---|---|---|
| id | BIGINT (AUTO) | Primary Key |
| email | VARCHAR | NOT NULL, UNIQUE, valid email format |
| source | VARCHAR | "offer" or "footer" |
| subscribed_at | DATETIME | Auto-set on insert |

> 💡 Note: IDs do not reuse gaps after deletion (e.g. deleting id=2 means it's gone forever, next insert continues from the highest existing id). This is normal MySQL `AUTO_INCREMENT`/`IDENTITY` behavior and is intentional for data integrity — not a bug.

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/` , `/home` | Home page |
| GET | `/about` | About page |
| GET | `/service` | Service page |
| GET | `/menu` | Menu page |
| GET | `/testimonial` | Testimonial page |
| GET | `/reservation` | Reservation form page |
| POST | `/success` | Submit reservation (validates → saves → notifies) |
| GET | `/success` | Booking confirmation page |
| GET | `/contact` | Contact form page |
| POST | `/contact` | Submit contact message (validates → saves → notifies) |
| POST | `/subscribe` | Newsletter sign-up (AJAX, returns JSON) |

### `/subscribe` response format

```json
{
  "status": "SUCCESS | DUPLICATE | INVALID | ERROR",
  "message": "Human-readable message"
}
```

---

## 🔒 Validation Rules

Every form uses **two layers of validation**:

```
User Input
    │
    ▼
1️⃣ JavaScript (client-side)
    • Instant feedback, no server round-trip
    • Can be bypassed (disabled JS, Postman, curl)
    │
    ▼
2️⃣ Jakarta Validation (server-side, @Valid)
    • Cannot be bypassed
    • Final gatekeeper before DB save
    │
    ▼
Database (MySQL)
```

| Field | Rule |
|---|---|
| Name | Required, 2–100 characters |
| Phone Number | Required, 9–12 digits (`+` allowed) |
| Email | Required, valid format with domain (e.g. `.com`, `.net`) |
| Subject | Required, 2–200 characters |
| Message | Required, 5–1000 characters |
| Date / Time | Required (selected via date/time picker) |
| Person | Required, minimum 1 |

---

## 🤖 Telegram Bot Notifications

The shared `MyTelegramBot` service sends formatted messages to the configured `chat_id` for every successful action:

**New Reservation:**
```
📌 New Reservation
👤 Name  : ...
📞 Phone : ...
📧 Email : ...
📅 Date  : ...
⏰ Time  : ...
👥 Person: ...
```

**New Contact Message:**
```
📬 New Contact Message
👤 Name    : ...
📧 Email   : ...
📌 Subject : ...
💬 Message : ...
```

**New Newsletter Subscriber:**
```
🔔 New Newsletter Subscriber
📧 Email  : ...
📍 Source : offer / footer
```

---

## 🧪 Testing

### Manual testing checklist

| Test | Tool | Expected Result |
|---|---|---|
| Empty form submit | Browser | All fields show red validation errors |
| Invalid email (`user@gmail`) | Browser | Blocked client-side AND server-side |
| Invalid phone (13+ digits) | Browser | Blocked client-side AND server-side |
| Duplicate newsletter email | Browser | "Already subscribed" warning |
| Bypass JS validation | Postman / curl | Server still rejects invalid data (`@Valid`) |
| Valid full submission | Browser | Redirect → success message → DB row inserted → Telegram alert received |

### Testing with Postman (bypass client-side JS)

```
POST http://localhost:8080/success
Content-Type: x-www-form-urlencoded

name=A&phoneNumber=123&email=bademail&date=&time=&person=0
```
Expected: `302` redirect back to `/reservation` with validation errors — **no database insert**.

> 💡 Tip: In Postman, disable "Automatically follow redirects" under Settings to inspect the raw `302` response and `Location` header.

### Verify database directly

```sql
SELECT * FROM db_coffeeshop.booked ORDER BY booked_at DESC;
SELECT * FROM db_coffeeshop.contacts ORDER BY submitted_at DESC;
SELECT * FROM db_coffeeshop.subscribers ORDER BY subscribed_at DESC;
```
