CREATE TABLE product
(
    product_id BIGINT       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    price      DECIMAL(19, 3),
    currency   VARCHAR(3),

    PRIMARY KEY (product_id)
);

CREATE TABLE article
(
    article_id      BIGINT       NOT NULL,
    name            VARCHAR(255) NOT NULL,
    available_stock INTEGER      NOT NULL,

    PRIMARY KEY (article_id)
);

CREATE TABLE product_article
(
    product_article_id BIGINT  NOT NULL,
    product_id         BIGINT  NOT NULL,
    article_id         BIGINT  NOT NULL,
    amount_of          INTEGER NOT NULL,

    PRIMARY KEY (product_article_id),
    CONSTRAINT FK_PRODUCT_ARTICLE_PRODUCT_ID FOREIGN KEY (product_id) REFERENCES product (product_id),
    CONSTRAINT FK_PRODUCT_ARTICLE_ARTICLE_ID FOREIGN KEY (article_id) REFERENCES article (article_id)
);
