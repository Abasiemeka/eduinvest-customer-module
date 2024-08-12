## Setting Up Your Development Environment

1. Create a file named `.env` in the root directory of the project.
2. Add the following environment variable to the `.env` file:
    ```
    PAYSTACK_SECRET_KEY=type_secret_key_here
    ```

   This file should not be committed to version control. Ensure `.env` is listed in your `.gitignore` file.

3. Add the following dependency in your pom.xml file for .env file recognition by the IDE:

         <dependency>
             <groupId>io.github.cdimascio</groupId>
             <artifactId>dotenv-java</artifactId>
             <version>3.0.0</version>
         </dependency>
