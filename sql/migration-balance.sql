INSERT INTO t_usermeta ( `uid`, `meta_key`, `meta_value` ) SELECT
t_user.id,
'balance',
'2000'
FROM
	t_user
