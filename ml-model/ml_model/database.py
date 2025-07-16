# ml_model/database.py

import psycopg2

def get_connection():
    return psycopg2.connect(
        dbname='pricing_system',
        user='postgres',
        password='2004',
        host='localhost',
        port='5432'
    )

def init_db():
    conn = get_connection()
    cur = conn.cursor()
    cur.execute('''
        CREATE TABLE IF NOT EXISTS product_prices (
            id SERIAL PRIMARY KEY,
            query TEXT NOT NULL,
            product_title TEXT NOT NULL,
            price INTEGER NOT NULL,
            source TEXT NOT NULL,
            fetched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );
    ''')
    conn.commit()
    cur.close()
    conn.close()

def save_to_db(query, results, source):
    conn = get_connection()
    cur = conn.cursor()
    for item in results:
        cur.execute('''
            INSERT INTO product_prices (query, product_title, price, source)
            VALUES (%s, %s, %s, %s)
        ''', (query, item['product'], item['price'], source))
    conn.commit()
    cur.close()
    conn.close()
