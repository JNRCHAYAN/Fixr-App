# Fixr – Instant Help & Services 🚀

Fixr is an Android application that connects users with local service providers in real time. Whether you need a plumber, electrician, mechanic, or home cleaning, Fixr makes it easy to find trusted professionals near you, view their services, and contact them instantly.

---

## 📱 Features

- 🔐 **User & Provider Login/Signup** (Firebase Auth)
- 🧑‍🔧 **Service Provider Dashboard**  
  - Add, view, edit, or delete services
  - Manage service information (title, description, price, category)
- 👤 **User Dashboard**  
  - View available services
  - Call the service provider directly
- 🔍 **Service Search & Filtering**  
  - Browse by category or location
- ⚙️ **Settings & Profile Management**  
  - Update account info (name, email, phone)
- 🔔 **Real-Time Updates**  
  - All data managed through Firebase Realtime Database

---

## 🗃️ Firebase Database Structure

```plaintext
ServiceProviders
 └── UID
     ├── name
     ├── email
     ├── phone
     └── servicelist
          └── serviceId
              ├── title
              ├── description
              ├── price
              ├── category
              └── contactNumber
