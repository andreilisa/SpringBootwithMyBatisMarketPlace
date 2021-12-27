CREATE TABLE users
(
    id        SERIAL not null
        constraint users_pkey
            primary key,
    email     varchar(255),
    enabled   boolean,
    locked    boolean,
    password  varchar(255),
    user_role varchar(255),
    username  varchar(255)
);
CREATE TABLE products
(
    id      SERIAL           not null
        constraint products_pkey
            primary key,
    name    varchar(255)     not null,
    price   double precision not null,
    user_id bigint,
    FOREIGN KEY (user_id) REFERENCES users (id)

);
CREATE TABLE rating
(

    isliked   boolean,
    productid integer not null
        constraint rating_productid_fkey
            references products,
    userid    integer not null
        constraint rating_userid_fkey
            references users,
    id        SERIAL  not null,
    constraint rating_pkey
        primary key (userid, productid)

);