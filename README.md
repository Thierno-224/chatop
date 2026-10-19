# Chatop — API REST de gestion de locations

Back-end Java / Spring Boot. Chatop permet à des utilisateurs de s'inscrire, de s'authentifier, de consulter et de gérer des annonces de location et d'envoyer des messages.

## Technologies

- Java 17 (environnement de développement utilisé)
- Spring Boot 4.1.1, Spring Web, Spring Data JPA, Spring Security
- MySQL
- JWT pour l'authentification
- Jakarta Bean Validation
- Maven (wrapper inclus dans le projet)
- springdoc-openapi / Swagger UI

> Vérifier la version de Java requise dans `pom.xml` et adapter la configuration locale si nécessaire.

## Prérequis

- Un JDK compatible avec le `pom.xml`
- MySQL installé et démarré
- Git (pour cloner le dépôt)

## Installation

Clonez le dépôt GitHub en utilisant l'une des méthodes suivantes.

### Avec HTTPS 

```bash
git clone https://github.com/Thierno-224/chatop.git
```

### Avec SSH

```bash
git clone git@github.com:Thierno-224/chatop.git
```

**Remarque :** le clonage via SSH nécessite une clé SSH configurée sur votre compte GitHub.

Créer une base de données MySQL (adapter le nom à votre configuration) :

```sql
CREATE DATABASE chatop;
```

Configurer la connexion MySQL et la clé secrète JWT selon les propriétés attendues par l'application. **Ne jamais publier de mots de passe ou de clés secrètes dans Git.** Utiliser des variables d'environnement ou une configuration locale non versionnée.

Exemple indicatif de paramètres (les noms exacts des propriétés JWT dépendent de l'implémentation du projet) :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatop
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Démarrer l'application depuis la racine du projet :

```bash
./mvnw spring-boot:run
```

Par défaut, l'API est accessible sur `http://localhost:8080` si le port n'a pas été modifié.

## Authentification

L'API utilise un jeton JWT. Après connexion, envoyer le jeton sur les endpoints protégés :

```http
Authorization: Bearer <JWT>
```

## Endpoints 

| Méthode | Endpoint | Fonction |
| --- | --- | --- |
| POST | `/api/auth/register` | Créer un compte |
| POST | `/api/auth/login` | Se connecter et obtenir un JWT |
| GET | `/api/auth/me` | Consulter l'utilisateur connecté |
| GET | `/api/rentals` | Lister les locations |
| GET | `/api/rentals/{id}` | Consulter une location |
| POST | `/api/rentals` | Créer une location avec image |
| PUT | `/api/rentals/{id}` | Modifier une location |
| GET | `/api/user/{id}` | Consulter un utilisateur |
| POST | `/api/messages` | Envoyer un message |

Les endpoints d'authentification `register` et `login` sont publics ; les autres endpoints listés sont protégés par JWT dans la configuration décrite pendant le développement.

## Envoi d'une image

La création d'une location utilise le format **`multipart/form-data`** avec les champs :

| Champ | Type | Obligatoire |
| --- | --- | --- |
| `name` | Texte | Oui |
| `surface` | Nombre décimal | Oui |
| `price` | Nombre décimal | Oui |
| `description` | Texte | Oui |
| `picture` | Fichier image | Oui |

Les images sont stockées sur le serveur, et leur chemin public est enregistré dans la colonne `picture` de la table des locations. Le stockage local est configuré par défaut sous `uploads/rentals/` dans l'implémentation présentée. Vérifier que ce dossier est accessible en écriture et que l'exposition HTTP des fichiers est configurée si le frontend doit afficher les images.

## Documentation Swagger / OpenAPI

Avec le serveur démarré :

- Interface Swagger UI : http://localhost:8080/swagger-ui/index.html
- Spécification OpenAPI JSON : http://localhost:8080/v3/api-docs

La documentation est générée automatiquement à partir des Controllers et DTO. Les annotations `@Operation`, `@ApiResponses` et `@SecurityRequirement` peuvent enrichir les descriptions des routes, des réponses et de la sécurité.

## Vérifications manuelles

Les routes peuvent être testées avec Postman ou Swagger UI :

1. Créer un utilisateur via `/api/auth/register`.
2. Se connecter via `/api/auth/login`.
3. Ajouter le jeton JWT à la requête.
4. Consulter `/api/rentals`.
5. Créer une location via un formulaire `multipart/form-data` contenant une image.
6. Vérifier la présence de l'annonce et du chemin de l'image en base.

## Sécurité et bonnes pratiques

- Ne pas versionner les secrets, les mots de passe ou les jetons JWT.
- Ne pas versionner les images téléversées (`uploads/`).
- Valider les champs et les fichiers reçus.
- Limiter les formats et la taille des images.
- Utiliser une configuration distincte pour les environnements locaux et de production.




