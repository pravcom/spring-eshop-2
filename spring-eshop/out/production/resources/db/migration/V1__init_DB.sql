CREATE SEQUENCE IF NOT EXISTS bucket_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS order_details_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS order_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE buckets
(
    id      BIGINT NOT NULL,
    user_id BIGINT,
    CONSTRAINT pk_buckets PRIMARY KEY (id)
);

CREATE TABLE buckets_product
(
    bucket_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);

CREATE TABLE categories
(
    id    BIGINT NOT NULL,
    title VARCHAR(255),
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id      BIGINT NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE,
    updated TIMESTAMP WITHOUT TIME ZONE,
    user_id BIGINT,
    sum     DECIMAL,
    address VARCHAR(255),
    status  VARCHAR(255),
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE order_detail
(
    id         BIGINT NOT NULL,
    order_id   BIGINT,
    product_id BIGINT,
    amount     DECIMAL,
    price      DECIMAL,
    CONSTRAINT pk_orders_details PRIMARY KEY (id)
);

CREATE TABLE product_categories
(
    category_id BIGINT NOT NULL,
    product_id  BIGINT NOT NULL
);

CREATE TABLE products
(
    id    BIGINT NOT NULL,
    title VARCHAR(255),
    price DECIMAL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       BIGINT  NOT NULL,
    name     VARCHAR(255),
    password VARCHAR(255),
    email    VARCHAR(255),
    archive  BOOLEAN NOT NULL,
    role     VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE buckets
    ADD CONSTRAINT FK_BUCKETS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE orders_details
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE orders_details
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE buckets_product
    ADD CONSTRAINT fk_bucpro_on_bucket FOREIGN KEY (bucket_id) REFERENCES buckets (id);

ALTER TABLE buckets_product
    ADD CONSTRAINT fk_bucpro_on_product FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE product_categories
    ADD CONSTRAINT fk_procat_on_category FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE product_categories
    ADD CONSTRAINT fk_procat_on_product FOREIGN KEY (product_id) REFERENCES products (id);