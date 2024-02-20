--create database Nhom2_DuAnMau;

use Nhom2_DuAnMau;


-- **************************************************************************************************
-- Tạo Table
create table NhanVien (
	MaNV nvarchar(10) not null,
	TenNV nvarchar(50) not null,
	GioiTinh nvarchar(10),
	SDT nvarchar(10),
	Email nvarchar(50),
	DiaChi nvarchar(50),
	Hinh nvarchar(50),
	NamSinh int,
	ChucVu nvarchar(10),
	TinhTrang nvarchar(50),
	constraint pk_NhanVien primary key (MaNV)
);

create table SanPham(
	MaLK nvarchar(50),
	TenLK nvarchar(100),
	LoaiLK nvarchar(50),
	Gia float,
	SoLuong int,
	BaoHanh int,
	NSX nvarchar(50),
	Hinh nvarchar(50),
	NguoiThem nvarchar(10),
	TinhTrang nvarchar(50),
	constraint fk_SanPham_NhanVien foreign key (NguoiThem) references NhanVien(MaNV),
	constraint pk_SanPham primary key (MaLK)
);

create table AccLogin(
	UserID nvarchar(10),
	Pass nvarchar(10),
	RoleAcc nvarchar(10),
	constraint pk_AccLogin primary key (UserID),
	constraint fk_AccLogin_NhanVien foreign key (UserID) references NhanVien(MaNV)
);

create table KhachHang(
	MaKH int IDENTITY(1,1),
	SDT nvarchar(10) not null,
	HoTen nvarchar(50) not null,
	DiaChi nvarchar(100),
	Quan nvarchar(50),
	ThanhPho nvarchar(50),
	constraint pk_KhachHang primary key (MaKH) 
);

create table RankKhachHang(
	MaKH int,
	TenKH nvarchar(50),
	TongChiTieu float,
	rankKH nvarchar(50),
	constraint fk_RankKH_KhachHang foreign key (makh) references khachhang(Makh)
);

create table HoaDon(
	MaHD int IDENTITY(1,1),
	NgayTaoHD date,
	NguoiBan nvarchar(10),
	TongTien float,
	MaKH int,
	constraint pk_HoaDon primary key (MaHD),
	constraint fk_HoaDon_NhanVien foreign key (NguoiBan) references NhanVien(MaNV),
	constraint fk_HoaDon_KhachHang foreign key (MaKH) references KhachHang(MaKH)
);

create table HDCT(
	MaHD int,
	TenLK nvarchar(100),
	MaLK nvarchar(50),
	SoLuong int,
	DonGiaLK float,
	constraint fk_HDCT_HoaDon foreign key (MaHD) references HoaDon(MaHD),
	constraint fk_HDCT_SanPham foreign key (MaLK) references SanPham(MaLK)
);
-- **************************************************************************************************



-- **************************************************************************************************
-- Tạo Trigger

-- 1/ Tình trạng SP 
create trigger tg_tinhtrang on sanpham
after update
as
begin
	if(update(soluong) and (select soluong from inserted) = 0)
	begin
		update SanPham set TinhTrang=N'Hết hàng' where  malk=(select malk from inserted);
	end
end;

-- 2/ Lấy thông tin tổng chi tiêu của KH và update rank KH
create trigger tg_RankKH on hoadon
after insert
as
begin
	declare @tongtien float;
	set @tongtien =(select sum(tongtien) from hoadon where makh =(select makh from inserted) group by makh);
	update RankKhachHang set TongChiTieu =@tongtien where makh= (select makh from inserted);
	update RankKhachHang set rankKH = case
	when @tongtien <10000000 then N'Đồng'
	when @tongtien between 10000000 and 20000000 then N'Bạc'
	when @tongtien between 20000000 and 30000000 then N'Vàng'
	when @tongtien between 30000000 and 50000000 then N'Bạch kim'
	when @tongtien >=50000000 then N'Kim cương'
	end
	where makh=(select makh from inserted)
end;

-- 3/ Lấy thông tin KH từ bảng KH sang bảng rank KH
create trigger tg_MaKH_from_KhachHang_to_RankKH on KhachHang
after insert
as
begin 
	insert into RankKhachHang values((select makh from inserted),(select hoten from inserted),0,N'Đồng');
end;
-- **************************************************************************************************




