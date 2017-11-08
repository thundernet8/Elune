INSERT INTO t_balancelog ( `uid`, `type`, `amount`, `cost`, `balance`, `content`, `link`, `create_time` ) SELECT
  id,
  1,
  2000,
  0,
  2000,
  '注册获得初始财富奖励',
  '',
  join_time
FROM
  t_user;

INSERT INTO t_balancelog ( `uid`, `type`, `amount`, `cost`, `balance`, `content`, `link`, `create_time` ) SELECT
  uid,
  1,
  10,
  0,
  0,
  after_status,
  link,
  create_time
FROM
  t_userlog
WHERE
  type = 10;


