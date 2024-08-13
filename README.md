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

4. Configure dotenv in your Main file as follows:

   import io.github.cdimascio.dotenv.Dotenv;
   
   public class CustomerModuleApplication {
   public static void main(String[] args) {
   Dotenv dotenv = Dotenv.configure().filename("environment_variables.env")  // Specify your custom filename .load();
   
           System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
           System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
           System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
   
           SpringApplication.run(CustomerModuleApplication.class, args);
       }
   }

Go ahead and set all the relevant properties as indicated in the examples above.