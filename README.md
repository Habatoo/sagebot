# Bot

### Docker start
* docker build --tag=sagebot:latest --build-arg dbUrl=*** --build-arg dbUserName=*** --build-arg dbPassword=*** --build-arg botName=** --build-arg botToken=*** --build-arg chatPort=** --build-arg chatHost=localhost .
* docker run -p 5000:5000 sagebot:latest

* docker inspect sagebot
* docker stop sagebot
* docker rm sagebot
* docker rm $(docker ps -a -q)
