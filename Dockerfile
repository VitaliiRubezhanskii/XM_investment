FROM openjdk:17-oracle
WORKDIR /
ADD build/libs/investment-0.0.1.jar investment-0.0.1.jar
CMD ["java", "-jar", "investment-0.0.1.jar"]