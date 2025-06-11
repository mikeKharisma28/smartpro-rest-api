use sipro;
go

insert into MasterData.Division(Name, CreatedBy, CreatedDate) values('Div. General Affair', 1, GETDATE());
go

insert into MasterData.Role(Name, CreatedBy, CreatedDate) values ('Administrator', 1, GETDATE());
go

insert into MasterData.Role(Name, CreatedBy, CreatedDate) values ('Staff', 1, GETDATE());
go

insert into MasterData.Staff(
	FullName, 
	Username, 
	PhoneNumber, 
	Password, 
	DivisionId, 
	RoleId, 
	CreatedBy, 
	CreatedDate
) values (
	'Michael Laksa Kharisma',
	'mike.laksa',
	'+6281912345678',
	'$2a$11$3GWCBi3oHnjS.daZfHK1g.yb2GVI.b0jsCPt62dYRMPoWFDbKvBgK',
	(select Id from MasterData.Division where Name = 'Div. General Affair'),
	(select Id from MasterData.Role where Name = 'Administrator'),
	1,
	GETDATE()
);
go

insert into MasterData.Staff(
	FullName, 
	Username, 
	PhoneNumber, 
	Password, 
	DivisionId, 
	RoleId, 
	CreatedBy, 
	CreatedDate
) values (
	'Riyan Gunawan',
	'riyan.gunawan',
	'+6281912341234',
	'$2a$11$ydfdFpkit/AjxDfj49iSHOnb4VtBFh86mWHF.OqAJTGrGiXxKKxHa',
	(select Id from MasterData.Division where Name = 'Div. General Affair'),
	(select Id from MasterData.Role where Name = 'Staff'),
	1,
	GETDATE()
);
go

--Procurement
insert into MasterData.Menu(Name, Path, CreatedBy, CreatedDate) values (
	'Procurement', '/procurement', 1, GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, CreatedBy, CreatedDate) values (
	'Request',
	(select id from MasterData.Menu where Name = 'Procurement'),
	'/procurement/request', 
	1, 
	GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, CreatedBy, CreatedDate) values (
	'Approval',
	(select id from MasterData.Menu where Name = 'Procurement'),
	'/procurement/approval', 
	1, 
	GETDATE()
);
go

--Purchase
insert into MasterData.Menu(Name, Path, CreatedBy, CreatedDate) values (
	'Purchase', '/purchase', 1, GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, CreatedBy, CreatedDate) values (
	'Request',
	(select id from MasterData.Menu where Name = 'Purchase'),
	'/purchase/request', 
	1, 
	GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, CreatedBy, CreatedDate) values (
	'Approval',
	(select id from MasterData.Menu where Name = 'Purchase'),
	'/purchase/approval', 
	1, 
	GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, CreatedBy, CreatedDate) values (
	'Order',
	(select id from MasterData.Menu where Name = 'Purchase'),
	'/purchase/order', 
	1, 
	GETDATE()
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Staff'),
	(select Id from MasterData.Menu where Name = 'Procurement')
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Staff'),
	(select Id from MasterData.Menu where Name = 'Request' and ParentId = (select id from MasterData.Menu where Name = 'Procurement'))
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Procurement')
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Request' and ParentId = (select id from MasterData.Menu where Name = 'Procurement'))
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Approval' and ParentId = (select id from MasterData.Menu where Name = 'Procurement'))
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Purchase')
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Request' and ParentId = (select id from MasterData.Menu where Name = 'Purchase'))
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Approval' and ParentId = (select id from MasterData.Menu where Name = 'Purchase'))
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Order' and ParentId = (select id from MasterData.Menu where Name = 'Purchase'))
);
go
