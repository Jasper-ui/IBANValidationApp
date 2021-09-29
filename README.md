# DraftTicketApplication

### Running the application

There are several ways to run the application.

Note: The application creates an SQLite iban.db if it does not already exist, which is used for storing the IBAN numbers. 

To run the application you have several options:

1) Run through Intellij , by running the IbanValidatorApplication configuration
2) Run through command line via command : `./gradlew bootRun`
3) Run through the Gradle bootRun task
4) Run gradle built bootJar:
    1) Create the jar by running ./gradlew bootJar
    2) navigate to build/libs and the IbanValidatorApplication-0.0.1-SNAPSHOT.jar should be visible
    3) Execute the jar file via the following command: `java -jar .\IbanValidatorApplication-0.0.1-SNAPSHOT.jar
       `

### Application structure

The IbanValidatorApplication structure is quite straightforward.

The main package name is
`com.bank.ibanvalidator`.

The `com.bank.ibanvalidator.controllers` package holds the IBANController, which is used as the main API for the requests ,
and the ValidationExceptionsControllerAdvice, which is an ControllerAdvice class on how to deal with
validation exceptions(since normally on validation responses there are no error messages, or they are messy)

The `com.bank.ibanvalidator.entities` package holds the required entity/data structure for the application to function. This
is the IBAN entity that is being saved and retrieved from the database.

The `com.bank.ibanvalidator.interfaces` package contains the IBANService interface and the 
IBANValidationService, which could be used for different services.

The `com.bank.ibanvalidator.services` package contains the IBANValidationServiceImpl and the IBANRepositoryServiceImpl services, 
which is responsible for all of the IBAN validation and retrieval/storing of IBAN in the SQLite DB.

### Application tests

The application Full tests utilises the test.db located in the top level, for ease of access, which is a copy of an actual
db that is being used in production. This is to make sure that the tests are appropriate and replicate the product 
environment.

There are 4 main testing packages contained in the application:

1) `com.bank.ibanvalidator.FullTest` - Which tests the application end to end and confirms that everything is working.
2) `com.bank.ibanvalidator.SmokeTests` - Which checks correct bean instantiations
3) `com.bank.ibanvalidator.UnitTests` - Contains the unit tests for the IBANValidationService
4) `com.bank.ibanvalidator.ValidationTests` - Tests the spring validation layer and error messages

### Acceptance Criteria testing

To test the acceptance criteria I used both the output from the FullTest.java, and the output from running the
application and using postman to submit a POST request. Screenshots can be seen bellow:
