/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.ThongKe;
import util.ConnectDB;

/**
 *
 * @author adim
 */
public class ThongKeService {
    private Connection conn;
    public ThongKeService(){
        conn = ConnectDB.getConnection();
    }
    public ArrayList<ThongKe> getData() throws SQLException{
        ArrayList<ThongKe> list = new ArrayList<>();
        String query = """
                      SELECT    dbo.Hoa_don.ngay_tao, dbo.Hoa_don.hinh_thuc_thanh_toan, dbo.Hoa_don_chi_tiet.thanh_tien, dbo.Hoa_don_chi_tiet.so_luong, dbo.San_pham_chi_tiet.ten_san_pham_chi_tiet, 
                                             dbo.San_pham_chi_tiet.ma_san_pham_chi_tiet, dbo.Hoa_don_chi_tiet.don_gia
                       FROM         dbo.Hoa_don INNER JOIN
                                             dbo.Hoa_don_chi_tiet ON dbo.Hoa_don.ID = dbo.Hoa_don_chi_tiet.id_hoa_don INNER JOIN
                                             dbo.San_pham_chi_tiet ON dbo.Hoa_don_chi_tiet.id_san_pham_chi_tiet = dbo.San_pham_chi_tiet.ID
                       """;
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(query);
        
        while(rs.next()){
            ThongKe tk = new ThongKe();
            
                tk.setNgay(rs.getDate("ngay_tao"));
                tk.setMaSP(rs.getString("ma_san_pham_chi_tiet"));
                tk.setTenSP(rs.getString("ten_san_pham_chi_tiet"));
                tk.setSoLuong(rs.getInt("so_luong"));
                tk.setGia(rs.getBigDecimal("don_gia"));
                tk.setHinhThuc(rs.getString("hinh_thuc_thanh_toan"));
                tk.setTongTien(rs.getBigDecimal("thanh_tien"));
                list.add(tk);
        }
         return list;
    }
   
    public ArrayList<ThongKe> search(String date) throws SQLException {
    ArrayList<ThongKe> list = new ArrayList<>();
    String query = """
                   SELECT 
                             dbo.Hoa_don.ngay_tao, 
                             dbo.San_pham_chi_tiet.ten_san_pham_chi_tiet, 
                             dbo.Hoa_don_chi_tiet.so_luong, 
                              dbo.Hoa_don.hinh_thuc_thanh_toan, 
                              dbo.San_pham_chi_tiet.ma_san_pham_chi_tiet, 
                              dbo.Hoa_don_chi_tiet.don_gia,
                              dbo.Hoa_don_chi_tiet.thanh_tien
                              FROM         dbo.Hoa_don INNER JOIN
                               dbo.Hoa_don_chi_tiet ON dbo.Hoa_don.ID = dbo.Hoa_don_chi_tiet.id_hoa_don INNER JOIN
                               dbo.San_pham_chi_tiet ON dbo.Hoa_don_chi_tiet.id_san_pham_chi_tiet = dbo.San_pham_chi_tiet.ID
                               WHERE 
                               CONVERT(date, dbo.Hoa_don.ngay_tao) = ?
                       
                   """;
     try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setString(1, date); // Tìm kiếm theo ngày
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ThongKe tk = new ThongKe();
            tk.setNgay(rs.getDate("ngay_tao"));
            tk.setMaSP(rs.getString("ma_san_pham_chi_tiet"));
            tk.setTenSP(rs.getString("ten_san_pham_chi_tiet"));
            tk.setSoLuong(rs.getInt("so_luong"));
            tk.setGia(rs.getBigDecimal("don_gia"));
            tk.setHinhThuc(rs.getString("hinh_thuc_thanh_toan"));
            tk.setTongTien(rs.getBigDecimal("thanh_tien"));
            list.add(tk);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
    return list;
}
}
