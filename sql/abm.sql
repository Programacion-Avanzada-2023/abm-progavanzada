-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 11, 2023 at 04:43 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `abm`
--

-- --------------------------------------------------------

--
-- Table structure for table `automovil`
--

CREATE TABLE `automovil` (
  `id` int(11) NOT NULL,
  `modelo` int(11) NOT NULL,
  `patente` varchar(12) NOT NULL,
  `cliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `automovil`
--

INSERT INTO `automovil` (`id`, `modelo`, `patente`, `cliente`) VALUES
(3, 2, 'OQC557', 2),
(6, 1, 'POP123', 3);

-- --------------------------------------------------------

--
-- Table structure for table `marca`
--

CREATE TABLE `marca` (
  `id` int(11) NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `origen` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `marca`
--

INSERT INTO `marca` (`id`, `nombre`, `origen`) VALUES
(1, 'Suzuki', 'China'),
(2, 'Mitsubishi', 'Japon'),
(3, 'Nissan', 'Japon');

-- --------------------------------------------------------

--
-- Table structure for table `modelo`
--

CREATE TABLE `modelo` (
  `id` int(11) NOT NULL,
  `marca` int(11) NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `year` mediumint(8) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `modelo`
--

INSERT INTO `modelo` (`id`, `marca`, `nombre`, `year`) VALUES
(1, 3, 'Tiida', 2015),
(2, 2, 'Lancer', 2018),
(4, 3, 'Supra', 2011);

-- --------------------------------------------------------

--
-- Table structure for table `persona`
--

CREATE TABLE `persona` (
  `id` int(11) NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `apellido` varchar(32) NOT NULL,
  `dni` int(10) UNSIGNED NOT NULL,
  `rol` tinyint(3) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `persona`
--

INSERT INTO `persona` (`id`, `nombre`, `apellido`, `dni`, `rol`) VALUES
(1, 'Juan', 'Ruiz', 29763829, 1),
(2, 'Juan', 'Vanzetti', 22893330, 0),
(3, 'Santiago', 'Pedraza', 43133610, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `automovil`
--
ALTER TABLE `automovil`
  ADD PRIMARY KEY (`id`),
  ADD KEY `modelo` (`modelo`),
  ADD KEY `cliente` (`cliente`);

--
-- Indexes for table `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `modelo`
--
ALTER TABLE `modelo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `marca` (`marca`);

--
-- Indexes for table `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `automovil`
--
ALTER TABLE `automovil`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `marca`
--
ALTER TABLE `marca`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `modelo`
--
ALTER TABLE `modelo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `persona`
--
ALTER TABLE `persona`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `automovil`
--
ALTER TABLE `automovil`
  ADD CONSTRAINT `automovil_ibfk_1` FOREIGN KEY (`modelo`) REFERENCES `modelo` (`id`),
  ADD CONSTRAINT `automovil_ibfk_2` FOREIGN KEY (`modelo`) REFERENCES `modelo` (`id`),
  ADD CONSTRAINT `automovil_ibfk_3` FOREIGN KEY (`cliente`) REFERENCES `persona` (`id`);

--
-- Constraints for table `modelo`
--
ALTER TABLE `modelo`
  ADD CONSTRAINT `modelo_ibfk_1` FOREIGN KEY (`marca`) REFERENCES `marca` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
