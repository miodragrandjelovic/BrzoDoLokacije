import cv2
import sys

def detect_face(path):
    cascPath =  "haarcascade_frontalface_default.xml"
    #faceCascade = cv2.CascadeClassifier(cv2.data.haarcascades + cascPath)
    faceCascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')

    # read and convert image
    image = cv2.imread(path)
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    # detect faces in the image
    faces = faceCascade.detectMultiScale(
        gray,
        scaleFactor=1.1,
        minNeighbors=5,
        minSize=(30, 30),
        #    flags = cv2.cv.CV_HAAR_SCALE_IMAGE
        )
    #print("Found {0} faces!".format(len(faces)))
    '''
    # show face detections
    for (x, y, w, h) in faces:
        cv2.rectangle(image, (x, y), (x+w, y+h), (255, 0, 0), 2)
    '''

    return len(faces)


def is_face_exists(path):
    face_count = detect_face(path)
    if(face_count > 0):
        return True
    
    return False
