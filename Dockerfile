# Pull base image.
FROM ubuntu:16.04
MAINTAINER Zhaiyk Nurbekov <zh.nurbekov@mail.com>

# Install Java.
RUN \
  apt-get update && \
  apt-get install -y wget && \
  wget --no-check-certificate -c --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u151-b12/e758a0de34e24606bca991d704f6dcbf/jdk-8u151-linux-x64.tar.gz -O java-linux-x64.tar.gz && \
  mkdir -p /usr/lib/jvm/java-8-oracle && \
  tar -C /usr/lib/jvm/java-8-oracle --strip-components=1 -xvf java-linux-x64.tar.gz && \
  update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/java-8-oracle/bin/java" 1 && \
  rm -rf /var/lib/apt/lists/* && \
  rm *linux-x64.tar.gz

ADD /jar/domofon.jar /domofon.jar
# Define working directory.
WORKDIR /

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Define default command.
CMD java -jar /domofon.jar