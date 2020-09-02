-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Set 02, 2020 alle 10:36
-- Versione del server: 10.4.11-MariaDB
-- Versione PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ferramenta_db`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `prodotti`
--

CREATE TABLE `prodotti` (
  `id` int(11) NOT NULL,
  `codice` varchar(5) NOT NULL,
  `prezzo` decimal(10,2) NOT NULL,
  `quantita` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `descrizione` varchar(500) DEFAULT NULL,
  `marca` varchar(50) NOT NULL,
  `attivo` tinyint(1) NOT NULL,
  `path_img` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `prodotti`
--

INSERT INTO `prodotti` (`id`, `codice`, `prezzo`, `quantita`, `nome`, `descrizione`, `marca`, `attivo`, `path_img`) VALUES
(2, 'CCC23', '6.72', 55, 'Cacciavite a croce CCC23', 'Cacciavite a croce in acciaio chrom-vanadium e finitura cromata', 'Mundial', 1, 'cacciavite_croce.jpg'),
(3, 'CCT24', '6.72', 83, 'Cacciavite a taglio CCT24', 'Cacciavite a taglio realizzato in acciaio chrom-vanadium con finitura cromata', 'Mundial', 1, 'cacciavite_taglio.jpg'),
(4, 'CLT38', '11.35', 26, 'Coltello in acciaio CLT38', 'Coltello con lama di acciaio', 'Opinel', 1, 'coltello_acciaio.jpg'),
(5, 'CTR71', '9.89', 41, 'Cutter professionale CTR71', 'Cutter professionale in metallo con lama retrattile da 25 mm. Dotato di rotella posta sul retro per la regolazione ed il blocco della lama.', 'Oldcutter', 1, 'cutter_metallo.jpg'),
(6, 'FRB12', '13.21', 18, 'Forbici da elettricista FRB12', 'Forbici da elettricista con custodia, con lame in acciaio inox per cavi, impugnature ergonomiche e supporto da cintura incluso.', 'Knipex', 1, 'forbici_elettricista.jpg'),
(7, 'MZT90', '24.98', 20, 'Mazzetta da muratore MZT90', 'Mazzetta da muratore con manico in fibra di vetro e testa in acciaio forgiato e temperato.', 'Rinaldi', 1, 'mazzetta_manico_fibra.jpg'),
(8, 'PNZ82', '11.40', 36, 'Pinza universale PNZ82', 'Pinza universale compatta con presa sicura e ottime caratteristiche di taglio, ottima per lavori di riparazione e installazione.', 'Knipex', 1, 'pinza.jpg'),
(9, 'SGC77', '25.02', 24, 'Segaccio per edilizia SGC77', 'Segaccio per edilizia con denti al widia per taglio di materiali edili con impugnatura in legno.', 'Bushman', 1, 'segaccio.jpg'),
(10, 'SGT96', '21.99', 53, 'Seghetto manuale SGT96', 'Seghetto manuale per rami verdi e da giardino. Lama a doppia dentatura completamente retrattile, protezione paradito e manico con gancio a clip per cintura. Il seghetto per legno e adatto al taglio di legno e rami verdi.', 'Fiskars', 1, 'seghetto_potatura.jpg');

-- --------------------------------------------------------

--
-- Struttura della tabella `storico`
--

CREATE TABLE `storico` (
  `id` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `id_prodotto` int(11) NOT NULL,
  `data_acquisto` timestamp NOT NULL DEFAULT current_timestamp(),
  `quantita` int(11) NOT NULL,
  `prezzo_tot` decimal(10,2) NOT NULL,
  `stato` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `storico`
--

INSERT INTO `storico` (`id`, `id_utente`, `id_prodotto`, `data_acquisto`, `quantita`, `prezzo_tot`, `stato`) VALUES
(3, 11, 3, '2020-08-21 09:25:03', 20, '30.20', 'IN_CORSO'),
(4, 11, 3, '2020-08-21 09:25:03', 20, '30.20', 'IN_CORSO'),
(5, 11, 3, '2020-08-21 09:25:03', 20, '30.20', 'IN_CORSO'),
(6, 11, 3, '2020-08-21 10:41:56', 20, '0.00', 'IN_CORSO'),
(7, 11, 3, '2020-08-21 10:43:12', 20, '0.00', 'IN_CORSO'),
(8, 11, 2, '2020-08-21 13:23:02', 5, '33.60', 'IN_CORSO'),
(9, 11, 3, '2020-08-21 13:23:26', 3, '20.16', 'IN_CORSO'),
(10, 11, 2, '2020-08-21 13:26:00', 2, '13.44', 'RIFIUTATO'),
(11, 11, 4, '2020-08-22 12:00:17', 2, '22.70', 'APPROVATO'),
(12, 12, 2, '2020-08-24 12:11:27', 2, '13.44', 'IN_CORSO');

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `id` int(11) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `ruolo` varchar(20) NOT NULL,
  `data_registrazione` timestamp NOT NULL DEFAULT current_timestamp(),
  `token` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`id`, `nome`, `cognome`, `username`, `PASSWORD`, `email`, `ruolo`, `data_registrazione`, `token`) VALUES
(11, 'Valentin', 'Puscasu', 'pusky', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'vale@gmail.com', 'USER', '2020-08-17 12:27:12', NULL),
(12, 'admin', 'admin', 'admin', 'fb27ca3d7d7edb64e8bfd60d623d256889dd3bf69d7039d8fd35675d7aced063', 'admin@email.com', 'ADMIN', '2020-08-24 09:54:17', 'cE6sWHCh_lXP4pSXDeFN1QFYFYmqZBpc');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `prodotti`
--
ALTER TABLE `prodotti`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codice` (`codice`),
  ADD UNIQUE KEY `nome` (`nome`);

--
-- Indici per le tabelle `storico`
--
ALTER TABLE `storico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_storico_utenti` (`id_utente`),
  ADD KEY `fk_storico_prodotti` (`id_prodotto`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `prodotti`
--
ALTER TABLE `prodotti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT per la tabella `storico`
--
ALTER TABLE `storico`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT per la tabella `utenti`
--
ALTER TABLE `utenti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `storico`
--
ALTER TABLE `storico`
  ADD CONSTRAINT `fk_storico_prodotti` FOREIGN KEY (`id_prodotto`) REFERENCES `prodotti` (`id`),
  ADD CONSTRAINT `fk_storico_utenti` FOREIGN KEY (`id_utente`) REFERENCES `utenti` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