-- **************************************************************************************************
-- Thêm dữ liệu DTB
insert into NhanVien (MaNV, TenNV, GioiTinh, SDT, Email, DiaChi, Hinh, NamSinh, ChucVu, TinhTrang) values
(N'PS26524', N'Nguyễn Trọng Khoa', N'Nam', N'0123456789', N'khoa@gmail.com', N'TPHCM', null, 1997, N'Nhân viên', N'Còn làm'),
(N'PS26548', N'Nguyễn Ngọc Minh Thuận', N'Nam', N'0987654321', N'thuan@gmail.com', N'Phan Thiết', null, 1997, N'Nhân viên', N'Còn làm'),
(N'PS26555', N'Nguyễn Hoàng Dũng', N'Nam', N'0112345678', N'hoangdung@gmail.com', N'Vũng Tàu', null, 1999, N'Quản lý', N'Còn làm'),
(N'PS26562', N'Nguyễn Đình Dũng', N'Nam', N'0212345678', N'dinhdung@gmail.com', N'Bình Dương', null, 2000, N'Nhân viên', N'Còn làm'),
(N'PS26440', N'Võ Trọng Quý', N'Nam', N'0312345678', N'quy@gmail.com', N'Lào Cai', null, 1995, N'Nhân viên', N'Còn làm');

insert into AccLogin (UserID, Pass, RoleAcc) values 
(N'PS26555', N'12345', N'Quản lý'),
(N'PS26524', N'12345', N'Nhân Viên');

