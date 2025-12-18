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
                    String[] parts= line.split(",");//"1,Gold,2312"->"1","Gold","2312"

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
                        // Eğer geçerli emtia ve gün ise diziye kaydediyoruz
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
        // Hatalı giriş check
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
    // 3. Bir emtianın belirli gün aralığındaki karı (her ayın)
    public static int commodityProfitInRange(String commodity, int from, int to) {
        // Emtia isminden indexi bul
        int CommodityIndex =-1;
        for(int i=0; i<COMMS; i++) {
            if(commodities[i].equals(commodity)) {
                CommodityIndex = i;
                break;
            }
        }
        // Hata kontrolleri
        if (CommodityIndex == -1 || from < 1 || to > DAYS || from > to) {
            return -99999;
        }
        int totalProfit = 0;
        // any month dediği için 12 ayı da geziyoz
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from; d <= to; d++) {
                totalProfit += profitData[m][CommodityIndex][d - 1];
            }
        }
        return totalProfit;
    }
    // 4. Bir ayın en karlı gününü bulma
    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return -1; //doğru ay girme checki
        }
        int maxProfit =0;
        int bestDay = -1;

        // 28 günü kontrol ediyoruz
        for (int d = 0; d < DAYS; d++) {
            int dailyTotal = 0;
            // O günün toplam karını hesapla tüm emtilar için
            for (int c = 0; c < COMMS; c++) {
                dailyTotal += profitData[month][c][d];
            }
            if (dailyTotal > maxProfit) {
                maxProfit = dailyTotal;
                bestDay= d + 1; // Index 0 ise gün 1
            }
        }
        return bestDay;
    }
    // 5.  emtia için en karlı ayı bulma
    public static String bestMonthForCommodity(String comm) {
        int commIndex = -1; //commodityi bulma
        for(int i=0; i<COMMS; i++) {
            if(commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex ==-1) return "INVALID COMMODITY";
        int maxProfit =0;
        int bestMonthIndex = -1;
        // 12 ayı geziyoz
        for (int m = 0; m < MONTHS; m++) {
            int monthTotal = 0;
            // O aydaki toplam karı hesapla
            for (int d = 0; d < DAYS; d++) {
                monthTotal += profitData[m][commIndex][d];
            }
            if (monthTotal > maxProfit) {
                maxProfit = monthTotal;
                bestMonthIndex = m;
            }
        }
        return months[bestMonthIndex];
    }
    // 6. Bir emtianın en uzun süre zarar ettiği zamanı bulur
    public static int consecutiveLossDays(String comm) {
        int comIndex = -1;
        for(int i=0; i<COMMS; i++) {
            if(commodities[i].equals(comm)) {
                comIndex = i;
                break;
            }
        }
        if (comIndex == -1) return -1;
        int maxStreak = 0;
        int currentStreak = 0;

        // Yıllık tüm günleri sırayla geziyoz
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                int profit = profitData[m][comIndex][d];//

                if (profit < 0) {
                    // Zarar varsa seriyi artır
                    currentStreak++;
                } else {
                    // Kar varsa seri bozuldu max ile kıyasla ve sıfırla
                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                    currentStreak = 0; //seri bozuldu 0 demek
                }
            }
        }
        // Döngü bittiğinde son seri en büyük olabilir check et
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }

        return maxStreak;
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