from msilib.schema import File
from os import path
from tkinter.tix import Tree
from fastapi import FastAPI
import image_compression
from pathlib import Path

from fastapi import Request
from pydantic import BaseModel
import json
app = FastAPI()

class ImageDataModel(BaseModel):
    ImagePath:str

class ResponseModel:
    Status:int
    Content:str
    def __init__(self,status,content) -> None:
        self.Status = status
        self.Content = content

    def toJSON(self):
        return json.dumps(self, default=lambda o: o.__dict__)


@app.get("/")
def getImagePath():
    return 'Hello'

@app.post("/image")
def get_image_path(request:ImageDataModel):

    try:
        image_compression.compress_img(request.ImagePath)
        return ResponseModel(0,"Image comperssion succesfful.").toJSON()
    except:
        return ResponseModel(0,"Error image compression").toJSON()




