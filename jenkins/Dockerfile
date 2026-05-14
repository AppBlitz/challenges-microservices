FROM jenkins/jenkins:lts-jdk21

USER root
RUN apt-get update && \
    apt-get install -y docker.io curl && \
    rm -rf /var/lib/apt/lists/* && \
    groupadd -f docker && usermod -aG docker jenkins


RUN usermod -aG docker jenkins

USER jenkins

RUN jenkins-plugin-cli --plugins \
  workflow-aggregator \
  docker-plugin \
  docker-workflow \
  git \
  pipeline-stage-view \
  configuration-as-code \
  job-dsl \
  sonar \
  jacoco \
  htmlpublisher \
  timestamper \
  ansicolor
