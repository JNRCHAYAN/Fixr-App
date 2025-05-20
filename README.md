# 🔧 Fixr – Instant Help & Services

Fixr is an Android-based service marketplace app that connects users with local service providers in real-time. Whether you need help from a plumber, electrician, cleaner, or technician, Fixr makes it quick and easy to find and contact service providers near you.

---

## 💡 Key Features

### 👤 User Side
- **Firebase Authentication** – Secure email/password sign-up and login.
- **Browse Services** – View a categorized list of available services.
- **Service Details** – Access detailed info including price, contact, and schedule.
- **Call Directly** – One-tap calling to reach service providers.
- **User Profile** *(Optional)* – View and edit your profile.

### 🧑‍🔧 Service Provider Side
- **Firebase Auth Login** – Secure login for service providers.
- **Provider Dashboard** – Easy access to manage services and settings.
- **Add/Edit Services** – Add new services with title, description, price, contact.
- **My Services Page** – View, update, and delete listed services.
- **Settings Page** – Edit provider profile information.

---

## ✅ Functional Requirements

### 👤 User Side
- Sign up/login with Firebase Authentication
- Password reset
- Browse & filter service categories
- View service details (title, price, contact)
- One-tap call to provider

### 🧑‍🔧 Provider Side
- Login using Firebase Auth
- Dashboard with navigation
- Add new service (name, description, price, contact)
- Manage own services (edit/delete)
- Edit provider profile in settings

---

## 💻 Technical Requirements

- **Backend**:
  - Firebase Authentication
  - Firebase Realtime Database
  - Firebase Rules for secure access

---

## 🗂️ Database Structure (JSON Example)

```json
{
  "ServiceProviders": {
    "{uid}": {
      "name": "Provider Name",
      "email": "example@mail.com",
      "phone": "1234567890",
      "servicelist": {
        "{serviceId}": {
          "title": "Plumbing Help",
          "description": "Fix pipe leaks and more",
          "price": "200",
          "contact": "1234567890"
        }
      }
    }
  },
  "Users": {
    "{uid}": {
      "fullName": "User Name",
      "email": "user@mail.com",
      "address": "User Address",
      "username": "user123"
    }
  }
}
```

---

## 🎨 UI/UX Requirements

### Android Screens (Kotlin + XML)
- Splash Screen
- Login & Signup Screen
- User Home Page (List of Services)
- Service Detail Page
- Provider Home Page (with Drawer Navigation)
- Add Service Page
- My Services Page
- Settings Page

### UI Design Principles
- ConstraintLayout for responsive UI
- Material Design buttons, inputs, cards
- DrawerLayout + NavigationView for provider dashboard
- CardView for displaying services
- Icons from Material or Android default set

---

## 🧰 Tools & Libraries

| Tool/Library        | Purpose                              |
|---------------------|--------------------------------------|
| Android Studio      | Development Environment              |
| Kotlin              | Primary Language                     |
| XML                 | UI Layouts                           |
| Firebase Auth       | Authentication                      |
| Firebase Realtime DB| Data Storage                         |
| Firebase Console    | Backend Management                   |
| Glide/Coil *(opt.)* | Image Loading                        |
| Jetpack Navigation *(opt.)* | Navigation between screens |

---

## 📝 Optional Advanced Features (Future Updates)

- ⭐ User ratings & reviews
- 🗓️ Booking & scheduling
- 🔔 Notifications using FCM
- 📍 Google Maps integration
- 🟢 Real-time service availability
- 🛡️ Admin panel to monitor providers and services

---

## 📸 Screenshots

*(Add screenshots of the UI here in `/screenshots` folder)*

---

## 🙋‍♂️ Developer

**Rohit**  
Android Developer | Firebase Enthusiast

---

## 📬 Contact

📧 Email: your.email@example.com  
🌐 GitHub: [github.com/yourusername](https://github.com/yourusername)

---

## 📁 Recommended GitHub Folder Structure

```
Fixr/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/yourcompany/fixr/
│   │   │   │   ├── activities/
│   │   │   │   ├── adapters/
│   │   │   │   ├── models/
│   │   │   │   ├── utils/
│   │   │   │   └── firebase/
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       ├── drawable/
│   │   │       ├── values/
├── screenshots/
│   └── (UI preview images)
├── docs/
│   └── database_structure.md
├── README.md
└── .gitignore
```
