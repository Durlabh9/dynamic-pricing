from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from webdriver_manager.chrome import ChromeDriverManager
import time
from difflib import SequenceMatcher
import requests
from ml_model.database import save_to_db  # Use your PostgreSQL save function
from ml_model.database import init_db
import json


# Initialize the database (create table if not exists)
init_db()

# The rest of your code where you fetch Amazon prices...


# Function to check similarity between query and title
def is_similar(a, b, threshold=0.4):
    return SequenceMatcher(None, a.lower(), b.lower()).ratio() > threshold

def fetch_amazon_prices(product_name):
    options = Options()
    options.add_argument("--headless")  # Comment out to see browser during testing
    options.add_argument("--disable-gpu")
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)

    try:
        query = product_name.replace(" ", "+")
        url = f"https://www.amazon.in/s?k={query}"
        driver.get(url)
        time.sleep(5)  # Allow page to fully load

        results = []

        # Flexible title & price scraping for search results
        titles = driver.find_elements(By.XPATH, "//h2//span")
        prices = driver.find_elements(By.XPATH, "//span[@class='a-price-whole']")

        if titles and prices:
            for i in range(min(len(titles), len(prices))):
                title = titles[i].text.strip()

                # Apply similarity check
                if not is_similar(product_name, title):
                    continue  # Skip irrelevant

                price_text = prices[i].text.replace(",", "").strip()
                try:
                    price = int(price_text)
                    results.append({"product": title, "price": price})
                except ValueError:
                    continue

        # If no results, check for single product page
        if not results:
            try:
                product_title = driver.find_element(By.ID, "productTitle").text.strip()
                price_element = driver.find_element(By.XPATH, "//span[@class='a-price-whole']")
                price_text = price_element.text.replace(",", "").strip()
                price = int(price_text)

                if is_similar(product_name, product_title):
                    results.append({"product": product_title, "price": price})

            except:
                pass

        return results

    finally:
        driver.quit()


def fetch_flipkart_prices(product_name):
    options = Options()
    options.add_argument("--headless")
    options.add_argument("--disable-gpu")
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")

    driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=options)

    try:
        query = product_name.replace(" ", "+")
        url = f"https://www.flipkart.com/search?q={query}"
        driver.get(url)
        time.sleep(5)

        results = []

        try:
            driver.find_element(By.XPATH, "//button[text()='✕']").click()  # Close login popup
        except:
            pass  # No popup

        titles = driver.find_elements(By.CSS_SELECTOR, "div._4rR01T")  # For laptops/phones
        prices = driver.find_elements(By.CSS_SELECTOR, "div._30jeq3._1_WHN1")

        for i in range(min(len(titles), len(prices))):
            title = titles[i].text.strip()
            if not is_similar(product_name, title):
                continue

            price_text = prices[i].text.replace("₹", "").replace(",", "").strip()
            try:
                price = int(price_text)
                results.append({"product": title, "price": price})
            except ValueError:
                continue

        return results

    finally:
        driver.quit()



import datetime

def send_to_backend(product_name, results, base_cost, inventory, margin, source):

    url = "http://localhost:9090/api/prices"
    headers = {'Content-Type': 'application/json'}
    fetched_at = datetime.datetime.now().isoformat()

    demand_level = 0.5  # Temporarily fixed or remove if not needed

    for item in results:
        predicted_price = get_predicted_price(
            base_cost, margin, inventory, item["price"], demand_level
        )
        payload = {
            "query": product_name,
            "productTitle": item["product"],
            "price": item["price"],
            "predictedPrice": round(predicted_price),
            "source": source,
            "fetchedAt": fetched_at
        }
        response = requests.post(url, json=payload, headers=headers)
        if response.status_code in (200, 201):
            print(f"✅ Sent: {item['product']} | ₹{item['price']} at {fetched_at}")
        else:
            print(f"❌ Failed to send {item['product']} | Status: {response.status_code}")


# ---- At the end of your script ----

def get_predicted_price(base_price, margin, inventory_level, competitor_price, demand_level):
    url = "http://localhost:9090/api/prices/predict"
    payload = {
        "basePrice": base_price,
        "margin": margin,
        "inventoryLevel": inventory_level,
        "competitorPrice": competitor_price,
        "demandLevel": demand_level
    }
    print(json.dumps(payload, indent=2))
    response = requests.post(url, json=payload)
    if response.status_code == 200:
        return response.json()
    else:
        print(f"Failed to fetch predicted price. Status: {response.status_code}")
        return competitor_price  # Fallback

def show_predicted_prices(query):
    url = f"http://localhost:9090/api/prices/{query}"
    response = requests.get(url)

    if response.status_code == 200:
        products = response.json()
        if not products:
            print("❌ No entries found for query:", query)
            return

        print(f"\n🔍 Predicted Prices for: '{query}'")
        for product in products:
            print("-" * 60)
            print("📦 Product Title :", product["productTitle"])
            print("💰 Competitor ₹  :", product["price"])
            print("📈 Predicted ₹   :", product.get("predictedPrice", "N/A"))
            print("🔗 Source        :", product["source"])
            print("🕒 Fetched At    :", product["fetchedAt"])
    else:
        print("❌ Failed to fetch from backend. Status:", response.status_code)



if __name__ == "__main__":
    product = input("Enter product name: ")
    base_cost = float(input("Enter base cost: "))
    inventory = int(input("Enter inventory level: "))
    margin = float(input("Enter margin : "))

    amazon_results = fetch_amazon_prices(product)
    if amazon_results:
        send_to_backend(product, amazon_results, base_cost, inventory, margin, "Amazon")
        print(f"✅ Amazon: {len(amazon_results)} prices sent to backend.")

    # Flipkart
    flipkart_results = fetch_flipkart_prices(product)
    if flipkart_results:
        send_to_backend(product, flipkart_results, base_cost, inventory, margin, "Flipkart")
        print(f"✅ Flipkart: {len(flipkart_results)} prices sent to backend.")
    show_predicted_prices(product)

    # if results:
    #     send_to_backend(product, results, base_cost, inventory,margin)
    #     print(f"Total {len(results)} prices sent to backend.")
    #     show_predicted_prices(product)
    # else:
    #     print("No prices found for Amazon.")




