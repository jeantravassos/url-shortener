DELETE FROM url;

insert into url values('YjM2ND', sysdate(), 'http://www.jeantravassos.com');

DELETE FROM urlstatistic;

insert into urlstatistic values(1, sysdate(), 'iPhone', 'Chrome', 'iOS', 'YjM2ND');