-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 17, 2024 at 06:25 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `charity_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`id`, `name`, `parent_ID`) VALUES
(1, 'USA', NULL),
(2, 'India', NULL),
(3, 'United Kingdom', NULL),
(4, 'Australia', NULL),
(5, 'California', 1),
(6, 'New York', 1),
(7, 'San Francisco', 2),
(8, 'Maharashtra', 6),
(9, 'Delhi', 6),
(10, 'Mumbai', 7),
(11, 'England', 3),
(12, 'Scotland', 3),
(13, 'London', 11),
(14, 'Manchester', 11),
(15, 'Edinburgh', 12),
(16, 'Glasgow', 12),
(17, 'New South Wales', 4),
(18, 'Victoria', 4),
(19, 'Sydney', 4);

-- --------------------------------------------------------

--
-- Table structure for table `assigned_tasks`
--

CREATE TABLE `assigned_tasks` (
  `person_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `assigned_tasks`
--

INSERT INTO `assigned_tasks` (`person_id`, `task_id`) VALUES
(1, 1),
(1, 6),
(2, 2),
(2, 5),
(3, 4),
(3, 9),
(4, 7),
(4, 8);

-- --------------------------------------------------------

--
-- Table structure for table `donation`
--

CREATE TABLE `donation` (
  `id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `date` date NOT NULL,
  `person_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `gender` enum('Male','Female','Other') NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_type` enum('volunteer','staff') NOT NULL,
  `national_id` varchar(255) NOT NULL,
  `date_of_birth` date NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `address_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `name`, `gender`, `phone_number`, `email`, `password`, `user_type`, `national_id`, `date_of_birth`, `is_active`, `address_id`) VALUES
(1, 'John Doe', 'Male', '1234567890', 'john.doe@example.com', 'password123', 'staff', '12345678901234', '1985-05-15', 1, 13),
(2, 'Jane Smith', 'Female', '9876543210', 'jane.smith@example.com', 'password456', 'staff', '23456789012345', '1990-03-22', 1, 18),
(3, 'Alice Johnson', 'Female', '1122334455', 'alice.johnson@example.com', 'password789', 'volunteer', '34567890123456', '1995-07-10', 1, 9),
(4, 'Bob Williams', 'Male', '5566778899', 'bob.williams@example.com', 'password012', 'volunteer', '45678901234567', '1988-11-30', 1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `skill`
--

CREATE TABLE `skill` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `skill`
--

INSERT INTO `skill` (`id`, `name`, `description`) VALUES
(1, 'Programming', 'Ability to write and debug code in various programming languages'),
(2, 'Communication', 'Excellent verbal and written communication skills'),
(3, 'Problem Solving', 'Ability to analyze issues and come up with effective solutions'),
(4, 'Time Management', 'Skill in managing time effectively to meet deadlines'),
(5, 'Teamwork', 'Capability to work effectively as part of a team');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `person_id` int(11) NOT NULL,
  `position` varchar(255) NOT NULL,
  `department` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`person_id`, `position`, `department`) VALUES
(1, 'Manager', 'Human Resources'),
(2, 'Coordinator', 'Operations');

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE `task` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `task`
--

INSERT INTO `task` (`id`, `name`, `description`) VALUES
(1, 'Prepare Monthly Report', 'Compile and analyze monthly data for all departments.'),
(2, 'Organize Volunteer Meeting', 'Arrange a meeting to discuss the upcoming events with volunteers.'),
(3, 'Update Database', 'Ensure all records are up to date and remove duplicates.'),
(4, 'Conduct Training Session', 'Provide training for new volunteers on charity operations.'),
(5, 'Plan Fundraising Event', 'Organize a charity event to raise funds for the organization.'),
(6, 'Design Marketing Material', 'Create posters and flyers for the upcoming campaign.'),
(7, 'Coordinate Logistics', 'Arrange transportation and equipment for charity distribution.'),
(8, 'Follow Up with Donors', 'Send thank-you emails and updates to all donors.'),
(9, 'Develop New Strategy', 'Propose and document a strategy to increase volunteer engagement.'),
(10, 'Audit Financial Records', 'Review and verify all financial records for accuracy.');

-- --------------------------------------------------------

--
-- Table structure for table `volunteer_skills`
--

CREATE TABLE `volunteer_skills` (
  `person_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `volunteer_skills`
--

INSERT INTO `volunteer_skills` (`person_id`, `skill_id`) VALUES
(3, 1),
(3, 2),
(3, 4),
(4, 3),
(4, 5),
(4, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`),
  ADD KEY `parent_ID` (`parent_ID`);

--
-- Indexes for table `assigned_tasks`
--
ALTER TABLE `assigned_tasks`
  ADD KEY `person_id` (`person_id`),
  ADD KEY `task_id` (`task_id`);

--
-- Indexes for table `donation`
--
ALTER TABLE `donation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `person_id` (`person_id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD KEY `address_id` (`address_id`);

--
-- Indexes for table `skill`
--
ALTER TABLE `skill`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD KEY `person_id` (`person_id`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `volunteer_skills`
--
ALTER TABLE `volunteer_skills`
  ADD KEY `person_id` (`person_id`),
  ADD KEY `skill_id` (`skill_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `address`
--
ALTER TABLE `address`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `donation`
--
ALTER TABLE `donation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `skill`
--
ALTER TABLE `skill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `address_ibfk_1` FOREIGN KEY (`parent_ID`) REFERENCES `address` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `assigned_tasks`
--
ALTER TABLE `assigned_tasks`
  ADD CONSTRAINT `assigned_tasks_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `assigned_tasks_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `donation`
--
ALTER TABLE `donation`
  ADD CONSTRAINT `donation_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `person`
--
ALTER TABLE `person`
  ADD CONSTRAINT `person_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `volunteer_skills`
--
ALTER TABLE `volunteer_skills`
  ADD CONSTRAINT `volunteer_skills_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `volunteer_skills_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
