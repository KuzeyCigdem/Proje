// Main.java — Students version
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};

    public static int[][][] profitData = new int[MONTHS][COMMS][DAYS];
    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {
            // Okunacak dosyanın yolunu oluşturuyoruz (Örnnek: Data_Files/January.txt)
            String fileName = "Data_Files/" + months[m] + ".txt";
            File file = new File(fileName);
            try {
                // Dosyayı okumak için Scanner kullanıyoz
                Scanner sc = new Scanner(file);

                // İlk satırı geçiyoruz
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
                // Dosyanın sonuna gelene kadar satır satır okuyoz
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    // Satırı virgüle göre bölüyoruz
                    String[] parts= line.split(",");

                    if (parts.length == 3) {
                        // String verileri çeviriyoruz
                        int day = Integer.parseInt(parts[0].trim());
                        String commodityName = parts[1].trim();
                        int profit = Integer.parseInt(parts[2].trim());

                        // Emtianın arraydeki indexini bul
                        int commodityIndex = -1;
                        for (int i = 0; i < COMMS; i++) {
                            if (commodities[i].equals(commodityName)) {
                                commodityIndex = i;
                                break;
                            }
                        }

                        if (commodityIndex != -1 && day >= 1 && day <= DAYS) {
                            profitData[m][commodityIndex][day - 1] = profit;
                        }
                    }
                }
                sc.close();
            } catch (FileNotFoundException e) {
                // Dosya bulunamazsa proje kuralları gereği hata yazdırmıyoz

            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========


    // 1. Bir ayda en çok kar getiren emtia
    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }
        int maxProfit =0;
        int bestCommodityIndex = -1;
        // 5 emtiayı tek tek gezip o aydaki toplamlarına bak
        for (int c = 0; c < COMMS; c++) {
            int currentTotalProfit = 0;
            // O emtianın 28 günlük toplamını hesapla
            for (int d = 0; d < DAYS; d++) {
                currentTotalProfit += profitData[month][c][d];
            }
            // Eğer bu emtianın karı şu ana kadar bulduğumuz en büyükten büyükse güncelliyoruz
            if (currentTotalProfit > maxProfit) {
                maxProfit = currentTotalProfit;
                bestCommodityIndex = c;
            }
        }
        if (bestCommodityIndex == -1) return "No Data";
        return commodities[bestCommodityIndex] + " " + maxProfit;
    }

    // 2. Belirli bir günde tüm emtiaların toplam karı
    public static int totalProfitOnDay(int month, int day) {
        // Hatalı giriş kontrolü
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) {
            return -99999;
        }
        int total = 0;
        // O gün için 5 emtianın karını topluyoz
        for (int c = 0; c < COMMS; c++) {
            total += profitData[month][c][day - 1];
        }
        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}