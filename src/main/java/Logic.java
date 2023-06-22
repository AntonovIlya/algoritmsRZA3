public class Logic {
    private CurrentF cF;
    private OutputData od = new OutputData();
    private boolean FirstStep;
    private boolean SecondStep;
    private boolean ThirdStep;
    private boolean[] flags = new boolean[5];
    private int[] counts = {0, 0, 0, 0};
    // timeouts PuskOrg, FirstStep, SecondStep, ThirdStep
    private int[] timeouts = {300, 20, 520, 1500};
    private double[] currentPhase = {0, 0, 0};
    private boolean flag;

    public Logic(CurrentF cF) {

        this.cF = cF;
    }

    public void log() {
        flag = puskOrg();
        if (!flags[4]) {
            tripSteps();
            stepsLogic();
            timers();
        }
        od.process(flags[4]);
    }

    private boolean tripFirstStep(double r, double x) {
        double X1 = -0.13 * r;
        double X2 = -3.3 * r;
        double X3 = 6.83 * r - 50.2;
        double X4 = -0.0875 * r + 71.67;
        double X5 = 6.83 * r + 50.2;
        return ((x > X1) && (x > X2) && (x > X3) && (x < X4) && (x < X5));
    }

    private boolean tripSecondStep(double r, double x) {
        double X1 = -0.13 * r;
        double X2 = -3.3 * r;
        double X3 = 6.83 * r - 200.8;
        double X4 = -0.0875 * r + 135.77;
        double X5 = 6.83 * r + 200.8;
        return ((x > X1) && (x > X2) && (x > X3) && (x < X4) && (x < X5));
    }

    private boolean tripThirdStep(double r, double x) {
        double X1 = -0.13 * r;
        double X2 = -3.3 * r;
        double X3 = 6.83 * r - 502.005;
        double X4 = -0.0875 * r + 217.195;
        double X5 = 6.83 * r + 502.005;
        return ((x > X1) && (x > X2) && (x > X3) && (x < X4) && (x < X5));
    }


    private boolean puskOrg() {
        boolean flag1 = cF.polarFormCurrentsPositiveSeq[1][0] - currentPhase[0] > 0.00749;
        boolean flag2 = cF.polarFormCurrentsPositiveSeq[1][1] - currentPhase[1] > 0.00749;
        boolean flag3 = cF.polarFormCurrentsPositiveSeq[1][2] - currentPhase[2] > 0.00749;
        currentPhase[0] = cF.polarFormCurrentsPositiveSeq[1][0];
        currentPhase[1] = cF.polarFormCurrentsPositiveSeq[1][1];
        currentPhase[2] = cF.polarFormCurrentsPositiveSeq[1][2];
        return (flag1 || flag2 || flag3);
    }

    private void tripSteps() {
        FirstStep = tripFirstStep(cF.FromPolarToExponentialFormZ()[0][0], cF.FromPolarToExponentialFormZ()[1][0])
                || tripFirstStep(cF.FromPolarToExponentialFormZ()[0][1], cF.FromPolarToExponentialFormZ()[1][1])
                || tripFirstStep(cF.FromPolarToExponentialFormZ()[0][2], cF.FromPolarToExponentialFormZ()[1][2]);

        SecondStep = tripSecondStep(cF.FromPolarToExponentialFormZ()[0][0], cF.FromPolarToExponentialFormZ()[1][0])
                || tripSecondStep(cF.FromPolarToExponentialFormZ()[0][1], cF.FromPolarToExponentialFormZ()[1][1])
                || tripSecondStep(cF.FromPolarToExponentialFormZ()[0][2], cF.FromPolarToExponentialFormZ()[1][2]);

        ThirdStep = tripThirdStep(cF.FromPolarToExponentialFormZ()[0][0], cF.FromPolarToExponentialFormZ()[1][0])
                || tripThirdStep(cF.FromPolarToExponentialFormZ()[0][1], cF.FromPolarToExponentialFormZ()[1][1])
                || tripThirdStep(cF.FromPolarToExponentialFormZ()[0][2], cF.FromPolarToExponentialFormZ()[1][2]);
    }

    private void timers() {

        //run timer PuskOrg
        if (flags[0] && ++counts[0] > (timeouts[0] * 4)) {
            flags[0] = false;
            counts[0] = 0;
        }
        //run timer first step
        if (flags[1] && ++counts[1] > (timeouts[1] * 4)) {
            flags[1] = false;
            counts[1] = 0;
        }

        //run timer second step
        if (flags[2] && ++counts[2] > (timeouts[2] * 4)) {
            flags[2] = false;
            counts[2] = 0;
        }

        //run timer third step
        if (flags[3] && ++counts[3] > (timeouts[3] * 4)) {
            flags[3] = false;
            counts[3] = 0;
        }
    }

    private void stepsLogic() {
        // Проверка попадания в область срабатывания
        if (FirstStep) {
            if (flag) {
                // run timeout step
                flags[1] = true;
                // run timeout PuskOrg
                flags[0] = true;
            }
            if (counts[1] == (timeouts[1] * 4)) {
                flags[4] = true;
            }
        } else {
            flags[1] = false;
            counts[1] = 0;
        }

        // Проверка попадания в область срабатывания
        if (SecondStep) {
            if (flag) {
                // run timeout step
                flags[2] = true;
                // run timeout PuskOrg
                flags[0] = true;
            }
            if (counts[2] == (timeouts[2] * 4)) {
                flags[4] = true;
            }

        } else {
            flags[2] = false;
            counts[2] = 0;
        }
        if (ThirdStep) {
            if (flag) {
                // run timeout step
                flags[3] = true;
                // run timeout PuskOrg
                flags[0] = true;
            }
            if (counts[3] == (timeouts[3] * 4)) {
                flags[4] = true;
            }
        } else {
            flags[3] = false;
            counts[3] = 0;
        }
    }

}

