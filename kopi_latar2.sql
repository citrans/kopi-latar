-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 13, 2018 at 07:11 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kopi_latar`
--

-- --------------------------------------------------------

--
-- Table structure for table `kategori`
--

CREATE TABLE `kategori` (
  `id_kategori` int(11) NOT NULL,
  `kategori_menu` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kategori`
--

INSERT INTO `kategori` (`id_kategori`, `kategori_menu`) VALUES
(1, 'kopi'),
(2, 'Minuman Hangat Kopi + Susu'),
(3, 'Minuman Dingin Es + Kopi'),
(4, 'Minuman Tanpa Kopi'),
(5, 'Fruit'),
(6, 'Lainnya'),
(7, 'Makanan');

-- --------------------------------------------------------

--
-- Table structure for table `laporan`
--

CREATE TABLE `laporan` (
  `id_transaksi` bigint(20) NOT NULL,
  `total_pendapatan` int(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id_menu` int(11) NOT NULL,
  `nama_menu` varchar(50) NOT NULL,
  `harga` int(5) NOT NULL,
  `id_kategori` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id_menu`, `nama_menu`, `harga`, `id_kategori`) VALUES
(1, 'Espresso Single Origin (Robusta)', 5000, 1),
(2, 'Espresso Single Origin (Arabica)', 6000, 1),
(3, 'Tubruk Latar', 4000, 1),
(4, 'Tubruk Single origin (Robusta)', 5000, 1),
(5, 'Tubruk Single Origin (Arabica)', 6000, 1),
(6, 'Long Black Latar', 4000, 1),
(7, 'Long Black Single Origin (Robusta)', 5000, 1),
(8, 'Long Black Single Origin (Arabica)', 6000, 1),
(9, 'Kopi Lanang', 5000, 1),
(10, 'Single Origin - V60 (arabica)', 8000, 1),
(11, 'Tubruk Susu Latar', 5000, 2),
(12, 'Tubruk Susu Singlr Origin (Robusta)', 6000, 2),
(13, 'Tubruk Susu Singlr Origin (Arabica)', 7000, 2),
(14, 'Vietnam Latar', 5000, 2),
(15, 'Vietnam Single Origin (Robusta)', 6000, 2),
(16, 'Vietnam Single Origin (Arabica)', 7000, 2),
(17, 'Macchiato', 6000, 2),
(18, 'Cappucino', 7000, 2),
(19, 'Coffe Latte', 7000, 2),
(20, 'Tiramisu', 7000, 2),
(21, 'Es Kopi Latar', 5000, 3),
(22, 'Es Vietnam Latar', 6000, 3),
(23, 'Es Vietnam Single Origin (Robusta)', 7000, 3),
(24, 'Es Vietnam Single Origin (Arabica)', 8000, 3),
(25, 'Es Cappucino', 8000, 3),
(26, 'Cola Float', 8000, 4),
(27, 'Es Cream Latar', 8000, 4),
(28, '(Coklat - Strow - Van) Ice Blend', 7500, 4),
(29, 'Black Oreo (Latte - Ice)', 8000, 4),
(30, 'Red Velvet (Latte- Ice)', 8000, 4),
(31, 'Mochacino (Latte-Ice)', 8000, 4),
(32, 'Teh Siam (Latte - Ice)', 8000, 4),
(33, 'Green Tea (Latte- Ice)', 8000, 4),
(34, 'Susu Coklat (Latte-Ice)', 7000, 4),
(35, 'Susu Fullcream(Panas-Es)', 7000, 4),
(36, 'Susu Jahe', 6000, 4),
(37, 'Wedang Jahe', 3000, 4),
(38, 'Air Mineral', 3000, 4),
(39, 'Teh Manis Panas', 3000, 4),
(40, 'Teh Manis Es', 4000, 4),
(41, 'Teh Latte(Panas - Es)', 6000, 4),
(42, 'Jus Mangga Biasa', 8000, 5),
(43, 'Jus Mangga Float', 10000, 5),
(44, 'Jus Melon Biasa', 8000, 5),
(45, 'Jus Melon Float', 10000, 5),
(46, 'Jus Strobery Biasa', 8000, 5),
(47, 'Jus Strobery Float', 10000, 5),
(48, 'Jus Alpukat Biasa', 8000, 5),
(49, 'Jus Alpukat Float', 10000, 5),
(50, 'Lemon Tea (Panas -Es)', 8000, 5),
(51, 'Lemon Mint (Panas - Es)', 8000, 5),
(52, 'Lemon Squash', 8000, 5),
(53, 'Kapiten', 14000, 6),
(54, 'Coffe Blend', 10000, 6),
(55, 'Cappucino Caramel', 10000, 6),
(56, 'Affogato', 10000, 6),
(57, 'ColdBrew', 10000, 6),
(58, 'Roti Bakar', 5000, 7),
(59, 'Kentang', 5000, 7),
(60, 'Tahu Inuy', 5000, 7),
(61, 'Pisang Pasir', 5000, 7),
(62, 'Terang Bulan Mini', 6000, 7),
(63, 'Terang Bulan Mini', 6000, 7),
(64, 'Omelet', 7000, 7),
(65, 'Waffel', 10000, 7),
(66, 'Rujak Londo', 13000, 7),
(67, 'Nasi Nyet2 (Ayam Krispi Penyet)', 13000, 7),
(68, 'Nasi Bento (Nasi Ayam Berkarakter)', 15000, 7),
(69, 'Cwie Mie Latar', 10000, 7);

-- --------------------------------------------------------

--
-- Table structure for table `pegawai`
--

CREATE TABLE `pegawai` (
  `id_pegawai` int(11) NOT NULL,
  `nama_pegawai` varchar(25) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `no_hp` varchar(13) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `status` varchar(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pegawai`
--

INSERT INTO `pegawai` (`id_pegawai`, `nama_pegawai`, `alamat`, `no_hp`, `username`, `password`, `status`) VALUES
(1, 'citra', 'blitar', '085748', 'citra', 'citra', 'Pegawai'),
(2, 'nika', 'jember', '85777666555', 'nika', 'nika', 'Pemilik'),
(3, 'sil', 'malang', '89765432346', 'sil', 'sil', 'Pegawai');

-- --------------------------------------------------------

--
-- Table structure for table `produk_fav`
--

CREATE TABLE `produk_fav` (
  `id_transaksi` bigint(20) NOT NULL,
  `jumlah_laku` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sementara`
--

CREATE TABLE `sementara` (
  `no_meja` int(2) NOT NULL,
  `id_menu` int(11) NOT NULL,
  `id_transaksi` bigint(20) NOT NULL,
  `jumlah_pesanan` int(3) NOT NULL,
  `total` int(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` bigint(20) NOT NULL,
  `id_pegawai` int(11) NOT NULL,
  `tgl_transaksi` varchar(10) NOT NULL,
  `tgl` int(2) NOT NULL,
  `bulan` int(2) NOT NULL,
  `tahun` int(4) NOT NULL,
  `jumlah_pesanan` int(3) NOT NULL,
  `total_harga` int(9) NOT NULL,
  `diskon` int(9) NOT NULL,
  `total` int(9) NOT NULL,
  `bayar` int(9) NOT NULL,
  `kembalian` int(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `id_pegawai`, `tgl_transaksi`, `tgl`, `bulan`, `tahun`, `jumlah_pesanan`, `total_harga`, `diskon`, `total`, `bayar`, `kembalian`) VALUES
(1, 2, '7-11-2018', 7, 11, 2018, 4, 24000, 4000, 20000, 30000, 10000),
(2, 3, '7-10-2018', 7, 10, 2018, 4, 26000, 0, 26000, 30000, 4000),
(3, 1, '7-10-2018', 7, 10, 2018, 4, 30000, 0, 30000, 50000, 20000),
(21, 1, '7-11-2018', 7, 11, 2018, 6, 48000, 3000, 45000, 50000, 3000),
(41, 3, '7-12-2018', 7, 12, 2018, 5, 29000, 0, 29000, 30000, 1000),
(61, 2, '7-12-2018', 7, 12, 2018, 1, 7000, 0, 7000, 80000, 73000),
(62, 1, '12-12-2018', 12, 11, 2018, 4, 30000, 0, 30000, 50000, 20000),
(121, 1, '12-12-2018', 12, 11, 2018, 4, 32000, 2000, 30000, 35000, 5000),
(181, 1, '12-12-2018', 12, 11, 2018, 2, 16000, 1000, 15000, 15000, 0),
(241, 1, '12-12-2018', 12, 11, 2018, 2, 14000, 0, 14000, 15000, 1000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `laporan`
--
ALTER TABLE `laporan`
  ADD KEY `id_transaksi` (`id_transaksi`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id_menu`),
  ADD KEY `id_kategori` (`id_kategori`);

--
-- Indexes for table `pegawai`
--
ALTER TABLE `pegawai`
  ADD PRIMARY KEY (`id_pegawai`);

--
-- Indexes for table `produk_fav`
--
ALTER TABLE `produk_fav`
  ADD KEY `id_menu` (`id_transaksi`),
  ADD KEY `id_transaksi` (`id_transaksi`);

--
-- Indexes for table `sementara`
--
ALTER TABLE `sementara`
  ADD KEY `id_menu` (`id_menu`),
  ADD KEY `id_transaksi` (`id_transaksi`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_pegawai` (`id_pegawai`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kategori`
--
ALTER TABLE `kategori`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `laporan`
--
ALTER TABLE `laporan`
  MODIFY `id_transaksi` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;
--
-- AUTO_INCREMENT for table `pegawai`
--
ALTER TABLE `pegawai`
  MODIFY `id_pegawai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=242;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `laporan`
--
ALTER TABLE `laporan`
  ADD CONSTRAINT `laporan_ibfk_1` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `menu`
--
ALTER TABLE `menu`
  ADD CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `kategori` (`id_kategori`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `produk_fav`
--
ALTER TABLE `produk_fav`
  ADD CONSTRAINT `produk_fav_ibfk_1` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`);

--
-- Constraints for table `sementara`
--
ALTER TABLE `sementara`
  ADD CONSTRAINT `sementara_ibfk_1` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id_pegawai`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
