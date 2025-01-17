# Charity Managment System



## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Volunteer Management System is a Java-based application designed to manage volunteers, their skills, and assigned tasks. It also includes functionalities for staff to review and manage campaigns.

## Features

- Volunteer registration and skill management
- Task assignment and management for volunteers
- Campaign management for staff
- User authentication and logout functionality

## Technologies Used

- Java
- JavaFX
- Maven
- SQL

## Setup and Installation

### Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven
- IntelliJ IDEA or any other preferred IDE

### Installation Steps

1. **Clone the repository:**
   ```sh
   git clone https://github.com/AbdelrahmanKhaled18/volunteer-management-system.git
   cd volunteer-management-system
   ```

2. **Open the project in IntelliJ IDEA:**
   - Open IntelliJ IDEA.
   - Select `File` > `Open` and navigate to the project directory.

3. **Build the project using Maven:**
   ```sh
   mvn clean install
   ```

4. **Run the application:**
   - Navigate to the `src/main/java/com/example/demo2/Main.java` file.
   - Right-click and select `Run 'Main.main()'`.

## Usage

### Running the Application

- **Login Page:** Enter your credentials to log in.
- **Staff Interface:** Manage campaigns, assign volunteers, and view campaign funds.
- **Volunteer Interface:** Register skills, view assigned tasks, and manage donations.

### Navigation

- **Staff Intro Page:** Accessible via `StaffReviewPendingCampaigns.java`.
- **Volunteer Intro Page:** Accessible via `VolunteerViewAssignedTasksController.java`.

## Project Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── demo2/
│   │               ├── Main.java
│   │               ├── staff/
│   │               │   └── StaffReviewPendingCampaigns.java
│   │               └── volunteer/
│   │                   └── VolunteerViewAssignedTasksController.java
│   └── resources/
│       └── com/
│           └── example/
│               └── demo2/
│                   ├── StaffIntroPage.fxml
│                   └── VolunteerIntroPage.fxml
└── test/
    └── java/
        └── com/
            └── example/
                └── demo2/
```

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.
