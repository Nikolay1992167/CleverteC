# CleverBank application
# Author: [NIkolay Minich](https://github.com/Nikolay1992167/Clevertec)

The application implements a service for adding funds to the account,
withdrawing from the account and transferring funds between accounts. 
CRUD operations are implemented for each entity. The Postgres database
is used, the connection to the database is carried out via JDBC. Entities
for requests and responses have been created for each entity. 
Exception handling has been performed. the basic functionality 
for working with HTTP requests is implemented using HttpServlets.

### Technologies that I used on the project:
* Java 17
* Gradle 8.1.1
* Commons-lang3:3.12.0
* Commons-configuration2:2.9.0
* Gson:2.10.1
* Postgresql:42.6.0
* Jackson-dataformat-yaml:2.15.2
* Group: 'com.aspose', name: 'aspose-pdf', version: '21.9', classifier: 'jdk17
* Lombok:1.18.28
* Javax.servlet-api:4.0.1
* Junit-jupiter-api:5.9.1
* Junit-jupiter
* Junit-jupiter-api:5.10.0
* Mockito-core:3.12.4
* Assertj-core:3.24.2

### Instructions to run application locally:
1. You must have Java 17, Intellij IDEA Ultimate, Tomcat 9.0.70
   and Postgresql installed.
2. In Postgresql, you need to create a database named "clever_bank".
3. In [settings_db.yml](CleverBank/src/main/resources/settings_db.xml) enter your username and password from your
   local postgresql.
