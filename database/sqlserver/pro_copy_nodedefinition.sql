-- =============================================
-- Export file for newCoolink
-- Create date: 2010-8-9, 14:00:00
-- =============================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[NODEDEF_PROCESSDEF]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[NODEDEF_PROCESSDEF](
	[NODE_DEFINITION] [varchar](128) NOT NULL,
	[PROCESS_DEFINITION] [varchar](128) NULL
) ON [PRIMARY]
END
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'大纲的pkid' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODEDEF_PROCESSDEF', @level2type=N'COLUMN', @level2name=N'NODE_DEFINITION'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流的pkid' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODEDEF_PROCESSDEF', @level2type=N'COLUMN', @level2name=N'PROCESS_DEFINITION'

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[NODE_DEFINITION_AUTHORIZATION]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[NODE_DEFINITION_AUTHORIZATION](
	[PRINCIPAL_ID] [varchar](32) NOT NULL,
	[PRINCIPAL_TYPE] [numeric](38, 0) NOT NULL,
	[DEFINITION_ID] [varchar](128) NOT NULL,
	[ACTION] [varchar](16) NOT NULL
) ON [PRIMARY]
END
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[NODE_DEFINITION_AUTHORIZATION]') AND name = N'NODE_DEF_AUTHORIZATION_ACTION')
CREATE NONCLUSTERED INDEX [NODE_DEF_AUTHORIZATION_ACTION] ON [dbo].[NODE_DEFINITION_AUTHORIZATION] 
(
	[ACTION] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[NODE_DEFINITION_AUTHORIZATION]') AND name = N'NODE_DEF_AUTHORIZATION_DEF_ID')
CREATE NONCLUSTERED INDEX [NODE_DEF_AUTHORIZATION_DEF_ID] ON [dbo].[NODE_DEFINITION_AUTHORIZATION] 
(
	[DEFINITION_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[NODE_DEFINITION_AUTHORIZATION]') AND name = N'NODE_DEF_AUTHORIZATION_PRINC')
CREATE NONCLUSTERED INDEX [NODE_DEF_AUTHORIZATION_PRINC] ON [dbo].[NODE_DEFINITION_AUTHORIZATION] 
(
	[PRINCIPAL_ID] ASC
)WITH (IGNORE_DUP_KEY = OFF) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'授权者ID组/用户/机构的ID' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_DEFINITION_AUTHORIZATION', @level2type=N'COLUMN', @level2name=N'PRINCIPAL_ID'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'项类型，1：用户，2：机构；3：组授权者类型' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_DEFINITION_AUTHORIZATION', @level2type=N'COLUMN', @level2name=N'PRINCIPAL_TYPE'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'大纲ID' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_DEFINITION_AUTHORIZATION', @level2type=N'COLUMN', @level2name=N'DEFINITION_ID'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'操作manage：管理read：读create：新增modify：修改delete：删除' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_DEFINITION_AUTHORIZATION', @level2type=N'COLUMN', @level2name=N'ACTION'

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[NODE_VIEW]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[NODE_VIEW](
	[NODEFID] [varchar](128) NOT NULL,
	[PRODEFID] [varchar](128) NOT NULL,
	[WIDTH] [numeric](38, 0) NULL,
	[ORDERNO] [numeric](38, 0) NULL
) ON [PRIMARY]
END
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'指定的某个大纲的主键' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_VIEW', @level2type=N'COLUMN', @level2name=N'NODEFID'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'指定的某个大纲定义下属性定义的主键' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_VIEW', @level2type=N'COLUMN', @level2name=N'PRODEFID'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'该列显示需要的宽度' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_VIEW', @level2type=N'COLUMN', @level2name=N'WIDTH'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'该列显示的序号' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_VIEW', @level2type=N'COLUMN', @level2name=N'ORDERNO'

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[pro_copy_NodeDefinition]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE PROCEDURE [dbo].[pro_copy_NodeDefinition]
(
@sourceID varchar(256),
@destID varchar(256)
)
AS
declare @sql varchar(max);
BEGIN
	SET NOCOUNT ON;
set @sql = ''Insert Into nodedef_processdef (node_definition,process_definition) Select ''''''+cast(@destID as varchar)+'''''' ,process_definition From nodedef_processdef x Where x.node_definition ''+''=''''''+@sourceID+'''''''';
exec (@sql);
set @sql =''Insert Into node_definition_authorization (principal_id,principal_type,definition_id,action) Select principal_id,principal_type,''''''+cast(@destID as varchar)+'''''',action From node_definition_authorization x Where x.definition_id ''+''=''''''+@sourceID+'''''''';
exec (@sql);
set @sql = ''Insert Into node_view (nodefid, prodefid, width, orderno) Select ''''''+cast(@destID as varchar)+'''''',prodefid,width,orderno From node_view x Where x.nodefid ''+''=''''''+@sourceID+'''''''';
exec (@sql);
END
' 
END
GO

