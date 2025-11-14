// MongoDB insertMany operation
db.products.insertMany([
  { name: 'Azucar', category: 'Alimentación', priceUnit: 2.10, stock: 20 },
  { name: 'Leche', category: 'Alimentación', priceUnit: 4.00, stock: 15 },
  { name: 'Jabón', category: 'Limpieza', priceUnit: 10.00, stock: 30 },
  { name: 'Mesa', category: 'Hogar', priceUnit: 110.00, stock: 4 },
  { name: 'Televisión', category: 'Hogar', priceUnit: 3.50, stock: 10 },
  { name: 'Huevos', category: 'Alimentación', priceUnit: 1.20, stock: 30 },
  { name: 'Fregona', category: 'Limpieza', priceUnit: 5.40, stock: 6 },
  { name: 'Detergente', category: 'Limpieza', priceUnit: 6.70, stock: 12 }
]);


db.products.drop();
db.createCollection("products");
db.products.createIndex({ name: 1 }, { unique: true });
db.products.createIndex({ category: 1 });