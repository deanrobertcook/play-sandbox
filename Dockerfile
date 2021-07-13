#FROM ghcr.io/graalvm/graalvm-ce:latest
FROM openjdk:8

ENV SBT_VERSION 1.5.4
RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install sbt && \
  sbt sbtVersion

WORKDIR /HelloWorld

ADD . /HelloWorld

CMD sbt run