-- auto-generated definition
create table address
(
    id           varchar(36)  not null
        primary key,
    created_time datetime(6)  not null,
    edited_time  datetime(6)  not null,
    creator_id   varchar(36)  not null,
    editor_id    varchar(36)  not null,
    latitude     double       not null,
    longitude    double       not null,
    address      varchar(255) not null,
    province     varchar(255) not null,
    city         varchar(255) not null,
    district     varchar(255) not null,
    phone_number varchar(255) not null,
    real_name    varchar(255) not null,
    top          tinyint(1)   not null,
    details      varchar(255) not null
);
-- auto-generated definition
create table base_order
(
    id           varchar(36)  not null
        primary key,
    created_time datetime(6)  not null,
    edited_time  datetime(6)  not null,
    creator_id   varchar(36)  not null,
    editor_id    varchar(36)  not null,
    payment_id   varchar(36)  not null,
    address_id   varchar(36)  not null,
    remark       varchar(255) not null,
    status       varchar(36)  not null
);
-- auto-generated definition
create table coupon
(
    id                varchar(36)    not null
        primary key,
    created_time      datetime(6)    not null,
    edited_time       datetime(6)    not null,
    creator_id        varchar(36)    not null,
    editor_id         varchar(36)    not null,
    threshold_amount  decimal(10, 2) not null,
    released_quantity int            not null,
    effective_date    datetime(6)    not null,
    expiration_date   datetime(6)    not null,
    coupon_type       varchar(36)    not null,
    scope_type        varchar(36)    not null,
    amount            decimal(10, 2) null,
    discount          decimal(3, 2)  null,
    name              varchar(255)   not null,
    constraint coupon_pk
        unique (name)
);
-- auto-generated definition
create table coupon_use_record
(
    id             varchar(36) not null
        primary key,
    created_time   datetime(6) not null,
    edited_time    datetime(6) not null,
    creator_id     varchar(36) not null,
    editor_id      varchar(36) not null,
    coupon_user_id varchar(36) not null,
    order_id       varchar(36) not null
);
-- auto-generated definition
create table dict
(
    id           varchar(36)  not null
        primary key,
    created_time datetime(6)  not null,
    edited_time  datetime(6)  not null,
    creator_id   varchar(36)  not null,
    editor_id    varchar(36)  not null,
    key_en_name  varchar(255) not null,
    key_name     varchar(36)  not null,
    dict_id      int          not null,
    dict_name    varchar(36)  not null,
    dict_en_name varchar(255) not null,
    order_num    int          not null
);

-- auto-generated definition
create table invite_history
(
    id           varchar(36) not null
        primary key,
    created_time datetime(6) not null,
    edited_time  datetime(6) not null,
    inviter_id   varchar(36) not null,
    invitee_id   varchar(36) null,
    status       int         not null
);
-- auto-generated definition
create table inviter
(
    id           varchar(36)  not null
        primary key,
    created_time datetime(6)  not null,
    edited_time  datetime(6)  not null,
    creator_id   varchar(36)  not null,
    editor_id    varchar(36)  not null,
    code         varchar(36)  not null,
    qr_code      varchar(255) null
);
-- auto-generated definition
create table menu
(
    id           varchar(36)   not null
        primary key,
    created_time datetime(6)   not null,
    edited_time  datetime(6)   not null,
    creator_id   varchar(36)   not null,
    editor_id    varchar(36)   not null,
    name         varchar(20)   not null,
    path         varchar(2000) not null,
    parent_id    varchar(36)   null,
    order_num    int           null,
    menu_type    varchar(36)   not null,
    icon         varchar(255)  null
);

