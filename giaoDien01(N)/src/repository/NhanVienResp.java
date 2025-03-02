/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author DUNG
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NhanVienResp {

    private Integer id;
    private String maNhanVien;
    private String hoTen;
    private Double luong;
    private String sdt;
    private String email;
    private String diaChi;
    private Date ngaySinh;
    private Boolean gioiTinh;
    private Date ngayBatDau;
    private String matKhau;

    private Integer trangThai;

    private String maChucVu;
    private String tenChucVu;

    public NhanVienResp(String maNhanVien, String hoTen, Double luong, String sdt, String email, String diaChi, Date ngaySinh, Boolean gioiTinh, Date ngayBatDau, String matKhau, Integer trangThai, String maChucVu) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.luong = luong;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.ngayBatDau = ngayBatDau;
        this.matKhau = matKhau;
        this.trangThai = trangThai;
        this.maChucVu = maChucVu;
    }

    public Object[] toRowTable(int stt){
          
        return new Object[]{
            stt,maNhanVien,hoTen,luong,sdt,email,diaChi,ngaySinh,gioiTinh==true?"Nam":"Nữ",ngayBatDau,matKhau,maChucVu.equalsIgnoreCase("NV")?"Nhân viên":"Quản lý",trangThai==0?"Nghỉ việc":"Đang làm"
        };
    }

   
}
