-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 31, 2025 at 08:41 PM
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
-- Database: `meyers`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts_entity`
--

CREATE TABLE `accounts_entity` (
  `account_id` bigint(20) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_role` enum('ROLE_ADMINISTRATOR','ROLE_EMPLOYEE','ROLE_JOURNALIST','ROLE_MANAGER','ROLE_PARTY') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `accounts_entity`
--

INSERT INTO `accounts_entity` (`account_id`, `user_email`, `user_password`, `user_role`) VALUES
(2, 'robot@tako.cz', '$2a$10$xIindlhwRSNs1OSoJnSRR.HFu//Z2TcAFOIS1Qj6wmMjvZpWJ4TgS', 'ROLE_ADMINISTRATOR'),
(3, 'trecko@treck.tre', '$2a$10$tI3oqb4CAMNdAq1HP9Z.QuIwrrHDIqMjbNL./48jA5IUp3fmspoeO', 'ROLE_JOURNALIST'),
(4, 'bana@bana.ba', '$2a$10$DfgPrTJq.3x3/Rnc/QbFmO0E.Jtd5FMFEBNWtG4bKeTKGKcqc7ruK', 'ROLE_JOURNALIST'),
(5, 'retro@retro.cz', '$2a$10$SMMArOip.VIHSJmTts.oeeMFO8VA0ajQ7X7.Ho8UiX9iGkuxe7LiG', 'ROLE_PARTY'),
(7, 'dog@dog.do', '$2a$10$AZlMICAxEgC4ZHMosoB6Veo9//xrzSuUDi.OL5ylteSwzhXSiLZOa', 'ROLE_MANAGER'),
(10, 'gg@gg.gg', '$2a$10$xqAoNiRWOspF5OKCYP7FsuRudsn2P.CB1GbT0SmRL2Di/MxxCoInu', 'ROLE_MANAGER'),
(16, 'pedro@lol.ko', '$2a$10$2aAE869NmUAsiC36AlnxTu4IxopNGYjdRyYh76nRWSrIrQ88QrNGq', 'ROLE_JOURNALIST'),
(18, 'tra@tr.tr', '$2a$10$R0MqBb1gqYFka7g3J9GFUu4NxUIfpXqbm1kKg3TDBTUdz9IPrNX9G', 'ROLE_PARTY');

-- --------------------------------------------------------

--
-- Table structure for table `contracts_entity`
--

