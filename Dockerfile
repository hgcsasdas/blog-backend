# Usar la imagen oficial de Maven para construir la aplicación
FROM maven:3.8.5-openjdk-17 AS build

# Copiar el contenido del proyecto al contenedor
COPY . /app

# Establecer el directorio de trabajo
WORKDIR /app

# Construir el paquete sin ejecutar las pruebas
RUN mvn clean package -DskipTests

# Usar la imagen oficial de OpenJDK para correr la aplicación
FROM openjdk:17-jdk-slim

# Copiar el jar construido en la etapa anterior al contenedor actual
COPY --from=build /app/target/backend-blog-0.0.1-SNAPSHOT.jar /app/backend-blog.jar

# Exponer el puerto 8080
EXPOSE 8080

# Definir el comando de inicio
ENTRYPOINT ["java", "-jar", "/app/backend-blog.jar"]
