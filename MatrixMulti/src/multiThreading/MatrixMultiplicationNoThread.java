package multiThreading;

import java.util.Scanner;
import java.util.Random;

public class MatrixMultiplicationNoThread {
    static int[][] matC;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Matris değerlerini elden mi girmek istersiniz yoksa rastgele mi oluşturulsun? (elden/rastgele): ");
        String choice = input.nextLine();

        int[][] matA, matB;
        int row1, colum1, row2, colum2;
       

        // Kullancı matrisi kendi oluşturmak isterse if bloğu çalışır, rastgele girdisi yaparsa else bloğuna gidilir.
        if (choice.equalsIgnoreCase("elden")) {
        	
        	// A matrisinin satır ve sütun bilgisi alındıktan sonra matris değerleri de alınır.
            System.out.print("Matris A için satır değerini giriniz: ");
            row1 = input.nextInt();
            System.out.print("Matris A için sütun değerini giriniz: ");
            colum1 = input.nextInt();
            matA = new int[row1][colum1];
            System.out.println("Matris A'nın değerlerini giriniz: ");
            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < colum1; j++) {
                    matA[i][j] = input.nextInt();
                }
            }

            // B matrisinin satır ve sütun bilgisi alındıktan sonra matris değerleri de alınır.
            System.out.print("Matris B için satır değerini giriniz: ");
            row2 = input.nextInt();
            System.out.print("Matris B için sütun değerini giriniz: ");
            colum2 = input.nextInt();
            matB = new int[row2][colum2];
            System.out.println("Matris B'nin değerlerini giriniz: ");
            for (int i = 0; i < row2; i++) {
                for (int j = 0; j < colum2; j++) {
                    matB[i][j] = input.nextInt();
                }
            }
        } else {
        	// Random olarak oluşturulacak matrisin boyutu kullanıcıdan istenir.
            System.out.print("Rastgele matris boyutu için satır sayısını giriniz: ");
            row1 = input.nextInt();
            System.out.print("Rastgele matris boyutu için sütun sayısını giriniz: ");
            colum1 = input.nextInt();
            row2 = colum1;  // Çarpımın yapılabilmesi için B'nin satır sayısı A'nın sütun sayısına eşit olmalıdır
            System.out.print("Rastgele matris B için sütun sayısını giriniz: ");
            colum2 = input.nextInt();

            matA = new int[row1][colum1];
            matB = new int[row2][colum2];
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

        // Matris çarpımı hesaplama
        if (colum1 != row2) {// Matris çarpma şartını sağlanmazsa kullanıcıya geribildirim gönderilir.
            System.out.println("Lütfen girdiğiniz matrisin boyutunu kontrol ediniz!\n");
        } else {
            matC = new int[row1][colum2];
            long startTime = System.nanoTime();// Başlangıç zamanı tanımlanır.

            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < colum2; j++) {
                    for (int k = 0; k < colum1; k++) {
                        matC[i][j] += matA[i][k] * matB[k][j];
                    }
                }
            }

            long endTime = System.nanoTime();// Bitiş zamanı tanımlanır.
            long multiTime = (endTime - startTime) / 1000000; // milisaniye cinsinden süre

            // Sonuç matrisini ekranda görüntüleme
            System.out.println("A ve B Matrislerinin Çarpımı:");
            for (int i = 0; i < row1; i++) {
                for (int j = 0; j < colum2; j++) {
                    System.out.print(matC[i][j] + " ");
                }
                System.out.println();
            }

            System.out.println("Threadsiz Matris Çarpımı Süresi: " + multiTime + " milisaniye");
        }
    }
}

