CREATE TABLE publisher (
	id int8 NOT NULL,
	country varchar(255) NULL,
	"date" date NULL,
	"name" varchar(255) NULL,
	CONSTRAINT publisher_pkey PRIMARY KEY (id)
);

CREATE TABLE artist (
	id int8 NOT NULL,
	"date" date NULL,
	"name" varchar(255) NULL,
	CONSTRAINT artist_pkey PRIMARY KEY (id)
);

CREATE TABLE book (
	id int8 NOT NULL,
	"date" date NULL,
	genre varchar(255) NULL,
	title varchar(255) NULL,
	"type" varchar(255) NULL,
	licencing_publisher_id int8 NOT NULL,
	original_publisher_id int8 NOT NULL,
	CONSTRAINT book_pkey PRIMARY KEY (id)
);

ALTER TABLE book ADD CONSTRAINT fk_original_publisher FOREIGN KEY (original_publisher_id) REFERENCES publisher(id);
ALTER TABLE book ADD CONSTRAINT fk_licencing_publisher FOREIGN KEY (licencing_publisher_id) REFERENCES publisher(id);

CREATE TABLE artist_role (
	id int8 NOT NULL,
	"date" date NULL,
	"type" int4 NULL,
	artist_id int8 NULL,
	book_id int8 NULL,
	CONSTRAINT artist_role_pkey PRIMARY KEY (id)
);

ALTER TABLE artist_role ADD CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES book(id);
ALTER TABLE artist_role ADD CONSTRAINT fk_artist FOREIGN KEY (artist_id) REFERENCES artist(id);

CREATE SEQUENCE hibernate_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;