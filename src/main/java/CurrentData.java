public class CurrentData {
    public double[] phABC = new double[6]; //array current instant values (UA, UB, UC, IA, IB, IC)

    public double[] getPhABC() {
        return phABC;
    }

    public void setPhABC(double[] phABC) {
        this.phABC = phABC;
    }

}