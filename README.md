# employee-management

This repository demonstrates a modular monolith architecture using Spring Boot and Java. The architecture is designed to be easily split into microservices by extracting functional modules with minimal effort. It emphasizes loose coupling, dependency injection, and modular design.


## Modules

### Functional Modules

The architecture comprises multiple functional modules and a technical web module. In this example, we have two functional modules:

1. **Employee Profile**:
    - Manages employee details such as personal information, department, and role.
    - Exposes APIs for CRUD operations on employee profiles.

2. **Employee Payroll**:
    - Handles payroll operations such as salary details, deductions, and payments.
    - Exposes APIs for managing payroll-related data.

Each module consists of:
- **Web Layer**: REST controllers to expose APIs.
- **API Layer**: Interfaces defining the module’s functionality.
- **Implementation Layer**: Business logic and database interactions.

All the layers are packaged as independent libraries, distributed as JAR files.

#### API Layer

This is an independent jar, which will contain interfaces that expose business functions related to the respective module.
This jar will always be packed in the app container and should not contain any kind of implementation as it is an abstraction
layer, providing necessary decoupling between the web and implementation layer.


#### Implementation Layer
The implementation layer includes
* implementation of the API specification (interface) provided in api layer.
* code for interaction with database (repository layer, entity classes)
* code for remote clients for external services or messaging queues, DTOs etc.


#### Web Layer

This layer has resources specific to REST api exposure (OpenAPI spec, Controller code) to external applications.

## **Features**

1. **Modular Monolith Design**:
    - Separate database schemas for each module to minimize coupling.
    - Independent module structure for easy separation into microservices.

2. **Flexible Deployment**:
    - **Monolith Mode**: All modules are deployed in a single runtime for simplicity.
    - **Microservices Mode**: Modules are deployed as independent services with inter-module communication via HTTP.

3. **Distributed Transactions**:
    - Uses **Atomikos** for distributed transaction management across separate data sources.

4. **Database Versioning**:
    - Manages schema migrations using **Flyway** for consistent database structure.

---

## **Project Structure**

```plaintext
employee-management/
├── employee-profile/
│   ├── api/         # Defines the Profile API interfaces
│   ├── web/         # REST controllers for Profile module
│   └── impl/        # Business logic and repository for Profile
├── employee-payroll/
│   ├── api/         # Defines the Payroll API interfaces
│   ├── web/         # REST controllers for Payroll module
│   └── impl/        # Business logic and repository for Payroll
├── app/             # Main application orchestrating all modules
```

---
## Deployment Modes

The key feature of this architecture style is the capability to deploy this application in a monolith as well as a collection of
microservices based on configuration.

##### How do we achieve that?

As described above, each module is a cohesive unit , comprising a service layer with business logic, a repository layer and models.
Each of these modules are packaged in a jar, each module having a separate and independent database schema, which can only be accessed by the respective module.

To facilitate these modes, Maven profiles are used for customized builds.

### Monolith Mode

In this mode, all the module jars are packaged within a single Jar and the whole application runs in a single runtime. The interaction between
the modules is just a java method call, by injecting the dependency of corresponding module's api interface. These calls can also use JTA or Spring transactions
in these calls to support transactional nature if required.

**Maven Profile Configuration**:
```xml
<profile>
    <id>monolith</id>
    <activation>
        <property>
            <name>deployment.mode</name>
            <value>monolith</value>
        </property>
    </activation>
    <build>
        <finalName>employee-management-monolith</finalName>
        <directory>${project.basedir}/target/monolith</directory>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</profile>
```
**Build Command**:
```bash
mvn clean install -Pmonolith
```

In this reference implementation, Atomikos is used to support distribtued transactions, as each module has its own Datasource.

#### Architecture

![Monolith Architecture](docs/monolith-arch.png)

### Microservice Mode

Each module has a capability to be deployed as an independent runtime. These modules can expose a REST layer for external applications, hiding all the module
services and repository behind it, acting like a typical n-tier applications. This allows them to be deployed as separate services, communicating via HTTP.

**Profile Maven Profile Configuration**:
```xml
<profile>
    <id>employee-profile</id>
    <activation>
        <property>
            <name>employee-profile</name>
        </property>
    </activation>
    <build>
        <finalName>employee-profile-app</finalName>
        <directory>${project.basedir}/target/profile</directory>
        <plugins>
            <plugin>
                <groupId>org.graalvm.buildtools</groupId>
                <artifactId>native-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                        <exclude>
                            <groupId>com.tech</groupId>
                            <artifactId>employee-payroll-impl</artifactId>
                        </exclude>
                        <exclude>
                            <groupId>com.tech</groupId>
                            <artifactId>employee-payroll-web</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</profile>
```

**Payroll Maven Profile Configuration**:
```xml
<profile>
    <id>employee-payroll</id>
    <activation>
        <property>
            <name>employee-payroll</name>
        </property>
    </activation>
    <build>
        <finalName>employee-payroll-app</finalName>
        <directory>${project.basedir}/target/payroll</directory>
        <plugins>
            <plugin>
                <groupId>org.graalvm.buildtools</groupId>
                <artifactId>native-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                        <exclude>
                            <groupId>com.tech</groupId>
                            <artifactId>employee-profile-impl</artifactId>
                        </exclude>
                        <exclude>
                            <groupId>com.tech</groupId>
                            <artifactId>employee-profile-web</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</profile>
```
The web and implementation dependencies from other modules are excluded from being packaged.

**Build Commands**:
- Profile Module:
  ```bash
  mvn clean install -Pemployee-profile
  ```
- Payroll Module:
  ```bash
  mvn clean install -Pemployee-payroll
  ```

---
#### Architecture


![Microservice Architecture](docs/microservice-modular.png)

## **API Endpoints**

### **Profile Module**
- **GET** `/profiles/{id}`: Fetch employee profile by ID.
- **POST** `/profiles`: Create a new employee profile.
- **PUT** `/profiles/{id}`: Update an existing profile.
- **DELETE** `/profiles/{id}`: Delete an employee profile.

### **Payroll Module**
- **GET** `/payrolls/{id}`: Fetch payroll details by ID.
- **POST** `/payrolls`: Create payroll for an employee.
- **PUT** `/payrolls/{id}`: Update payroll details.
- **DELETE** `/payrolls/{id}`: Delete payroll details.
## **Configuration**

### **application.yaml**

Key properties:
```yaml
deployment:
  mode: monolith  # Change to "micro" for microservices mode

profile:
  datasource:
    url: jdbc:postgresql://localhost:54320/postgres?currentSchema=employee_profile
    user: profile_user
    password: profile_password

payroll:
  datasource:
    url: jdbc:postgresql://localhost:54320/postgres?currentSchema=employee_payroll
    user: payroll_user
    password: payroll_password
```

## **Contributing**

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m "Description of changes"`.
4. Push to the branch: `git push origin feature-name`.
5. Open a pull request.

---

## **License**

This project is licensed under the [MIT License](LICENSE).