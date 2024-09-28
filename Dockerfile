# Utiliser une image OpenJDK
FROM eclipse-temurin:17-jdk-alpine


# Copier le fichier .jar généré par Maven dans le conteneur
COPY target/portail-*.jar /portail.jar
# Exposer le port sur lequel l'application Spring Boot écoute
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "/portail.jar"]
