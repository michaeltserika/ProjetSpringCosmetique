Voici un exemple de fichier `README.md` pour ton projet avec PostgreSQL :

```markdown
# Projet Spring Cosmetique

## Description
Le projet **Spring Cosmetique** est une application de gestion des produits cosmétiques, développée avec le framework **Spring Boot**. Il permet de gérer l'ajout, la modification et la suppression de produits cosmétiques, ainsi que la gestion des stocks et des statistiques associées. Ce projet utilise une base de données PostgreSQL pour le stockage des données.

## Fonctionnalités
- Gestion des produits cosmétiques : ajout, modification, suppression.
- Affichage des produits avec leurs informations : titre, catégorie, prix, remise, stock, etc.
- Statistiques des stocks avec un graphique visuel.
- Recherche des produits via un formulaire.

## Technologies utilisées
- **Spring Boot** : Framework principal pour le développement de l'application.
- **PostgreSQL** : Base de données utilisée pour stocker les informations des produits.
- **Thymeleaf** : Moteur de templates pour la génération des pages HTML.
- **Chart.js** : Bibliothèque JavaScript pour afficher des graphiques.
- **Bootstrap** : Framework CSS pour une interface réactive et professionnelle.

## Prérequis
Avant de commencer, assurez-vous d'avoir les outils suivants installés sur votre machine :
- **JDK 11 ou version supérieure**
- **Maven** ou **Gradle** pour la gestion des dépendances
- **PostgreSQL** pour la gestion de la base de données

## Installation

1. Clonez ce repository :
   ```bash
   git clone https://github.com/michaeltserika/ProjetSpringCosmetique.git
   ```

2. Naviguez dans le répertoire du projet :
   ```bash
   cd ProjetSpringCosmetique
   ```

3. Configurez la base de données PostgreSQL :
   - Créez une base de données PostgreSQL et configurez le fichier `application.properties` avec les paramètres de votre base de données.
   - Exemple de configuration :
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/nom_de_la_base
     spring.datasource.username=nom_utilisateur
     spring.datasource.password=mot_de_passe
     spring.jpa.hibernate.ddl-auto=update
     ```

4. Lancez l'application Spring Boot :
   ```bash
   mvn spring-boot:run
   ```
   Ou, si vous utilisez Gradle :
   ```bash
   ./gradlew bootRun
   ```

5. L'application sera accessible à l'adresse suivante dans votre navigateur :
   ```
   http://localhost:9999
   ```

## Structure du projet
Voici la structure du projet :
- `src/` : Le code source de l'application.
  - `main/java/` : Contient les classes Java pour le backend.
  - `main/resources/` : Contient les fichiers de configuration et les templates Thymeleaf.
- `application.properties` : Fichier de configuration de Spring Boot.

## Contribuer
Les contributions sont les bienvenues ! Si vous avez des suggestions ou des corrections, n'hésitez pas à ouvrir un ticket ou à soumettre une pull request.

1. Forkez le projet.
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/nom-fonctionnalité`).
3. Faites un commit de vos modifications (`git commit -am 'Ajoute une nouvelle fonctionnalité'`).
4. Poussez la branche (`git push origin feature/nom-fonctionnalité`).
5. Créez une nouvelle pull request.

## Licence
Ce projet est sous licence [MIT](LICENSE).
```

Ce `README.md` couvre les informations essentielles du projet, y compris la description, les technologies utilisées, l'installation et la configuration, ainsi que la structure du projet. N'oublie pas de personnaliser les informations (comme le nom de la base de données et les paramètres d'utilisateur PostgreSQL) selon ta configuration.
