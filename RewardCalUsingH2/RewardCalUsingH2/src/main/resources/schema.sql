
create table customerNew
(
   id varchar(255) not null,
   total_rewards integer,
   month_rewards integer,
   three_month_rewards integer,
   primary key(id)
);


create table customerInvoice
(
  id varchar(255) not null,
  date_of_purchase date,
  billed_amt double
);