

set foreign_key_checks = 0;

truncate table learning_party;
truncate table authority;
truncate table instructor;

insert into learning_party(`id`, `email`, `password`, `enabled`)
values(123, 'tomi@gmail.com', '123pass123', false),
    (124, 'tomi1@gmail.com', '123pass123', false),
    (125, 'tomi2@gmail.com', '123pass123', false),
    (126, 'tomi3@gmail.com', '123pass123', false),
    (127, 'tomi4@gmail.com', '123pass123', false),
    (128, 'tomi5@gmail.com', '123pass123', false);



set foreign_key_checks = 1