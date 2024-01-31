CREATE TYPE orders_type AS (
                               id bigint,
                               name character varying(255),
                               createdAt timestamp without time zone
                           );

CREATE OR REPLACE FUNCTION getOrdersByUserId(userId bigint)
    RETURNS SETOF orders_type AS $$
BEGIN
    RETURN QUERY SELECT o.id, os.name, os.created_at FROM public.orders o
                                                              left join order_statuses os on o.id = os.order_id and os.created_at =
                                                                                                                    (select max(oss.created_at) from order_statuses oss where o.id = oss.order_id)
    WHERE o.user_id = userId;
END;
$$ LANGUAGE plpgsql;



CREATE TYPE orders_admin_type AS (
    id bigint,
    name character varying(255),
    userId bigint,
    createdAt timestamp without time zone,
    productCount decimal,
    totalSum decimal,
    paymentType character varying(255),
    paymentStatus character varying(255),
    deliveryType character varying(255)
);


CREATE OR REPLACE FUNCTION getOrdersForAdmin()
    RETURNS SETOF orders_admin_type AS $$
BEGIN
    RETURN QUERY SELECT o.id, os.name, o.user_id, os.created_at,
                        (select sum(oi.count) from order_items oi where o.id = oi.order_id) as productCount,
                        (select sum(oi.count * oi.price) from order_items oi where o.id = oi.order_id) as totalSum,
                        o.payment_type as paymentType,
                        o.payment_status as paymentStatus,
                        o.delivery_type as deliveryType
                 FROM public.orders o
                          left join order_statuses os on o.id = os.order_id and os.created_at =
                                                                                (select max(oss.created_at) from order_statuses oss
                                                                                 where o.id = oss.order_id);
END;
$$ LANGUAGE plpgsql;


CREATE TYPE users_admin_type AS (
    id bigint,
    name character varying(255),
    phoneNumber character varying(255),
    email character varying(255),
    orderCount bigint,
    totalSum decimal
);

CREATE OR REPLACE FUNCTION getUsersForAdmin()
    RETURNS SETOF users_admin_type AS $$
BEGIN
    RETURN QUERY select u.id, u.name, u.username as phoneNumber, u.email,
                        (select count(o.id) from orders o where o.user_id = u.id) as orderCount,
                        (select sum((select sum((item.price - item.discount) * item.count) from order_items item where item.order_id = oo.id)) from orders oo where oo.user_id = u.id) as totalSum
                 from users u left join roles r on u.role_id = r.id where r.name = 'ROLE_USER';
END;
$$ LANGUAGE plpgsql;





CREATE TYPE products_admin_type AS (
    id bigint,
    isPublished boolean,
    code character varying(20),
    name character varying(255),
    unitType character varying(255),
    unitValue decimal,
    stock numeric,
    price decimal,
    opt_price decimal,
    categoryName character varying(255)
);

CREATE OR REPLACE FUNCTION getProductsForAdmin(searchText text)
    RETURNS SETOF products_admin_type AS $$
BEGIN
    RETURN QUERY select
                     product.id,
                     product.is_published,
                     product.code,
                     product.name,
                     product.unit_type,
                     product.unit_value,
                     product.stock,
                     product.price,
                     product.opt_price,
                     category.name as category
                 from products product
                          left join categories category on product.category_id = category.id
                 where product.*::text ilike searchText;
END;
$$ LANGUAGE plpgsql;

CREATE TYPE categories_admin_type AS (
    id bigint,
    is_published boolean,
    name character varying(255),
    childs character varying[],
    childsTwo character varying[],
    totalProducts bigint
);

CREATE OR REPLACE FUNCTION getCategoriesForAdmin(searchText text)
    RETURNS SETOF categories_admin_type AS $$
BEGIN
    RETURN QUERY SELECT parent.id, parent.is_published, parent.name,
                        (select array_agg(child.name) from categories child where child.parent_id = parent.id) as childs,
                        (select array_agg(child_two.name) from categories child right join categories child_two on child.id = child_two.parent_id  where child.parent_id = parent.id) as childs_two,
                        (select count(prod.id) from categories category right join categories child_cat on child_cat.parent_id = category.id right join products prod on prod.category_id = category.id or prod.category_id = child_cat.id where category.parent_id = parent.id) as totalProducts
                 from categories parent where parent.parent_id ISNULL and (parent.id::text ilike searchText or parent.name ilike searchText or (select count(prod.id) from categories category right join categories child_cat on child_cat.parent_id = category.id right join products prod on prod.category_id = category.id or prod.category_id = child_cat.id where category.parent_id = parent.id)::text ilike searchText);
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION getCategoryDetailsForAdmin(categoryId bigint)
    RETURNS SETOF categories_admin_type AS $$
BEGIN
    RETURN QUERY SELECT parent.id, parent.is_published, parent.name,
                        (select array_agg(child.name) from categories child where child.parent_id = parent.id) as childs,
                        (select array_agg(child_two.name) from categories child right join categories child_two on child.id = child_two.parent_id  where child.parent_id = parent.id) as childs_two,
                        (select count(prod.id) from categories category right join categories child_cat on child_cat.parent_id = category.id right join products prod on prod.category_id = category.id or prod.category_id = child_cat.id where category.parent_id = parent.id) as totalProducts
                 from categories parent where parent.id = categoryId;
END;
$$ LANGUAGE plpgsql;