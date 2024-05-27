USE master
GO
CREATE DATABASE EduSys
GO
USE EduSys
GO
IF OBJECT_ID('NhanVien') IS NOT NULL
DROP TABLE NhanVien

GO
IF OBJECT_ID('ChuyenDe') IS NOT NULL
DROP TABLE ChuyenDe

GO
IF OBJECT_ID('KhoaHoc') IS NOT NULL
DROP TABLE KhoaHoc

GO
IF OBJECT_ID('NguoiHoc') IS NOT NULL
DROP TABLE NguoiHoc

GO
IF OBJECT_ID('HocVien') IS NOT NULL
DROP TABLE HocVien

GO
CREATE TABLE ChuyenDe(
	MaCD varchar(5) NOT NULL,
	TenCD nvarchar(50) NOT NULL,
	HocPhi float NOT NULL,
	ThoiLuong int NOT NULL,
	Hinh varchar(50) NOT NULL,
	MoTa nvarchar(255) NOT NULL,
	CONSTRAINT PK_ChuyenDe PRIMARY KEY CLUSTERED (MaCD)
)

GO
CREATE TABLE HocVien(
	MaHV int IDENTITY(1,1) NOT NULL,
	MaKH int NOT NULL,
	MaNH varchar(7) NOT NULL,
	Diem float NOT NULL,
	CONSTRAINT PK_HocVien PRIMARY KEY CLUSTERED (MaHV)
)

GO
CREATE TABLE KhoaHoc(
	MaKH int IDENTITY(1,1) NOT NULL,
	MaCD varchar(5) NOT NULL,
	HocPhi float NOT NULL,
	ThoiLuong int NOT NULL,
	NgayKG date NOT NULL,
	GhiChu nvarchar(50) NULL,
	MaNV varchar(50) NOT NULL,
	NgayTao date NOT NULL,
	CONSTRAINT PK_KhoaHoc PRIMARY KEY CLUSTERED (MaKH),
)

GO
CREATE TABLE NguoiHoc(
	MaNH varchar(7) NOT NULL,
	HoTen nvarchar(50) NOT NULL,
	NgaySinh date NOT NULL,
	GioiTinh bit NOT NULL,
	DienThoai varchar(50) NOT NULL,
	Email varchar(50) NOT NULL,
	GhiChu nvarchar(255) NULL,
	MaNV varchar(50) NOT NULL,
	NgayDK date NOT NULL,
	Hinh varchar(50) NOT NULL,
	CONSTRAINT PK_NguoiHoc PRIMARY KEY CLUSTERED (MaNH)
)

GO
CREATE TABLE NhanVien(
	MaNV varchar(50) NOT NULL,
	MatKhau varchar(50) NOT NULL,
	HoTen nvarchar(50) NOT NULL,
	Email varchar(50) NOT NULL,
	VaiTro bit NOT NULL,
	CONSTRAINT PK_NhanVien PRIMARY KEY CLUSTERED (MaNV)
)

