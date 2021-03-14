-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 09-Mar-2021 às 02:43
-- Versão do servidor: 10.4.17-MariaDB
-- versão do PHP: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `locacaobike`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cad_bicicleta`
--

CREATE TABLE `cad_bicicleta` (
  `idBicicleta` int(6) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `modelo` varchar(45) NOT NULL,
  `tamanho` float NOT NULL,
  `tempUs` float NOT NULL,
  `cor` varchar(45) NOT NULL,
  `tipoP` varchar(45) NOT NULL,
  `valorLoc` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `cad_bicicleta`
--

INSERT INTO `cad_bicicleta` (`idBicicleta`, `nome`, `modelo`, `tamanho`, `tempUs`, `cor`, `tipoP`, `valorLoc`) VALUES
(1, 'Melissa', 'bmx', 78, 20, 'prata', 'legal', 152),
(3, 'mtxt', '56mtxdz', 34, 34, 'frt', '34', 34.5),
(6, 'croizinha', 'df', 54, 54, 'frd', '78', 25);

-- --------------------------------------------------------

--
-- Estrutura da tabela `cad_cliente`
--

CREATE TABLE `cad_cliente` (
  `idCliente` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cpf` varchar(45) NOT NULL,
  `idade` int(11) NOT NULL,
  `endereco` varchar(45) NOT NULL,
  `telefone` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `sexo` char(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `cad_cliente`
--

INSERT INTO `cad_cliente` (`idCliente`, `nome`, `cpf`, `idade`, `endereco`, `telefone`, `email`, `sexo`) VALUES
(4, 'cereja', '173.932.300-97', 12, 'rdtgfhjklç', '(85) 22633-6666', '686868ehdtfj@rt', 'M'),
(7, 'melissa', '144.056.196-62', 21, 'jhdbjhdbcj', '(84) 54669-8988', 'eded@gk.com', 'F');

-- --------------------------------------------------------

--
-- Estrutura da tabela `cad_locacao`
--

CREATE TABLE `cad_locacao` (
  `idLocacao` int(6) NOT NULL,
  `idCliente` int(6) NOT NULL,
  `idBicicleta` int(6) NOT NULL,
  `horarioL` float NOT NULL,
  `dataL` date NOT NULL,
  `horarioD` float NOT NULL,
  `dataD` date NOT NULL,
  `valorLoc` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `cad_locacao`
--

INSERT INTO `cad_locacao` (`idLocacao`, `idCliente`, `idBicicleta`, `horarioL`, `dataL`, `horarioD`, `dataD`, `valorLoc`) VALUES
(1, 4, 1, 15, '2020-02-15', 15, '2020-02-16', 56),
(5, 4, 3, 15, '2050-04-01', 810, '1234-04-04', 56);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `cad_bicicleta`
--
ALTER TABLE `cad_bicicleta`
  ADD PRIMARY KEY (`idBicicleta`);

--
-- Índices para tabela `cad_cliente`
--
ALTER TABLE `cad_cliente`
  ADD PRIMARY KEY (`idCliente`),
  ADD UNIQUE KEY `cpf` (`cpf`);

--
-- Índices para tabela `cad_locacao`
--
ALTER TABLE `cad_locacao`
  ADD PRIMARY KEY (`idLocacao`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `cad_bicicleta`
--
ALTER TABLE `cad_bicicleta`
  MODIFY `idBicicleta` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `cad_cliente`
--
ALTER TABLE `cad_cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `cad_locacao`
--
ALTER TABLE `cad_locacao`
  MODIFY `idLocacao` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
