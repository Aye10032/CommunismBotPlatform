USE bot;

CREATE TABLE sub_task
(
    id           BIGINT AUTO_INCREMENT COMMENT 'id'
        PRIMARY KEY,
    reciver_type INT          NOT NULL COMMENT '接收者类型',
    reciver_id   BIGINT       NOT NULL COMMENT '接受者id',
    args         TEXT         NOT NULL COMMENT '参数',
    sub_name     VARCHAR(255) NOT NULL COMMENT '订阅类型名称'
)
    ROW_FORMAT = DYNAMIC;
