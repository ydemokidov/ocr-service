FROM ubuntu:latest

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ADD src/main/resources/tessdata /tessdata/

ENV TESSDATA_PREFIX="/tessdata"
ENV JAVA_HOME /usr/java/openjdk-18

RUN mkdir -p $JAVA_HOME

RUN apt update;
RUN apt-get install openjdk-18-jre -y;
RUN apt-get install software-properties-common -y;
RUN add-apt-repository ppa:alex-p/tesseract-ocr-devel;
RUN apt install -y tesseract-ocr

ENTRYPOINT ["java","-jar","/app.jar"]