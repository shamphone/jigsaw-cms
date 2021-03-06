-- =============================================
-- Export file for newCoolink
-- Create date: 2010-8-9, 14:00:00
-- =============================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[NODE_DEFINITION_SUPER]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[NODE_DEFINITION_SUPER](
	[DEFINITION_ID] [varchar](128) NULL,
	[SUPER_ID] [varchar](128) NULL,
	[TYPE] [char](1) NOT NULL CONSTRAINT [DF_NODE_DEFINITION_SUPER_TYPE]  DEFAULT ((0))
) ON [PRIMARY]
END
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容分类ID' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_DEFINITION_SUPER', @level2type=N'COLUMN', @level2name=N'DEFINITION_ID'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'父分类ID' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_DEFINITION_SUPER', @level2type=N'COLUMN', @level2name=N'SUPER_ID'

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否是主父分类.1:主父分类；0：辅父分类' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'NODE_DEFINITION_SUPER', @level2type=N'COLUMN', @level2name=N'TYPE'



SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[pro_insert_NodeDefinition]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
alter PROCEDURE [dbo].[pro_insert_NodeDefinition]
(
@PKID varchar(128), 
@DESCRIPTION varchar(512),
@IS_SYSTEM Char(1), 
@DELETE_MARK Char(1),
@CREATE_TIME DateTime,
@Name varchar(512),
@SUPER_ID varchar(128)
)
AS

BEGIN
Insert Into node_definition (pkid,delete_mark,description,is_system,create_time,Name) 
       Values (@PKID, @DELETE_MARK,@DESCRIPTION,@IS_SYSTEM, @CREATE_TIME,@NAME);
Insert Into node_definition_super (definition_id,super_id,Type) 
       Values (@PKID,@SUPER_ID,''1'');
END
' 
END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[pro_update_NodeDefinition]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
alter PROCEDURE [dbo].[pro_update_NodeDefinition]
( @pro_PKID        VARCHAR(128),
  @pro_DESCRIPTION VARCHAR(512),
  @pro_IS_SYSTEM   CHAR(1),
  @pro_DELETE_MARK CHAR(1),
  @pro_name        Varchar(512),
  @pro_SUPER_ID    VARCHAR(128))
AS
BEGIN
    update NODE_DEFINITION 
     set DESCRIPTION = @pro_DESCRIPTION,
         IS_SYSTEM   = @pro_IS_SYSTEM,
         DELETE_MARK = @pro_DELETE_MARK,
         name        = @pro_name
   where PKID = @pro_PKID;

  update NODE_DEFINITION_SUPER 
     set SUPER_ID = @pro_SUPER_ID
   where DEFINITION_ID = @pro_PKID
     And Type = ''1'';
END
' 
END
GO

