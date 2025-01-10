# csu33012-2425-project26

# RateMyModule TCD Application (csu33012-2425-project26)

## Description
The RateMyModule TCD application is a web-based platform designed to allow users to interact with a Spring Boot backend and a React frontend. The frontend is built with React, using Vite for fast development, while the backend is a Spring Boot application that handles database interactions and API integrations.

## Dependencies

# Project26 README

## Overview
This project includes a frontend built with modern JavaScript tools and a backend developed with Spring Boot. It integrates database handling, API services, and testing frameworks for a robust and scalable application.

## User Expierences Our Journey
https://drive.google.com/drive/folders/1z9Zf2wxnzpm2ZRuQpDbkhGzuxlCz7a-Q?usp=drive_link

## Frontend Demo
https://media.heanet.ie/page/62924c8b4dc341c39e2a610c807e1631

---

## Installation

### Frontend Installation
1. Clone the repository:
   ```bash
   git clone https://gitlab.scss.tcd.ie/csu33012-2425-group26/csu33012-2425-project26.git
   cd csu33012-2425-project26
   ```
2. Navigate to the frontend directory and install the dependencies:
   ```bash
   npm install
   ```

### Backend Installation
1. Ensure you have **Java** and **Maven** installed.
2. Navigate to the backend directory (where the `pom.xml` file is located).
3. Install the dependencies:
   ```bash
   mvn clean install
   ```

---

## Running the Project

### Running the Frontend
To run the frontend in development mode:
```bash
npm run dev
```

### Running the Backend
To start the backend with Spring Boot:
```bash
mvn spring-boot:run
```

---

## Backend Dependencies

The backend uses the following dependencies (specified in `pom.xml`):

- **Spring Boot Web Starter**: Provides REST API support.
- **Spring Data JPA**: Enables database access and integration.
- **Lombok**: Simplifies Java development with annotations for boilerplate code.
- **SQLite JDBC Driver**: Provides support for SQLite database connections.
- **Spring Boot Starter Test**: Adds testing support.
- **Spring Boot OpenAI API Starter**: Enables integration with OpenAI's API.
- **Jackson Databind**: Facilitates JSON processing and data binding.

To install these dependencies, ensure Maven is configured and run:
```bash
mvn clean install

```


## Creating the Database

To set up the database for this project, the following Python script is used to scrape module information and populate the SQLite database:

### Dependencies
Ensure you have the following Python packages installed:
- `requests`
- `beautifulsoup4`
- `sqlite3` (part of Python's standard library)

---

## Contributing

If you wish to contribute:
1. Fork the repository.
2. Create a new branch for your changes:
   ```bash
   git checkout -b your-feature-branch
   ```
3. Commit your changes and push to your fork.
4. Submit a pull request with a description of your changes.

Before submitting, ensure:
- Your changes adhere to the project's contribution guidelines.
- All tests are passing.

---

## License

This project is licensed under the **MIT License**. See the `LICENSE` file for full details.

---

## Authors and Acknowledgments

Special thanks to all contributors and collaborators for making this project a success!
