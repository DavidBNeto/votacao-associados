CREATE TABLE VOTO (
    ID BIGINT NOT NULL,
    CPF VARCHAR(11) NOT NULL,
    ID_PAUTA BIGINT NOT NULL,
    VOTO VARCHAR(3) NOT NULL,
    HORA TIMESTAMP NOT NULL,
    FOREIGN KEY (ID_PAUTA) REFERENCES PAUTA(ID),
    PRIMARY KEY (ID)
);

CREATE SEQUENCE VOTO_SEQ START WITH 1 INCREMENT BY 1;