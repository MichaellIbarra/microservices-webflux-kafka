
CREATE TABLE IF NOT EXISTS products (
codProduct INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
category VARCHAR(50) NOT NULL,
priceUnit DECIMAL(10, 2) NOT NULL,
stock INT NOT NULL
);

// update attribute codeProduct AUTO_INCREMENT
ALTER TABLE products MODIFY codProduct INT AUTO_INCREMENT;

INSERT INTO products (name, category, priceUnit, stock) VALUES ('Azucar', 'Alimentación', 1.10, 20);
INSERT INTO products (name, category, priceUnit, stock) VALUES ('Leche', 'Alimentación', 5.00, 15);
INSERT INTO products (name, category, priceUnit, stock) VALUES ('Jabón', 'Limpieza', 8.00, 30);
INSERT INTO products (name, category, priceUnit, stock) VALUES ('Mesa', 'Hogar', 126.00, 4);
INSERT INTO products (name, category, priceUnit, stock) VALUES ('Televisión', 'Hogar', 6.50, 10);
INSERT INTO products (name, category, priceUnit, stock) VALUES ('Huevos', 'Alimentación', 2.20, 30);
INSERT INTO products (name, category, priceUnit, stock) VALUES ('Fregona', 'Limpieza', 3.40, 6);
INSERT INTO products (name, category, priceUnit, stock) VALUES ('Detergente', 'Limpieza', 8.70, 12);


DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `rol` varchar(50) NOT NULL,
  `user` varchar(50) NOT NULL,
  PRIMARY KEY (`rol`,`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('ADMIN','admin'),('OPERATOR','user2'),('USER','admin'),('USER','user1');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user` varchar(50) NOT NULL,
  `pwd` varchar(100) DEFAULT NULL,
  `enabled` tinyint DEFAULT NULL,
  PRIMARY KEY (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','admin',1),('user1','user1',1),('user2','user2',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;