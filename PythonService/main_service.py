from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def getImagePath():
    return 'Hello'