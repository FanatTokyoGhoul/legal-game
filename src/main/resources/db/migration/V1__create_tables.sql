CREATE TABLE company
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(256) NOT NULL,
    capital  FLOAT        NOT NULL,
    game_key BIGINT       NOT NULL
);

CREATE TABLE game
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(256) NOT NULL,
    tax_percent INT          NOT NULL
);

CREATE TABLE game_action
(
    id       BIGSERIAL PRIMARY KEY,
    log      TEXT NOT NULL,
    month    INT          NOT NULL,
    game_key BIGINT       NOT NULL
);

CREATE TABLE insurance
(
    id                   BIGSERIAL PRIMARY KEY,
    type_of_insurance_id BIGINT NOT NULL,
    base_rate            FLOAT  NOT NULL,
    treaty_key           BIGINT NOT NULL
);

CREATE TABLE treaty
(
    id                       BIGSERIAL PRIMARY KEY,
    size                     INT    NOT NULL,
    installment_frequency    FLOAT  NOT NULL,
    validity                 INT    NOT NULL,
    max_payment              FLOAT  NOT NULL,
    size_hit_for_min_payment FLOAT  NOT NULL,
    company_key              BIGINT NOT NULL
);

CREATE TABLE type_of_insurance
(
    id          BIGSERIAL PRIMARY KEY,
    type        TEXT   NOT NULL,
    base_demand FLOAT  NOT NULL,
    game_key    BIGINT NOT NULL
)