insert into SanPham (MaLK, TenLK, LoaiLK, Gia, SoLuong, BaoHanh, NSX, Hinh, NguoiThem, TinhTrang) values
(N'RAM PC/LAPTOP 01', N'Ram Laptop  DDR4 4Gb 2666Hz 1.2v', N'Ram LAPTOP', 250000, 40, 36, N'Lexar', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 02', N'Ram Laptop G.Skill DDR3L 4GB 1600MHz 1.35v', N'Ram LAPTOP', 290000, 60, 36, N'G.Skill', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 03', N'Ram PC Kingston 4GB 2666MHz DDR4', N'Ram PC', 390000, 56, 36, N'Kingston', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 04', N'Ram Laptop Crucial DDR4 4GB 2400MHz 1.2v', N'Ram LAPTOP', 400000, 62, 36, N'Crucial', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 05', N'Ram Laptop Crucial DDR4 4GB 2666MHz 1.2v', N'Ram LAPTOP', 430000, 75, 36, N'Crucial', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 06', N'Ram PC G.SKILL Aegis 8GB 1600MHz DDR3 8GB', N'Ram PC', 450000, 64, 36, N'G.Skill', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 07', N'Ram Laptop G.Skill Ripjaws DDR4 8GB 3200MHz 1.2v', N'Ram LAPTOP', 455000, 78, 36, N'G.Skill', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 08', N'Ram Laptop Corsair Vengeance DDR4 8GB 3200MHz 1.2v', N'Ram LAPTOP', 490000, 74, 36, N'Corsair', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 09', N'Ram Laptop G.Skill Ripjaws DDR3L 8GB 1600MHz 1.35v', N'Ram LAPTOP', 490000, 47, 36, N'G.Skill', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 10', N'Ram PC Kingston Fury Beast Black 8GB 3200MHz DDR4', N'Ram PC', 499000, 57, 36, N'Kingston', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 11', N'Ram PC Corsair Vengeance LPX 8GB 3200MHz DDR4', N'Ram PC', 499000, 85, 36, N'Corsair', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 12', N'Ram PC Samsung 8GB 3200MHz DDR4', N'Ram PC', 650000, 75, 36, N'Samsung', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 13', N'Ram PC Server Transcend 8GB 2666MHz DDR4 1Rx8', N'Ram PC', 550000, 89, 36, N'Transcend', null, N'PS26555', N'Còn hàng'),
(N'RAM PC/LAPTOP 14', N'Ram PC Server Kingston 8GB 2666MHz DDR4', N'Ram PC', 990000, 76, 36, N'Kingsston', null, N'PS26555', N'Còn hàng'),
(N'DISK 01', N'SSD MSI SPATIUM 2.5-Inch SATA III 120GB', N'SSD', 280000, 46, 36, N'Spatium', null, N'PS26555', N'Còn hàng'),
(N'DISK 02', N'SSD Lexar NS100 2.5-Inch SATA III 128GB', N'SSD', 320000, 68, 36, N'Lexar', null, N'PS26555', N'Còn hàng'),
(N'DISK 03', N'SSD Lexar NQ100 2.5-Inch SATA III 240GB', N'SSD', 355000, 97, 36, N'Lexar', null, N'PS26555', N'Còn hàng'),
(N'DISK 04', N'SSD MSI SPATIUM S270 2.5-Inch SATA III 240GB ', N'SSD', 399000, 88, 36, N'Spatium', null, N'PS26555', N'Còn hàng'),
(N'DISK 05', N'SSD Kingston A400 2.5-Inch SATA III 240GB', N'SSD', 430000, 96, 36, N'Kingston', null, N'PS26555', N'Còn hàng'),
(N'DISK 06', N'SSD Western Digital Green M.2 2280 Sata III 240GB', N'SSD', 450000, 123, 36, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 07', N'SSD Western Digital Green 2.5-Inch SATA III 240GB', N'SSD', 450000, 79, 36, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 08', N'SSD Kingston NV2 M.2 PCIe Gen4 x4 NVMe 250G', N'SSD', 550000, 46, 36, N'Kingston', null, N'PS26555', N'Còn hàng'),
(N'DISK 09', N'SSD Samsung NVMe M.2 PCIe Gen3 x4 256GB', N'SSD', 690000, 84, 36, N'Samsung', null, N'PS26555', N'Còn hàng'),
(N'DISK 10', N'SSD Kioxia Exceria Plus G2 M.2 PCIe Gen3 500GB', N'SSD', 750000, 53, 60, N'Kioxia', null, N'PS26555', N'Còn hàng'),
(N'DISK 11', N'HDD WD Blue 1TB 3.5 inch SATA III 64MB Cache 7200RPM', N'HDD', 920000, 35, 24, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 12', N'HDD WD Purple 1TB 3.5 inch SATA III 64MB Cache 5400RPM', N'HDD', 990000, 230, 36, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 13', N'HDD WD Black 1TB 3.5 inch SATA III 64MB Cache 7200RPM', N'HDD', 1990000, 56, 60, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 14', N'HDD Seagate BarraCuda 2TB 3.5 inch SATA III 256MB Cache 7200RPM', N'HDD', 1390000, 58, 24, N'Seagate', null, N'PS26555', N'Còn hàng'),
(N'DISK 15', N'HDD WD Ultrastar 2TB 3.5 inch SATA Ultra 512N 128MB Cache 7200RPM', N'HDD', 3100000, 79, 60, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 16', N'HDD WD Purple Pro 18TB 3.5 inch SATA III 512MB Cache 7200RPM', N'HDD', 13800000, 99, 60, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 17', N'HDD WD Purple Pro 12TB 3.5 inch SATA III 256MB Cache 7200RPM', N'HDD', 9950000, 48, 60, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 18', N'HDD WD Red Plus 12TB 3.5 inch SATA III 256MB Cache 7200RPM', N'HDD', 7450000, 88, 36, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 19', N'HDD WD Red Plus 6TB 3.5 inch SATA III 256MB Cache 5400RPM', N'HDD', 4190000, 33, 36, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 20', N'HDD WD Ultrastar 4TB 3.5 inch SATA Ultra256MB Cache 7200RPM', N'HDD', 4050000, 77, 60, N'Western Digital', null, N'PS26555', N'Còn hàng'),
(N'DISK 21', N'USB 3.2 Gen 1 Kingston DataTraveler Exodia M 32GB', N'USB', 75000, 165, 60, N'Kingston', null, N'PS26555', N'Còn hàng'),
(N'DISK 22', N'USB 3.0 SanDisk Cruzer Glide CZ600 16GB', N'USB', 89000, 87, 60, N'SanĐisk', null, N'PS26555', N'Còn hàng'),
(N'DISK 23', N'USB 3.0 SanDisk Ultra Flair CZ73 64GB 150MB/s', N'USB', 140000, 58, 60, N'SanDisk', null, N'PS26555', N'Còn hàng'),
(N'DISK 24', N'USB 3.2 SanDisk Extreme Pro CZ880 128GB', N'USB', 890000, 79, 60, N'SanDisk', null, N'PS26555', N'Còn hàng'),
(N'DISK 25', N'USB 3.1 SanDisk Ultra Luxe CZ74 512GB 150MB/s', N'USB', 990000, 11, 60, N'SanDisk', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 01', N'Fan Case XIGMATEK X22F RGB Fixed 120mm Black', N'Case', 100000, 155, 6, N'XIGMATEK', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 02', N'Case Silicon bảo vệ GoPro MAX', N'Tản nhiệt', 120000, 43, 0, N'OEM', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 03', N'Kem tản nhiệt Cooler Master CRYOFUZE High Performance', N'Tản nhiệt', 135000, 80, 0, N'Cooler Master', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 04', N'Box ổ cứng 2.5-inch USB 3.0 Orico', N'Linh kiện', 150000, 123, 12, N'Orico', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 05', N'Kem tản nhiệt Thermal Grizzly Kryonaut 1G', N'Tản nhiệt', 160000, 48, 0, N'Thermal Grizzly', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 06', N'Bộ chia USB 7 cổng USB 3.0 Orico', N'Linh kiện', 295000, 59, 12, N'Orico', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 07', N'Giá đỡ màn hình North Bayou 17-30 Inch F80', N'Linh kiện', 330000, 155, 12, N'North Bayou', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 08', N'Giá đỡ 2 màn hình North Bayou 17-27 Inch F160', N'Linh kiện', 590000, 210, 12, N'North Bayou', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 09', N'Nguồn máy tính Deepcool PF450 450W 80 Plus', N'Nguồn', 750000, 67, 36, N'Deepcool', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 10', N'Case máy tính Deepcool E-SHIELD', N'Case', 790000, 35, 12, N'Deepcool', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 11', N'Giá đỡ màn hình Human Motion Arm T6 Pro 17-32 Inch', N'Linh kiện', 790000, 43, 12, N'Human Motion', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 12', N'Bộ chia USB Type-C Kingston Nucleum 7-in-1', N'Linh kiện', 990000, 79, 24, N'Kingston', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 13', N'Nguồn máy tính Corsair 550W RGB 80 Plus Bronze Black', N'Nguồn', 1150000, 68, 60, N'Corsair', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 14', N'Case máy tính MicroATX Asus Prime AP201 MESH White', N'Case', 1390000, 47, 24, N'Asus', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 15', N'VGA Asus GeForce GT 730 2GB GDDR5', N'VGA', 1390000, 34, 36, N'Asus', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 16', N'Nguồn máy tính Corsair CV750 750W 80 Plus Bronze', N'Nguồn', 1550000, 56, 36, N'Cosair', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 17', N'Tản nhiệt nước AIO ID-Cooling DASHFLOW 360 BASIC BLACK', N'Tản nhiệt', 1690000, 15, 36, N'ID-Cooling', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 18', N'Tản nhiệt nước AIO ID-Cooling ZOOMFLOW 360 XT SNOW', N'Tản nhiệt', 1990000, 45, 24, N'ID-Cooling', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 19', N'Màn hình LCD Corsair cho tản nhiệt nước AIO Corsair ELITE', N'Linh kiện', 2790000, 57, 60, N'Cosair', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 20', N'Tản nhiệt khí Noctua NH-D15 Chromax Black', N'Tản nhiệt', 3150000, 37, 72, N'Noctua', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 21', N'CPU AMD Ryzen 5 5500 3.6GHz 6 cores 12 threads 19MB', N'CPU', 2700000, 57, 36, N'AMD', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 22', N'CPU AMD Ryzen 5 5600 3.5GHz 6 cores 12 threads 35MB', N'CPU', 3990000, 14, 36, N'AMD', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 23', N'CPU AMD Ryzen 7 5700G 3.8GHz 8 cores 16 threads 16MB', N'CPU', 5450000, 13, 36, N'AMD', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 24', N'CPU Intel Core i3-12100F Up to 4.3GHz 4 cores 8 threads 12MB', N'CPU', 2590000, 21, 36, N'Intel', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 25', N'CPU Intel Core i5-13400F Up to 4.6GHz 10 cores 16 threads 20MB', N'CPU', 5450000, 17, 36, N'Intel', null, N'PS26555', N'Còn hàng'),
(N'LK-PC/LAPTOP 26', N'CPU Intel Core i9-13900K Up to 5.8GHz 24 cores 32 threads 36MB', N'CPU', 15490000, 5, 36, N'Intel', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 01', N'Màn hình BenQ 22 Inch IPS 60Hz', N'Màn', 2250000, 13, 36, N'BenQ', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 02', N'Màn hình MSI PRO MP243 24 Inch IPS 75Hz', N'Màn', 2590000, 19, 24, N'MSI', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 03', N'Màn hình LG 24 Inch IPS 75Hz', N'Màn', 3190000, 68, 24, N'LG', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 04', N'Màn hình MSI PRO MP273 27 Inch IPS 75Hz', N'Màn', 3690000, 10, 24, N'MSI', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 05', N'Màn hình AOC 27 Inch IPS 75Hz', N'Màn', 3850000, 9, 36, N'AOC', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 06', N'Màn hình LG UltraWide 29 Inch IPS 75Hz', N'Màn', 5190000, 5, 24, N'LG', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 07', N'Màn hình BenQ ZOWIE 24 Inch TN 144Hz', N'Màn', 5090000, 3, 36, N'BenQ', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 08', N'Màn hình cong MSI Optix G27C4X 27 Inch VA 250Hz', N'Màn', 5590000, 21, 36, N'MSI', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 09', N'Màn hình Dell 27 Inch IPS 60Hz', N'Màn', 5850000, 13, 36, N'Dell', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 10', N'Màn hình Dell 27 Inch VA 60Hz', N'Màn', 4290000, 8, 36, N'Dell', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 11', N'Màn hình 2K Dell 32 Inch IPS 165Hz', N'Màn', 12190000, 9, 36, N'Dell', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 12', N'Màn hình BenQ ZOWIE 24.5 Inch TN 240Hz', N'Màn', 11900000, 12, 36, N'BenQ', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 13', N'Màn hình 2K LG DualUp 28 Inch IPS 60Hz', N'Màn', 12990000, 2, 24, N'LG', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 14', N'Màn hình 4K Dell 32 Inch IPS 60Hz', N'Màn', 13350000, 7, 36, N'Dell', null, N'PS26555', N'Còn hàng'),
(N'MONITOR 15', N'Màn hình cong LG UltraWide 38 Inch IPS 144Hz', N'Màn', 27990000, 4, 24, N'LG', null, N'PS26555', N'Còn hàng'),
(N'GEAR 01', N'Chuột Corsair Harpoon RGB PRO', N'Chuột', 440000, 23, 24, N'Cosair', null, N'PS26555', N'Còn hàng'),
(N'GEAR 02', N'Chuột Gaming Razer DeathAdder Essential', N'Chuột', 450000, 123, 24, N'Razer', null, N'PS26555', N'Còn hàng'),
(N'GEAR 03', N'Chuột không dây Logitech M331 Silent', N'Chuột', 280000, 15, 12, N'Logitech', null, N'PS26555', N'Còn hàng'),
(N'GEAR 04', N'Chuột có dây Logitech B100', N'Chuột', 80000, 34, 36, N'Logitech', null, N'PS26555', N'Còn hàng'),
(N'GEAR 05', N'Chuột Gaming HyperX Pulsefire FPS Pro', N'Chuột', 790000, 45, 24, N'HyperX', null, N'PS26555', N'Còn hàng'),
(N'GEAR 06', N'Chuột Gaming Razer DeathAdder V2', N'Chuột', 849000, 34, 24, N'Razer', null, N'PS26555', N'Còn hàng'),
(N'GEAR 07', N'Chuột Gaming SteelSeries Aerox 5', N'Chuột', 1290000, 12, 6, N'SteelSeries', null, N'PS26555', N'Còn hàng'),
(N'GEAR 08', N'Chuột không dây Logitech G PRO Gaming HERO', N'Chuột', 1890000, 15, 24, N'Logitech', null, N'PS26555', N'Còn hàng'),
(N'GEAR 09', N'Chuột Gaming không dây HyperX Pulsefire Haste 2 Wireless', N'Chuột', 1990000, 22, 24, N'HyperX', null, N'PS26555', N'Còn hàng'),
(N'GEAR 10', N'Chuột Gaming không dây Razer Basilisk V3 Pro', N'Chuột', 3690000, 21, 24, N'Razer', null, N'PS26555', N'Còn hàng'),
(N'GEAR 11', N'Bàn phím Logitech K120', N'Bản phím', 160000, 11, 36, N'Logitech', null, N'PS26555', N'Còn hàng'),
(N'GEAR 12', N'Bàn phím cơ TKL DareU EK87 Black D Switch Red / Blue / Brown', N'Bản phím', 490000, 22, 24, N'DareU', null, N'PS26555', N'Còn hàng'),
(N'GEAR 13', N'Bàn phím không dây Touchpad Logitech K400 Plus White', N'Bản phím', 650000, 31, 12, N'Logitech', null, N'PS26555', N'Còn hàng'),
(N'GEAR 14', N'Bàn phím cơ AKKO MonsGeek MG108 RGB Doll of Princess', N'Bản phím', 1290000, 7, 12, N'Akko', null, N'PS26555', N'Còn hàng'),
(N'GEAR 15', N'Bàn phím cơ TKL không dây AKKO 3087 RF Matcha Red Bean', N'Bản phím', 1390000, 8, 12, N'Akko', null, N'PS26555', N'Còn hàng'),
(N'GEAR 16', N'Bàn phím giả cơ Razer Ornata V3 Low Profile', N'Bản phím', 1465000, 11, 24, N'Razer', null, N'PS26555', N'Còn hàng'),
(N'GEAR 17', N'Bàn phím cơ không dây AKKO 3108 RF White on Black', N'Bản phím', 1490000, 1, 12, N'Akko', null, N'PS26555', N'Còn hàng'),
(N'GEAR 18', N'Bàn phím HyperX Alloy Origins RGB', N'Bản phím', 1835000, 6, 24, N'HyperX', null, N'PS26555', N'Còn hàng'),
(N'GEAR 19', N'Bàn phím Keychron V2 Full Assembled Knob Carbon Black HotSwap', N'Bản phím', 1990000, 17, 12, N'Keychron', null, N'PS26555', N'Còn hàng'),
(N'GEAR 20', N'Bàn phím không dây Keychron K3 Pro RGB HotSwap Gateron', N'Bản phím', 2690000, 15, 12, N'Keychron', null, N'PS26555', N'Còn hàng');

insert into KhachHang(SDT, HoTen, DiaChi, Quan, ThanhPho) values 
(N'0198765432', N'Nguyễn Văn Tèo', N'25 Hai Bà Trưng', N'3', N'TPHCM');
insert into KhachHang(SDT, HoTen, DiaChi, Quan, ThanhPho) values 
(N'0298765432', N'Bành Thị Bưởi', N'214 Nguyễn Xí', N'Bình Thạnh', N'TPHCM');
insert into KhachHang(SDT, HoTen, DiaChi, Quan, ThanhPho) values 
(N'0398765432', N'Bùi Văn Nhon', N'75/7 Phan Huy Ích', N'12', N'TPHCM');

insert into HoaDon (NgayTaoHD, NguoiBan, TongTien, MaKH) values
('2023-04-10', N'PS26524', 6380000, 1);
insert into HoaDon (NgayTaoHD, NguoiBan, TongTien, MaKH) values
('2023-04-27', N'PS26548', 27990000, 2);
insert into HoaDon (NgayTaoHD, NguoiBan, TongTien, MaKH) values
('2023-05-04', N'PS26562', 998000, 3);

insert into HDCT (MaHD, TenLK, MaLK, SoLuong, DonGiaLK) values
(1, N'Bàn phím cơ không dây Keychron K3 Pro RGB HotSwap Gateron Low Profile', N'GEAR 20', 1, 2690000),
(1, N'Chuột Gaming không dây Razer Basilisk V3 Pro', N'GEAR 10', 1, 3690000),
(2, N'Màn hình cong LG UltraWide 38 Inch IPS 144Hz', N'MONITOR 15', 1, 27990000),
(3, N'Ram PC Corsair Vengeance LPX 8GB 3200MHz DDR4', N'RAM PC/LAPTOP 11', 2, 998000);
-- **************************************************************************************************


select * from AccLogin;
select * from HDCT;
select * from HoaDon;
select * from KhachHang;
select * from NhanVien;
select * from SanPham;
select * from RankKhachHang