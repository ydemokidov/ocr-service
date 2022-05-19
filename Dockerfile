FROM ubuntu:latest

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ADD src/main/resources/tessdata /tessdata/

ENV TESSDATA_PREFIX="/tessdata"
ENV JAVA_HOME /usr/java/openjdk-18

RUN mkdir -p $JAVA_HOME

RUN apt update;\
    apt-get install openjdk-18-jre -y;\
    apt-get install software-properties-common -y;\
    add-apt-repository ppa:alex-p/tesseract-ocr-devel;\
    apt install -y tesseract-ocr

ENTRYPOINT ["java","-jar","/app.jar"]