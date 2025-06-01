use sipro;
go

insert into MasterData.Division(Name, CreatedBy, CreatedDate) values('Div. General Affair', 1, GETDATE());
go

insert into MasterData.Role(Name, CreatedBy, CreatedDate) values ('Administrator', 1, GETDATE());
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
	(select Id from MasterData.Division where Name = 'Div. General Affair'),
	(select Id from MasterData.Role where Name = 'Administrator'),
	1,
	GETDATE()
);
go

insert into MasterData.Menu(Name, Path, CreatedBy, CreatedDate) values (
	'Procurement', '/procurement', 1, GETDATE()
)

insert into MasterData.Menu(Name, ParentId, Path, CreatedBy, CreatedDate) values (
	'Request', 
	(select id from MasterData.Menu where Name = 'Procurement'),
	'/procurement', 
	1, 
	GETDATE()
)

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Procurement')
)

insert into MasterData.AccessPermission (RoleId, MenuId) values (
	(select Id from MasterData.Role where Name = 'Administrator'),
	(select Id from MasterData.Menu where Name = 'Request' and ParentId = (select id from MasterData.Menu where Name = 'Procurement'))
)