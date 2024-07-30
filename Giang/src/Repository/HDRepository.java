/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

/**
 *
 * @author Admin
 */
import entity.HoaDon;
import entity.HoaDonChiTiet;
import java.awt.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import utils.DBContext;
public class HDRepository {
    Connection conn;
    public HDRepository() {
        try {
            conn = DBContext.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<HoaDon> findall() {
        ArrayList<HoaDon> listHD = new ArrayList<>();
        String sql = "SELECT * FROM Hoa_don";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {         
                int id = rs.getInt("ID");
                String ma = rs.getString("ma_hoa_don");
                int idKH = rs.getInt("id_khach_hang");
                int idNV = rs.getInt("id_nhan_vien");
                String ten = rs.getString("ten_khach_hang");
                String diaChi = rs.getString("dia_chi_khach_hang");
                String sdt = rs.getString("so_dt");
                String ngay = rs.getString("ngay_tao");
                Double tongTien = rs.getDouble("tong_tien");
                String hinhThucTT = rs.getString("hinh_thuc_thanh_toan");
                int idVoucher = rs.getInt("id_voucher");
                int trangThai = rs.getInt("trang_thai");
                HoaDon hd = new HoaDon(id, ma, idKH, idNV, ten, diaChi, sdt, ngay, tongTien, hinhThucTT, idVoucher, trangThai);
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }
    public ArrayList<HoaDonChiTiet> findChiTietByidHD(int idHD) {
        ArrayList<HoaDonChiTiet> listHDCT = new ArrayList<>();
        String sql = "select * from Hoa_don_chi_tiet where id_hoa_don = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setId(rs.getInt("id"));
                hdct.setMa(rs.getString("ma_hoa_don_chi_tiet"));
                hdct.setIdHD(rs.getInt("id_hoa_don"));
                hdct.setIdSP(rs.getInt("id_san_pham_chi_tiet"));
                hdct.setSl(rs.getInt("so_luong"));
                hdct.setDonGia(rs.getDouble("don_gia"));
                hdct.setThanhTien(rs.getDouble("thanh_tien"));
                hdct.setTrangThai(rs.getInt("trang_thai"));
                listHDCT.add(hdct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHDCT;
    }
    public ArrayList<HoaDon> search(String keyword) {
        ArrayList<HoaDon> listHD = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Hoa_don WHERE ma_hoa_don LIKE ? OR ten_khach_hang LIKE ? OR so_dt LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
                String key = "%" +keyword+ "%";
                ps.setObject(1, key);
                ps.setObject(2, key);
                ps.setObject(3, key);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("ID");
                String ma = rs.getString("ma_hoa_don");
                int idKH = rs.getInt("id_khach_hang");
                int idNV = rs.getInt("id_nhan_vien");
                String ten = rs.getString("ten_khach_hang");
                String diaChi = rs.getString("dia_chi_khach_hang");
                String sdt = rs.getString("so_dt");
                String ngay = rs.getString("ngay_tao");
                Double tongTien = rs.getDouble("tong_tien");
                String hinhThucTT = rs.getString("hinh_thuc_thanh_toan");
                int idVoucher = rs.getInt("id_voucher");
                int trangThai = rs.getInt("trang_thai");
                HoaDon hd = new HoaDon(id, ma, idKH, idNV, ten, diaChi, sdt, ngay, tongTien, hinhThucTT, idVoucher, trangThai);
                listHD.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }
    public ArrayList<HoaDon> fillHoaDon(Integer trangThai, String hinhThucTT, Double tongTienMin, Double tongTienMax, Integer thang, Integer nam) {
        ArrayList<HoaDon> listHD = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Hoa_don WHERE 1=1");
        if (trangThai != null) {
            sql.append(" AND trang_thai = ?");
        }
        if (hinhThucTT != null && !hinhThucTT.isEmpty()) {
            sql.append(" AND hinh_thuc_thanh_toan = ?");
        }
        if (tongTienMin != null) {
            sql.append(" AND tong_tien > ?");
        }
        if (tongTienMax != null) {
            sql.append(" AND tong_tien <= ?");
        }
        if (thang != null) {
            sql.append(" AND MONTH(ngay_tao) = ?");
        }
        if (nam != null) {
            sql.append(" AND YEAR(ngay_tao) = ?");
        }
        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int i = 1;
            if (trangThai != null) {
                ps.setInt(i++, trangThai);
            }
            if (hinhThucTT != null && !hinhThucTT.isEmpty()) {
                ps.setString(i++, hinhThucTT);
            } //loi
            if (tongTienMin != null) {
                ps.setDouble(i++, tongTienMin);
            }
            if (tongTienMax != null) {
                ps.setDouble(i++, tongTienMax);
            }
            if (thang != null) {
                ps.setInt(i++, thang);
            }
            if (nam != null) {
                ps.setInt(i++, nam);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon hd = new HoaDon();
                    hd.setId(rs.getInt("ID"));
                    hd.setMa(rs.getString("ma_hoa_don"));
                    hd.setIdKH(rs.getInt("id_khach_hang"));
                    hd.setIdNV(rs.getInt("id_nhan_vien"));
                    hd.setTen(rs.getString("ten_khach_hang"));
                    hd.setDiaChi(rs.getString("dia_chi_khach_hang"));
                    hd.setSdt(rs.getString("so_dt"));
                    hd.setNgay(rs.getString("ngay_tao"));
                    hd.setTongTien(rs.getDouble("tong_tien"));
                    hd.setHinhThucTT(rs.getString("hinh_thuc_thanh_toan"));
                    hd.setIdVoucher(rs.getInt("id_voucher"));
                    hd.setTrangThai(rs.getInt("trang_thai"));
                    listHD.add(hd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return listHD;
        }

    
    public String getTenNV(int idNV) {
        String tenNV ="";
        try {
            String sql = "SELECT ho_ten  FROM Nhan_vien WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idNV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {                
              tenNV = rs.getString("ho_ten");  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenNV;
    }
    public Double getGiaTriVoucher(int idV) {
        Double giaTriVoucher = 0.0;
        try {
            String sql = "SELECT gioi_han_giam_toi_thieu FROM Voucher WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                giaTriVoucher = rs.getDouble("gioi_han_giam_toi_thieu");
            }
        } catch (Exception e) {
        }
        return giaTriVoucher;
    }
    public String getTenSPCT(int idSPCT) {
        String tenSPCT ="";
        try {
            String sql ="SELECT ten_san_pham_chi_tiet FROM San_pham_chi_tiet WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSPCT);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                tenSPCT = rs.getString("ten_san_pham_chi_tiet");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenSPCT;
    }
}
