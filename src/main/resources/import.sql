insert into USER(ID, USER_ID, PASSWORD, NAME, EMAIL,CREATE_DATE) values ('1','nekisse','test', '이선','nekisse@google.com',now());
insert into USER(ID, USER_ID, PASSWORD, NAME, EMAIL,CREATE_DATE) values ('2','perci','test', '킹재','kjw@google.com',now());


insert into QUESTION(ID, WRITER_ID, TITLE, CONTENTS, CREATE_DATE, count_of_answer) values ('1', '1','Nekisse testTitle','contents',now(), 0)

--insert into QUESTION(ID, WRITER_ID, TITLE, CONTENTS, CREATE_DATE) values ('2', '2','perci Title','perCi contents',now())
