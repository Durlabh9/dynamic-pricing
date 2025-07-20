
# 📈 Dynamic Pricing System

An intelligent pricing engine that scrapes competitor prices, predicts optimal selling prices using an ML-powered backend, and stores results in PostgreSQL.

## 📚 Table of Contents

- [🚀 Features](#-features)
- [🛠️ Tech Stack](#️-tech-stack)
- [📁 Project Structure](#-project-structure)
- [⚙️ Setup Guide](#️-setup-guide)
- [🧩 Step-by-Step Usage](#-step-by-step-usage)
- [📊 Sample Output](#-sample-output)
- [📌 Future Improvements](#-future-improvements)
- [🤝 Contributions](#-contributions)


## 🚀 Features

- ✅ Real-time price scraping from **Amazon** and **Flipkart**
- 🧠 Price prediction using a **Spring Boot ML-based API**
- 🗃️ Data storage and retrieval via **PostgreSQL**
- 🔁 Product price history tracking
- 🧩 Modular architecture for easy extension to other platforms

## 🛠️ Tech Stack

| Layer      | Technology             |
|------------|------------------------|
| Backend    | Spring Boot            |
| Scraper    | Python + Selenium      |
| Database   | PostgreSQL             |
| ML Logic   | Java-based prediction logic |

## 📁 Project Structure

dynamic-pricing/
├── .vscode/ # VSCode configuration
│ └── launch.json
│
├── ml-model/ # Python-based ML service & scraping
│ ├── demand-service/
│ │ ├── demand_api.py # Flask API for ML pricing logic
│ │ └── requirements.txt
│ ├── ml_model/
│ │ ├── database.py # DB interaction for ML layer
│ │ └── amazon_price_collector.py # Web scraper (e.g., Amazon)
│ └── requirements.txt
│
├── src/main/java/com/dynamicpricing/ # Spring Boot Backend
│ ├── controller/
│ │ └── ProductPriceController.java # REST API endpoints
│ ├── dto/
│ │ └── PricePredictionRequest.java # DTO for prediction input
│ ├── model/
│ │ └── ProductPrice.java # JPA entity for prices
│ ├── repository/
│ │ └── ProductPriceRepository.java # Spring Data JPA repository
│ ├── scheduler/
│ │ └── PriceUpdateScheduler.java # Scheduled scraping tasks
│ ├── service/
│ │ ├── DemandService.java
│ │ ├── PricePredictionService.java
│ │ └── ProductPriceService.java
│ ├── utilities/
│ │ └── (any utility/helper classes)
│ └── DynamicPricingApplication.java # Main Spring Boot application
│
├── src/main/resources/
│ └── application.properties # Spring Boot config
│
├── pom.xml # Maven build configuration
└── target/ # Compiled classes and JAR (auto-generated)


## ⚙️ Setup Guide

### 1. Clone the Repository

```bash
git clone https://github.com/Durlabh9/dynamic-pricing.git
cd dynamic-pricing
```

### 2. Install Python Dependencies

```bash
cd price_collector
pip install -r requirements.txt
```

### 3. Set Up PostgreSQL

- Create a PostgreSQL database (e.g., `pricing_db`)
- Update database credentials in `application.properties` inside `dynamic_model/`

### 4. Build and Start Spring Boot Backend

```bash
cd dynamic_model
./mvnw spring-boot:run
```

Or open the project in IntelliJ IDEA / Eclipse and run it.

## 🧩 Step-by-Step Usage

1. **Run Backend**
   - Ensure PostgreSQL is running and Spring Boot API is active.

2. **Start the Scraper**
   ```bash
   cd price_collector
   python main.py
   ```

3. **Input Product Details When Prompted**
   - 📝 Product name (e.g., `iPhone 15`)
   - 💰 Base cost (e.g., `65000`)
   - 📦 Inventory level (e.g., `10`)
   - 📈 Profit margin (e.g., `0.15` for 15%)

4. **System Flow**
   - 🛒 Scrapes competitor prices from Amazon & Flipkart
   - 🧠 Sends data to Spring Boot backend for prediction
   - 🗂️ Saves result to PostgreSQL
   - 📊 Displays current prediction and previous pricing history

## 📊 Sample Output

```
Enter product name: iPhone 15
Enter base cost: 65000
Enter inventory level: 12
Enter margin : 0.15

✅ Amazon: 3 prices sent to backend.
✅ Flipkart: 2 prices sent to backend.

🔍 Predicted Prices for: 'iPhone 15'
------------------------------------------------------------
📦 Product Title : Apple iPhone 15 (128GB, Blue)
💰 Competitor ₹  : 69999
📈 Predicted ₹   : 68449
🔗 Source        : Flipkart
🕒 Fetched At    : 2025-07-15T10:30:15
```

## 📌 Future Improvements

- 🔮 Integrate **Google Trends** or AI demand indicators
- 🌐 Add a **React** or **Angular** frontend dashboard
- 🧩 Extend support to other platforms: **Croma**, **Reliance Digital**, etc.
- 📤 Enable price auto-update to seller platforms via API (if available)
- 📉 Add historical charts and demand analytics visualization

## 🤝 Contributions

Contributions are welcome and appreciated!  
Feel free to fork this repository, improve the code, and submit a pull request.