CREATE TABLE `contracts_entity` (
  `contract_id` bigint(20) NOT NULL,
  `begin_date` date NOT NULL,
  `contract_number` varchar(255) NOT NULL,
  `insurance_type` enum('ANIMAL','DISABILITY','HEALTH','LEGAL','LIFE','NATURAL_DISASTERS','PROPERTY','TRAVEL','VEHICLE') NOT NULL,
  `insured_subject` enum('ANIMAL','ASSETS','LEGAL','PERSON','PROPERTY') NOT NULL,
  `liability_percentage` float NOT NULL,
  `price_per_period` bigint(20) NOT NULL,
  `signature_date` date NOT NULL,
  `insurances_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `contracts_entity`
--

INSERT INTO `contracts_entity` (`contract_id`, `begin_date`, `contract_number`, `insurance_type`, `insured_subject`, `liability_percentage`, `price_per_period`, `signature_date`, `insurances_id`) VALUES
(39, '2025-07-31', '1', 'LIFE', 'PERSON', 0.01, 1, '2025-07-31', 1),
(42, '2024-06-30', '2', 'LIFE', 'PERSON', 0.01, 1, '2023-06-30', 1);

-- --------------------------------------------------------

--
-- Table structure for table `deleted_insurances_entity`
--

CREATE TABLE `deleted_insurances_entity` (
  `deleted_insurances_id` bigint(20) NOT NULL,
  `delete_description` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `insurances_type` enum('ANIMAL','DISABILITY','HEALTH','LEGAL','LIFE','NATURAL_DISASTERS','PROPERTY','TRAVEL','VEHICLE') NOT NULL,
  `maximum_insurance_value` bigint(20) NOT NULL,
  `maximum_payout_value` bigint(20) NOT NULL,
  `minimum_insurance_value` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `removal_reason` enum('EXPIRED','REDUNDANT','UPGRADE') NOT NULL,
  `request_date` date NOT NULL,
  `todays_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `deleted_insurances_entity`
--

INSERT INTO `deleted_insurances_entity` (`deleted_insurances_id`, `delete_description`, `description`, `insurances_type`, `maximum_insurance_value`, `maximum_payout_value`, `minimum_insurance_value`, `name`, `removal_reason`, `request_date`, `todays_date`) VALUES
(1, 'dsa', 'Asd', 'HEALTH', 100, 100, 100, 'Lama-', 'EXPIRED', '2025-07-24', '2025-07-24'),
(2, 'asd', 'Toto', 'LIFE', 100, 100, 100, 'Lama-', 'REDUNDANT', '2025-07-08', '2025-07-24'),
(3, 'fff', 'Dr', 'HEALTH', 1, 1, 1, 'Brembo', 'EXPIRED', '2025-07-28', '2025-07-28'),
(4, 'fasd', 'Kokosko', 'HEALTH', 200, 151, 100, 'Tremble', 'REDUNDANT', '2025-07-02', '2025-07-30'),
(5, 'asd', 'ASFDfsdafs', 'DISABILITY', 6546545445, 546654654654, 16565, 'Merlin', 'REDUNDANT', '2025-07-01', '2025-07-31'),
(6, 'sdfg', 'TRauasodfhsoif', 'PROPERTY', 6868747498874, 465498879874897, 16684, 'House STOLEN', 'REDUNDANT', '2025-07-30', '2025-07-31');

-- --------------------------------------------------------

--
-- Table structure for table `deleted_parties_entity`
--

CREATE TABLE `deleted_parties_entity` (
  `delete_reason_id` bigint(20) NOT NULL,
  `additional_information` varchar(255) DEFAULT NULL,
  `birth_day` date NOT NULL,
  `birth_number` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `date_of_request` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `party_id` bigint(20) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `removal_reason` enum('CEASED','REDUNDANT','RELOCATION','REQUESTED') NOT NULL,
  `street` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `todays_date` date NOT NULL,
  `zip_code` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `deleted_parties_entity`
--

INSERT INTO `deleted_parties_entity` (`delete_reason_id`, `additional_information`, `birth_day`, `birth_number`, `city`, `date_of_request`, `email`, `name`, `party_id`, `phone_number`, `removal_reason`, `street`, `surname`, `todays_date`, `zip_code`) VALUES
(1, 'asd', '2025-07-21', '384592/7849', 'Brdlice', '2025-07-02', 'sanyhes@yahoo.com', 'Petr', 11, '+420791345628', 'REDUNDANT', 'Letnická 784/52', 'Henesová', '2025-07-23', '78431'),
(2, 'asd', '2025-07-01', '784592/7849', 'Hrskov', '2025-07-15', 'jonhy@rocketfingers.com', 'Petr', 1, '+420975864325', 'REQUESTED', 'Helnická 657/82', 'Henský', '2025-07-28', '70007'),
(3, 'erwrew', '2024-07-29', '134785/6473', 'Brdlice', '2025-07-16', 'marge@homer.cz', 'Margarita', 23, '+420791345628', 'REQUESTED', 'Mrchlovická 300', 'Velká', '2025-07-30', '74582'),
(4, 'kl', '2025-07-02', '3', 'C', '2025-07-08', 'sanyhes@yahoo.com', 'C', 16, '+420777666555', 'REDUNDANT', 'C', 'C', '2025-07-31', '3'),
(5, 'asd', '2025-07-08', '5', 'A', '2025-07-08', 'da@da.da', 'D', 22, '+420777666555', 'REQUESTED', 'A', 'D', '2025-07-31', '1');

-- --------------------------------------------------------

--
-- Table structure for table `incidents_entity`
--

CREATE TABLE `incidents_entity` (
  `incident_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `accident_date` date NOT NULL,
  `birth_number` varchar(255) NOT NULL,
  `case_number` varchar(255) NOT NULL,
  `closure_date` date DEFAULT NULL,
  `current_status` enum('CLOSED','OPEN','PROCESSING') NOT NULL,
  `description` varchar(255) NOT NULL,
  `incident_subject` enum('ANIMAL','ASSETS','LEGAL','PERSON','PROPERTY') NOT NULL,
  `incident_type` enum('DAMAGE','ELEMENTS','INJURY','LOST','THEFT') NOT NULL,
  `report_date` date NOT NULL,
  `todays_date` date NOT NULL,
  `incident_resolution` tinyint(4) DEFAULT NULL CHECK (`incident_resolution` between 0 and 3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `incidents_entity`
--

INSERT INTO `incidents_entity` (`incident_id`, `title`, `accident_date`, `birth_number`, `case_number`, `closure_date`, `current_status`, `description`, `incident_subject`, `incident_type`, `report_date`, `todays_date`, `incident_resolution`) VALUES
(2, 'Spadla pec', '2025-07-14', '134785/6473', '350350350350', NULL, 'OPEN', 'Tonda zakopl o drát', 'PROPERTY', 'DAMAGE', '2025-07-15', '2025-07-15', NULL),
(6, 'Tento', '2024-06-20', '600', '300', NULL, 'PROCESSING', 'Au', 'PERSON', 'THEFT', '2024-06-20', '2025-07-21', NULL),
(12, 't', '2024-06-26', '5', '500', NULL, 'OPEN', 'a', 'PERSON', 'DAMAGE', '2024-06-26', '2025-07-27', NULL),
(14, 'T', '2025-07-28', '6', '2', NULL, 'OPEN', 'R', 'PERSON', 'DAMAGE', '2025-07-28', '2025-07-28', NULL),
(15, 'Ztráta peněženky', '2025-07-14', '887452/6000', '9687651468R', '2025-07-29', 'CLOSED', 'Karel zase ztratil peněženku.', 'ASSETS', 'LOST', '2025-07-25', '2025-07-29', 1),
(16, 'Kokosky', '2024-07-29', '5', '200', NULL, 'OPEN', 'na shne', 'PROPERTY', 'THEFT', '2024-06-29', '2025-07-30', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `incident_comments_entity`
--

CREATE TABLE `incident_comments_entity` (
  `incident_comment_id` bigint(20) NOT NULL,
  `comment_date` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `incident_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `incident_comments_entity`
--

INSERT INTO `incident_comments_entity` (`incident_comment_id`, `comment_date`, `description`, `title`, `incident_id`) VALUES
(30, '2025-07-29', 'jejko', 'Kelmo', 15),
(31, '2025-07-29', 'smitec', 'Konec', 15),
(37, '2025-07-31', 'asd', 'asd', 6);

-- --------------------------------------------------------

--
-- Table structure for table `insurances_entity`
--

CREATE TABLE `insurances_entity` (
  `insurances_id` bigint(20) NOT NULL,
  `description` varchar(255) NOT NULL,
  `insurances_type` enum('ANIMAL','DISABILITY','HEALTH','LEGAL','LIFE','NATURAL_DISASTERS','PROPERTY','TRAVEL','VEHICLE') NOT NULL,
  `is_annual_payment_required` bit(1) DEFAULT NULL,
  `is_auto_renewal_required` bit(1) DEFAULT NULL,
  `maximum_insurance_value` bigint(20) NOT NULL,
  `maximum_payout_value` bigint(20) NOT NULL,
  `minimum_insurance_value` bigint(20) NOT NULL,
  `minimum_policy_term` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `renewal_period` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `insurances_entity`
--

INSERT INTO `insurances_entity` (`insurances_id`, `description`, `insurances_type`, `is_annual_payment_required`, `is_auto_renewal_required`, `maximum_insurance_value`, `maximum_payout_value`, `minimum_insurance_value`, `minimum_policy_term`, `name`, `renewal_period`) VALUES
(1, 'Degralovasstos', 'LIFE', b'1', b'1', 400, 800, 202, 24, 'Basic-', 12);

-- --------------------------------------------------------

--
-- Table structure for table `news_entity`
--

CREATE TABLE `news_entity` (
  `news_id` bigint(20) NOT NULL,
  `content` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `post_date` date NOT NULL,
  `title` varchar(255) NOT NULL,
  `picture_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `news_entity`
--

INSERT INTO `news_entity` (`news_id`, `content`, `description`, `post_date`, `title`, `picture_url`) VALUES
(1, 'Texting some text to text.', 'Some text inside.', '2025-07-14', 'Cool text', '/uploads/news-pictures/grand-opening.jpg'),
(7, 'Teasd trimus el denda er trende de le mela es tu derevio el mendio bele de vue rete.', 'SDDDDDDDDDDDDDDDDDDDD', '2025-07-21', 'Tereminokal event', '/uploads/news-pictures/1.jpg'),
(13, 'asdasd', 'asd', '2025-07-31', 'Nakouknuti', '/uploads/news-pictures/tom2.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `parties_entity`
--

CREATE TABLE `parties_entity` (
  `party_id` bigint(20) NOT NULL,
  `birth_day` date NOT NULL,
  `birth_number` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `zip_code` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parties_entity`
--

INSERT INTO `parties_entity` (`party_id`, `birth_day`, `birth_number`, `city`, `email`, `name`, `phone_number`, `street`, `surname`, `zip_code`) VALUES
(2, '2025-07-31', '784592/7849', 'Mrchov', 'sanyhes@yahoo.com', 'Sandra', '+420791345628', 'Mrchlovická 7', 'Bollocks', '06661'),
(10, '2025-07-21', '887452/6000', 'Prskov', 'dada@ism.io', 'Pedro', '+420975864300', 'Mrchlovická 300', 'Kratochvíle', '23223'),
(12, '2025-07-15', '696969/6969', 'A', 'jonhy@rocketfingers.com', 'Karel', '+420791345628', 'A', 'Merlský', '1'),
(17, '2025-07-30', '4', 'D', 'jonhy@rocketfingers.com', 'D', '+420777666555', 'D', 'D', '4'),
(18, '2025-07-08', '784592/7849', 'Hrskov', 'jonhy@rocketfingers.com', 'Lola', '+420777666555', 'Letnická 784/52', 'Merlský', '0'),
(19, '2025-07-17', '600', 'Mrchov', 'pet67mer@gmail.com', 'Anton', '+420975864325', 'Letnická 784/52', 'Merlský', '12587'),
(20, '2025-07-08', '784592/7849', 'Hrskov', 'prapra@645.tr', 'Anton', '+420777666', 'Letovická 455/58', 'a', '06660'),
(21, '2025-07-29', '696969/6969', 'Mrchov', 'd@d.dd', 'Margarita', '+420975864300', 'Helnická 657/82', 'Brzovidrová', '06660'),
(24, '2024-06-30', '3', 'Hrskov', 'hel@l.cz', 'Maron', '+420791345628', 'Letovická 455/58', 'Kremble', '13247'),
(25, '2024-07-04', '784592/7849', 'Hrskov', 'asd1@fart.ct', 'Karlos', '+420975864300', 'Mrchlovická 300', 'Domber', '12587');

-- --------------------------------------------------------

--
-- Table structure for table `party_contracts_entity`
--

CREATE TABLE `party_contracts_entity` (
  `id` bigint(20) NOT NULL,
  `contract_role` enum('INSURED','POLICY_OWNER','REGISTERED','UNINSURED') DEFAULT NULL,
  `todays_date` date DEFAULT NULL,
  `contract_id` bigint(20) NOT NULL,
  `party_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `party_contracts_entity`
--

INSERT INTO `party_contracts_entity` (`id`, `contract_role`, `todays_date`, `contract_id`, `party_id`) VALUES
(42, 'POLICY_OWNER', '2025-07-30', 39, 17),
(46, 'POLICY_OWNER', '2025-07-31', 42, 17),
(54, 'INSURED', '2025-07-31', 42, 21);

-- --------------------------------------------------------

--
-- Table structure for table `removed_contracts_entity`
--

CREATE TABLE `removed_contracts_entity` (
  `removed_insurances_id` bigint(20) NOT NULL,
  `begin_date` date NOT NULL,
  `birth_number` varchar(255) NOT NULL,
  `contract_number` varchar(255) NOT NULL,
  `date_of_cancellation` date NOT NULL,
  `date_of_request` date NOT NULL,
  `delete_reason` enum('AUTOMATED','CEASED','EXPIRED','MISCONDUCT','REDUNDANT','RELOCATION','REQUESTED','UPGRADE') NOT NULL,
  `description` varchar(255) NOT NULL,
  `insurance_name` varchar(255) NOT NULL,
  `insurance_type` enum('ANIMAL','DISABILITY','HEALTH','LEGAL','LIFE','NATURAL_DISASTERS','PROPERTY','TRAVEL','VEHICLE') NOT NULL,
  `insurances_type` enum('ANIMAL','DISABILITY','HEALTH','LEGAL','LIFE','NATURAL_DISASTERS','PROPERTY','TRAVEL','VEHICLE') NOT NULL,
  `insured_subject` enum('ANIMAL','ASSETS','LEGAL','PERSON','PROPERTY') NOT NULL,
  `liability_percentage` float NOT NULL,
  `price_per_period` bigint(20) NOT NULL,
  `signature_date` date NOT NULL,
  `todays_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `removed_contracts_entity`
--

INSERT INTO `removed_contracts_entity` (`removed_insurances_id`, `begin_date`, `birth_number`, `contract_number`, `date_of_cancellation`, `date_of_request`, `delete_reason`, `description`, `insurance_name`, `insurance_type`, `insurances_type`, `insured_subject`, `liability_percentage`, `price_per_period`, `signature_date`, `todays_date`) VALUES
(1, '2025-07-16', '3', '356', '2025-07-17', '2025-07-17', 'AUTOMATED', 'Smlouva uzavřena, jelikož byla osoba odstraněna ze systému', 'Hyper', 'HEALTH', 'HEALTH', 'PERSON', 2, 12500, '2025-07-17', '2025-07-17'),
(2, '2025-07-17', '2', '150', '2025-07-17', '2025-07-17', 'AUTOMATED', 'Smlouva uzavřena, jelikož byla osoba odstraněna ze systému', 'Hyper', 'HEALTH', 'HEALTH', 'PERSON', 13, 12, '2025-07-17', '2025-07-17'),
(3, '2025-07-17', '2', '151', '2025-07-17', '2025-07-17', 'AUTOMATED', 'Smlouva uzavřena, jelikož byla osoba odstraněna ze systému', 'Hyper', 'LIFE', 'HEALTH', 'PROPERTY', 14, 13, '2025-07-17', '2025-07-17'),
(4, '2025-07-13', '134785/6473', '9875124658', '2025-07-17', '2025-07-17', 'EXPIRED', 'Mega', 'Hyper', 'HEALTH', 'HEALTH', 'PERSON', 12, 123543, '2025-07-13', '2025-07-17'),
(5, '2025-07-17', '134785/6473', '1001', '2025-07-17', '2025-07-17', 'EXPIRED', 'Mega', 'Hyper', 'HEALTH', 'HEALTH', 'PERSON', 14, 13, '2025-07-17', '2025-07-17'),
(6, '2025-07-21', '134785/6473', '99', '2025-07-21', '2025-07-21', 'EXPIRED', 'Trochu', 'Basic+', 'HEALTH', 'HEALTH', 'PERSON', 2.25, 2300, '2025-07-21', '2025-07-21'),
(7, '2025-07-21', '134785/6473', '99', '2025-07-21', '2025-07-21', 'EXPIRED', 'Trochu', 'Basic+', 'HEALTH', 'HEALTH', 'PERSON', 2.25, 2300, '2025-07-21', '2025-07-21'),
(8, '2025-07-21', '134785/6473', '99', '2025-07-21', '2025-07-21', 'EXPIRED', 'Trochu', 'Basic+', 'HEALTH', 'HEALTH', 'PERSON', 2.25, 2300, '2025-07-21', '2025-07-21'),
(9, '2025-07-21', '134785/6473', '99', '2025-07-21', '2025-07-21', 'EXPIRED', 'Trochu', 'Basic+', 'HEALTH', 'HEALTH', 'PERSON', 2.25, 2300, '2025-07-21', '2025-07-21'),
(10, '2025-07-21', '887452/6000', '99', '2025-07-21', '2025-07-21', 'EXPIRED', 'Trochu', 'Basic+', 'HEALTH', 'HEALTH', 'PERSON', 2.25, 2300, '2025-07-21', '2025-07-21'),
(11, '2025-07-22', '384592/7849', '422', '2025-07-23', '2025-07-23', 'AUTOMATED', 'Smlouva uzavřena, jelikož byla osoba odstraněna ze systému', 'Basic+', 'HEALTH', 'LIFE', 'PERSON', 5, 1235, '2025-07-22', '2025-07-23'),
(12, '2025-07-01', '1', '1', '2025-07-14', '2025-07-01', 'EXPIRED', 'Degralovasstos', 'Basic+', 'HEALTH', 'LIFE', 'PERSON', 1, 10, '2025-07-01', '2025-07-23'),
(13, '2025-07-10', '784592/7849', '2', '2025-07-23', '2025-07-23', 'REQUESTED', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'ASSETS', 12, 1250, '2025-07-01', '2025-07-23'),
(14, '2025-07-01', '784592/7849', '366', '2025-07-23', '2025-07-23', 'EXPIRED', 'Degralovasstos', 'Basic+', 'PROPERTY', 'LIFE', 'ASSETS', 2.5, 5660, '2025-07-15', '2025-07-23'),
(15, '2024-06-20', '784592/7849', '10012', '2025-07-01', '2025-07-15', 'UPGRADE', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PROPERTY', 12.12, 250, '2024-06-20', '2025-07-24'),
(16, '2024-06-20', '784592/7849', '10012', '2025-07-01', '2025-07-15', 'UPGRADE', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PROPERTY', 12.12, 250, '2024-06-20', '2025-07-24'),
(17, '2025-07-24', '4', '1', '2025-07-24', '2025-07-24', 'REQUESTED', 'Degralovasstos', 'Basic+', 'HEALTH', 'LIFE', 'PERSON', 10, 1000, '2025-07-24', '2025-07-24'),
(18, '2025-07-08', '1', '1', '2025-07-24', '2025-07-24', 'REQUESTED', 'Degralovasstos', 'Basic+', 'HEALTH', 'LIFE', 'PERSON', 10, 1000, '2025-07-01', '2025-07-24'),
(19, '2025-07-28', '5', '2', '2025-07-28', '2025-07-28', 'REDUNDANT', 'Dr', 'Brembo', 'VEHICLE', 'HEALTH', 'ASSETS', 1, 500, '2025-07-28', '2025-07-28'),
(20, '2025-07-28', '4', '2', '2025-07-28', '2025-07-28', 'REDUNDANT', 'Dr', 'Brembo', 'VEHICLE', 'HEALTH', 'ASSETS', 1, 500, '2025-07-28', '2025-07-28'),
(21, '2025-07-28', '1', '253', '2025-07-28', '2025-07-28', 'REDUNDANT', 'Dr', 'Brembo', 'VEHICLE', 'HEALTH', 'ASSETS', 1, 500, '2025-07-28', '2025-07-28'),
(22, '2024-06-28', '4', '1', '2025-07-29', '2025-07-29', 'REDUNDANT', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PERSON', 0.02, 1, '2024-06-28', '2025-07-29'),
(23, '2024-05-28', '4', '1', '2025-07-29', '2025-07-29', 'REDUNDANT', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PERSON', 0.01, 1, '2024-05-28', '2025-07-29'),
(24, '2024-06-29', '5', '500', '2025-07-30', '2025-07-30', 'REQUESTED', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PERSON', 0.02, 100, '2023-06-30', '2025-07-30'),
(25, '2025-07-30', '600', '100100100', '2025-07-30', '2025-07-30', 'REQUESTED', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PROPERTY', 0.02, 300, '2025-07-30', '2025-07-30'),
(26, '2025-07-30', '3', '100100100', '2025-07-22', '2025-07-29', 'REDUNDANT', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PROPERTY', 0.02, 300, '2025-07-30', '2025-07-31'),
(27, '2025-07-30', '5', '100100100', '2025-07-02', '2025-07-09', 'REQUESTED', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PROPERTY', 0.02, 300, '2025-07-30', '2025-07-31'),
(28, '2024-05-29', '4', '5', '2025-06-30', '2025-07-15', 'REDUNDANT', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PERSON', 0.02, 3, '2024-06-30', '2025-07-31'),
(29, '2024-05-29', '696969/6969', '5', '2025-06-30', '2025-07-15', 'REDUNDANT', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PERSON', 0.02, 3, '2024-06-30', '2025-07-31'),
(30, '2024-05-29', '696969/6969', '5', '2025-06-30', '2025-07-15', 'REDUNDANT', 'Degralovasstos', 'Basic+', 'LIFE', 'LIFE', 'PERSON', 0.02, 3, '2024-06-30', '2025-07-31'),
(31, '2025-07-31', '5', '300', '2025-07-31', '2025-07-31', 'REDUNDANT', 'TRauasodfhsoif', 'House STOLEN', 'PROPERTY', 'PROPERTY', 'PERSON', 2.5, 2500, '2025-07-31', '2025-07-31'),
(32, '2025-07-31', '696969/6969', '300', '2025-07-31', '2025-07-31', 'REQUESTED', 'TRauasodfhsoif', 'House STOLEN', 'PROPERTY', 'PROPERTY', 'PERSON', 2.5, 2500, '2025-07-31', '2025-07-31'),
(33, '2025-07-31', '696969/6969', '300', '2025-07-31', '2025-07-31', 'REQUESTED', 'TRauasodfhsoif', 'House STOLEN', 'PROPERTY', 'PROPERTY', 'PERSON', 2.5, 2500, '2025-07-31', '2025-07-31');

-- --------------------------------------------------------

--
-- Table structure for table `removed_incidents_entity`
--

CREATE TABLE `removed_incidents_entity` (
  `deleted_incident_id` bigint(20) NOT NULL,
  `accident_date` date NOT NULL,
  `birth_number` varchar(255) NOT NULL,
  `case_number` varchar(255) NOT NULL,
  `closure_date` date DEFAULT NULL,
  `current_status` enum('CLOSED','OPEN','PROCESSING') NOT NULL,
  `description` varchar(255) NOT NULL,
  `incident_removal_reason` tinyint(4) NOT NULL CHECK (`incident_removal_reason` between 0 and 4),
  `incident_resolution` tinyint(4) DEFAULT NULL CHECK (`incident_resolution` between 0 and 3),
  `incident_subject` enum('ANIMAL','ASSETS','LEGAL','PERSON','PROPERTY') NOT NULL,
  `incident_type` enum('DAMAGE','ELEMENTS','INJURY','LOST','THEFT') NOT NULL,
  `removal_date` date NOT NULL,
  `removal_description` varchar(255) NOT NULL,
  `report_date` date NOT NULL,
  `title` varchar(255) NOT NULL,
  `todays_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `removed_incidents_entity`
--

INSERT INTO `removed_incidents_entity` (`deleted_incident_id`, `accident_date`, `birth_number`, `case_number`, `closure_date`, `current_status`, `description`, `incident_removal_reason`, `incident_resolution`, `incident_subject`, `incident_type`, `removal_date`, `removal_description`, `report_date`, `title`, `todays_date`) VALUES
(1, '2025-07-08', '976315/8476', '95464654564', '2025-07-16', 'CLOSED', 'Petr si zase zlomil ruku.', 0, 1, 'PERSON', 'INJURY', '2025-07-17', 'Jen tak.', '2025-07-17', 'Zlomená ruka', '2025-07-15'),
(2, '2025-07-08', '976315/8476', '95464654564', '2025-07-16', 'CLOSED', 'Petr si zase zlomil ruku.', 0, 1, 'PERSON', 'INJURY', '2025-07-17', 'asd', '2025-07-17', 'Zlomená ruka', '2025-07-15'),
(3, '2025-07-08', '976315/8476', '95464654564', '2025-07-16', 'CLOSED', 'Petr si zase zlomil ruku.', 0, 1, 'PERSON', 'INJURY', '2025-07-17', 'dasd', '2025-07-17', 'Zlomená ruka', '2025-07-15'),
(4, '2025-07-08', '976315/8476', '95464654564', '2025-07-16', 'CLOSED', 'Petr si zase zlomil ruku.', 0, 1, 'PERSON', 'INJURY', '2025-07-17', 'asdds', '2025-07-17', 'Zlomená ruka', '2025-07-15'),
(5, '2025-07-17', '123', '222', '2025-07-17', 'CLOSED', 'hle popis', 0, 2, 'ASSETS', 'THEFT', '2025-07-17', 'sdf', '2025-07-17', 'Toto', '2025-07-17'),
(6, '2025-07-21', '200', '333', NULL, 'CLOSED', 'a tamto', 0, NULL, 'PERSON', 'DAMAGE', '2025-07-21', 'aaa', '2025-07-21', 'Tohle', '2025-07-21'),
(7, '2025-07-22', '1', '100', '2025-07-22', 'CLOSED', 'S', 0, 2, 'PERSON', 'DAMAGE', '2025-07-22', 'asd', '2025-07-22', 'T', '2025-07-22'),
(8, '2025-07-27', '9', '700', NULL, 'OPEN', 'sr', 1, NULL, 'ANIMAL', 'INJURY', '2025-07-27', 'asd', '2025-07-27', 'Au', '2025-07-27'),
(9, '2025-07-16', '3', '200', NULL, 'OPEN', 'R', 2, NULL, 'PERSON', 'DAMAGE', '2025-07-27', 'dsssa', '2025-07-14', 'T', '2025-07-27'),
(10, '2025-07-21', '5', '100', '2025-07-27', 'CLOSED', 'kelo', 0, 0, 'PROPERTY', 'DAMAGE', '2025-07-27', 'sdf', '2025-07-09', 'Tero', '2025-07-27'),
(11, '2025-07-23', '1', '1', '2025-07-28', 'CLOSED', 'Some text insider.', 1, 1, 'PERSON', 'DAMAGE', '2025-07-28', 'asd', '2025-07-23', 's', '2025-07-23'),
(12, '2025-07-30', '3', '1', '2025-07-30', 'CLOSED', 'ne', 1, 1, 'ASSETS', 'LOST', '2025-07-30', 'asd', '2025-07-30', 'Velký název', '2025-07-16'),
(13, '2025-07-08', '6', '100', '2025-07-31', 'CLOSED', 'afasdfsdfsdf', 2, 1, 'PERSON', 'DAMAGE', '2025-07-31', 'asdsda\r\n', '2025-07-04', 'Tre', '2025-07-31'),
(14, '2025-07-30', '3', '1', '2025-07-31', 'CLOSED', 'asfasff', 2, 1, 'PERSON', 'DAMAGE', '2025-07-31', 'asd', '2025-07-30', 'AWFasf', '2025-07-31');

-- --------------------------------------------------------

--
-- Table structure for table `removed_incident_comments_entity`
--

CREATE TABLE `removed_incident_comments_entity` (
  `removed_incident_comment_id` bigint(20) NOT NULL,
  `comment_date` date NOT NULL,
  `description` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `deleted_incident_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `removed_incident_comments_entity`
--

INSERT INTO `removed_incident_comments_entity` (`removed_incident_comment_id`, `comment_date`, `description`, `title`, `deleted_incident_id`) VALUES
(7, '2025-07-15', 'fa', 'Tre', 1),
(8, '2025-07-15', 'My post', 'A title here', 2),
(9, '2025-07-15', 'My post', 'A title here', 3),
(10, '2025-07-15', 'My post', 'A title here', 4),
(11, '2025-07-15', 'My post', 'A title here', 4),
(12, '2025-07-15', 'My post', 'A title here', 4),
(13, '2025-07-15', 'My post', 'A title here', 4),
(14, '2025-07-15', 'My post', 'A title here', 4),
(15, '2025-07-15', 'My post', 'A title here', 4),
(16, '2025-07-15', 'fa', 'Tre', 4),
(17, '2025-07-16', 'my hurts', 'Ass', 4),
(18, '2025-07-16', 'hurting', 'Asss', 4),
(19, '2025-07-17', 'taky', 'Tohle', 5),
(20, '2025-07-17', 'rna', 'Tawe', 5),
(21, '2025-07-21', 'taky traky', 'Processing', 6),
(22, '2025-07-21', 'velký', 'Konec', 6),
(23, '2025-07-22', 'text', 'Prijato', 7),
(24, '2025-07-22', 'asd', 'asd', 7),
(25, '2025-07-22', 'asd', 'asd', 7),
(26, '2025-07-27', 're', 'Tr', 10),
(27, '2025-07-27', 'dasdasdas', 'asd', 10),
(28, '2025-07-27', 'dasdasdas', 'asd', 10),
(29, '2025-07-27', 'asd', 'asd', 10),
(30, '2025-07-27', 'asd', 'asd', 10),
(31, '2025-07-28', 'era', 'Are', 11),
(32, '2025-07-28', 'dsa', 'ad', 11),
(33, '2025-07-16', 'ne', 'Ano', 12),
(34, '2025-07-16', 'dss', 'Asd', 12),
(35, '2025-07-16', 'sddd', 'Tra', 12),
(36, '2025-07-16', 'llaelk', 'Anmo', 12),
(37, '2025-07-17', 'ale thole', 'Ne togle', 12),
(38, '2025-07-30', 'krek', 'TRek', 12),
(39, '2025-07-30', 'kkk', 'KkK', 12),
(40, '2025-07-31', 'asdasdsad', 'ASAddd', 13),
(41, '2025-07-31', 'asdad', 'asdasda', 13),
(42, '2025-07-31', 'asdasdasd', 'asdasd', 13),
(43, '2025-07-31', 'sdsad', 'ASDsd', 14),
(44, '2025-07-31', 'asdasd', 'asdasd', 14);

-- --------------------------------------------------------

--
-- Table structure for table `removed_parties_entity`
--

CREATE TABLE `removed_parties_entity` (
  `delete_reason_id` bigint(20) NOT NULL,
  `additional_information` varchar(255) DEFAULT NULL,
  `birth_day` date NOT NULL,
  `birth_number` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `date_of_request` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `party_id` bigint(20) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `removal_reason` enum('CEASED','REDUNDANT','RELOCATION','REQUESTED') NOT NULL,
  `street` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `todays_date` date NOT NULL,
  `zip_code` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts_entity`
--
ALTER TABLE `accounts_entity`
  ADD PRIMARY KEY (`account_id`),
  ADD UNIQUE KEY `UKddu7tgnxu8ln48x5p2myjcks8` (`user_email`);

--
-- Indexes for table `contracts_entity`
--
ALTER TABLE `contracts_entity`
  ADD PRIMARY KEY (`contract_id`),
  ADD KEY `FK4ougo7976yg09uj5biyva33ym` (`insurances_id`);

--
-- Indexes for table `deleted_insurances_entity`
--
ALTER TABLE `deleted_insurances_entity`
  ADD PRIMARY KEY (`deleted_insurances_id`);

--
-- Indexes for table `deleted_parties_entity`
--
ALTER TABLE `deleted_parties_entity`
  ADD PRIMARY KEY (`delete_reason_id`);

--
-- Indexes for table `incidents_entity`
--
ALTER TABLE `incidents_entity`
  ADD PRIMARY KEY (`incident_id`);

--
-- Indexes for table `incident_comments_entity`
--
ALTER TABLE `incident_comments_entity`
  ADD PRIMARY KEY (`incident_comment_id`),
  ADD KEY `FKd0pty7fodg7chp2ncl1ujpnvd` (`incident_id`);

--
-- Indexes for table `insurances_entity`
--
ALTER TABLE `insurances_entity`
  ADD PRIMARY KEY (`insurances_id`);

--
-- Indexes for table `news_entity`
--
ALTER TABLE `news_entity`
  ADD PRIMARY KEY (`news_id`);

--
-- Indexes for table `parties_entity`
--
ALTER TABLE `parties_entity`
  ADD PRIMARY KEY (`party_id`);

--
-- Indexes for table `party_contracts_entity`
--
ALTER TABLE `party_contracts_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKs0o4c6exm3yjfogmp2mom9erf` (`contract_id`),
  ADD KEY `FKkldcp09uwqco6916h7mh9567g` (`party_id`);

--
-- Indexes for table `removed_contracts_entity`
--
ALTER TABLE `removed_contracts_entity`
  ADD PRIMARY KEY (`removed_insurances_id`);

--
-- Indexes for table `removed_incidents_entity`
--
ALTER TABLE `removed_incidents_entity`
  ADD PRIMARY KEY (`deleted_incident_id`);

--
-- Indexes for table `removed_incident_comments_entity`
--
ALTER TABLE `removed_incident_comments_entity`
  ADD PRIMARY KEY (`removed_incident_comment_id`),
  ADD KEY `FK44exp909dg03v2efk1tugh22d` (`deleted_incident_id`);

--
-- Indexes for table `removed_parties_entity`
--
ALTER TABLE `removed_parties_entity`
  ADD PRIMARY KEY (`delete_reason_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts_entity`
--
ALTER TABLE `accounts_entity`
  MODIFY `account_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `contracts_entity`
--
ALTER TABLE `contracts_entity`
  MODIFY `contract_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `deleted_insurances_entity`
--
ALTER TABLE `deleted_insurances_entity`
  MODIFY `deleted_insurances_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `deleted_parties_entity`
--
ALTER TABLE `deleted_parties_entity`
  MODIFY `delete_reason_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `incidents_entity`
--
ALTER TABLE `incidents_entity`
  MODIFY `incident_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `incident_comments_entity`
--
ALTER TABLE `incident_comments_entity`
  MODIFY `incident_comment_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `insurances_entity`
--
ALTER TABLE `insurances_entity`
  MODIFY `insurances_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `news_entity`
--
ALTER TABLE `news_entity`
  MODIFY `news_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `parties_entity`
--
ALTER TABLE `parties_entity`
  MODIFY `party_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `party_contracts_entity`
--
ALTER TABLE `party_contracts_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `removed_contracts_entity`
--
ALTER TABLE `removed_contracts_entity`
  MODIFY `removed_insurances_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `removed_incidents_entity`
--
ALTER TABLE `removed_incidents_entity`
  MODIFY `deleted_incident_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `removed_incident_comments_entity`
--
ALTER TABLE `removed_incident_comments_entity`
  MODIFY `removed_incident_comment_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `removed_parties_entity`
--
ALTER TABLE `removed_parties_entity`
  MODIFY `delete_reason_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `contracts_entity`
--
ALTER TABLE `contracts_entity`
  ADD CONSTRAINT `FK4ougo7976yg09uj5biyva33ym` FOREIGN KEY (`insurances_id`) REFERENCES `insurances_entity` (`insurances_id`);

--
-- Constraints for table `incident_comments_entity`
--
ALTER TABLE `incident_comments_entity`
  ADD CONSTRAINT `FKd0pty7fodg7chp2ncl1ujpnvd` FOREIGN KEY (`incident_id`) REFERENCES `incidents_entity` (`incident_id`);

--
-- Constraints for table `party_contracts_entity`
--
ALTER TABLE `party_contracts_entity`
  ADD CONSTRAINT `FKkldcp09uwqco6916h7mh9567g` FOREIGN KEY (`party_id`) REFERENCES `parties_entity` (`party_id`),
  ADD CONSTRAINT `FKs0o4c6exm3yjfogmp2mom9erf` FOREIGN KEY (`contract_id`) REFERENCES `contracts_entity` (`contract_id`);

--
-- Constraints for table `removed_incident_comments_entity`
--
ALTER TABLE `removed_incident_comments_entity`
  ADD CONSTRAINT `FK44exp909dg03v2efk1tugh22d` FOREIGN KEY (`deleted_incident_id`) REFERENCES `removed_incidents_entity` (`deleted_incident_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
