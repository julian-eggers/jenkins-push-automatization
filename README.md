Jenkins Push Automatization
============

[![Codacy Badge](https://api.codacy.com/project/badge/grade/289236a7e7b046eb89a6a0ae63863a28)](https://www.codacy.com/app/eggers-julian/jenkins-push-automatization)
[![Coverage Status](https://coveralls.io/repos/julian-eggers/jenkins-push-automatization/badge.svg?branch=master&service=github)](https://coveralls.io/github/julian-eggers/jenkins-push-automatization?branch=master)
[![Build Status](https://travis-ci.org/julian-eggers/jenkins-push-automatization.svg?branch=master)](https://travis-ci.org/julian-eggers/jenkins-push-automatization)


# Docker
[Dockerhub](https://hub.docker.com/r/jeggers/jenkins-push-automatization/)

```
docker run \
-d \
--name=jenkins-push-automatization \
--restart=always \
-p 17450:8080 \
-e GITLAB_URL=https://gitlab:8080/ \
-e GITLAB_TOKEN=AfQ6P9DSfnu4tuzbe \
-e JENKINS_URL=http://jenkins-server:8080/ \
jeggers/jenkins-push-automatization:latest
```

### Properties
| Environment variable  | Required | Default | Example |
| ------------- | ------------- | ------------- | ------------- |
| GITLAB_URL  | yes  |  | https://gitlab:8080/ |
| GITLAB_TOKEN  | yes  |  | AfQ6P9DSfnu4tuzbe |
| GITLAB_CHECKOUTURLSCHEME  | no  | SSH  | SSH or HTTP |
| JENKINS_URL  | yes  |  | http://jenkins-server:8080/  |
| TASK_DELAY  | no  | 60000  | 60000 (in milliseconds!)  |
| HTTP_PORT | no | 8080 | 8080 |
