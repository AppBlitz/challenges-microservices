FROM jenkins/jenkins:lts-jdk17
# Instalar plugins necesarios de forma automatizada
# Referencia: https://github.com/jenkinsci/docker#preinstalling-plugins
RUN jenkins-plugin-cli --plugins \
docker-pipeline \
docker-workflow \
pipeline-stage-view \
workflow-aggregator \
git \
configuration-as-code \
sonar
# Instalar cliente Docker dentro del contenedor Jenkins
USER root
RUN apt-get update && \
apt-get install -y docker.io && \
rm -rf /var/lib/apt/lists/*
# Agregar usuario jenkins al grupo docker para acceder al socket
RUN groupadd -f docker && usermod -aG docker jenkins
USER jenkins
