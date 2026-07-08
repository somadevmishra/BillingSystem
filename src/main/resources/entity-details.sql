CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),

    display_order INT NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    created_by VARCHAR(50),
    updated_by VARCHAR(50),

    INDEX idx_categories_code (code),
    INDEX idx_categories_active (active)
);

CREATE TABLE items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    item_code VARCHAR(20) NOT NULL UNIQUE,

    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),

    category_id BIGINT NULL,

    unit_price DECIMAL(10,2) NOT NULL,
    gst_percentage DECIMAL(5,2) NOT NULL DEFAULT 0.00,

    display_order INT NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    created_by VARCHAR(50),
    updated_by VARCHAR(50),

    CONSTRAINT fk_items_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id),

    INDEX idx_items_code (item_code),
    INDEX idx_items_category (category_id),
    INDEX idx_items_active (active)
);

CREATE TABLE code_sequence (
    sequence_name VARCHAR(50) PRIMARY KEY,
    next_value BIGINT NOT NULL
);

CREATE TABLE bills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    bill_no VARCHAR(30) NOT NULL UNIQUE,

    bill_date TIMESTAMP NOT NULL,

    status VARCHAR(20) NOT NULL,

    subtotal DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    discount_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    tax_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    grand_total DECIMAL(12,2) NOT NULL DEFAULT 0.00,

    payment_mode VARCHAR(20),
    payment_status VARCHAR(20),

    amount_paid DECIMAL(12,2) DEFAULT 0.00,
    balance_amount DECIMAL(12,2) DEFAULT 0.00,

    customer_name VARCHAR(100),
    customer_mobile VARCHAR(15),

    remarks VARCHAR(255),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    created_by VARCHAR(50),
    updated_by VARCHAR(50),

    INDEX idx_bills_bill_no (bill_no),
    INDEX idx_bills_date (bill_date),
    INDEX idx_bills_status (status),
    INDEX idx_bills_payment_status (payment_status)
);

CREATE TABLE bill_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    bill_id BIGINT NOT NULL,

    item_id BIGINT NOT NULL,

    item_code VARCHAR(20) NOT NULL,
    item_name VARCHAR(255) NOT NULL,

    quantity DECIMAL(10,3) NOT NULL,

    unit_price DECIMAL(10,2) NOT NULL,

    gst_percentage DECIMAL(5,2) NOT NULL DEFAULT 0.00,

    discount_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,

    line_subtotal DECIMAL(12,2) NOT NULL,
    tax_amount DECIMAL(12,2) NOT NULL,
    line_total DECIMAL(12,2) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    created_by VARCHAR(50),

    CONSTRAINT fk_bill_items_bill
        FOREIGN KEY (bill_id)
        REFERENCES bills(id),

    CONSTRAINT fk_bill_items_item
        FOREIGN KEY (item_id)
        REFERENCES items(id),

    INDEX idx_bill_items_bill_id (bill_id),
    INDEX idx_bill_items_item_id (item_id)
);