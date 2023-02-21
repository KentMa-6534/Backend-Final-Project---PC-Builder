-- Customer
INSERT INTO customer(customer_id, first_name, last_name, email) VALUES ('MA_KENT','Kent', 'Ma', 'realkentma@gmail.com');

-- CPU
INSERT INTO pc_cpu(cpu_id, cpu_brand, cpu_name, core_count, price) VALUES ('RYZEN_7_7700X','AMD', 'Ryzen 7 7700X', '8', '343.62');
INSERT INTO pc_cpu(cpu_id, cpu_brand, cpu_name, core_count, price) VALUES ('CORE_I7_13700K','INTEL', 'Core i7-13700K', '16', '410.00');

-- CPU Cooler
INSERT INTO cpu_cooler(cpu_cooler_id, manufacturer, cooler_name, is_water_cooled, price) VALUES ('KRAKEN_Z73_RGB','NZXT', 'Kraken Z73 RGB', true, 399.95);

-- Motherboard
INSERT INTO motherboard(motherboard_id, manufacturer, motherboard_name, cpu_socket, form_factor, memory_slots, price) VALUES ('MPG_Z790_EDGE_WIFI', 'MSI', 'MPG Z790 EDGE WIFI', 'LGA_1700', 'STANDARD_ATX', 4, 370.00); 

-- Memory
INSERT INTO pc_memory(memory_id ,manufacturer, memory_name, memory_type, memory_speed, memory_size, price) VALUES ('TRIDENT_Z5_RGB' ,'G.Skill', 'Trident Z5 RGB' , 'DDR5', '6000 MHz', '32 GB', 159.95);

-- Storage
INSERT INTO pc_storage(storage_id, manufacturer, storage_name, storage_capacity, storage_type, storage_cache, price) VALUES ('970_EVO_PLUS','Samsung', '970 Evo Plus', '1 TB', 'NVMe', true, 79.98);

-- Video Card
INSERT INTO video_card(video_card_id ,video_card_brand, video_card_name, video_card_memory, price) VALUES ('ASUS_TUF_GAMING_OC_RTX_4090', 'NVIDIA', 'ASUS TUF GAMING OC RTX 4090', '24 GB', 1799.99);

-- Case
INSERT INTO pc_case(case_id, manufacturer, case_name, case_type, color, price) VALUES ('O11D_XL_X', 'Lian Li', 'O11D XL-X', 'FULL_TOWER', 'BLACK', 299.00);

-- Power Supply
INSERT INTO power_supply(power_supply_id, manufacturer, power_supply_name, wattage, modular, price) VALUES ('RM850X_2021', 'Corsair', 'RM850x (2021)', 850, 'FULLY_MODULAR', 149.99);

-- Accessories
INSERT INTO accessories(accessory_id, category, manufacturer, accessory_name, price) VALUES ('TUF_GAMING_VG289Q1A' ,'MONITOR', 'ASUS', 'TUF Gaming VG289Q1A 28 Monitor, 4K UHD', 249.00);