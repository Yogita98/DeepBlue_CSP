from flask import Flask, request, render_template
import cv2
import os
import matplotlib.pyplot as plt
from darkflow.net.build import TFNet
from io import BytesIO
import base64
import os
if os.path.exists("static/plot_.jpg"):
    os.remove("static/plot_.jpg")
    print("hi")    


options = {
        'model': 'cfg/yolo-obj.cfg',
        'load': 'bin/yolo-obj.weights',
        'threshold': 0.3,
        'gpu': 1.0
    }

tfnet = TFNet(options)


app = Flask(__name__)
APP_ROOT = os.path.dirname(os.path.abspath(__file__))


@app.route("/")
def index():
    return render_template("upload.html")


@app.route("/upload", methods=["POST"])
def upload():
    target = os.path.join(APP_ROOT, 'images/')
    print(target)
    if not os.path.isdir(target):
        os.mkdir(target)
    for upload in request.files.getlist("file"):
        print(upload)
        filename = upload.filename
        destination = "/".join([target, filename])
        print("Save it to:", destination)
        upload.save(destination)

    img = cv2.imread(destination, cv2.IMREAD_COLOR)
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

    pred = tfnet.return_predict(img)

    for element in pred:
        element.pop('confidence', None)

    for ele in pred:
        t = (ele['topleft']['x'], ele['topleft']['y'])
        br = (ele['bottomright']['x'], ele['bottomright']['y'])
        label = ele['label']

        img = cv2.rectangle(img, t, br, (0, 255, 0), 7)
        img = cv2.putText(img, label, t, cv2.FONT_HERSHEY_COMPLEX, 2, (0, 0, 0), 3)
        plt.imshow(img)

    plt.savefig("static/plot_.jpg")

    figfile = BytesIO()
    plt.savefig(figfile, format='jpg')
    figfile.seek(0)
    figdata_jpg = base64.b64encode(figfile.getvalue())
    result = figdata_jpg

    return render_template('complete.html', pred=pred)


if __name__ == "__main__":
    app.run(port=4555, debug=True)
