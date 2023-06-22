public class CurrentF {
    public double[][] polarFormZ = new double[2][3]; // argument, module
    public double[][] polarFormCurrentsPositiveSeq = new double[2][3]; // argument, module

    public double[][] FromPolarToExponentialFormZ() { // F,phi => Fx + iFy
        double[][] F = new double[2][3];
        for (int j = 0; j < 3; j++) {
            F[0][j] = Math.cos(Math.toRadians(polarFormZ[0][j])) * polarFormZ[1][j]; // Fx  (R)
            F[1][j] = Math.sin(Math.toRadians(polarFormZ[0][j])) * polarFormZ[1][j]; // Fy  (X)
        }
        return F;
    }

    public double[][] getPolarFormZ() {
        return polarFormZ;
    }

    public void setPolarFormZ(double[][] f50algebraic) {
        polarFormZ = f50algebraic;
    }

    public double[][] getPolarFormCurrentsPositiveSeq() {
        return polarFormCurrentsPositiveSeq;
    }

    public void setPolarFormCurrentsPositiveSeq(double[][] polarFormCurrentsPositiveSeq) {
        this.polarFormCurrentsPositiveSeq = polarFormCurrentsPositiveSeq;
    }
}
