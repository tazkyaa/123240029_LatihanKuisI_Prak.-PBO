/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package kopikita;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Pelanggan> daftarPelanggan = new ArrayList<>();
        List<PesananKopi> daftarPesanan = new ArrayList<>();
        boolean running = true;

        while (running) {
            System.out.println("\n=== SISTEM KASIR KEDAI KOPI - KopiKita ===");
            System.out.println("1. Tambah Pesanan");
            System.out.println("2. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = Integer.parseInt(sc.nextLine());

            if (pilihan == 1) {
                // Input nama
                System.out.print("\n--- Data Pelanggan ---");
                System.out.print("\nNama           : ");
                String nama = sc.nextLine();

                // Input & validasi nomor meja
                System.out.print("Nomor Meja     : ");
                String nomorMeja = sc.nextLine();
                if (!nomorMeja.matches("[0-9]+")) {
                    System.out.println(
                        "[ERROR] Nomor meja hanya boleh berisi angka!");
                    continue; // batalkan, kembali ke menu
                }

                // Input harga dasar & jenis kopi
                try {
                System.out.print("\n--- Data Pesanan ---");
                System.out.print("\nHarga Dasar    : ");
                double hargaDasar = Double.parseDouble(sc.nextLine());

                // Validasi harga dulu sebelum tanya jenis kopi
                if (hargaDasar <= 0) {
                    throw new DataPesananTidakValidException(
                        "Harga dasar harus lebih dari 0!");
                }

                System.out.print("Jenis Kopi (1. Regular / 2. Spesial): ");
                int jenis = Integer.parseInt(sc.nextLine());

                PesananKopi pesanan;
                if (jenis == 1) {
                    pesanan = new KopiRegular(hargaDasar);
                } else {
                    pesanan = new KopiSpesial(hargaDasar);
                }

                Pelanggan pelanggan = new Pelanggan(nama, nomorMeja);
                daftarPelanggan.add(pelanggan);
                daftarPesanan.add(pesanan);
                System.out.println("[OK] Pesanan berhasil ditambahkan!");

            } catch (DataPesananTidakValidException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }

            } else if (pilihan == 2) {
                running = false;
            }
        }

        // Tampilkan rekap
        System.out.println("\n=========== REKAP PESANAN ===========");
        for (int i = 0; i < daftarPelanggan.size(); i++) {
            Pelanggan p = daftarPelanggan.get(i);
            PesananKopi pesanan = daftarPesanan.get(i);

            System.out.println("Pelanggan : " + p.getNama());
            System.out.println("Pesanan   : " + pesanan.getNamaKopi());
            System.out.printf("Total     : Rp %.0f%n", 
                              pesanan.hitungTotalHarga());

            // Cek apakah pesanan bisa refill (polymorphism)
            if (pesanan instanceof Refill) {
                System.out.print("Refill    : ");
                ((Refill) pesanan).cetakInfoRefill();
            } else {
                System.out.println("Refill    : -");
            }
            System.out.println("--------------------------------------");
        }
        System.out.println("======================================");
    }
}
    