-- auto-generated definition
create table payment
(
    id           varchar(36)    not null
        primary key,
    created_time datetime(6)    not null,
    edited_time  datetime(6)    not null,
    creator_id   varchar(36)    not null,
    editor_id    varchar(36)    not null,
    pay_type     varchar(36)    not null,
    pay_amount   decimal(10, 2) not null,
    trade_no     varchar(36)    not null
);
-- auto-generated definition
create table product
(
    id           varchar(36)    not null
        primary key,
    created_time datetime(6)    not null,
    edited_time  datetime(6)    not null,
    creator_id   varchar(36)    not null,
    editor_id    varchar(36)    not null,
    name         varchar(255)   not null,
    price        decimal(10, 2) not null,
    image        varchar(255)   not null,
    brand        varchar(255)   not null,
    category_id  varchar(36)    not null,
    stock        int            not null,
    description  text           not null,
    tags         varchar(255)   not null,
    features     text           not null,
    specs        text           not null
);
-- auto-generated definition
create table product_attribute
(
    id           varchar(36)  not null
        primary key,
    created_time datetime(6)  not null,
    edited_time  datetime(6)  not null,
    creator_id   varchar(36)  not null,
    editor_id    varchar(36)  not null,
    `values`     varchar(255) not null,
    name         varchar(255) not null,
    product_id   varchar(36)  not null
);
-- auto-generated definition
create table product_category
(
    id           varchar(36)  not null
        primary key,
    created_time datetime(6)  not null,
    edited_time  datetime(6)  not null,
    creator_id   varchar(36)  not null,
    editor_id    varchar(36)  not null,
    name         varchar(50)  not null,
    parent_id    int          null,
    image        varchar(100) not null,
    description  text         not null,
    sort_order   int          not null
);
-- auto-generated definition
create table product_order
(
    id             varchar(36)    not null
        primary key,
    created_time   datetime(6)    not null,
    edited_time    datetime(6)    not null,
    creator_id     varchar(36)    not null,
    editor_id      varchar(36)    not null,
    status         varchar(36)    not null,
    base_order_id  varchar(36)    not null,
    coupon_user_id varchar(36)    null,
    vip_amount     decimal(10, 2) not null,
    coupon_amount  decimal(10, 2) not null,
    delivery_fee   decimal(10, 2) not null,
    product_amount decimal(10, 2) not null
);
-- auto-generated definition
create table product_order_item
(
    id               varchar(36) not null
        primary key,
    created_time     datetime(6) not null,
    edited_time      datetime(6) not null,
    creator_id       varchar(36) not null,
    editor_id        varchar(36) not null,
    product_order_id varchar(36) not null,
    product_sku_id   varchar(36) not null,
    count            int         not null
);
-- auto-generated definition
create table product_sku
(
    id           varchar(36)    not null
        primary key,
    created_time datetime(6)    not null,
    edited_time  datetime(6)    not null,
    creator_id   varchar(36)    not null,
    editor_id    varchar(36)    not null,
    `values`     varchar(255)   not null,
    name         varchar(255)   not null,
    product_id   varchar(255)   not null,
    cover        varchar(255)   null,
    price        decimal(38, 2) null,
    stock        int            null,
    description  varchar(255)   null
);
-- auto-generated definition
create table role
(
    id           varchar(36) not null
        primary key,
    created_time datetime(6) not null,
    edited_time  datetime(6) not null,
    creator_id   varchar(36) not null,
    editor_id    varchar(36) not null,
    name         varchar(36) not null,
    constraint role_pk
        unique (name)
);

-- auto-generated definition
create table role_menu_rel
(
    id           varchar(36) not null
        primary key,
    created_time datetime(6) not null,
    edited_time  datetime(6) not null,
    creator_id   varchar(36) not null,
    editor_id    varchar(36) not null,
    role_id      varchar(36) not null,
    menu_id      varchar(36) not null,
    constraint role_id
        unique (role_id, menu_id)
);

-- auto-generated definition
create table user
(
    id           varchar(36)  not null
        primary key,
    created_time datetime(6)  not null,
    edited_time  datetime(6)  not null,
    nickname     varchar(20)  null,
    avatar       varchar(255) null,
    gender       varchar(36)  null,
    phone        varchar(20)  not null,
    password     varchar(100) not null,
    constraint phone
        unique (phone)
);

-- auto-generated definition
create table user_role_rel
(
    id           varchar(36) not null
        primary key,
    created_time datetime(6) not null,
    edited_time  datetime(6) not null,
    creator_id   varchar(36) not null,
    editor_id    varchar(36) not null,
    role_id      varchar(36) not null,
    user_id      varchar(36) not null,
    constraint role_id
        unique (role_id, user_id)
);

-- auto-generated definition
create table user_wechat
(
    id           varchar(36) not null
        primary key,
    created_time datetime(6) not null,
    edited_time  datetime(6) not null,
    open_id      varchar(30) not null,
    user_id      varchar(36) not null,
    constraint open_id unique (open_id)
);