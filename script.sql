CREATE DATABASE SWE102
USE [SWE102]
GO
/****** Object:  Table [dbo].[notifications]    Script Date: 03/24/2018 21:02:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[notifications](
	[user_id] [int] NULL,
	[ntype] [varchar](200) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[users]    Script Date: 03/24/2018 21:02:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](200) NULL,
	[avatar] [varchar](200) NULL,
	[email] [varchar](200) NULL,
	[phone] [varchar](15) NULL,
	[brand] [varchar](200) NULL,
	[plate] [varchar](200) NULL,
	[password] [varchar](200) NULL,
	[create_at] [datetime] NULL,
	[updated_at] [datetime] NULL,
 CONSTRAINT [pk_users_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[trips]    Script Date: 03/24/2018 21:02:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[trips](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[driver_id] [int] NULL,
	[passenger_id] [int] NULL,
	[type] [varchar](200) NULL,
	[from] [varchar](200) NULL,
	[to] [varchar](200) NULL,
	[time] [datetime] NULL,
	[price] [float] NULL,
	[description] [text] NULL,
	[status] [int] NULL,
	[create_at] [datetime] NULL,
	[update_at] [datetime] NULL,
 CONSTRAINT [pk_trips_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[bookings]    Script Date: 03/24/2018 21:02:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[bookings](
	[trip_id] [int] NULL,
	[booker_id] [int] NULL,
	[created_at] [datetime] NULL,
	[update_at] [datetime] NULL,
	[pickup] [varchar](200) NULL,
	[dropoff] [varchar](200) NULL,
	[id] [int] IDENTITY(1,1) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  ForeignKey [FK__bookings__booker__1920BF5C]    Script Date: 03/24/2018 21:02:54 ******/
ALTER TABLE [dbo].[bookings]  WITH CHECK ADD FOREIGN KEY([booker_id])
REFERENCES [dbo].[users] ([id])
GO
/****** Object:  ForeignKey [FK__bookings__trip_i__182C9B23]    Script Date: 03/24/2018 21:02:54 ******/
ALTER TABLE [dbo].[bookings]  WITH CHECK ADD FOREIGN KEY([trip_id])
REFERENCES [dbo].[trips] ([id])
GO
/****** Object:  ForeignKey [FK__trips__driver_id__15502E78]    Script Date: 03/24/2018 21:02:54 ******/
ALTER TABLE [dbo].[trips]  WITH CHECK ADD FOREIGN KEY([driver_id])
REFERENCES [dbo].[users] ([id])
GO
/****** Object:  ForeignKey [FK__trips__passenger__164452B1]    Script Date: 03/24/2018 21:02:54 ******/
ALTER TABLE [dbo].[trips]  WITH CHECK ADD FOREIGN KEY([passenger_id])
REFERENCES [dbo].[users] ([id])
GO
