CREATE TABLE categories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    display_order INT NOT NULL,
    active BOOLEAN NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uk_categories_code UNIQUE (code),
    INDEX idx_categories_code (code),
    INDEX idx_categories_active (active)
);

CREATE TABLE items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    item_code VARCHAR(20) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    category_id BIGINT,
    unit_price DECIMAL(10,2) NOT NULL,
    gst_percentage DECIMAL(5,2) NOT NULL,
    display_order INT NOT NULL,
    active BOOLEAN NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT pk_items PRIMARY KEY (id),
    CONSTRAINT uk_items_item_code UNIQUE (item_code),
    CONSTRAINT fk_items_category FOREIGN KEY (category_id) REFERENCES categories (id),
    INDEX idx_items_code (item_code),
    INDEX idx_items_category (category_id),
    INDEX idx_items_active (active)
);

CREATE TABLE code_sequence (
    sequence_name VARCHAR(255) NOT NULL,
    next_value BIGINT NOT NULL,
    CONSTRAINT pk_code_sequence PRIMARY KEY (sequence_name)
);

CREATE TABLE bills (
    id BIGINT NOT NULL AUTO_INCREMENT,
    bill_no VARCHAR(30) NOT NULL,
    bill_date DATETIME(6) NOT NULL,
    status VARCHAR(20) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    discount_amount DECIMAL(12,2) NOT NULL,
    tax_amount DECIMAL(12,2) NOT NULL,
    grand_total DECIMAL(12,2) NOT NULL,
    payment_mode VARCHAR(20),
    payment_status VARCHAR(20),
    amount_paid DECIMAL(12,2) NOT NULL,
    balance_amount DECIMAL(12,2) NOT NULL,
    customer_name VARCHAR(100),
    customer_mobile VARCHAR(15),
    remarks VARCHAR(500),
    finalized_at DATETIME(6),
    cancelled_at DATETIME(6),
    cancelled_by VARCHAR(50),
    cancel_reason VARCHAR(255),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT pk_bills PRIMARY KEY (id),
    CONSTRAINT uk_bills_bill_no UNIQUE (bill_no),
    INDEX idx_bills_bill_no (bill_no),
    INDEX idx_bills_date (bill_date),
    INDEX idx_bills_status (status),
    INDEX idx_bills_payment_status (payment_status)
);

CREATE TABLE bill_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    bill_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    item_code VARCHAR(20) NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    quantity DECIMAL(10,3) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    gst_percentage DECIMAL(5,2) NOT NULL,
    discount_amount DECIMAL(10,2) NOT NULL,
    line_subtotal DECIMAL(12,2) NOT NULL,
    tax_amount DECIMAL(12,2) NOT NULL,
    line_total DECIMAL(12,2) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    version BIGINT,
    CONSTRAINT pk_bill_items PRIMARY KEY (id),
    CONSTRAINT fk_bill_items_bill FOREIGN KEY (bill_id) REFERENCES bills (id),
    CONSTRAINT fk_bill_items_item FOREIGN KEY (item_id) REFERENCES items (id),
    INDEX idx_bill_items_bill_id (bill_id),
    INDEX idx_bill_items_item_id (item_id)
);

INSERT INTO code_sequence (sequence_name, next_value)
VALUES
    ('CATEGORY', 1),
    ('ITEM', 1),
    ('BILL', 1);
