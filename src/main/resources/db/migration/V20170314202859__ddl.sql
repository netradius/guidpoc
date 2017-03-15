CREATE TABLE dbo.record (
	id   [uniqueidentifier] NOT NULL DEFAULT (newsequentialid()),
	name [varchar](25)      NOT NULL,
	CONSTRAINT pk_record_id PRIMARY KEY CLUSTERED (id)
)

