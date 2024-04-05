-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 05, 2024 at 02:26 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `proyekpbo`
--

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `pelanggan_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `staff_id` int(11) NOT NULL,
  `order_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`id`, `pelanggan_id`, `product_id`, `staff_id`, `order_date`) VALUES
(1, 2, 1, 1, '2023-06-12'),
(2, 2, 1, 1, '2023-06-12'),
(3, 2, 2, 1, '2023-06-13'),
(4, 1, 2, 1, '2023-06-21'),
(5, 1, 2, 1, '2023-06-23'),
(6, 4, 2, 3, '2023-06-23'),
(7, 4, 2, 3, '2023-06-23'),
(8, 4, 1, 3, '2023-06-23'),
(9, 5, 3, 1, '2023-06-23'),
(10, 5, 3, 1, '2023-06-23'),
(11, 5, 1, 1, '2023-06-23'),
(12, 2, 1, 1, '2023-06-23'),
(13, 1, 2, 1, '2023-06-23'),
(14, 1, 2, 1, '2023-06-23'),
(15, 1, 2, 4, '2023-07-06');

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id` int(11) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `phone_number` int(20) NOT NULL,
  `email` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id`, `first_name`, `last_name`, `phone_number`, `email`) VALUES
(1, 'Niko', 'Hoc', 815, 'Hoc@gmail'),
(2, 'Bagas', 'Bw', 819, 'Bw@gmail'),
(3, 'Darren', 'Liem', 829, 'liem@gmail'),
(4, 'Yenny', 'Kantasilo', 850, 'Yen@gmail'),
(5, 'ERSON', 'Lo', 869, 'lo@gmail'),
(6, 'Dicky', 'Hau', 695, '');

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `price` int(10) NOT NULL,
  `category` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`id`, `name`, `price`, `category`) VALUES
(1, 'LemonTea', 10000, 'Beverage'),
(2, 'Burger', 15000, 'Food'),
(3, 'Vanilla Ice Cream', 10000, 'Dessert'),
(4, 'Slushie', 20000, 'Dessert');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `id` int(11) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `phone_number` int(20) NOT NULL,
  `user` varchar(20) NOT NULL,
  `pass` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`id`, `first_name`, `last_name`, `phone_number`, `user`, `pass`) VALUES
(1, 'Kristo', 'Go', 811, 'daniel', '321'),
(2, 'Aurum', 'G', 818, 'kevin', 'kev'),
(3, 'Dillan', 'E', 817, 'dillan', '123'),
(4, 'Andrew', 'Zane', 321, 'andrew', '123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_pelanggan` (`pelanggan_id`),
  ADD KEY `fk_staff` (`staff_id`),
  ADD KEY `fk_produk` (`product_id`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_pelanggan` FOREIGN KEY (`pelanggan_id`) REFERENCES `pelanggan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_produk` FOREIGN KEY (`product_id`) REFERENCES `produk` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
