package multiThreading;

import java.util.Scanner;
import java.util.Random;

public class MatrixMultiplicationThread {
    static int step_i = 0; // İşlem adım sayacı
    static int[][] matC; // Sonuç matrisi

    // Matris çarpımı için worker sınıfı, Runnable arayüzünü uygular
    static class Worker implements Runnable {
        int[][] matA;
        int[][] matB;
        int i;
        int colum1;
        int colum2;
        int row1;
        int row2;

        // Worker sınıfı için constructor
        Worker(int i, int[][] matA, int[][] matB, int colum1, int colum2, int row1, int row2) {
            this.i = i; 
            this.matA = matA; 
            this.matB = matB; 
            this.colum1 = colum1; // A matrisinin sütun sayısı
            this.colum2 = colum2; // B matrisinin sütun sayısı
            this.row1 = row1; // A matrisinin satır sayısı
            this.row2 = row2; // B matrisinin satır sayısı
        }

        // run metodu,worker thread tarafından çalıştırılır
        @Override
        public void run() {
            if (colum1 != row2) { // Matris boyut kontrolü
                System.out.println("Lütfen girdiğiniz matrisin boyutunu kontrol ediniz!\n");
            } else {
                // Matris çarpımı işlemi
                for (int j = 0; j < colum2; j++) {
                    for (int k = 0; k < colum1; k++) {
                        matC[i][j] += matA[i][k] * matB[k][j];
                    }
                }
            }
        }
    }

    // Ana metot
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Matris değerlerini elden mi girmek istersiniz yoksa rastgele mi oluşturulsun? (elden/rastgele): ");
        String choice = input.nextLine(); // Kullanıcı seçimi

        int[][] matA, matB;
        int row1, colum1, row2, colum2;

        // Kullanıcı matris değerlerini elle girmek isterse
        if (choice.equalsIgnoreCase("elden")) {
            System.out.print("Matris A için satır değerini giriniz: ");
            row1 = input.nextInt();
            System.out.print("Matris A için sütun değerini giriniz: ");
            colum1 = input.nextInt();
            matA = new int[row1][colum1]; // A matrisini oluştur
            System.out.println("Matris A'nın değerlerini giriniz: ");
            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < colum1; j++) {
                    matA[i][j] = input.nextInt(); // A matrisi elemanları
                }
            }

            System.out.print("Matris B için satır değerini giriniz: ");
            row2 = input.nextInt();
            System.out.print("Matris B için sütun değerini giriniz: ");
            colum2 = input.nextInt();
            matB = new int[row2][colum2]; // B matrisini oluştur
            System.out.println("Matris B'nin değerlerini giriniz: ");
            for (int i = 0; i < row2; i++) {
                for (int j = 0; j < colum2; j++) {
                    matB[i][j] = input.nextInt(); // B matrisi elemanları
                }
            }
        } else { // Kullanıcı rastgele matris oluşturmayı seçerse
            System.out.print("Rastgele matris boyutu için satır sayısını giriniz: ");
            row1 = input.nextInt();
            System.out.print("Rastgele matris boyutu için sütun sayısını giriniz: ");
            colum1 = input.nextInt();
            row2 = colum1;  // B'nin satır sayısı A'nın sütun sayısına eşit olmalıdır
            System.out.print("Rastgele matris B için sütun sayısını giriniz: ");
            colum2 = input.nextInt();

            matA = new int[row1][colum1]; // A matrisini oluştur
            matB = new int[row2][colum2]; // B matrisini oluştur
            Random rand = new Random();

            // Matris A ve B'yi rastgele doldur
            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < colum1; j++) {
                    matA[i][j] = rand.nextInt(10);  // 0-9 arasında rastgele sayılar
                }
            }
            for (int i = 0; i < row2; i++) {
                for (int j = 0; j < colum2; j++) {
                    matB[i][j] = rand.nextInt(10);  // 0-9 arasında rastgele sayılar
                }
            }
        }

        // Matris A'yı ekrana yazdırma
        System.out.println("Matris A:");
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < colum1; j++) {
                System.out.print(matA[i][j] + " ");
            }
            System.out.println();
        }

        // Matris B'yi ekrana yazdırma
        System.out.println("Matris B:");
        for (int i = 0; i < row2; i++) {
            for (int j = 0; j < colum2; j++) {
                System.out.print(matB[i][j] + " ");
            }
            System.out.println();
        }

        // Thread'leri belirtelim
        matC = new int[row1][colum2]; // Sonuç matrisi oluşturma
        Thread[] threads = new Thread[row1]; // Thread dizisi

        // Her bir satır için thread oluşturacak yapı
        long startTime = System.nanoTime(); // Başlangıç zamanı
        for (int i = 0; i < row1; i++) {
            threads[i] = new Thread(new Worker(i, matA, matB, colum1, colum2, row1, row2)); // Yeni thread oluşturma
            threads[i].start(); // Thread başlatma
            step_i++;
        }

        // Tüm thread'lerin çalışmayı bitirmesi için bekletme
        for (int i = 0; i < row1; i++) {
            try {
                threads[i].join(); // Thread'lerin bitmesini bekleme
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.nanoTime(); // Bitiş zamanı
        long duration = (endTime - startTime) / 1000000; // milisaniye cinsinden süre

        // Sonuç matrisini ekranda görüntüleme
        System.out.println("A ve B Matrislerinin Çarpımı:");
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < colum2; j++) {
                System.out.print(matC[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Thread'li Matris Çarpımı Süresi: " + duration + " milisaniye"); // İşlem süresini ekrana yazdırma
    }
}
