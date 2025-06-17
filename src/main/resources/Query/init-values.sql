use sipro;
go

insert into MasterData.Division(Name, CreatedBy, CreatedDate) values('Div. General Affair', 1, GETDATE());
go

insert into MasterData.Division(Name, CreatedBy, CreatedDate) values('Div. Finance', 1, GETDATE());
go

insert into MasterData.Division(Name, CreatedBy, CreatedDate) values('Div. Application Development', 1, GETDATE());
go

insert into MasterData.Role(Name, CreatedBy, CreatedDate) values ('Administrator', 1, GETDATE());
go

insert into MasterData.Role(Name, CreatedBy, CreatedDate) values ('Staff', 1, GETDATE());
go

insert into MasterData.Role(Name, CreatedBy, CreatedDate) values ('Supervisor', 1, GETDATE());
go

insert into MasterData.Role(Name, CreatedBy, CreatedDate) values ('Account Manager', 1, GETDATE());
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
	(select Id from MasterData.Division where Name = 'Div. Application Development'),
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
	(select Id from MasterData.Division where Name = 'Div. Application Development'),
	(select Id from MasterData.Role where Name = 'Staff'),
	1,
	GETDATE()
);
go

--Procurement
insert into MasterData.Menu(Name, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Dashboard', '/', 'home', 1, GETDATE()
);
go

insert into MasterData.Menu(Name, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Procurement', '#', 'shopping-cart', 1, GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Request',
	(select id from MasterData.Menu where Name = 'Procurement'),
	'/procurement/request',
	'inbox',
	1,
	GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Approval',
	(select id from MasterData.Menu where Name = 'Procurement'),
	'/procurement/approval',
	'user-check',
	1,
	GETDATE()
);
go

--Purchase
insert into MasterData.Menu(Name, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Purchase', '#', 'dollar-sign', 1, GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Request',
	(select id from MasterData.Menu where Name = 'Purchase'),
	'/purchase/request',
	'inbox',
	1,
	GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Approval',
	(select id from MasterData.Menu where Name = 'Purchase'),
	'/purchase/approval',
	'user-check',
	1,
	GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Order',
	(select id from MasterData.Menu where Name = 'Purchase'),
	'/purchase/order',
	'file-text',
	1,
	GETDATE()
);
go

--App Setting
insert into MasterData.Menu(Name, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'App Setting', '#', 'settings', 1, GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Division',
	(select id from MasterData.Menu where Name = 'App Setting'),
	'/app-setting/division',
	'briefcase',
	1,
	GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Role',
	(select id from MasterData.Menu where Name = 'App Setting'),
	'/app-setting/role',
	'sliders',
	1,
	GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Role Access',
	(select id from MasterData.Menu where Name = 'App Setting'),
	'/app-setting/role-access',
	'unlock',
	1,
	GETDATE()
);
go

insert into MasterData.Menu(Name, ParentId, Path, FeatherIconTag, CreatedBy, CreatedDate) values (
	'Staff',
	(select id from MasterData.Menu where Name = 'App Setting'),
	'/app-setting/staff',
	'users',
	1,
	GETDATE()
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Dashboard')
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'App Setting')
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Division' and ParentId = (select id from MasterData.Menu where Name = 'App Setting'))
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Role' and ParentId = (select id from MasterData.Menu where Name = 'App Setting'))
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Role Access' and ParentId = (select id from MasterData.Menu where Name = 'App Setting'))
);
go

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Staff' and ParentId = (select id from MasterData.Menu where Name = 'App Setting'))
);
go

--insert into MasterData.AccessPermission (RoleId, MenuId) values (
--	(select Id from MasterData.Role where Name = 'Administrator'),
--	(select Id from MasterData.Menu where Name = 'Procurement')
--);
--go

--insert into MasterData.AccessPermission (RoleId, MenuId) values (
--	(select Id from MasterData.Role where Name = 'Administrator'),
--	(select Id from MasterData.Menu where Name = 'Request' and ParentId = (select id from MasterData.Menu where Name = 'Procurement'))
--);
--go

--insert into MasterData.AccessPermission (RoleId, MenuId) values (
--	(select Id from MasterData.Role where Name = 'Administrator'),
--	(select Id from MasterData.Menu where Name = 'Approval' and ParentId = (select id from MasterData.Menu where Name = 'Procurement'))
--);
--go

--insert into MasterData.AccessPermission (RoleId, MenuId) values (
--	(select Id from MasterData.Role where Name = 'Administrator'),
--	(select Id from MasterData.Menu where Name = 'Purchase')
--);
--go

--insert into MasterData.AccessPermission (RoleId, MenuId) values (
--	(select Id from MasterData.Role where Name = 'Administrator'),
--	(select Id from MasterData.Menu where Name = 'Request' and ParentId = (select id from MasterData.Menu where Name = 'Purchase'))
--);
--go

--insert into MasterData.AccessPermission (RoleId, MenuId) values (
--	(select Id from MasterData.Role where Name = 'Administrator'),
--	(select Id from MasterData.Menu where Name = 'Approval' and ParentId = (select id from MasterData.Menu where Name = 'Purchase'))
--);
--go

--insert into MasterData.AccessPermission (RoleId, MenuId) values (
--	(select Id from MasterData.Role where Name = 'Administrator'),
--	(select Id from MasterData.Menu where Name = 'Order' and ParentId = (select id from MasterData.Menu where Name = 'Purchase'))
--);
--go
