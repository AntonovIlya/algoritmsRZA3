public class FourierFilter {
    private CurrentData cd;
    private CurrentF currentF = new CurrentF();
    private Logic logic = new Logic(currentF);
    private int step = 80;
    private double[][] phABC = new double[6][step]; //arrays current selections
    private short count = 0;

    public FourierFilter(CurrentData cd) {
        this.cd = cd;
    }

    public void processing() {
        for (int i = 0; i < 6; i++) {
            phABC[i][count] = cd.phABC[i];
        }
        fourier50(phABC);
        logic.log();
        if (++count >= step) count = 0;
    }

    //Discrete Fourier transform 1st harmonic
    private void fourier50(double[][] mas) {
        double[] Fx = new double[6];
        double[] Fy = new double[6];
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < step; i++) {
                Fx[j] += 0.025 * Math.sin(0.025 * i * Math.PI) * mas[j][i];
                Fy[j] += 0.025 * Math.cos(0.025 * i * Math.PI) * mas[j][i];

            }
            double F = Math.sqrt((Math.pow(Fx[j], 2) + Math.pow(Fy[j], 2)) * 0.5); // module
            Charts.addAnalogData(j, 1, F);

        }

        currentF.setPolarFormZ(shiftPhase(Fx, Fy));
        currentF.setPolarFormCurrentsPositiveSeq(PositiveSequenceCurrents(Fx, Fy));

        /*if (count2 != 10) {
            ChartsXY.addAnalogData(0, 3, currentF.FromPolarToExponentialFormZ()[0][0], currentF.FromPolarToExponentialFormZ()[1][0]);
            ChartsXY.addAnalogData(0, 4, currentF.FromPolarToExponentialFormZ()[0][1], currentF.FromPolarToExponentialFormZ()[1][1]);
            ChartsXY.addAnalogData(0, 5, currentF.FromPolarToExponentialFormZ()[0][2], currentF.FromPolarToExponentialFormZ()[1][2]);
        }*/
    }

    private double[][] shiftPhase(double[] Fx, double[] Fy) {
        double[][] F = new double[2][6];
        double[][] Fmod = new double[2][6];
        double[][] Z = new double[2][3]; // Zab, Zbc, Zca

        // shift phase: (Ua, Ub, Uc, Ia, Ib, Ic) => (Uab, Ubc, Uca, Iab, Ibc, Ica)
        for (int j = 0; j < 6; j++) {
            if (j == 2 || j == 5) { // Uc - Ua (Ic - Ia)
                F[0][j] = (Fx[j] - Fx[j - 2]);
                F[1][j] = (Fy[j] - Fy[j - 2]);
            } else { // Ua - Ub (Ia - Ib), Ub - Uc (Ib - Ic)
                F[0][j] = (Fx[j] - Fx[j + 1]);
                F[1][j] = (Fy[j] - Fy[j + 1]);
            }
        }

        // Convert from Exponential to Polar form (Uab, Ubc, Uca, Iab, Ibc, Ica)
        for (int j = 0; j < 6; j++) {
            Fmod[0][j] = Math.toDegrees(Math.atan2(F[1][j], F[0][j])); // argument
            Fmod[1][j] = Math.sqrt((Math.pow(F[0][j], 2) + Math.pow(F[1][j], 2)) * 0.5); // module
        }

        // Convert (Uab, Ubc, Uca, Iab, Ibc, Ica) => (Zab, Zbc, Zca)
        for (int j = 0; j < 3; j++) {
            Z[0][j] = Fmod[0][0] - Fmod[0][j + 3]; // argument
            Z[1][j] = Fmod[1][0] / Fmod[1][j + 3]; // module
        }
        return Z;

    }

    // addition three currents in polar form
    private double[] addition(double[] module, double[] argument) {
        double[] solve = new double[2];
        double Fx = 0;
        double Fy = 0;
        for (int i = 0; i < module.length; i++) {
            Fx += Math.cos(Math.toRadians(argument[i])) * module[i];
            Fy += Math.sin(Math.toRadians(argument[i])) * module[i];
        }
        solve[0] = Math.toDegrees(Math.atan2(Fy, Fx)); // argument
        solve[1] = Math.sqrt(((Math.pow(Fx, 2) + Math.pow(Fy, 2))) * 0.5) / 3; // module
        return solve;
    }

    // (Ia, Ib, Ic) => (I1a, I1b, I1c)
    private double[][] PositiveSequenceCurrents(double[] Fx, double[] Fy) {
        double[] arg = new double[3]; // argument (Ia, Ib, Ic)
        double[] mod = new double[3]; // module (Ia, Ib, Ic)
        double[][] F = new double[2][3]; // Ia1, Ib1, Ic1

        for (int i = 0; i < 3; i++) {
            if (i == 1) { // Ib * a
                arg[i] = Math.toDegrees(Math.atan2(Fy[i + 3], Fx[i + 3])) + 120; // argument
                mod[i] = Math.sqrt(Math.pow(Fx[i + 3], 2) + Math.pow(Fy[i + 3], 2)); // module
            } else if (i == 2) { // Ib * a
                arg[i] = Math.toDegrees(Math.atan2(Fy[i + 3], Fx[i + 3])) + 240; // argument
                mod[i] = Math.sqrt(Math.pow(Fx[i + 3], 2) + Math.pow(Fy[i + 3], 2)); // module
            } else {
                arg[i] = Math.toDegrees(Math.atan2(Fy[i + 3], Fx[i + 3])); // argument
                mod[i] = Math.sqrt(Math.pow(Fx[i + 3], 2) + Math.pow(Fy[i + 3], 2)); // module
            }
        }

        F[0][0] = addition(mod, arg)[0]; // argument Ia1
        F[1][0] = addition(mod, arg)[1]; // module Ia1

        F[0][1] = addition(mod, arg)[0] + 120; // argument Ib1
        F[1][1] = addition(mod, arg)[1]; // module Ib1

        F[0][2] = addition(mod, arg)[0] + 240; // argument Ic1
        F[1][2] = addition(mod, arg)[1]; // module Ic1

        return F;
    }
}
