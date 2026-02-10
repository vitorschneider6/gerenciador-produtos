-- V2: Migration to new tb_product_material to use the quantity of each material in products

-- New tb_product_raw_material

CREATE TABLE tb_product_raw_material (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    raw_material_id BIGINT NOT NULL,
    required_quantity INTEGER NOT NULL,
        CONSTRAINT fk_prm_product
        FOREIGN KEY (product_id)
        REFERENCES tb_product(id)
        ON DELETE CASCADE,

        CONSTRAINT fk_prm_raw_material
        FOREIGN KEY (raw_material_id)
        REFERENCES tb_raw_material(id)
        ON DELETE CASCADE
);

-- Migration to the new table

INSERT INTO tb_product_raw_material (
    product_id,
    raw_material_id,
    required_quantity
)
SELECT
    product_id,
    material_id,
    1
FROM tb_product_material;

-- Drop old table
DROP TABLE tb_product_material;

-- Indexes
CREATE INDEX idx_prm_product_id
    ON tb_product_raw_material(product_id);

CREATE INDEX idx_prm_raw_material_id
    ON tb_product_raw_material(raw_material_id);

