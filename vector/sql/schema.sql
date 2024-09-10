create table if not exists alerts(
                                     alert_id serial primary key,
                                     message_id             TEXT,
                                     description            TEXT         NOT NULL,
                                     severity               smallint     NOT NULL,
                                     additional_information TEXT);

create table if not exists chat_history(
                                           id serial primary key,
                                           alert_id bigint not null,
                                           question text not null,
                                           answer text not null
    );

CREATE INDEX if not exists chat_history_id_alert_id ON chat_history
    (
    id asc ,
    alert_id asc
    );