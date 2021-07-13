FROM ghcr.io/graalvm/graalvm-ce:latest
#FROM openjdk:8u232

RUN gu install sbt

#ENV SBT_VERSION 1.5.4
#
## Install sbt
#RUN \
#    mkdir /working/ && \
#    cd /working/ && \
#    curl -L -o sbt-$SBT_VERSION.deb https://repo.scala-sbt.org/scalasbt/debian/sbt-$SBT_VERSION.deb && \
#    dpkg -i sbt-$SBT_VERSION.deb && \
#    rm sbt-$SBT_VERSION.deb && \
#    apt-get update && \
#    apt-get install sbt && \
#    cd && \
#    rm -r /working/ && \
#    sbt sbtVersion