CREATE OR REPLACE VIEW vw_product_production AS
SELECT
    product_id,
    name,
    price,
    producible_amount,
    active,
    price * producible_amount AS total_value
FROM (
         SELECT
             p.id AS product_id,
             p.name,
             p.price,
             p.active,
             MIN(
                     CASE
                         WHEN pm.required_quantity <= 0 THEN 999999
                         ELSE FLOOR(rm.amount / pm.required_quantity)
                         END
             ) AS producible_amount
         FROM tb_product p
                  JOIN tb_product_raw_material pm ON pm.product_id = p.id
                  JOIN tb_raw_material rm ON rm.id = pm.raw_material_id
         GROUP BY p.id, p.name, p.price
     ) t
WHERE producible_amount > 0;
