📈 Dynamic Pricing System
A smart dynamic pricing engine that automatically scrapes product prices from Amazon and Flipkart, predicts an optimal selling price based on cost, margin, demand, inventory, and competitor prices, and stores the results in a PostgreSQL database.

🚀 Features
  ✅ Scrapes prices from Amazon and Flipkart
  🧠 Predicts selling price using a Spring Boot ML-based API
  🗃️ Stores product data and predicted price in PostgreSQL
  📦 Fetches and displays previous predictions for the same product
  🔌 Modular codebase for easy integration with other ecommerce platforms or pricing strategies

  🛠️ Tech Stack
    Layer	        Technology
    Backend	      Spring Boot
    Scraper	      Python + Selenium
    Database	    PostgreSQL
    ML Model	    Java-based Prediction Logic

  📁 Project Structure
    


  ⚙️ Setup Guide
  1. Clone the Repository
    git clone https://github.com/your-username/dynamic-pricing.git
    cd dynamic-pricing
  2. Install Python Dependencies
    cd price_collector
    pip install -r requirements.txt
3. Set up PostgreSQL
    Create a PostgreSQL database (e.g., pricing_db) and update the credentials in your backend config.

4. Start Spring Boot Backend

    cd dynamic_model
    ./mvnw spring-boot:run
Or using IntelliJ/IDEA.

5. Run the Scraper
    cd price_collector
    python main.py

   
You'll be prompted for:
  Product name (e.g., Samsung Galaxy S24)
  Base price
  Inventory level
  Profit margin

This will:
  Scrape data from Amazon & Flipkart
  Predict optimal price
  Send all to backend
  Display stored price history

📊 Sample Output
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

📌 Future Improvements
  🔮 Integrate Google Trends or AI models for real-time demand factor
  🌐 Add frontend for dashboard and user interaction
  🧩 Expand to other ecommerce platforms (e.g., Croma, Reliance Digital)
  📤 Auto-update price on seller platforms (if API is available)
  📉 Historical trends and demand analysis visualization


