USE [LibraryCrypt]
GO
/****** Object:  Table [dbo].[Book]    Script Date: 29/10/2022 11:37:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Book](
	[Id] [varchar](100) NOT NULL,
	[Name] [nvarchar](200) NOT NULL,
	[Author] [nvarchar](200) NOT NULL,
	[Quantity] [varchar](50) NOT NULL,
	[KindOfBook] [nvarchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LibStaff]    Script Date: 29/10/2022 11:37:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LibStaff](
	[Id] [varchar](100) NOT NULL,
	[FullName] [nvarchar](200) NOT NULL,
	[Gender] [nvarchar](50) NOT NULL,
	[Dob] [varchar](50) NOT NULL,
	[Address] [nvarchar](200) NOT NULL,
	[License] [varchar](100) NOT NULL,
	[PhoneNumber] [varchar](100) NOT NULL,
	[Gmail] [nvarchar](200) NULL,
	[StartWorkDate] [varchar](50) NOT NULL,
	[Position] [nvarchar](100) NOT NULL,
	[BasicSalary] [nvarchar](50) NULL,
	[SalaryBonus] [nvarchar](50) NULL,
	[Penalty] [nvarchar](50) NULL,
	[ActualSalary] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reader]    Script Date: 29/10/2022 11:37:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reader](
	[Id] [varchar](100) NOT NULL,
	[FullName] [nvarchar](200) NOT NULL,
	[Gender] [nvarchar](50) NOT NULL,
	[Dob] [varchar](50) NOT NULL,
	[Address] [nvarchar](200) NOT NULL,
	[License] [varchar](100) NULL,
	[PhoneNumber] [varchar](100) NOT NULL,
	[Gmail] [nvarchar](200) NULL,
	[KindOfReader] [nvarchar](200) NULL,
	[StartDate] [date] NOT NULL,
	[endDate] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RentalInformation]    Script Date: 29/10/2022 11:37:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RentalInformation](
	[Id] [varchar](100) NOT NULL,
	[ReaderId] [varchar](100) NOT NULL,
	[BookId] [varchar](100) NOT NULL,
	[LibStaffId] [varchar](100) NOT NULL,
	[BookBorrowDate] [date] NOT NULL,
	[BookReturnDate] [date] NOT NULL,
	[Note] [nvarchar](3000) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[view_get_infor_borrow]    Script Date: 29/10/2022 11:37:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create   view [dbo].[view_get_infor_borrow] as (
	Select 	ri.Id ID,
			r.Id ReaderID,
			r.FullName ReaderFullName,
			r.License ReaderLicense,
			r.PhoneNumber ReaderPhoneNumber,
			r.Gmail ReaderGmail,
			b.Id BookID,
			b.Name BookName,
			b.Author BookAuthor,
			ls.Id LibID,
			ls.FullName LibFullName,
			ls.License LibLicense,
			ls.PhoneNumber LibPhoneNumber,
			ls.Gmail LibGmail
	from RentalInformation ri,Reader r,Book b,LibStaff ls
	where ri.ReaderId=r.Id and ri.BookId=b.Id and ri.LibStaffId=ls.Id 
	and (ri.BookReturnDate <  GETDATE() OR r.endDate<GETDATE()) 
)
GO
/****** Object:  Table [dbo].[Account]    Script Date: 29/10/2022 11:37:46 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[UserName] [varchar](100) NOT NULL,
	[Password] [varchar](700) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[RentalInformation]  WITH CHECK ADD  CONSTRAINT [fk_ri_book] FOREIGN KEY([BookId])
REFERENCES [dbo].[Book] ([Id])
GO
ALTER TABLE [dbo].[RentalInformation] CHECK CONSTRAINT [fk_ri_book]
GO
ALTER TABLE [dbo].[RentalInformation]  WITH CHECK ADD  CONSTRAINT [fk_ri_libstaff] FOREIGN KEY([LibStaffId])
REFERENCES [dbo].[LibStaff] ([Id])
GO
ALTER TABLE [dbo].[RentalInformation] CHECK CONSTRAINT [fk_ri_libstaff]
GO
ALTER TABLE [dbo].[RentalInformation]  WITH CHECK ADD  CONSTRAINT [fk_ri_reader] FOREIGN KEY([ReaderId])
REFERENCES [dbo].[Reader] ([Id])
GO
ALTER TABLE [dbo].[RentalInformation] CHECK CONSTRAINT [fk_ri_reader]
GO
