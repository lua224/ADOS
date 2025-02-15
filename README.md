# ADOS

**ADOS** is an Android application designed to monitor the positions of ADOS buses in real time and manage user-specific blood donation information. The application fetches weekly bus positions by city via an external API and offers a settings screen for personalized data management.

---

## **Features**

### 1. **Monitor Bus Positions**
- Retrieve weekly positions of ADOS buses in different cities through an API.
- Display the bus positions dynamically in the app.
- Filter and view buses by city for better navigation and planning.

### 2. **Personalized Settings**
- Save and manage user-specific information, including:
    - **Blood Type**: Record your blood type for easy access.
    - **Donation History**: Log the dates, locations, and frequency of your blood donations.
- Review your donation history directly from the settings screen.

---

## **Technical Details**

### **API Integration**
- The app connects to an external API to fetch weekly bus position data using Okhttp3 and Retrofit.
- Data is retrieved in JSON format and displayed in the app.

### **Storage**
- User data (blood type and donation history) is stored locally using Jetpack Room and SQLite for persistence.

### **Architecture**
- The app follows the **MVVM (Model-View-ViewModel)** architecture for maintainability and scalability.

### **UI Design**
- Built with **Jetpack Compose**, adhering to Material Design 3 principles for a modern and intuitive user interface.

---
