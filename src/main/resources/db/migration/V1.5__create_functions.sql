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