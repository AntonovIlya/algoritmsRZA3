public class OutputData {

    public void process(boolean flag) {
        if (flag) Charts.addAnalogData(6, 0, 1);
        else Charts.addAnalogData(6, 0, 0);
    }
}
