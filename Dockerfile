# Utiliser une image OpenJDK
FROM openjdk:17-jdk
# Copier le fichier .jar généré par Maven dans le conteneur
COPY target/portail-*.jar /portail.jar
# Exposer le port sur lequel l'application Spring Boot écoute
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "/portail.jar"]
