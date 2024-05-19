CREATE DATABASE fastfood;

\c fastfood

CREATE TABLE tb_coupon(
                          "id_coupon" integer CONSTRAINT pk_id_coupon PRIMARY KEY,
                          "discount" float not null,
                          "status" boolean not null,
                          "code" VARCHAR(100) not null
);

CREATE TABLE tb_product(
                           "id_product" integer CONSTRAINT pk_id_product PRIMARY KEY,
                           "name" VARCHAR(100) not null,
                           "price" decimal(12,2) not null,
                           "category" VARCHAR(50) not null
);

CREATE TABLE tb_customer(
                            "id_customer" integer CONSTRAINT pk_id_customer PRIMARY KEY,
                            "name" VARCHAR(200) not null,
                            "cpf" VARCHAR(11) not null,
                            "email" VARCHAR(100) not null
);

CREATE TABLE tb_order(
                         "id_order" integer CONSTRAINT pk_id_order PRIMARY KEY,
                         "id_customer" integer not null,
                         FOREIGN KEY ("id_customer") REFERENCES tb_customer ("id_customer") ON DELETE CASCADE,
                         "status" boolean not null,
                         "date_order" date,
                         "id_coupon" integer,
                         FOREIGN KEY ("id_coupon") REFERENCES tb_coupon ("id_coupon") ON DELETE CASCADE
);

CREATE TABLE tb_order_payment(
                                 "id_order_payment" integer CONSTRAINT pk_id_order_payment PRIMARY KEY,
                                 "id_order" integer not null,
                                 FOREIGN KEY ("id_order") REFERENCES tb_order ("id_order") ON DELETE CASCADE,
                                 "id_customer" integer not null,
                                 FOREIGN KEY ("id_customer") REFERENCES tb_customer ("id_customer") ON DELETE CASCADE,
                                 "status" boolean not null,
                                 "date_order_payment" date
);

CREATE TABLE tb_order_item(
                              "id_order_item" integer CONSTRAINT pk_id_order_item PRIMARY KEY,
                              "id_product" integer not null,
                              FOREIGN KEY ("id_product") REFERENCES tb_product ("id_product") ON DELETE CASCADE,
                              "id_order" integer not null,
                              FOREIGN KEY ("id_order") REFERENCES tb_order ("id_order") ON DELETE CASCADE,
                              "quantity" integer not null,
                              "amount" decimal(12,2)
);
