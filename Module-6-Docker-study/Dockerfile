# Etapa 1 - Build da aplicação
FROM eclipse-temurin:22-jdk AS build

WORKDIR /app

# Copia o arquivo pom.xml e faz o download das dependências
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B || true

# Copia o restante dos arquivos
COPY . .

# Etapa 2 - Imagem final e mais enxuta
FROM eclipse-temurin:22-jre

WORKDIR /app

# Copia o jar gerado
COPY --from=build /app/target/module-6-docker-study-1.0.0.jar app.jar

# Expõe a porta (ajuste se não for 8080)
EXPOSE 8080

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
