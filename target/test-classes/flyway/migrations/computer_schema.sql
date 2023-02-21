drop table if exists order_accessories;
drop table if exists orders;
drop table if exists accessories;
drop table if exists power_supply;
drop table if exists pc_case;
drop table if exists video_card;
drop table if exists pc_storage;
drop table if exists pc_memory;
drop table if exists motherboard;
drop table if exists cpu_cooler;
drop table if exists pc_cpu;
drop table if exists customer;

create table customer (
customer_pk int auto_increment not null,
customer_id varchar(40) not null,
first_name varchar(45) not null,
last_name varchar(45) not null,
email varchar(45) not null,
primary key(customer_pk)
);
create table pc_cpu (
cpu_pk int auto_increment not null,
cpu_id varchar(40) not null,
cpu_brand enum('AMD', 'INTEL') not null,
cpu_name varchar(40) not null,
core_count int not null,
price decimal(7,2) not null,
primary key(cpu_pk)
);
create table cpu_cooler (
cpu_cooler_pk int auto_increment not null,
cpu_cooler_id varchar(40) not null,
manufacturer varchar(70) not null,
cooler_name varchar(50) not null,
is_water_cooled boolean not null,
price decimal(7,2) not null,
primary key(cpu_cooler_pk)
);
create table motherboard (
motherboard_pk int auto_increment not null,
motherboard_id varchar(40) not null,
manufacturer varchar(70) not null,
motherboard_name varchar(50) not null,
cpu_socket enum('AM4', 'AM5', 'LGA_1700', 'LGA_1200') not null,
form_factor enum('STANDARD_ATX', 'MICRO_ATX', 'MINI_ITX') not null,
memory_slots int not null,
price decimal(7,2) not null,
primary key(motherboard_pk)
);
create table pc_memory (
memory_pk int auto_increment not null,
memory_id varchar(40) not null,
manufacturer varchar(70) not null,
memory_name varchar(50) not null,
memory_type enum ('DDR3', 'DDR4', 'DDR5') not null,
memory_speed varchar(10) not null,
memory_size varchar(10) not null,
price decimal(7,2) not null,
primary key(memory_pk)
);
create table pc_storage (
storage_pk int auto_increment not null,
storage_id varchar(40) not null,
manufacturer varchar(70) not null,
storage_name varchar(50) not null,
storage_capacity varchar(10) not null,
storage_type varchar(10) not null,
storage_cache boolean not null,
price decimal(7,2) not null,
primary key(storage_pk)
);
create table video_card (
video_card_pk int auto_increment not null,
video_card_id varchar(40) not null,
video_card_brand enum('AMD', 'NVIDIA', 'INTEL') not null,
video_card_name varchar(50) not null,
video_card_memory varchar(10) not null,
price decimal(7,2) not null,
primary key(video_card_pk)
);
create table pc_case (
case_pk int auto_increment not null,
case_id varchar(40) not null,
manufacturer varchar(70) not null,
case_name varchar(50) not null,
case_type enum('FULL_TOWER', 'MID_TOWER', 'MINI_TOWER') not null,
color enum('BLACK', 'WHITE'),
price decimal(7,2) not null,
primary key(case_pk)
);
create table power_supply (
power_supply_pk int auto_increment not null,
power_supply_id varchar(40) not null,
manufacturer varchar(70) not null,
power_supply_name varchar(50) not null,
wattage int not null,
modular enum('FULLY_MODULAR', 'SEMI_MODULAR', 'NON_MODULAR') not null,
price decimal(7,2) not null,
primary key(power_supply_pk)
);
create table accessories (
accessory_pk int auto_increment not null,
accessory_id varchar(40) not null,
category enum('MONITOR', 'KEYBOARD', 'MOUSE', 'SPEAKERS') null,
manufacturer varchar(70) not null,
accessory_name varchar(60) not null,
price decimal(7,2) not null,
primary key(accessory_pk)
);
create table orders(
order_pk int auto_increment not null,
customer_fk int not null,
cpu_fk int not null,
cpu_cooler_fk int not null,
motherboard_fk int not null,
memory_fk int not null,
storage_fk int not null,
video_card_fk int not null,
case_fk int not null,
power_supply_fk int not null,
price decimal(7,2) not null,
primary key(order_pk),
foreign key (customer_fk) references customer(customer_pk) on delete cascade,
foreign key (cpu_fk) references pc_cpu(cpu_pk) on delete cascade,
foreign key (cpu_cooler_fk) references cpu_cooler(cpu_cooler_pk) on delete cascade,
foreign key (motherboard_fk) references motherboard(motherboard_pk) on delete cascade,
foreign key (memory_fk) references pc_memory(memory_pk) on delete cascade,
foreign key (storage_fk) references pc_storage(storage_pk) on delete cascade,
foreign key (video_card_fk) references video_card(video_card_pk) on delete cascade,
foreign key (case_fk) references pc_case(case_pk) on delete cascade,
foreign key (power_supply_fk) references power_supply(power_supply_pk) on delete cascade
);

create table order_accessories(
	accessory_fk int auto_increment not null,
    order_fk int not null,
    foreign key (accessory_fk) references accessories(accessory_pk) on delete cascade,
    foreign key (order_fk) references orders(order_pk) on delete cascade
);