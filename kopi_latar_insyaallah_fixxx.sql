-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 22, 2018 at 02:58 PM
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
-- Table structure for table `detail`
--

CREATE TABLE `detail` (
  `no_meja` int(2) NOT NULL,
  `id_menu` int(11) NOT NULL,
  `nama_menu` varchar(100) NOT NULL,
  `id_transaksi` bigint(20) NOT NULL,
  `jumlah_pesanan` int(3) NOT NULL,
  `total` int(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `detail`
--
DELIMITER $$
CREATE TRIGGER `pindah_detail` AFTER DELETE ON `detail` FOR EACH ROW BEGIN
INSERT INTO sementara SET id_transaksi= old.id_transaksi ,id_menu = old.id_menu, no_meja = old.no_meja,jumlah_pesanan =old.jumlah_pesanan, total=old.total ;
UPDATE menu_laku SET jumlah_laku = jumlah_laku+old.jumlah_pesanan WHERE nama_menu = old.nama_menu;
END
$$
DELIMITER ;

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
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id_menu` int(11) NOT NULL,
  `nama_menu` varchar(100) NOT NULL,
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
(4, 'Tubruk Single Origin (Robusta)', 5000, 1),
(5, 'Tubruk Single Origin (Arabica)', 6000, 1),
(6, 'Long Black Latar', 4000, 1),
(7, 'Long Black Single Origin (Robusta)', 5000, 1),
(8, 'Long Black Single Origin (Arabica)', 6000, 1),
(9, 'Kopi Lanang', 5000, 1),
(10, 'Single Origin - V60 (Arabica)', 8000, 1),
(11, 'Tubruk Susu Latar', 5000, 2),
(12, 'Tubruk Susu Single Origin (Robusta)', 6000, 2),
(13, 'Tubruk Susu Single Origin (Arabica)', 7000, 2),
(14, 'Vietnam Latar', 5000, 2),
(15, 'Vietnam Single Origin (Robusta)', 6000, 2),
(16, 'Vietnam Single Origin (Arabica)', 7000, 2),
(17, 'Macchiato', 6000, 2),
(18, 'Cappucino', 7000, 2),
(19, 'Caffe Latte', 7000, 2),
(20, 'Tiramisu', 7000, 2),
(21, 'Es Kopi Latar', 5000, 3),
(22, 'Es Vietnam Latar', 6000, 3),
(23, 'Es Vietnam Single Origin (Robusta)', 7000, 3),
(24, 'Es Vietnam Single Origin (Arabica)', 8000, 3),
(25, 'Es Cappucino', 8000, 3),
(26, 'Es Tiramisu', 8000, 3),
(27, 'Cola Float ', 6000, 4),
(28, 'Es Cream Latar', 8000, 4),
(29, 'Coklat Ice Blend', 7500, 4),
(30, 'Strawberry Ice Blend', 7500, 4),
(31, 'Vanila Ice Blend', 7500, 4),
(32, 'Black Oreo (Latte - Ice)', 8000, 4),
(33, 'Red Velvet (Latte - Ice)', 8000, 4),
(34, 'Mochacino (Latte - Ice)', 8000, 4),
(35, 'Teh Siam (Latte - Ice)', 8000, 4),
(36, 'Green Tea (Latte - Ice)', 8000, 4),
(37, 'Susu Coklat (Latte - Ice)', 7000, 4),
(38, 'Susu Fullcream ( Panas - Ice)', 7000, 4),
(39, 'Susu Jahe', 6000, 4),
(40, 'Wedang Jahe', 3000, 4),
(41, 'Air Mineral', 3000, 4),
(42, 'Teh Manis Panas', 3000, 4),
(43, 'Teh Manis Es', 4000, 4),
(44, 'Teh Latte (Panas - Es)', 6000, 4),
(45, 'Jus Mangga Biasa', 8000, 5),
(46, 'Jus Mangga Float', 10000, 5),
(47, 'Jus Melon Biasa', 8000, 5),
(48, 'Jus Melon Float', 10000, 5),
(49, 'Jus Strawberry Biasa', 8000, 5),
(50, 'Jus Strawberry Float', 10000, 5),
(51, 'Jus Alpukat Biasa', 8000, 5),
(52, 'Jus Alpukat Float', 10000, 5),
(53, 'Lemon Tea (Panas - Es)', 8000, 5),
(54, 'Lemon Mint (Panas - Es)', 8000, 5),
(55, 'Lemon Squash', 8000, 5),
(56, 'Kapiten', 14000, 6),
(57, 'Coffe Blend', 10000, 6),
(58, 'Green Tea Latte Caramel', 10000, 6),
(59, 'Affogato', 10000, 6),
(60, 'Coldbrew', 10000, 6),
(61, 'Roti Bakar', 5000, 7),
(62, 'Kentang', 5000, 7),
(63, 'Tahu Inuy', 5000, 7),
(64, 'Pisang Pasir', 5000, 7),
(65, 'Terang Bulan Mini', 6000, 7),
(66, 'Omelet', 7000, 7),
(67, 'Waffel', 10000, 7),
(68, 'Rujak Londo', 13000, 7),
(69, 'Nasi Nyet2 (Ayam Krispi Penyet)', 13000, 7),
(70, 'Nasi Bento (Nasi Ayam Berkarakter)', 15000, 7),
(71, 'Cwie Mie Latar', 10000, 7);

--
-- Triggers `menu`
--
DELIMITER $$
CREATE TRIGGER `tambah_laku` AFTER INSERT ON `menu` FOR EACH ROW BEGIN
 INSERT INTO menu_laku SET id_menu= NEW.id_menu ,nama_menu = new.nama_menu, harga = new.harga;
  
  END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `update_jumlah_laku` AFTER UPDATE ON `menu` FOR EACH ROW BEGIN
UPDATE menu_laku SET id_menu= new.id_menu ,nama_menu = new.nama_menu, harga =new.harga;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `menu_laku`
--

CREATE TABLE `menu_laku` (
  `id_menu` int(11) NOT NULL,
  `nama_menu` varchar(100) NOT NULL,
  `harga` int(5) NOT NULL,
  `jumlah_laku` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menu_laku`
--

INSERT INTO `menu_laku` (`id_menu`, `nama_menu`, `harga`, `jumlah_laku`) VALUES
(1, 'Espresso Single Origin (Robusta)', 5000, 0),
(2, 'Espresso Single Origin (Arabica)', 6000, 0),
(3, 'Tubruk Latar', 4000, 0),
(5, 'Tubruk Single Origin (Arabica)', 6000, 0),
(6, 'Long Black Latar', 4000, 0),
(7, 'Long Black Single Origin (Robusta)', 5000, 0),
(8, 'Long Black Single Origin (Arabica)', 6000, 0),
(9, 'Kopi Lanang', 5000, 0),
(10, 'Single Origin - V60 (Arabica)', 8000, 0),
(11, 'Tubruk Susu Latar', 5000, 0),
(12, 'Tubruk Susu Single Origin (Robusta)', 6000, 0),
(13, 'Tubruk Susu Single Origin (Arabica)', 7000, 0),
(14, 'Vietnam Latar', 5000, 0),
(15, 'Vietnam Single Origin (Robusta)', 6000, 0),
(16, 'Vietnam Single Origin (Arabica)', 7000, 0),
(17, 'Macchiato', 6000, 0),
(18, 'Cappucino', 7000, 3),
(19, 'Caffe Latte', 7000, 0),
(20, 'Tiramisu', 7000, 0),
(21, 'Es Kopi Latar', 5000, 0),
(22, 'Es Vietnam Latar', 6000, 0),
(23, 'Es Vietnam Single Origin (Robusta)', 7000, 0),
(24, 'Es Vietnam Single Origin (Arabica)', 8000, 0),
(25, 'Es Cappucino', 8000, 0),
(26, 'Es Tiramisu', 8000, 0),
(27, 'Cola Float ', 6000, 0),
(28, 'Es Cream Latar', 8000, 0),
(29, 'Coklat Ice Blend', 7500, 0),
(30, 'Strawberry Ice Blend', 7500, 0),
(31, 'Vanila Ice Blend', 7500, 0),
(33, 'Red Velvet (Latte - Ice)', 8000, 0),
(34, 'Mochacino (Latte - Ice)', 8000, 0),
(35, 'Teh Siam (Latte - Ice)', 8000, 0),
(36, 'Green Tea (Latte - Ice)', 8000, 0),
(37, 'Susu Coklat (Latte - Ice)', 7000, 0),
(38, 'Susu Fullcream ( Panas - Ice)', 7000, 0),
(39, 'Susu Jahe', 6000, 0),
(40, 'Wedang Jahe', 3000, 0),
(41, 'Air Mineral', 3000, 0),
(42, 'Teh Manis Panas', 3000, 0),
(43, 'Teh Manis Es', 4000, 0),
(44, 'Teh Latte (Panas - Es)', 6000, 0),
(47, 'Jus Melon Biasa', 8000, 0),
(48, 'Jus Melon Float', 10000, 0),
(49, 'Jus Strawberry Biasa', 8000, 0),
(50, 'Jus Strawberry Float', 10000, 0),
(51, 'Jus Alpukat Biasa', 8000, 0),
(52, 'Jus Alpukat Float', 10000, 0),
(53, 'Lemon Tea (Panas - Es)', 8000, 0),
(54, 'Lemon Mint (Panas - Es)', 8000, 0),
(55, 'Lemon Squash', 8000, 0),
(56, 'Kapiten', 14000, 0),
(57, 'Coffe Blend', 10000, 0),
(58, 'Green Tea Latte Caramel', 10000, 0),
(60, 'Coldbrew', 10000, 0),
(61, 'Roti Bakar', 5000, 0),
(62, 'Kentang', 5000, 0),
(64, 'Pisang Pasir', 5000, 0),
(65, 'Terang Bulan Mini', 6000, 0),
(66, 'Omelet', 7000, 0),
(67, 'Waffel', 10000, 0),
(68, 'Rujak Londo', 13000, 0),
(69, 'Nasi Nyet2 (Ayam Krispi Penyet)', 13000, 0),
(70, 'Nasi Bento (Nasi Ayam Berkarakter)', 15000, 0),
(71, 'Cwie Mie Latar', 10000, 0),
(4, 'Tubruk Single Origin (Robusta)', 5000, 0),
(32, 'Black Oreo (Latte - Ice)', 8000, 0),
(45, 'Jus Mangga Biasa', 8000, 0),
(46, 'Jus Mangga Float', 10000, 0),
(59, 'Affogato', 10000, 0),
(63, 'Tahu Inuy', 5000, 0);

--
-- Triggers `menu_laku`
--
DELIMITER $$
CREATE TRIGGER `pindah_fav` AFTER DELETE ON `menu_laku` FOR EACH ROW BEGIN
INSERT INTO produk_fav SET nama_menu= old.nama_menu ,jumlah_laku = old.jumlah_laku, tanggal = CURRENT_TIMESTAMP();
INSERT INTO penampung_fav SET id_menu = old.id_menu, nama_menu= old.nama_menu ,jumlah_laku = 0, harga = old.harga;
END
$$
DELIMITER ;

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
-- Table structure for table `penampung_fav`
--

CREATE TABLE `penampung_fav` (
  `id_menu` int(11) NOT NULL,
  `nama_menu` varchar(100) NOT NULL,
  `harga` int(5) NOT NULL,
  `jumlah_laku` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Triggers `penampung_fav`
--
DELIMITER $$
CREATE TRIGGER `balikkan_menu_laku` AFTER DELETE ON `penampung_fav` FOR EACH ROW BEGIN
INSERT INTO menu_laku SET id_menu = old.id_menu, nama_menu= old.nama_menu ,jumlah_laku = 0, harga = old.harga;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `produk_fav`
--

CREATE TABLE `produk_fav` (
  `nama_menu` varchar(100) NOT NULL,
  `jumlah_laku` varchar(10) NOT NULL,
  `tanggal` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produk_fav`
--

INSERT INTO `produk_fav` (`nama_menu`, `jumlah_laku`, `tanggal`) VALUES
('Tubruk Single Origin (Robusta)', '3', '2018-12-22 06:58:08'),
('Black Oreo (Latte - Ice)', '2', '2018-12-22 06:58:08'),
('Jus Mangga Biasa', '3', '2018-12-22 06:58:08'),
('Jus Mangga Float', '3', '2018-12-22 06:58:08'),
('Affogato', '2', '2018-12-22 06:58:08'),
('Tahu Inuy', '3', '2018-12-22 06:58:08');

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

--
-- Dumping data for table `sementara`
--

INSERT INTO `sementara` (`no_meja`, `id_menu`, `id_transaksi`, `jumlah_pesanan`, `total`) VALUES
(1, 56, 1, 2, 28000),
(1, 50, 1, 2, 20000),
(1, 59, 61, 2, 20000),
(1, 32, 61, 2, 16000),
(1, 4, 61, 3, 15000),
(1, 63, 121, 3, 15000),
(1, 46, 181, 3, 30000),
(1, 45, 181, 3, 24000),
(1, 18, 241, 3, 21000);

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
(1, 1, '21-12-2018', 21, 12, 2018, 4, 48000, 0, 48000, 50000, 2000),
(61, 1, '22-12-2018', 20, 12, 2018, 7, 51000, 0, 51000, 55000, 4000),
(121, 1, '22-12-2018', 19, 12, 2018, 3, 15000, 0, 15000, 15000, 0),
(181, 1, '22-12-2018', 21, 12, 2018, 6, 54000, 4000, 50000, 50000, 0),
(241, 1, '22-12-2018', 22, 12, 2018, 3, 21000, 0, 21000, 22000, 1000);

--
-- Triggers `transaksi`
--
DELIMITER $$
CREATE TRIGGER `hapus_detail` AFTER INSERT ON `transaksi` FOR EACH ROW BEGIN
DELETE FROM detail WHERE id_transaksi=new.id_transaksi;
 
  END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `hapus_sementara` AFTER DELETE ON `transaksi` FOR EACH ROW BEGIN
DELETE FROM sementara WHERE id_transaksi=old.id_transaksi;
  END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `kategori`
--
ALTER TABLE `kategori`
  ADD PRIMARY KEY (`id_kategori`);

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
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;
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
-- Constraints for table `menu`
--
ALTER TABLE `menu`
  ADD CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `kategori` (`id_kategori`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sementara`
--
ALTER TABLE `sementara`
  ADD CONSTRAINT `sementara_ibfk_1` FOREIGN KEY (`id_menu`) REFERENCES `menu` (`id_menu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `sementara_ibfk_2` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id_pegawai`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
