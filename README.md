# 🔧 Fixr – Instant Help & Services

Fixr is an Android-based service marketplace app that connects users with local service providers instantly. Whether you need a plumber, electrician, cleaner, or technician, Fixr lets you find, book, and contact help in real-time with just a few taps.

---

## 💡 Key Features

### ✅ User Features
- **Easy Sign Up/Login**: Secure authentication using Firebase Auth.
- **Browse Services**: Explore various service categories with intuitive navigation.
- **View Service Details**: Includes description, availability, pricing, and contact info.
- **Call Provider Directly**: One-tap call to the service provider.
- **Rate & Review** *(Upcoming)*: Leave feedback to ensure quality services.

### 🛠️ Service Provider Features
- **Provider Dashboard**: Personalized dashboard for managing services.
- **Add/Edit Services**: Add or update services with Firebase Realtime Database integration.
- **My Services**: Manage the list of provided services.
- **Settings**: Edit profile and service details.

---

## 🧱 Technical Stack

- **Frontend**: Android (Kotlin + XML)
- **Backend**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **UI Tools**: `ConstraintLayout`, `DrawerLayout`, `NavigationView`

---

## 🗂️ Firebase Realtime Database Structure

```json
{
  "Users": {
    "uid": {
      "fullName": "User Name",
      "email": "user@example.com",
      "address": "Location",
      "username": "username123"
    }
  },
  "ServiceProviders": {
    "providerId": {
      "fullName": "Provider Name",
      "email": "provider@example.com",
      "address": "Location",
      "username": "providername",
      "serviceType": "Plumber | Electrician | etc.",
      "servicelist": {
        "serviceId": {
          "title": "Service Title",
          "description": "Full description of the service",
          "category": "Service Category",
          "availableDays": ["Mon", "Tue", ...],
          "availableTime": "HH:MM",
          "location": "City",
          "minPrice": "xxx",
          "maxPrice": "xxx",
          "phone": "Mobile Number",
          "providerId": "same as above",
          "serviceId": "unique id"
        }
      }
    }
  },
  "servicelist": {
    "serviceId": {
      "title": "Same as above",
      "description": "Same as above",
      ...
    }
  }
}
