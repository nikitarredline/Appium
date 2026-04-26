FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /workspace

# копируем проект
COPY . .

# прогреваем зависимости (ускоряет CI)
RUN mvn -q -DskipTests dependency:go-offline

# дефолтная команда (можно переопределить в Jenkins)
CMD ["mvn", "clean", "test"]