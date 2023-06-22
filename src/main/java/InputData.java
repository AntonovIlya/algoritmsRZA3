import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InputData {
    private double[] coeffA = new double[6];
    private double[] coeffB = new double[6];
    private CurrentData cd = new CurrentData();
    private FourierFilter filter = new FourierFilter(cd);
    static String caseName = "KZ7";
    private int count2 = 0;

    public void readFile() {
        try {
            String path = "C:\\Users\\IlYHA_PC\\Desktop\\II_half-year\\Algorithms_RP\\LR3\\Лабораторная работа №3\\Опыты\\";
            File cfg = new File(path + caseName + ".cfg");
            FileReader fr1 = new FileReader(cfg);
            Scanner scanner1 = new Scanner(fr1);
            int count = 0;
            String[] lineData;
            while (scanner1.hasNextLine()) {
                if (count >= 2 && count <= 7) {
                    lineData = scanner1.nextLine().split(",");
                    coeffA[count - 2] = Double.parseDouble(lineData[5]);
                    coeffB[count - 2] = Double.parseDouble(lineData[6]);
                } else scanner1.nextLine();
                count++;
            }
            fr1.close();
            File dat = new File(path + caseName + ".dat");
            FileReader fr2 = new FileReader(dat);
            Scanner scanner2 = new Scanner(fr2);
            while (scanner2.hasNextLine()) {
                lineData = scanner2.nextLine().split(",");
                for (int i = 0; i < 6; i++) {
                    //cd.phABC[i] = Double.parseDouble(lineData[i + 2]) * coeffA[i] + coeffB[i];
                    Charts.addAnalogData(i, 0, cd.phABC[i]);
                }
                //filter.processing();
            }
            fr2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
