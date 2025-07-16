# demand_api.py
from fastapi import FastAPI, Query
from pytrends.request import TrendReq
import uvicorn

app = FastAPI()

@app.get("/get-demand")
def get_demand(product: str):
    pytrends = TrendReq(hl='en-US', tz=330)
    pytrends.build_payload([product], cat=0, timeframe='now 7-d', geo='IN')
    data = pytrends.interest_over_time()

    trend_score = 0.5
    if not data.empty:
        trend_score = float(data[product].mean()) / 100

    return {"demand": round(trend_score, 2)}

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8001)
