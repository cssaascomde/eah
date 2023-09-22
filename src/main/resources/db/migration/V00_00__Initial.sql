-- Organization Units
CREATE TABLE ou
(
    id              varchar(255) NOT NULL,
    sys_version     int,
    sys_modified_by varchar(255) NOT NULL,
    sys_modified_at datetime     NOT NULL,
    civento_key     varchar(128),
    regional_key    varchar(12),
    name            varchar(512) NOT NULL,
    address         varchar(512),
    zip_code        varchar(5),
    city            varchar(128),
    email           varchar(256),
    phone           varchar(32),
    type            varchar(255) NOT NULL,
    CONSTRAINT pk_ou PRIMARY KEY (id)
)
GO

ALTER TABLE ou
    ADD CONSTRAINT uc_ouentity_civento_key UNIQUE (civento_key)
GO

CREATE NONCLUSTERED INDEX idx_ouentity_regional_key ON ou (regional_key)
GO

-- Services
CREATE TABLE service
(
    id                  varchar(255) NOT NULL,
    sys_version         int,
    sys_modified_by     varchar(255) NOT NULL,
    sys_modified_at     datetime     NOT NULL,
    leika_key           varchar(16),
    civento_key         varchar(128),
    name                varchar(512) NOT NULL,
    responsibility_type varchar(255) NOT NULL,
    delivery_type       varchar(255) NOT NULL,
    CONSTRAINT pk_service PRIMARY KEY (id)
)
GO

ALTER TABLE service
    ADD CONSTRAINT uc_serviceentity_civento_key UNIQUE (civento_key)
GO

-- Responsibilities
CREATE TABLE responsibility
(
    id                varchar(255) NOT NULL,
    sys_version       int,
    sys_modified_by   varchar(255) NOT NULL,
    sys_modified_at   datetime     NOT NULL,
    ou_entity_id      varchar(255),
    service_entity_id varchar(255),
    regional_key      varchar(12) NOT NULL,
    delivery_type     varchar(255) NOT NULL,
    interface_type    varchar(16)
    CONSTRAINT pk_responsibility PRIMARY KEY (id)
)
GO

ALTER TABLE responsibility
    ADD CONSTRAINT FK_RESPONSIBILITY_ON_OU_ENTITY FOREIGN KEY (ou_entity_id) REFERENCES ou (id)
GO

ALTER TABLE responsibility
    ADD CONSTRAINT FK_RESPONSIBILITY_ON_SERVICE_ENTITY FOREIGN KEY (service_entity_id) REFERENCES service (id)
GO