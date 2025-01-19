# Charity Management System

A comprehensive application for managing volunteers, campaigns, donations, and tasks. This Java-based system leverages design patterns to ensure scalability, flexibility, and maintainability. It includes features for managing staff, volunteers, campaigns, and donations effectively.

---

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Design Patterns Used](#design-patterns-used)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

---

## Introduction

The Charity Management System is designed to streamline the management of volunteers, their skills, assigned tasks, and donations. It aims to make charity organizations more efficient and organized by implementing robust and modular features.

---

## Features

- **Volunteer Management**: Registration, skill management, and task assignment.
- **Campaign Management**: Creation and monitoring of fundraising campaigns with state management.
- **Donation Management**: Supports in-kind and monetary donations with validation and notifications.
- **Authentication**: User login functionality using email and mobile number.
- **Observer Pattern**: Real-time updates for campaign progress.
- **Command Pattern**: Undo and redo functionality for donation actions.
- **State Management**: Campaign and task lifecycle management.

---

## Technologies Used

- **Programming Language**: Java
- **GUI Framework**: JavaFX
- **Database**: MySQL (phpMyAdmin for management)
- **Build Tool**: Maven
- **Design Patterns**: Observer, Command, Proxy, Factory, Decorator, Adapter, and State patterns.

---

## Setup and Installation

### Prerequisites
- Install Java JDK 17 or higher.
- Install MySQL and phpMyAdmin.
- Install Maven for project dependency management.

### Steps
1. **Database Setup**:
   - Import the database schema from the provided SQL file in phpMyAdmin.
   - Update the database connection configurations in the `DatabaseConnection` class.

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/charity-management-system.git
   cd charity-management-system
   ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   - Use your IDE (e.g., IntelliJ IDEA or Eclipse) to run the `MainApp` class.

---

## Usage

- **User Authentication**: Log in as a volunteer or staff member.
- **Campaign Creation**: Staff can create, monitor, and manage campaigns.
- **Task Assignment**: Assign tasks to volunteers and track progress.
- **Donation Processing**: Accept monetary and in-kind donations with real-time updates.

---

## Design Patterns Used

### **1. Observer Pattern**
- **Purpose**: Allows objects to observe changes in another object and get notified when changes occur.
- **Implementation**:
   - `Campaign` acts as the **subject** that notifies observers.
   - `CampaignTargetObserver` observes changes in the campaign's collected amount and updates the progress.
- **Use Case**:
   - When a new donation is made to a campaign, the `CampaignTargetObserver` is notified to update the campaign's progress towards its goal.

### **2. Command Pattern**
- **Purpose**: Encapsulates requests as objects, allowing undo and redo functionality.
- **Implementation**:
   - `MakeDonationCommand` encapsulates the logic for processing a donation.
   - `DonationInvoker` manages a stack of executed commands to allow undo/redo operations.
- **Use Case**:
   - Enables reversing a donation action (e.g., refunding a donation) if needed, ensuring flexibility in donation management.

### **3. Factory Pattern**
- **Purpose**: Provides a way to create objects without specifying their concrete classes.
- **Implementation**:
   - `PersonFactory` is used to create instances of `Person`, which can be a `Volunteer` or `Staff`.
- **Use Case**:
   - Simplifies the creation of `Person` objects, reducing the complexity of object instantiation.

### **4. Proxy Pattern**
- **Purpose**: Controls access to a certain object by providing a surrogate or placeholder.
- **Implementation**:
   - `TaskProtectiveProxy` ensures that only authorized staff members can manage campaigns.
- **Use Case**:
   - Prevents unauthorized users from modifying campaign details, enhancing security and role-based access.

### **5. Decorator Pattern**
- **Purpose**: Adds additional responsibilities to an object dynamically.
- **Implementation**:
   - `PaymentDecorator` is used to enhance the `BasicPayment` functionality.
   - `DiscountsDecorator` and `ExtraFeesDecorator` add discounts and service fees to the payment.
- **Use Case**:
   - Customizes payment processing by dynamically applying discounts or fees based on the context.

### **6. Adapter Pattern**
- **Purpose**: Converts the interface of a class into another interface that clients expect.
- **Implementation**:
   - `EmailAdapter` integrates email functionality into the system by adapting it to the existing interface.
- **Use Case**:
   - Allows the system to send emails (e.g., notifications to donors) using an external email library.

### **7. State Pattern**
- **Purpose**: Allows an object to alter its behavior when its internal state changes.
- **Implementation**:
   - `CampaignState` defines the different states a campaign can have (e.g., `PlannedState`, `ActiveState`, `CompletedState`).
   - Each state has its own behavior implemented in its respective class.
- **Use Case**:
   - Manages the lifecycle of a campaign, ensuring appropriate transitions (e.g., from `InitiateState` to `ActiveState`).

### **8. Strategy Pattern**
- **Purpose**: Defines a family of algorithms, encapsulates each one, and makes them interchangeable.
- **Implementation**:
   - `ILogin` interface defines the login strategy.
   - `EmailLogin` and `MobileNumberLogin` implement different strategies for user authentication.
- **Use Case**:
   - Provides flexibility in authentication, allowing users to log in using either their email or phone number.

### **9. Template Method Pattern**
- **Purpose**: Defines the skeleton of an algorithm and lets subclasses override specific steps.
- **Implementation**:
   - `Donation` class defines common donation behaviors.
   - Subclasses like `MoneyDonation` and `InKindDonation` override specific steps such as validation and saving.
- **Use Case**:
   - Streamlines the donation process by providing a shared structure while allowing customization for different donation types.

### **10. Iterator Pattern**
- **Purpose**: Provides a way to access elements of a collection sequentially without exposing its underlying representation.
- **Implementation**:
   - Used to iterate over collections such as tasks assigned to volunteers.
- **Use Case**:
   - Allows efficient traversal of a volunteer's assigned tasks.

---

## Project Structure

```
src/main/java/com/example/demo2/
│
├── staff/                     # Staff-related controllers
├── volunteer/                 # Volunteer-related controllers
├── model/                     # Data models and design patterns
│   ├── DesignPatterns/        # Implementation of design patterns
│   ├── Address.java           # Address entity
│   ├── Campaign.java          # Campaign entity
│   ├── Task.java              # Task entity
│   └── ...                    # Other model classes
└── resources/                 # FXML files for JavaFX UI
```

---

## Contributing

Contributions are welcome! Please follow these steps:
1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature/your-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add your feature"
   ```
4. Push to your branch:
   ```bash
   git push origin feature/your-feature
   ```
5. Create a pull request.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

