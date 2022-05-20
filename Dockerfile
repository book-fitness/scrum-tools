FROM openjdk:13-jdk-alpine

WORKDIR /app

ADD ./target/DailyTool-1.0-SNAPSHOT.jar /app

CMD ["java", "-cp", "DailyTool-1.0-SNAPSHOT.jar", "net.scrumtools.Main"]

# 1. Create image.
# $ docker build -t tiger/javadockertest1 .
# 2. Run container.
# $ docker run -it tiger/javadockertest1

# Списк образов.
# $ docker image ls

# Список контейнеров.
# $ docker container ls -a

# Очиска всех ненужных образов.
# $ docker system prune