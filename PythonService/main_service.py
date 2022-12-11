from msilib.schema import File
from os import path
from tkinter.tix import Tree
from fastapi import FastAPI
import image_compression
import face_detect
from pathlib import Path

from fastapi import Request
from pydantic import BaseModel
import json
app = FastAPI()

class ImageDataModel(BaseModel):
    ImagePath:str

class Response:
    StatusCode:int
    Message:str
    def __init__(self,status,message) -> None:
        self.StatusCode = status
        self.Message = message

    def toJSON(self):
        return json.dumps(self, default=lambda o: o.__dict__)


@app.get("/")
def getImagePath():
    return 'Hello'

@app.post("/image")
def get_image_path(request:ImageDataModel):

    try:
        image_compression.compress_img(request.ImagePath)
        return Response(0,"Image comperssion succesfful.")
    except:
        return Response(1,"Error image compression")



@app.post("/face-detect")
def get_image_path(request:ImageDataModel):

    faces_on_image = face_detect.detect_face(request.ImagePath)
    if(faces_on_image == 1):
        return Response(1,"Faces on image found.")
    elif(faces_on_image > 1):
        return Response(-1,"Found one face.")
    else:
        return Response(0,"No faces found on image.")

