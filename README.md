Jenkins Push Automatization
===========================

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/68b851244e904f60abba9cca74c2ead1)](https://www.codacy.com/app/eggers-julian/jenkins-push-automatization)
[![Coverage Status](https://coveralls.io/repos/julian-eggers/jenkins-push-automatization/badge.svg?branch=master&service=github)](https://coveralls.io/github/julian-eggers/jenkins-push-automatization?branch=master)
[![Build Status](https://travis-ci.org/julian-eggers/jenkins-push-automatization.svg?branch=master)](https://travis-ci.org/julian-eggers/jenkins-push-automatization)

Adds a [jenkins webhook](http://kohsuke.org/2011/12/01/polling-must-die-triggering-jenkins-builds-from-a-git-hook/) to every associated project.

## Docker
[Dockerhub](https://hub.docker.com/r/jeggers/jenkins-push-automatization/)

```
docker run \
-d \
--name=jenkins-push-automatization \
--restart=always \
jeggers/jenkins-push-automatization:2.0.1-RELEASE \
--gitlab.url=http://gitlab-server:8080/ \
--gitlab.token=AfQ6P9DSfnu4tuzbe \
--jenkins.url=http://jenkins-server:8080/
```

### Properties
| Property | Required | Default | Example |
| -------- | -------- | ------- | ------- |
| --gitlab.url | yes |  | AfQ6P9DSfnu4tuzbe |
| --gitlab.token | yes |  | http://gitlab-server:8080/ |
| --gitlab.checkout.url.scheme | no | SSH | SSH or HTTP |
| --jenkins.url | yes |  | http://jenkins-server:8080/ |
| --task.delay | no | 60000 | 60000 (in milliseconds!) |