4. In [appsettings.yml](CleverBank/src/main/resources/appsettings.xml ) enter the values of the interest rate and the period of verification of interest accrual.
5. In the Intellij IDEA settings, select "Run" -> Tomcat 9.0.70.
6. The application is ready to work
7. To create tables in the database and fill them with data, you must first execute a GET request  [http://localhost:8080/db].

### Unit tests
Unit tests have been written with 100% coverage of services.
You can run the tests for this project, by at the root of the project executing:
```
./gradlew test
```

### Docker
Dockerfile and docker-compose.yaml files were created to create 
a Docker image and launch the application. For the application 
to work correctly in the container, it is necessary in the 
settings_db file.yml, replace host '127.0.0.1' with 'db'.

### Functionalities
In summary the application can:

# UserController
* **GET getUserById | Finds user by id**
* http://localhost:8080/users/1
* Response example:
````json
[
{
  "id": 1,
  "name": "Anna Petrova",
  "email": "petrova@mail.ru",
  "password": "qwerty"
  }
]
````
* **GET getUsers | Finds all users**
* http://localhost:8080/users
* Response example:
````json
[
{
"id": 1,
"name": "Anna Petrova",
"email": "petrova@mail.ru",
"password": "qwerty"
},
{
"id": 2,
"name": "Sergej Smirnov",
"email": "smirnov@mail.ru",
"password": "asdfgh"
},
{
"id": 3,
"name": "Maria Sidorova",
"email": "sidorova@mail.ru",
"password": "zxcvbn"
}
]
````
* **POST addUser | Creates new user**
* http://localhost:8080/users
* Request example:
````json
[
   {
      "name": "Dave",
      "email": "dave@example.com",
      "password": "123456"
   }
]
````
* **PUT updateUser | Updates user with id**
* http://localhost:8080/users/1
* Request example:
````json
[
   {
      "name": "Bob Smith",
      "email": "smith@example.com",
      "password": "654321"
   }
]
````
* **DELETE deleteUser | Deletes user with id**
* http://localhost:8080/users/1

# BankController
* **GET getBankById | Finds bank by id**
* http://localhost:8080/banks/1
* Response example:
````json
[
   {
      "id": 1,
      "title": "CleverBank",
      "bic": "CLEVER"
   }
]
````
* **GET getBanks | Finds all banks**
* http://localhost:8080/banks
* Response example:
````json
[
   {
      "id": 1,
      "title": "CleverBank",
      "bic": "CLEVER"
   },
   {
      "id": 2,
      "title": "VTB",
      "bic": "VTBRRUMM"
   },
   {
      "id": 3,
      "title": "GazPromBank",
      "bic": "GAZPRUMM"
   }
]
````
* **POST addBank | Creates new bank**
* http://localhost:8080/banks
* Request example:
````json
[
   {
      "title":"Tinkoff",
      "bic":"TIN"
   }
]
````
* **PUT updateBank | Updates bank with id**
* http://localhost:8080/banks/6
* Request example:
````json
[
   {
      "title":"TinBel",
      "bic":"TINBEL"
   }
]
````
* **DELETE deleteBank | Deletes bank with id**
* http://localhost:8080/banks/4

# AccountController
* **GET getAccountById | Finds account by id**
* http://localhost:8080/accounts/1
* Response example:
````json
[
   {
      "id": 26,
      "currency": "BYN",
      "dateOpen": "2023-05-24T18:00",
      "number": "00026",
      "balance": 184643.65,
      "bankTitle": "GazPromBank",
      "userName": "Den Socolov"
   }
]
````
* **GET getAccounts | Finds all accounts**
* http://localhost:8080/accounts
* Response example:
````json
[
   {
      "id": 26,
      "currency": "BYN",
      "dateOpen": "2023-05-24T18:00",
      "number": "00026",
      "balance": 184643.65,
      "bankTitle": "GazPromBank",
      "userName": "Den Socolov"
   },
   {
      "id": 25,
      "currency": "BYN",
      "dateOpen": "2023-05-22T17:00",
      "number": "00025",
      "balance": 164860.40,
      "bankTitle": "RosSelBank",
      "userName": "Helen Popova"
   },
   {
      "id": 28,
      "currency": "BYN",
      "dateOpen": "2023-06-12T11:00",
      "number": "00028",
      "balance": 145077.12,
      "bankTitle": "VTB",
      "userName": "Andrej Morozov"
   }
]
````
* **POST addAccounts | Creates new account**
* http://localhost:8080/accounts
* Request example:
````json
[
   {
      "number": "234234",
      "currency": "BYN",
      "dateOpen": "2023-04-06T11:54:03",
      "balance": 100.00,
      "bankId": 1,
      "userId": 1
   }
]
````
* **PUT updateAccount | Updates account with id**
* http://localhost:8080/accounts/3
* Request example:
````json
[
   {
      "currency": "BYN",
      "dateOpen": "2021-01-01T00:00:00",
      "number": "86878",
      "balance": 2000.00,
      "bankId": 1,
      "userId": 1
   }
]
````
* **DELETE deleteAccount | Deletes account with id**
* http://localhost:8080/accounts/3

# TransactionController
* **GET getTransactionById | Finds transaction by id**
* http://localhost:8080/transactions/1
* Response example:
````json
[
   {
      "id": 1,
      "typeTransaction": "TRANSFER",
      "fromAccountNumber": "00002",
      "toAccountNumber": "00001",
      "amount": 50.00,
      "date": "2023-07-15T14:00"
   }
]
````
* **GET getTransactions | Finds all transactions**
* http://localhost:8080/transactions
* Response example:
````json
[
   {
      "id": 1,
      "typeTransaction": "TRANSFER",
      "fromAccountNumber": "00002",
      "toAccountNumber": "00001",
      "amount": 50.00,
      "date": "2023-07-15T14:00"
   },
   {
      "id": 2,
      "typeTransaction": "TRANSFER",
      "fromAccountNumber": "00006",
      "toAccountNumber": "00004",
      "amount": 150.00,
      "date": "2023-08-18T14:00"
   }
]
````
* **POST addTransaction | Creates new transaction**
* http://localhost:8080/transactions
* Request example:
````json
[
   {
      "typeTransaction": "WITHDRAWAL",
      "accountNumber": "00018",
      "amount": 200.00
   }
]
````
* **PUT updateAccount | Updates transaction with id**
* http://localhost:8080/transactions/2
* Request example:
````json
[
   {
      "typeTransaction": "DEPOSIT",
      "accountNumber": "00010",
      "amount": 3000.00
   }
]
````
* **DELETE deleteTransaction | Deletes transaction with id**
* http://localhost:8080/transactions/3

# ServiceController
* **POST deposit | Increases the invoice amount and saves the receipt to a folder 'check'**
* http://localhost:8080/services/deposit
* Request example:
````json
[
   {
      "typeTransaction": "DEPOSIT",
      "accountNumber": "00002",
      "amount": 200.00
   }
]
````

* **POST withdraw | Reduces the invoice amount and saves the receipt to a folder 'check'**
* http://localhost:8080/services/withdraw
* Request example:
````json
[
   {
      "typeTransaction": "WITHDRAWAL",
      "accountNumber": "00014",
      "amount": 200.00
   }
]
````

* **POST transfer | Transfers the amount from one account to another and saves the receipt in a folder 'check'**
* http://localhost:8080/services/transfer
* Request example:
````json
[
   {
      "typeTransaction": "TRANSFER",
      "accountNumber": "00014,00012",
      "amount": 100.00
   }
]
````


* **POST accrueInterest | Performs interest accrual at the end of the month and saves the receipt in a folder 'check'**
* http://localhost:8080/services/accrueInterest
