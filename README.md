Jenkins Push Automatization
============

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/68b851244e904f60abba9cca74c2ead1)](https://www.codacy.com/app/eggers-julian/jenkins-push-automatization?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=julian-eggers/jenkins-push-automatization&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/julian-eggers/jenkins-push-automatization/badge.svg?branch=master&service=github)](https://coveralls.io/github/julian-eggers/jenkins-push-automatization?branch=master)
[![Build Status](https://travis-ci.org/julian-eggers/jenkins-push-automatization.svg?branch=master)](https://travis-ci.org/julian-eggers/jenkins-push-automatization)


# Docker
[Dockerhub](https://hub.docker.com/r/jeggers/jenkins-push-automatization/)

```
docker run \
-d \
--name=jenkins-push-automatization \
--restart=always \
-e JENKINSURL=http://jenkins-server:8080/ \
-e GITLAB_TOKEN=AfQ6P9DSfnu4tuzbe \
-e GITLAB_URL=http://gitlab-server:8080/ \
jeggers/jenkins-push-automatization:1.0.2-RELEASE
```

### Properties
| Environment variable  | Required | Default | Example |
| ------------- | ------------- | ------------- | ------------- |
| JENKINSURL  | yes  |  | http://jenkins-server:8080/  |
| GITLAB_TOKEN  | yes  |  | AfQ6P9DSfnu4tuzbe |
| GITLAB_URL  | yes  |  | http://gitlab-server:8080/ |
| GITLAB_CHECKOUTURLSCHEME  | no  | SSH  | SSH or HTTP |
| TASK_DELAY  | no  | 60000  | 60000 (in milliseconds!)  |
| HTTP_PORT | no | 8080 | 8080 |
| MAX_HEAP | no | 100M | 100M |
| JAVA_OPTS | no |  | |
