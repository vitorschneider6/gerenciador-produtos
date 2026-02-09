-- V1: Initial migration for products and raw materials

-- TABLE: tb_raw_material
CREATE TABLE tb_raw_material (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(500),
    amount INTEGER NOT NULL
);

-- TABLE: tb_product
CREATE TABLE tb_product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT true
);

-- TABLE: tb_product_material (Many-to-Many)
CREATE TABLE tb_product_material (
    product_id BIGINT NOT NULL,
    material_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, material_id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES tb_product(id) ON DELETE CASCADE,
    CONSTRAINT fk_material FOREIGN KEY (material_id) REFERENCES tb_raw_material(id) ON DELETE CASCADE
);

-- Indexes
CREATE INDEX idx_product_material_product_id ON tb_product_material(product_id);
CREATE INDEX idx_product_material_material_id ON tb_product_material(material_id);
