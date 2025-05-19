package uts.oop.c.pkg2301010288;

import java.io.*;
import java.util.Scanner;

class Catatan {
    String isi;

    public Catatan(String isi) {
        this.isi = isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getIsi() {
        return isi;
    }
}

public class UTSOOPC2301010288 {
    static Catatan[] catatanList = new Catatan[100];
    static int jumlahCatatan = 0;
    static Scanner input = new Scanner(System.in);
    static final String FILE_NAME = "catatan.txt";

    public static void main(String[] args) {
        // Membaca catatan dari file saat pertama kali program dijalankan
        bacaCatatanDariFile();

        int pilihan;

        do {
            System.out.println("\n--- Menu Aplikasi Pencatatan ---");
            System.out.println("1. Tambah Catatan");
            System.out.println("2. Tampilkan Semua Catatan");
            System.out.println("3. Ubah Catatan");
            System.out.println("4. Hapus Catatan");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1-5): ");

            while (!input.hasNextInt()) {
                System.out.println("Input harus angka!");
                System.out.print("Pilih menu (1-5): ");
                input.next(); // skip input yang salah
            }

            pilihan = input.nextInt();
            input.nextLine(); // konsumsi newline

            switch (pilihan) {
                case 1:
                    tambahCatatan();
                    break;
                case 2:
                    tampilkanCatatan();
                    break;
                case 3:
                    ubahCatatan();
                    break;
                case 4:
                    hapusCatatan();
                    break;
                case 5:
                    System.out.println("Terima kasih telah menggunakan aplikasi.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 5);
    }

    static void tambahCatatan() {
        if (jumlahCatatan >= catatanList.length) {
            System.out.println("Penyimpanan catatan penuh.");
            return;
        }
        System.out.print("Masukkan isi catatan: ");
        String isi = input.nextLine();
        catatanList[jumlahCatatan++] = new Catatan(isi);
        System.out.println("Catatan berhasil ditambahkan.");
        simpanCatatanKeFile();  // Simpan setelah tambah
    }

    static void tampilkanCatatan() {
        System.out.println("\nDaftar Catatan:");
        if (jumlahCatatan == 0) {
            System.out.println("Belum ada catatan.");
            return;
        }
        for (int i = 0; i < jumlahCatatan; i++) {
            System.out.println((i + 1) + ". " + catatanList[i].getIsi());
        }
    }

    static void ubahCatatan() {
        tampilkanCatatan();
        if (jumlahCatatan == 0) return;

        System.out.print("Masukkan nomor catatan yang ingin diubah: ");
        if (!input.hasNextInt()) {
            System.out.println("Input harus angka!");
            input.next(); // skip input yang salah
            return;
        }
        int index = input.nextInt();
        input.nextLine(); // konsumsi newline
        if (index >= 1 && index <= jumlahCatatan) {
            System.out.print("Masukkan isi baru: ");
            String isiBaru = input.nextLine();
            catatanList[index - 1].setIsi(isiBaru);
            System.out.println("Catatan berhasil diubah.");
            simpanCatatanKeFile();  // Simpan setelah ubah
        } else {
            System.out.println("Nomor catatan tidak valid.");
        }
    }

    static void hapusCatatan() {
        tampilkanCatatan();
        if (jumlahCatatan == 0) return;

        System.out.print("Masukkan nomor catatan yang ingin dihapus: ");
        if (!input.hasNextInt()) {
            System.out.println("Input harus angka!");
            input.next(); // skip input yang salah
            return;
        }
        int index = input.nextInt();
        input.nextLine(); // konsumsi newline
        if (index >= 1 && index <= jumlahCatatan) {
            for (int i = index - 1; i < jumlahCatatan - 1; i++) {
                catatanList[i] = catatanList[i + 1];
            }
            catatanList[jumlahCatatan - 1] = null; // hapus terakhir
            jumlahCatatan--;
            System.out.println("Catatan berhasil dihapus.");
            simpanCatatanKeFile();  // Simpan setelah hapus
        } else {
            System.out.println("Nomor catatan tidak valid.");
        }
    }

    static void simpanCatatanKeFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < jumlahCatatan; i++) {
                writer.println(catatanList[i].getIsi());
            }
            System.out.println("(Catatan disimpan ke file)");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan catatan: " + e.getMessage());
        }
    }

    static void bacaCatatanDariFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (jumlahCatatan < catatanList.length) {
                    catatanList[jumlahCatatan++] = new Catatan(line);
                }
            }
            if (jumlahCatatan > 0) {
                System.out.println("(Catatan berhasil dimuat dari file)");
            }
        } catch (FileNotFoundException e) {
            // File belum ada, tidak masalah
            System.out.println("(Belum ada file catatan, mulai baru)");
        } catch (IOException e) {
            System.out.println("Gagal membaca catatan: " + e.getMessage());
        }
    }
}
