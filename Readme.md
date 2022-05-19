Точки и примеры запросов:

1. **Вернуть текст с картинки**
http://hostname:8080/ocr/text
   
Запрос:

POST /ocr/text HTTP/1.1
Host: localhost:8080
Content-Length: 239
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="/C:/Users/ydemokidov/IdeaProjects/ocr-service/src/test/resources/price.bmp"
Content-Type: image/bmp

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW



2. **Вернуть число с картинки**
   http://hostname:8080/ocr/num

Запрос - см п.1


3. **Проверить наличие строки в тексте с картинки**

Запрос:

POST /ocr/searchpat HTTP/1.1
Host: localhost:8080
Content-Length: 342
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW

----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="file"; filename="/C:/Users/ydemokidov/IdeaProjects/ocr-service/src/test/resources/price.bmp"
Content-Type: image/bmp

(data)
----WebKitFormBoundary7MA4YWxkTrZu0gW
Content-Disposition: form-data; name="pattern"

string_to_search
----WebKitFormBoundary7MA4YWxkTrZu0gW


