# 🔧 Fixr – Instant Help & Services (Mobile App)

A smart and easy-to-use Android app that connects users with local service providers for instant help. Built using Kotlin, XML, and Firebase, Fixr aims to simplify the way users request services like plumbing, electrical work, or home cleaning.

---

## 📘 Executive Summary

Fixr enhances the user experience of connecting with local help through:
- Clean and user-friendly design
- Real-time Firebase integration
- Separate flows for users and service providers
- Direct calling and service detail views

---

## 🎯 Objectives

- Help users easily discover nearby service providers
- Let providers manage services with ease
- Ensure real-time updates and fast interactions
- Secure and scalable app structure

---

## ✅ Functional Requirements

### 👤 User Side
- Email/password registration & login (Firebase)
- Browse and search for services by category or location
- View service details (provider name, contact, price)
- One-tap call to providers
- (Optional) View/Edit profile

### 🧑‍🔧 Provider Side
- Login/Register via Firebase
- Provider dashboard with Add, Edit, Delete service
- Profile settings with editable business details
- My Services list to view/manage listings

### 🧾 Customer Management (Provider Feature)
- Create and manage customer profiles
- Generate and update service invoices
- Save invoice history securely

---

## 🔐 Non-functional Requirements

- 🔐 Secure Firebase Authentication & Database Rules
- ⚡ Real-time database access with Firebase
- 📱 Responsive UI with fast navigation
- ☁️ Scalability to thousands of users
- 🔒 HTTPS encrypted communication

---

## 🧱 Architecture

### 📂 Data-Centered Architecture
- Central Firebase DB to store service, user, booking & invoice data

### 🎛️ MVC Architecture
- Model: Firebase interaction, data logic
- View: User Interface using XML
- Controller: Activity and Fragment control flows

---

## 📊 Database Structure

```json
{
  "ServiceProviders": {
    "uid123": {
      "name": "Provider Name",
      "email": "email@example.com",
      "phone": "0123456789",
      "servicelist": {
        "serviceId001": {
          "title": "AC Repair",
          "description": "Fix AC issues",
          "price": "1000",
          "contact": "0123456789"
        }
      }
    }
  },
  "Users": {
    "uid456": {
      "fullName": "User Name",
      "email": "user@example.com",
      "address": "Saidpur"
    }
  }
}
```

---

## 🧪 Test Cases

| ID      | Description                          | Expected Outcome                       |
|---------|--------------------------------------|----------------------------------------|
| UC-001  | User login validation                | Successful login                       |
| UC-002  | Service provider registration        | Account created and saved in DB        |
| UC-003  | Search and view service              | Relevant services displayed            |
| UC-004  | Service booking (future scope)       | Booking saved and notified             |
| UC-005  | Customer profile creation            | Profile saved and editable             |

---

## 🖥️ UI Screens

- Splash Screen
- Login & Signup
- User Homepage (Service list)
- Service Detail
- Provider Dashboard (Drawer)
- Add Service
- My Services
- Settings

---

## 🛠️ Tools & Technologies

| Tool / Tech     | Usage                                     |
|-----------------|-------------------------------------------|
| Kotlin          | Main programming language                 |
| XML             | Layout design                             |
| Firebase        | Authentication, Realtime DB               |
| Android Studio  | Development IDE                           |
| Figma           | UI/UX design prototype                    |
| Firebase Rules  | Secure data access                        |

---

## 🧩 API Endpoints

- `/createServiceProviderAccount`
- `/login`
- `/searchService`
- `/bookService`
- `/createUserProfile`
- `/updateServiceDetails`

---

## 🚀 Future Features

- ⭐ Ratings & Reviews
- 📅 Booking & Scheduling
- 🔔 Push Notifications
- 🗺️ Google Maps Integration
- 🛡️ Admin Dashboard

---

## 👨‍💻 Team Member

**Jogendra Nath Roy Chayan**  
ID: 082310405101058  
📧 jnrchayan@gmail.com  
📱 01723193226  

---

## 📁 Suggested GitHub Structure

```
Fixr/
├── app/
│   └── src/main/java/com/fixr/
│       ├── activities/
│       ├── adapters/
│       ├── firebase/
│       ├── models/
│       ├── utils/
│   └── res/
│       ├── layout/
│       ├── drawable/
│       ├── values/
├── screenshots/
├── docs/
├── README.md
└── .gitignore
```

---

## 🗃️ Firebase Realtime Database Outer Structure

```json
{
  "ServiceProviders": {
    "{providerId}": {
      "address": "",
      "email": "",
      "fullName": "",
      "serviceType": "",
      "username": "",
      "servicelist": {
        "{serviceId}": {
          "availableDays": [],
          "availableTime": "",
          "category": "",
          "description": "",
          "location": "",
          "maxPrice": "",
          "minPrice": "",
          "phone": "",
          "providerId": "",
          "serviceId": "",
          "title": ""
        }
      }
    }
  },
  "Users": {
    "{userId}": {
      "address": "",
      "email": "",
      "fullName": "",
      "username": ""
    }
  },
  "servicelist": {
    "{serviceId}": {
      "availableDays": [],
      "availableTime": "",
      "category": "",
      "description": "",
      "location": "",
      "maxPrice": "",
      "minPrice": "",
      "phone": "",
      "providerId": "",
      "serviceId": "",
      "title": ""
    }
  }
}
```
