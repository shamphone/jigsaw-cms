-- =============================================
-- Export file for newCoolink
-- Create date: 2010-8-9, 14:00:00
-- =============================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[NODE_QUOTA]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[NODE_QUOTA](
	[PRINCIPAL_ID] [varchar](32) NOT NULL,
	[PRINCIPAL_TYPE] [numeric](38, 0) NOT NULL,
	[NODE_ID] [varchar](32) NOT NULL,
	[UNIT] [varchar](16) NULL,
	[QUOTA] [numeric](38, 0) NULL
) ON [PRIMARY]
END
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[NODE_QUOTA]') AND name = N'NODE_NODE_QUOTA_PRINCIPAL')
CREATE NONCLUSTERED INDEX [NODE_NODE_QUOTA_PRINCIPAL] ON [dbo].[NODE_QUOTA] 
(
	[PRINCIPAL_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[NODE_QUOTA]') AND name = N'NODE_QUOTA_NODE_ID')
CREATE NONCLUSTERED INDEX [NODE_QUOTA_NODE_ID] ON [dbo].[NODE_QUOTA] 
(
	[NODE_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[NODE_QUOTA]') AND name = N'NODE_QUOTA_UNIT')
CREATE NONCLUSTERED INDEX [NODE_QUOTA_UNIT] ON [dbo].[NODE_QUOTA] 
(
	[UNIT] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'授权者ID组/用户/机构的ID
' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_QUOTA', @level2type=N'COLUMN', @level2name=N'PRINCIPAL_ID'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项类型，1：用户，2：机构；3：组
授权者类型
' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_QUOTA', @level2type=N'COLUMN', @level2name=N'PRINCIPAL_TYPE'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'节点ID' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_QUOTA', @level2type=N'COLUMN', @level2name=N'NODE_ID'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'单位' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_QUOTA', @level2type=N'COLUMN', @level2name=N'UNIT'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'配额' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_QUOTA', @level2type=N'COLUMN', @level2name=N'QUOTA'

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE trigger [TR_NodeDefinition_INSERT]
on [dbo].[NODE_DEFINITION]  
after insert 
AS
BEGIN 
declare @pkid varchar(128);
select @pkid=pkid from inserted;
INSERT INTO node_quota  (principal_id, principal_type, node_id, unit, quota)
 values ('100', 0, @pkid, 'long-term', 5);
END
GO

