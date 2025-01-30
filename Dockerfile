# Usando OpenJDK 17 para rodar a aplicação
FROM openjdk:21-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR gerado pelo Maven
COPY target/*.jar app.jar

# Expor a porta 8080
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]
