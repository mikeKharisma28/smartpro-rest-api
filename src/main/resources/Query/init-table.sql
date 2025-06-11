create database sipro;
go

use sipro;
go

create schema MasterData;
go

create table MasterData.Division (Id bigint identity not null, CreatedBy bigint not null, CreatedDate datetime2(6) not null, Name varchar(70), UpdatedBy bigint, UpdatedDate datetime2(6), primary key (Id));
go
create table MasterData.Menu (Id bigint identity not null, CreatedBy bigint not null, CreatedDate datetime2(6) not null, Name varchar(70), Path varchar(255), UpdatedBy bigint, UpdatedDate datetime2(6), ParentId bigint, primary key (Id));
go
create table MasterData.Role (Id bigint identity not null, CreatedBy bigint not null, CreatedDate datetime2(6) not null, Name varchar(70), UpdatedBy bigint, UpdatedDate datetime2(6), primary key (Id));
go
create table MasterData.Staff (Id bigint identity not null, CreatedBy bigint not null, CreatedDate datetime2(6) not null, FullName varchar(70) not null, Password varchar(255) not null, PhoneNumber varchar(15), UpdatedBy bigint, UpdatedDate datetime2(6), Username varchar(50) not null, DivisionId bigint not null, RoleId bigint not null, primary key (Id));
go
create table MasterData.AccessPermission (RoleId bigint not null, MenuId bigint not null);
go

alter table MasterData.AccessPermission add constraint UK22612gdsoo2eo51f1pad3qh84 unique (RoleId, MenuId);
go
create unique nonclustered index UK7ko57exlxsklwniiwlieg5fn8 on MasterData.Division (Name) where Name is not null;
go
create unique nonclustered index UK3cum603hb0nklewavkwrw0cfy on MasterData.Role (Name) where Name is not null;
go
alter table MasterData.Staff add constraint UKeafr8pg6hp3werxqx82m1twpi unique (FullName);
go
create unique nonclustered index UKqu659om7qvlrfm5ljk61mevwg on MasterData.Staff (PhoneNumber) where PhoneNumber is not null;
go
alter table MasterData.Staff add constraint UKmire0p8m2hso5ac6p3ciapudh unique (Username);
go

create schema TransactionData;
go

create table TransactionData.ProcurementRequest (ProcurementNo varchar(15) not null, CreatedBy bigint not null, CreatedDate datetime2(6) not null, ExpectedDate date not null, ItemDescription varchar(100), ItemName varchar(100), RequestedQuantity int not null, Status int not null, Unit varchar(10), UpdatedBy bigint, UpdatedDate datetime2(6), DivisionId bigint not null, primary key (ProcurementNo));
go
create table TransactionData.PurchaseRequest (PurchaseRequestNo varchar(15) not null, CreatedBy bigint not null, CreatedDate datetime2(6) not null, EstimatedPrice numeric(38,2) not null, EstimatedQuantity int not null, ItemReferenceUrl date, ItemSpecificationUrl date, Status int not null, Unit varchar(10), UpdatedBy bigint, UpdatedDate datetime2(6), ProcurementRequestNo varchar(15) not null, primary key (PurchaseRequestNo));
go
create table TransactionData.PurchaseOrder (PurchaseOrderNo varchar(15) not null, CreatedBy bigint not null, CreatedDate datetime2(6) not null, QuantityOrdered int not null, Status int not null, TotalAmount numeric(38,2) not null, Unit varchar(10), UnitPrice numeric(38,2) not null, UpdatedBy bigint, UpdatedDate datetime2(6), PurchaseRequestNo varchar(15) not null, primary key (PurchaseOrderNo));
go
create table TransactionData.ApprovalTracking (Id bigint identity not null, CreatedBy bigint not null, CreatedDate datetime2(6) not null, Notes varchar(255), Timestamp datetime2(6), UpdatedBy bigint, UpdatedDate datetime2(6), ProcurementNo varchar(15) not null, PurchaseRequestNo varchar(15) not null, StaffId bigint not null, primary key (Id));
go

create unique nonclustered index UKkh89wl55gqk3469xt4ydlpxx8 on TransactionData.ProcurementRequest (ItemDescription) where ItemDescription is not null;
go
create unique nonclustered index UKhw6maowc8fohfkwumfopyje07 on TransactionData.ProcurementRequest (ItemName) where ItemName is not null;
go
alter table TransactionData.PurchaseOrder add constraint UKs0hmhots07kwihds4wmrnvnfl unique (PurchaseRequestNo);
go
alter table TransactionData.PurchaseRequest add constraint UK4w1wkmddrxisn3eut9eo00ria unique (ProcurementRequestNo);
go
alter table MasterData.AccessPermission add constraint FK36fva9sj5m47sfxomou0vfkwi foreign key (MenuId) references MasterData.Menu;
go
alter table MasterData.AccessPermission add constraint FKn3n7o6brex49j3w38gwdcotc0 foreign key (RoleId) references MasterData.Role;
go
alter table MasterData.Menu add constraint FKdv6na2x34w7c9ik7gwaeop44o foreign key (ParentId) references MasterData.Menu;
go
alter table MasterData.Staff add constraint FKgf9ovnq775g02m5qhuvkffcjc foreign key (DivisionId) references MasterData.Division;
go
alter table MasterData.Staff add constraint FKi9xmahyh65di7x2wn5fvt8lv3 foreign key (RoleId) references MasterData.Role;
go
alter table TransactionData.ApprovalTracking add constraint FKbxxwny32qssae1j457v38xvur foreign key (ProcurementNo) references TransactionData.ProcurementRequest;
go
alter table TransactionData.ApprovalTracking add constraint FKtj1l199gsqi3978f3cvjcpgi9 foreign key (PurchaseRequestNo) references TransactionData.PurchaseRequest;
go
alter table TransactionData.ApprovalTracking add constraint FK7cdjio4j6pvkfjf1pyp2t3f8w foreign key (StaffId) references MasterData.Staff;
go
alter table TransactionData.ProcurementRequest add constraint FK2jadmmar9yjr5byxwhp0l9vdx foreign key (DivisionId) references MasterData.Division;
go
alter table TransactionData.PurchaseOrder add constraint FK59jn3vxgycr8mprorh493dib8 foreign key (PurchaseRequestNo) references TransactionData.PurchaseRequest;
go
alter table TransactionData.PurchaseRequest add constraint FK6swrrn1qtgv9vmx0ovpe0vwk1 foreign key (ProcurementRequestNo) references TransactionData.ProcurementRequest;
go