GO
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'ADR01', N'Lập trình Android cơ bản', 250, 90, N'ADRBS.jpg', N'ADR01 - Lập trình Android cơ bản')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'ADR02', N'Lập trình Android nâng cao', 300, 90, N'ADRAV.jpg', N'ADR02 - Lập trình Android nâng cao')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'JAV01', N'Lập trình Java cơ bản', 250, 90, N'JABSC.jpg', N'JAV01 - Lập trình Java cơ bản')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'JAV02', N'Lập trình Java nâng cao', 300, 90, N'JAADV.jpg', N'JAV02 - Lập trình Java nâng cao')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'JAV03', N'Lập trình mạng với Java', 200, 70, N'JANEW.jpg', N'JAV03 - Lập trình mạng với Java')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'JAV04', N'Lập trình desktop với Swing', 200, 70, N'JASWI.jpg', N'JAV04 - Lập trình desktop với Swing')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO01', N'Dự án với công nghệ MS.NET MVC', 300, 90, N'MSMVC.jpg', N'PRO01 - Dự án với công nghệ MS.NET MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO02', N'Dự án với công nghệ Spring MVC', 300, 90, N'SPMVC.jpg', N'PRO02 - Dự án với công nghệ Spring MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO03', N'Dự án với công nghệ Servlet/JSP', 300, 90, N'JAJSP.jpg', N'PRO03 - Dự án với công nghệ Servlet/JSP')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO04', N'Dự án với AngularJS & WebAPI', 300, 90, N'AGWEB.jpg', N'PRO04 - Dự án với AngularJS & WebAPI')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO05', N'Dự án với Swing & JDBC', 300, 90, N'JASJD.jpg', N'PRO05 - Dự án với Swing & JDBC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'PRO06', N'Dự án với WindowForm', 300, 90, N'JASWI.jpg', N'PRO06 - Dự án với WindowForm')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'RDB01', N'Cơ sở dữ liệu SQL Server', 100, 50, N'SQLSV.jpg', N'RDB01 - Cơ sở dữ liệu SQL Server')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'RDB02', N'Lập trình JDBC', 150, 60, N'JASJD.jpg', N'RDB02 - Lập trình JDBC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'RDB03', N'Lập trình cơ sở dữ liệu Hibernate', 250, 80, N'HIBER.jpg', N'RDB03 - Lập trình cơ sở dữ liệu Hibernate')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'SER01', N'Lập trình web với Servlet/JSP', 350, 100, N'JAJSP.jpg', N'SER01 - Lập trình web với Servlet/JSP')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'SER02', N'Lập trình Spring MVC', 400, 110, N'SPMVC.jpg', N'SER02 - Lập trình Spring MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'SER03', N'Lập trình MS.NET MVC', 400, 110, N'MSMVC.jpg', N'SER03 - Lập trình MS.NET MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'SER04', N'Xây dựng Web API với Spring MVC & ASP.NET MVC', 200, 70, N'Subject.png', N'SER04 - Xây dựng Web API với Spring MVC & ASP.NET MVC')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'WEB01', N'Thiết kế web với HTML và CSS', 200, 70, N'HTCSS.jpg', N'WEB01 - Thiết kế web với HTML và CSS')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'WEB02', N'Thiết kế web với Bootstrap', 60, 40, N'WEBST.jpg', N'WEB02 - Thiết kế web với Bootstrap')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'WEB03', N'Lập trình front-end với JavaScript và jQuery', 150, 60, N'JSJQE.jpg', N'WEB03 - Lập trình front-end với JavaScript và jQuery')
INSERT [dbo].[ChuyenDe] ([MaCD], [TenCD], [HocPhi], [ThoiLuong], [Hinh], [MoTa]) VALUES (N'WEB04', N'Lập trình AngularJS', 250, 80, N'AGLJS.jpg', N'WEB04 - Lập trình AngularJS')
GO
SET IDENTITY_INSERT [dbo].[HocVien] ON 
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (1, 1002, N'PS18293', 5)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (4, 1003, N'PS19771', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (5, 1003, N'PS18867', 2)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (6, 1003, N'PS19930', 1)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (7, 1001, N'PS18293', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (8, 1001, N'PS18037', 9)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (9, 1001, N'PS18867', 3)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (13, 1001, N'PS19983', 6)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (14, 1001, N'PS18988', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (15, 1001, N'PS18031', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (16, 1001, N'PS17046', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (17, 1001, N'PS18080', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (18, 1001, N'PS19088', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (19, 1001, N'PS18096', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (20, 1001, N'PS19104', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (21, 1001, N'PS19120', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (22, 1001, N'PS17130', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (23, 1003, N'PS19983', 5)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (24, 1003, N'PS18096', 9)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (25, 1003, N'PS19120', 6)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (26, 1003, N'PS17046', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (27, 1005, N'PS18293', 5)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (28, 1005, N'PS19771', 9)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (29, 1005, N'PS18037', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (30, 1004, N'PS18293', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (31, 1004, N'PS18031', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (32, 1004, N'PS19979', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (33, 1006, N'PS18293', 5)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (34, 1006, N'PS18037', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (35, 1006, N'PS19771', 9)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (36, 1006, N'PS18867', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (37, 1006, N'PS19930', 5)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (38, 1007, N'PS19930', 5)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (39, 1007, N'PS19979', 9)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (40, 1007, N'PS19983', 4)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (41, 1007, N'PS18988', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (42, 1007, N'PS18031', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (43, 1007, N'PS17046', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (44, 1007, N'PS18080', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (45, 1008, N'PS18988', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (46, 1008, N'PS18031', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (47, 1008, N'PS17046', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (48, 1008, N'PS18080', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (49, 1008, N'PS19088', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (50, 1008, N'PS18096', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (51, 1008, N'PS19104', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (52, 1008, N'PS19120', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (54, 1010, N'PS18134', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (55, 1010, N'PS18172', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (56, 1010, N'PS17202', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (57, 1010, N'PS19203', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (58, 1010, N'PS19205', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (59, 1010, N'PS19222', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (60, 1010, N'PS17230', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (61, 1010, N'PS18233', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (62, 1010, N'PS18251', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (63, 1010, N'PS19304', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (64, 1010, N'PS19306', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (65, 1010, N'PS17308', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (66, 1010, N'PS19325', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (67, 1010, N'PS17346', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (68, 1010, N'PS19383', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (69, 1012, N'PS18425', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (70, 1012, N'PS19454', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (71, 1012, N'PS19472', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (72, 1012, N'PS18488', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (73, 1012, N'PS18530', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (74, 1012, N'PS18553', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (75, 1012, N'PS18561', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (76, 1012, N'PS17596', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (77, 1012, N'PS18603', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (78, 1012, N'PS17610', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (79, 1012, N'PS17614', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (80, 1012, N'PS19618', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (81, 1012, N'PS19638', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (82, 1012, N'PS18640', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (83, 1012, N'PS18662', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (84, 1012, N'PS18674', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (85, 1005, N'PS17230', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (86, 1005, N'PS18233', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (87, 1005, N'PS18251', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (88, 1005, N'PS19304', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (89, 1005, N'PS19306', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (90, 1005, N'PS17308', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (91, 1005, N'PS19325', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (92, 1005, N'PS17346', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (93, 1005, N'PS19383', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (94, 1005, N'PS19389', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (95, 1005, N'PS19410', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (96, 1005, N'PS19411', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (97, 1013, N'PS18293', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (98, 1013, N'PS18037', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (99, 1013, N'PS19771', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (100, 1013, N'PS18867', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (101, 1013, N'PS19930', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (102, 1013, N'PS19979', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (103, 1013, N'PS19983', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (104, 1013, N'PS18988', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (105, 1013, N'PS18031', 0)
GO
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (106, 1013, N'PS17046', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (107, 1013, N'PS18080', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (108, 1013, N'PS19088', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (109, 1014, N'PS18037', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (110, 1014, N'PS19771', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (111, 1014, N'PS18867', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (112, 1014, N'PS19930', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (113, 1014, N'PS19979', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (114, 1014, N'PS19983', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (115, 1014, N'PS18988', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (116, 1014, N'PS18031', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (117, 1014, N'PS17046', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (118, 1014, N'PS18080', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (119, 1015, N'PS19088', 5)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (120, 1015, N'PS18096', 6)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (121, 1015, N'PS19104', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (122, 1015, N'PS19120', 2)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (123, 1015, N'PS17130', 9)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (124, 1015, N'PS18134', 10)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (125, 1015, N'PS18172', 10)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (126, 1015, N'PS17202', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (127, 1006, N'PS18488', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (128, 1006, N'PS18553', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (129, 1006, N'PS17596', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (130, 1006, N'PS19104', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (131, 1006, N'PS19411', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (132, 1006, N'PS18530', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (133, 1014, N'PS19088', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (134, 1014, N'PS19104', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (135, 1008, N'PS17130', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (136, 1016, N'PS17046', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (137, 1016, N'PS17130', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (138, 1016, N'PS17202', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (139, 1016, N'PS17230', 6)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (140, 1016, N'PS17308', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (141, 1016, N'PS17346', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (142, 1016, N'PS18293', 9)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (143, 1016, N'PS18037', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (144, 1017, N'PS18561', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (145, 1017, N'PS18662', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (146, 1017, N'PS19325', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (147, 1017, N'PS19618', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (148, 1018, N'PS19383', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (149, 1018, N'PS19454', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (150, 1018, N'PS18603', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (151, 1018, N'PS18488', 6)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (152, 1018, N'PS18134', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (153, 1019, N'PS18096', 9)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (154, 1019, N'PS17610', 7)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (155, 1019, N'PS18640', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (156, 1019, N'PS19771', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (157, 1019, N'PS19983', 8)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (158, 1019, N'PS19979', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (159, 1009, N'PS19304', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (160, 1009, N'PS19389', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (161, 1009, N'PS19638', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (162, 1009, N'PS18640', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (163, 1009, N'PS19325', 0)
INSERT [dbo].[HocVien] ([MaHV], [MaKH], [MaNH], [Diem]) VALUES (164, 1009, N'PS19771', 0)
SET IDENTITY_INSERT [dbo].[HocVien] OFF
GO
SET IDENTITY_INSERT [dbo].[KhoaHoc] ON 
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1001, N'PRO02', 300, 90, CAST(N'2020-01-10' AS Date), N'Dự án với công nghệ Spring MVC', N'DuyPlus', CAST(N'2019-12-31' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1002, N'JAV04', 300, 90, CAST(N'2020-02-11' AS Date), N'Lập trình desktop với Swing', N'DuyPlus', CAST(N'2019-12-31' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1003, N'JAV04', 100, 50, CAST(N'2020-03-12' AS Date), N'Lập trình desktop với Swing', N'DuyPlus', CAST(N'2019-12-31' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1004, N'JAV04', 250, 80, CAST(N'2020-04-01' AS Date), N'Lập trình desktop với Swing', N'DuyPlus', CAST(N'2019-12-31' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1005, N'PRO04', 300, 90, CAST(N'2020-03-08' AS Date), N'Dự án với AngularJS & WebAPI', N'DuyPlus', CAST(N'2019-03-27' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1006, N'JAV01', 300, 90, CAST(N'2021-04-12' AS Date), N'Lập trình Java cơ bản', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1007, N'JAV02', 300, 90, CAST(N'2021-04-12' AS Date), N'Lập trình Java nâng cao', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1008, N'JAV03', 200, 70, CAST(N'2021-04-12' AS Date), N'Lập trình mạng với Java', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1009, N'JAV04', 200, 70, CAST(N'2021-04-12' AS Date), N'Lập trình ứng dụng Desktop với Java', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1010, N'PRO01', 300, 90, CAST(N'2021-04-12' AS Date), N'Lập trình .NET MVC', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1011, N'PRO02', 300, 90, CAST(N'2021-04-12' AS Date), N'Lập trình Spring MVC', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1012, N'PRO03', 300, 90, CAST(N'2021-04-12' AS Date), N'Làm dự án với Servlet và JSP', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1013, N'PRO04', 300, 90, CAST(N'2021-04-12' AS Date), N'Làm dự án với REST API và AngularJS', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1014, N'JAV01', 300, 90, CAST(N'2021-04-10' AS Date), N'Lập trình Java cơ bản', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1015, N'JAV01', 250, 90, CAST(N'2021-04-08' AS Date), N'Lập trình Java cơ bản', N'DuyPlus', CAST(N'2020-05-02' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1016, N'ADR01', 250, 90, CAST(N'2021-11-14' AS Date), N'Lập trình Android cơ bản', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1017, N'ADR01', 250, 90, CAST(N'2021-12-23' AS Date), N'Lập trình Android cơ bản', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1018, N'ADR02', 300, 90, CAST(N'2021-11-14' AS Date), N'Lập trình Android nâng cao', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1019, N'ADR02', 300, 90, CAST(N'2021-12-17' AS Date), N'Lập trình Android nâng cao', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1020, N'WEB04', 250, 80, CAST(N'2021-11-14' AS Date), N'Lập trình AngularJS', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1021, N'WEB03', 150, 60, CAST(N'2021-11-14' AS Date), N'Lập trình front-end với JavaScript và jQuery', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1022, N'WEB02', 60, 40, CAST(N'2021-11-14' AS Date), N'Thiết kế web với Bootstrap', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1023, N'WEB01', 200, 70, CAST(N'2021-11-14' AS Date), N'Thiết kế web với HTML và CSS', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1024, N'SER04', 200, 70, CAST(N'2021-11-14' AS Date), N'Xây dựng Web API với Spring MVC & ASP.NET MVC', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1025, N'SER03', 400, 110, CAST(N'2021-11-14' AS Date), N'Lập trình MS.NET MVC', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1026, N'SER02', 400, 110, CAST(N'2021-11-14' AS Date), N'Lập trình Spring MVC', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1027, N'SER01', 350, 100, CAST(N'2021-11-14' AS Date), N'Lập trình web với Servlet/JSP', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1028, N'RDB03', 250, 80, CAST(N'2021-11-14' AS Date), N'Lập trình cơ sở dữ liệu Hibernate', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1029, N'RDB02', 150, 60, CAST(N'2021-11-14' AS Date), N'Lập trình JDBC', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1030, N'RDB01', 100, 50, CAST(N'2021-11-14' AS Date), N'Cơ sở dữ liệu SQL Server', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1031, N'PRO06', 300, 90, CAST(N'2021-11-14' AS Date), N'Dự án với WindowForm', N'DuyPlus', CAST(N'2021-10-15' AS Date))
INSERT [dbo].[KhoaHoc] ([MaKH], [MaCD], [HocPhi], [ThoiLuong], [NgayKG], [GhiChu], [MaNV], [NgayTao]) VALUES (1032, N'PRO05', 300, 90, CAST(N'2021-11-14' AS Date), N'Dự án với Swing & JDBC', N'DuyPlus', CAST(N'2021-10-15' AS Date))
SET IDENTITY_INSERT [dbo].[KhoaHoc] OFF
GO
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS17046', N'NGUYỄN CAO PHƯỚC', CAST(N'2003-01-28' AS Date), 1, N'0977117727', N'PS17046@fpt.edu.vn', N'0977117727 - NGUYỄN CAO PHƯỚC', N'DuyPlus', CAST(N'2021-01-28' AS Date), N'anh13.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS17130', N'ĐẶNG BẢO VIỆT', CAST(N'2003-02-14' AS Date), 1, N'0917749344', N'PS17130@fpt.edu.vn', N'0917749344 - ĐẶNG BẢO VIỆT', N'DuyPlus', CAST(N'2021-02-14' AS Date), N'anh7.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS17202', N'PHAN QUỐC QUI', CAST(N'1998-02-04' AS Date), 1, N'0930649274', N'PS17202@fpt.edu.vn', N'0930649274 - PHAN QUỐC QUI', N'DuyPlus', CAST(N'2020-02-04' AS Date), N'anh9.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS17230', N'TRẦN THỊ ÁI NHI', CAST(N'2000-10-14' AS Date), 0, N'0983469892', N'PS17230@fpt.edu.vn', N'0983469892 - TRẦN THỊ ÁI NHI', N'DuyPlus', CAST(N'2019-10-14' AS Date), N'anh2.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS17308', N'TRẦN VIẾT HÙNG', CAST(N'1999-02-24' AS Date), 1, N'0916050164', N'PS17308@fpt.edu.vn', N'0916050164 - TRẦN VIẾT HÙNG', N'DuyPlus', CAST(N'2021-02-24' AS Date), N'anh12.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS17346', N'PHAN THANH PHONG', CAST(N'1998-01-21' AS Date), 1, N'0937891282', N'PS17346@fpt.edu.vn', N'0937891282 - PHAN THANH PHONG', N'DuyPlus', CAST(N'2021-01-21' AS Date), N'anh11.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS17596', N'NGUYỄN THANH HIỀN', CAST(N'1997-07-09' AS Date), 0, N'0910545901', N'PS17596@fpt.edu.vn', N'0910545901 - NGUYỄN THANH HIỀN', N'DuyPlus', CAST(N'2020-07-09' AS Date), N'anh4.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS17610', N'TRẦN ĐÌNH TRƯỜNG', CAST(N'2003-01-16' AS Date), 1, N'0941528106', N'PS17610@fpt.edu.vn', N'0941528106 - TRẦN ĐÌNH TRƯỜNG', N'DungNH', CAST(N'2021-01-16' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS17614', N'NGUYỄN VĂN SÁU', CAST(N'1999-04-27' AS Date), 1, N'0940711328', N'PS17614@fpt.edu.vn', N'0940711328 - NGUYỄN VĂN SÁU', N'DungNH', CAST(N'2021-04-27' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18031', N'PHAN TẤN VIỆT', CAST(N'1997-04-05' AS Date), 1, N'0924832716', N'PS18031@fpt.edu.vn', N'0924832716 - PHAN TẤN VIỆT', N'DuyPlus', CAST(N'2021-04-05' AS Date), N'anh8.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18037', N'NGUYỄN NGỌC HUYỀN VI', CAST(N'2001-10-24' AS Date), 0, N'0968095685', N'PS18037@fpt.edu.vn', N'0968095685 - NGUYỄN NGỌC HUYỀN VI', N'DuyPlus', CAST(N'2019-10-24' AS Date), N'anh5.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18080', N'HUỲNH THANH HUY', CAST(N'1998-09-06' AS Date), 1, N'0916436052', N'PS18080@fpt.edu.vn', N'0916436052 - HUỲNH THANH HUY', N'DuyPlus', CAST(N'2019-09-06' AS Date), N'anh15.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18096', N'ĐOÀN HỮU KHANG', CAST(N'2000-02-21' AS Date), 1, N'0945196719', N'PS18096@fpt.edu.vn', N'0945196719 - ĐOÀN HỮU KHANG', N'DuyPlus', CAST(N'2021-02-21' AS Date), N'anh14.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18134', N'LÊ DUY BẢO', CAST(N'2000-08-08' AS Date), 1, N'0926714368', N'PS18134@fpt.edu.vn', N'0926714368 - LÊ DUY BẢO', N'DungNH', CAST(N'2019-08-08' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18172', N'NGUYỄN ANH TUẤN', CAST(N'1998-02-15' AS Date), 1, N'0920020472', N'PS18172@fpt.edu.vn', N'0920020472 - NGUYỄN ANH TUẤN', N'DuyPlus', CAST(N'2021-02-15' AS Date), N'anh10.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18233', N'LƯU KIM HOÀNG DUY', CAST(N'2003-07-04' AS Date), 1, N'0938357735', N'PS18233@fpt.edu.vn', N'0938357735 - LƯU KIM HOÀNG DUY', N'DungNH', CAST(N'2020-07-04' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18251', N'TRẦN QUANG VŨ', CAST(N'2000-03-10' AS Date), 1, N'0914531913', N'PS18251@fpt.edu.vn', N'0914531913 - TRẦN QUANG VŨ', N'DungNH', CAST(N'2021-03-10' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18293', N'NGUYỄN HOÀNG DUY', CAST(N'1999-08-22' AS Date), 1, N'0919993715', N'PS18293@fpt.edu.vn', N'0919993715 - NGUYỄN HOÀNG DUY', N'DuyPlus', CAST(N'2019-09-08' AS Date), N'anh1.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18425', N'TÔ VĂN NĂNG', CAST(N'2001-07-28' AS Date), 1, N'0915134551', N'PS18425@fpt.edu.vn', N'0915134551 - TÔ VĂN NĂNG', N'DungNH', CAST(N'2020-07-28' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18488', N'NGUYỄN NHƯ NGỌC', CAST(N'1998-05-09' AS Date), 0, N'0912880267', N'PS18488@fpt.edu.vn', N'0912880267 - NGUYỄN NHƯ NGỌC', N'DuyPlus', CAST(N'2020-05-09' AS Date), N'anh3.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18530', N'PHẠM THÀNH TÂM', CAST(N'2000-03-11' AS Date), 1, N'0918161783', N'PS18530@fpt.edu.vn', N'0918161783 - PHẠM THÀNH TÂM', N'DuyPlus', CAST(N'2021-03-11' AS Date), N'anh11.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18553', N'ĐINH TẤN CÔNG', CAST(N'2000-08-15' AS Date), 1, N'0918182551', N'PS18553@fpt.edu.vn', N'0918182551 - ĐINH TẤN CÔNG', N'DungNH', CAST(N'2019-08-15' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18561', N'LÊ MINH ĐIỀN', CAST(N'1998-01-05' AS Date), 1, N'0948199564', N'PS18561@fpt.edu.vn', N'0948199564 - LÊ MINH ĐIỀN', N'DungNH', CAST(N'2021-01-05' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18603', N'LÊ PHẠM KIM THANH', CAST(N'2002-08-05' AS Date), 0, N'0924696779', N'PS18603@fpt.edu.vn', N'0924696779 - LÊ PHẠM KIM THANH', N'DuyPlus', CAST(N'2019-08-05' AS Date), N'anh4.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18640', N'LƯU THANH NGỌC', CAST(N'1997-12-01' AS Date), 0, N'0918358164', N'PS18640@fpt.edu.vn', N'0918358164 - LƯU THANH NGỌC', N'DuyPlus', CAST(N'2019-12-01' AS Date), N'anh5.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18662', N'NGUYỄN CAO NGỌC LỢI', CAST(N'1998-04-24' AS Date), 1, N'0930260679', N'PS18662@fpt.edu.vn', N'0930260679 - NGUYỄN CAO NGỌC LỢI', N'DungNH', CAST(N'2020-04-24' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18674', N'TRẦN TUẤN ANH', CAST(N'2002-06-11' AS Date), 1, N'0914082094', N'PS18674@fpt.edu.vn', N'0914082094 - TRẦN TUẤN ANH', N'DungNH', CAST(N'2020-06-11' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18867', N'NGUYỄN HỮU TRÍ', CAST(N'2002-10-27' AS Date), 1, N'0946984711', N'PS18867@fpt.edu.vn', N'0946984711 - NGUYỄN HỮU TRÍ', N'DuyPlus', CAST(N'2019-10-27' AS Date), N'anh7.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS18988', N'HỒ HỮU HẬU', CAST(N'2002-02-08' AS Date), 1, N'0924984876', N'PS18988@fpt.edu.vn', N'0924984876 - HỒ HỮU HẬU', N'DuyPlus', CAST(N'2021-02-08' AS Date), N'anh15.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19088', N'NGUYỄN HOÀNG TRUNG', CAST(N'2000-09-02' AS Date), 1, N'0938101529', N'PS19088@fpt.edu.vn', N'0938101529 - NGUYỄN HOÀNG TRUNG', N'DungNH', CAST(N'2019-09-02' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19104', N'LÊ THÀNH PHƯƠNG', CAST(N'2002-02-21' AS Date), 0, N'0922948096', N'PS19104@fpt.edu.vn', N'0922948096 - LÊ THÀNH PHƯƠNG', N'DuyPlus', CAST(N'2021-02-21' AS Date), N'anh2.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19120', N'PHẠM NGỌC NHẬT TRƯỜNG', CAST(N'1999-06-24' AS Date), 1, N'0994296169', N'PS19120@fpt.edu.vn', N'0994296169 - PHẠM NGỌC NHẬT TRƯỜNG', N'DungNH', CAST(N'2020-06-24' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19203', N'ĐẶNG LÊ QUANG VINH', CAST(N'1998-01-02' AS Date), 1, N'0920197355', N'PS19203@fpt.edu.vn', N'0920197355 - ĐẶNG LÊ QUANG VINH', N'DungNH', CAST(N'2021-01-02' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19205', N'NGUYỄN MINH SANG', CAST(N'2001-05-02' AS Date), 1, N'0967006218', N'PS19205@fpt.edu.vn', N'0967006218 - NGUYỄN MINH SANG', N'DuyPlus', CAST(N'2021-05-02' AS Date), N'anh6.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19222', N'TRẦM MINH MẪN', CAST(N'1997-02-09' AS Date), 1, N'0911183649', N'PS19222@fpt.edu.vn', N'0911183649 - TRẦM MINH MẪN', N'DuyPlus', CAST(N'2021-02-09' AS Date), N'anh11.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19304', N'BÙI NGỌC THUẬN', CAST(N'2001-01-26' AS Date), 1, N'0999794115', N'PS19304@fpt.edu.vn', N'0999794115 - BÙI NGỌC THUẬN', N'DungNH', CAST(N'2021-01-26' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19306', N'HỒ VĂN DŨNG', CAST(N'2000-04-15' AS Date), 1, N'0912277868', N'PS19306@fpt.edu.vn', N'0912277868 - HỒ VĂN DŨNG', N'DuyPlus', CAST(N'2021-04-15' AS Date), N'anh9.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19325', N'NGUYỄN HOÀNG MINH ĐỨC', CAST(N'2002-12-11' AS Date), 1, N'0912234437', N'PS19325@fpt.edu.vn', N'0912234437 - NGUYỄN HOÀNG MINH ĐỨC', N'DungNH', CAST(N'2020-12-11' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19383', N'TRẦN VŨ LUÂN', CAST(N'1998-04-08' AS Date), 1, N'0962030316', N'PS19383@fpt.edu.vn', N'0962030316 - TRẦN VŨ LUÂN', N'DuyPlus', CAST(N'2020-04-08' AS Date), N'anh12.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19389', N'PHẠM YẾN NHI', CAST(N'2000-02-24' AS Date), 0, N'0911637415', N'PS19389@fpt.edu.vn', N'0911637415 - PHẠM YẾN NHI', N'DuyPlus', CAST(N'2021-02-24' AS Date), N'anh3.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19410', N'NGUYỄN HOÀNG DŨNG', CAST(N'1999-06-08' AS Date), 1, N'0946219377', N'PS19410@fpt.edu.vn', N'0946219377 - NGUYỄN HOÀNG DŨNG', N'DuyPlus', CAST(N'2020-06-08' AS Date), N'anh13.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19411', N'TRƯƠNG THÀNH ĐẠT', CAST(N'1999-11-05' AS Date), 1, N'0991509408', N'PS19411@fpt.edu.vn', N'0991509408 - TRƯƠNG THÀNH ĐẠT', N'DungNH', CAST(N'2020-11-05' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19454', N'NGUYỄN NHẬT VĨNH', CAST(N'1999-07-09' AS Date), 1, N'0917886371', N'PS19454@fpt.edu.vn', N'0917886371 - NGUYỄN NHẬT VĨNH', N'DungNH', CAST(N'2020-07-09' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19472', N'NGUYỄN VĂN HUY', CAST(N'1999-01-22' AS Date), 1, N'0960620997', N'PS19472@fpt.edu.vn', N'0960620997 - NGUYỄN VĂN HUY', N'DungNH', CAST(N'2021-01-22' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19618', N'VŨ THỊ NGỌC ANH', CAST(N'2002-12-09' AS Date), 0, N'0939020097', N'PS19618@fpt.edu.vn', N'0939020097 - VŨ THỊ NGỌC ANH', N'DuyPlus', CAST(N'2020-12-09' AS Date), N'anh5.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19638', N'PHẠM NHẬT MINH', CAST(N'2002-07-18' AS Date), 1, N'0927771672', N'PS19638@fpt.edu.vn', N'0927771672 - PHẠM NHẬT MINH', N'DungNH', CAST(N'2020-07-18' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19771', N'NGUYỄN TẤN HIẾU', CAST(N'1998-09-15' AS Date), 1, N'0927594734', N'PS19771@fpt.edu.vn', N'0927594734 - NGUYỄN TẤN HIẾU', N'DungNH', CAST(N'2019-09-15' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19930', N'TRẦN VĂN NAM', CAST(N'2000-06-03' AS Date), 1, N'0924774498', N'PS19930@fpt.edu.vn', N'0924774498 - TRẦN VĂN NAM', N'DuyPlus', CAST(N'2020-06-03' AS Date), N'anh8.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19979', N'ĐOÀN TRẦN NHẬT VŨ', CAST(N'2001-08-28' AS Date), 1, N'0912374818', N'PS19979@fpt.edu.vn', N'0912374818 - ĐOÀN TRẦN NHẬT VŨ', N'DuyPlus', CAST(N'2019-08-28' AS Date), N'NoImage.png')
INSERT [dbo].[NguoiHoc] ([MaNH], [HoTen], [NgaySinh], [GioiTinh], [DienThoai], [Email], [GhiChu], [MaNV], [NgayDK], [Hinh]) VALUES (N'PS19983', N'LÂM ÁI VY', CAST(N'2000-04-04' AS Date), 0, N'0912499836', N'PS19983@fpt.edu.vn', N'0912499836 - LÂM ÁI VY', N'DuyPlus', CAST(N'2021-04-04' AS Date), N'anh3.png')
GO
INSERT [dbo].[NhanVien] ([MaNV], [MatKhau], [HoTen], [Email], [VaiTro]) VALUES (N'DungNH', N'123456', N'Nguyễn Hoàng Dũng', N'duynh@gmail.com', 0)
INSERT [dbo].[NhanVien] ([MaNV], [MatKhau], [HoTen], [Email], [VaiTro]) VALUES (N'DuyPlus', N'123456', N'Nguyễn Hoàng Duy', N'duyplusdz@gmail.com', 1)
INSERT [dbo].[NhanVien] ([MaNV], [MatKhau], [HoTen], [Email], [VaiTro]) VALUES (N'NoPT', N'123456', N'Phạm Thị Nở', N'nopt@gmail.com', 0)
INSERT [dbo].[NhanVien] ([MaNV], [MatKhau], [HoTen], [Email], [VaiTro]) VALUES (N'PheoNC', N'123456', N'Nguyễn Chí Phèo', N'pheocn@gmail.com', 0)
INSERT [dbo].[NhanVien] ([MaNV], [MatKhau], [HoTen], [Email], [VaiTro]) VALUES (N'TeoNV', N'123456', N'Nguyễn Văn Tèo', N'teonv@gmail.com', 1)
GO
-- Khoá phụ bảng Khoá Học
ALTER TABLE KhoaHoc WITH CHECK ADD CONSTRAINT FK_KhoaHoc_ChuyenDe FOREIGN KEY(MaCD)
REFERENCES ChuyenDe(MaCD)
GO
ALTER TABLE KhoaHoc WITH CHECK ADD CONSTRAINT FK_KhoaHoc_NhanVien FOREIGN KEY(MaNV)
REFERENCES NhanVien (MaNV)
GO
-- Khoá phụ bảng Người Học
ALTER TABLE NguoiHoc WITH CHECK ADD CONSTRAINT FK_NguoiHoc_NhanVien FOREIGN KEY(MaNV)
REFERENCES NhanVien (MaNV)
GO
-- Khoá phụ bảng Học Viên
ALTER TABLE HocVien  WITH CHECK ADD CONSTRAINT FK_HocVien_KhoaHoc FOREIGN KEY(MaKH)
REFERENCES KhoaHoc (MaKH) ON UPDATE CASCADE ON DELETE CASCADE
GO
ALTER TABLE HocVien WITH CHECK ADD CONSTRAINT FK_HocVien_NguoiHoc FOREIGN KEY(MaNH)
REFERENCES NguoiHoc(MaNH) ON UPDATE CASCADE
GO

-- Thủ tục Bảng Điểm
CREATE PROC sp_BangDiem(@MaKH INT)
AS BEGIN
	SELECT
		nh.MaNH,
		nh.HoTen,
		hv.Diem
	FROM HocVien hv
		JOIN NguoiHoc nh ON nh.MaNH = hv.MaNH
	WHERE hv.MaKH = @MaKH
	ORDER BY hv.Diem DESC
END
GO
-- Thủ tục Điểm Chuyên Đề
CREATE PROC sp_DiemChuyenDe
AS BEGIN
	SELECT
		TenCD ChuyenDe,
		COUNT(MaHV) SoHV,
		MIN(Diem) ThapNhat,
		MAX(Diem) CaoNhat,
		AVG(Diem) TrungBinh
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKH = hv.MaKH
		JOIN ChuyenDe cd ON cd.MaCD = kh.MaCD
	GROUP BY TenCD
END
GO
-- Thủ tục Doanh Thu
CREATE PROC sp_DoanhThu(@Year INT)
AS BEGIN
	SELECT
		TenCD ChuyenDe,
		COUNT(DISTINCT kh.MaKH) SoKH,
		COUNT(hv.MaHV) SoHV,
		SUM(kh.HocPhi) DoanhThu,
		MIN(kh.HocPhi) ThapNhat,
		MAX(kh.HocPhi) CaoNhat,
		AVG(kh.HocPhi) TrungBinh
	FROM KhoaHoc kh
		JOIN HocVien hv ON kh.MaKH = hv.MaKH
		JOIN ChuyenDe cd ON cd.MaCD = kh.MaCD
	WHERE YEAR(NgayKG) = @Year
	GROUP BY TenCD
END
GO
-- Thủ tục Lượng Người Học
CREATE PROC sp_LuongNguoiHoc
AS BEGIN
	SELECT
		YEAR(NgayDK) Nam,
		COUNT(*) SoLuong,
		MIN(NgayDK) DauTien,
		MAX(NgayDK) CuoiCung
	FROM NguoiHoc
	GROUP BY YEAR(NgayDK)
END