public class Main {
    private static InputData inputData = new InputData();

    public static void main(String[] args) {
        System.out.println(Time.getCurrentHour());
        createCharts();
        createStepOne();
        createStepTwo();
        createStepThree();
        inputData.readFile();
        System.out.println("Время работы программы: " + Time.getCurrentHour() + " мс");
    }

    private static void createCharts() {
        Charts.createAnalogChart("UA", 0);
        Charts.addSeries("UA, кV", 0, 0);
        Charts.addSeries("UAModule, kV", 0, 1);

        Charts.createAnalogChart("UB", 1);
        Charts.addSeries("UB, kV", 1, 0);
        Charts.addSeries("UBModule, kV", 1, 1);

        Charts.createAnalogChart("UC", 2);
        Charts.addSeries("UC, kV", 2, 0);
        Charts.addSeries("UCModule, kV", 2, 1);

        Charts.createAnalogChart("IA", 3);
        Charts.addSeries("IA, kA", 3, 0);
        Charts.addSeries("IAModule, kA", 3, 1);

        Charts.createAnalogChart("IB", 4);
        Charts.addSeries("IB, kA", 4, 0);
        Charts.addSeries("IBModule, kA", 4, 1);

        Charts.createAnalogChart("IC", 5);
        Charts.addSeries("IC, kA", 5, 0);
        Charts.addSeries("ICModule, kA", 5, 1);

        Charts.createAnalogChart("Signal", 6);
        Charts.addSeries("Signal", 6, 0);

        ChartsXY.createAnalogChart("X", 0);
        ChartsXY.addSeries("ZI", 0, 0);
        ChartsXY.addSeries("ZII", 0, 1);
        ChartsXY.addSeries("ZIII", 0, 2);
        ChartsXY.addSeries("Zab", 0, 3);
        ChartsXY.addSeries("Zbc", 0, 4);
        ChartsXY.addSeries("Zca", 0, 5);
    }

    private static void createStepOne() {
        for (Double i : range(0, 7.21, 0.1)) {
            ChartsXY.addAnalogData(0, 0, i, (-0.13 * i));
        }
        for (Double i : range(7.21, 17.63, 0.1)) {
            ChartsXY.addAnalogData(0, 0, i, (6.83 * i - 50.2));
        }
        for (Double i : range(3.103, 17.63, 0.1)) {
            ChartsXY.addAnalogData(0, 0, i, (-0.0875 * i + 71.67));
        }
        for (Double i : range(-4.94, 0, 0.1)) {
            ChartsXY.addAnalogData(0, 0, i, (-3.33 * i));
        }
        for (Double i : range(-4.94, 3.103, 0.1)) {
            ChartsXY.addAnalogData(0, 0, i, (6.83 * i + 50.2));
        }
    }

    private static void createStepTwo() {
        for (Double i : range(0, 28.85, 0.1)) {
            ChartsXY.addAnalogData(0, 1, i, (-0.13 * i));
        }
        for (Double i : range(28.85, 48.77, 0.1)) {
            ChartsXY.addAnalogData(0, 1, i, (6.83 * i - 200.8));
        }
        for (Double i : range(-19.764, -9.4, 0.1)) {
            ChartsXY.addAnalogData(0, 1, i, (6.83 * i + 200.8));
        }
        for (Double i : range(-9.4, 48.77, 0.1)) {
            ChartsXY.addAnalogData(0, 1, i, (-0.0875 * i + 135.77));
        }
        for (Double i : range(-19.764, 0, 0.1)) {
            ChartsXY.addAnalogData(0, 1, i, (-3.33 * i));
        }

    }

    private static void createStepThree() {
        for (Double i : range(0, 72.127, 0.1)) {
            ChartsXY.addAnalogData(0, 2, i, -0.13 * i);
        }
        for (Double i : range(72.127, 103.97, 0.1)) {
            ChartsXY.addAnalogData(0, 2, i, (6.83 * i - 502.005));
        }
        for (Double i : range(-41.172, 103.97, 0.1)) {
            ChartsXY.addAnalogData(0, 2, i, (-0.0875 * i + 217.195));
        }
        for (Double i : range(-49.41, -41.172, 0.1)) {
            ChartsXY.addAnalogData(0, 2, i, (6.83 * i + 502.005));
        }
        for (Double i : range(-49.41, 0, 0.1)) {
            ChartsXY.addAnalogData(0, 2, i, (- 3.33 * i));
        }

    }

    private static double[] range(double start, double end, double step) {
        int len = (int) ((end - start) / step);
        double[] range = new double[len];
        range[0] = start;
        for (int i = 1; i < len; i++) {
            start += step;
            range[i] = start;
        }
        return range;
    }
}
