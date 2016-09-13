package db;


/**
 * Created by Sanak Alex on 26.05.2016.
 */
public class Education {
    private long[] y = new long[7];
    private long[] yFiltered;

    private double[] M = new double[7];
    private double[] D = new double[7];

    private void setY(long[] y) {
        this.y = y;
    }
    double getM(){
        long sum = 0;
        for (long aYFiltered : yFiltered) {
            sum += aYFiltered;
        }
        return (double)sum/yFiltered.length;
    }
    private void calcM(int i){
        long sum = 0;
        for (int i1 = 0; i1 < y.length; i1++) {
            if(i1==i) continue;
            sum += y[i1];
        }
        M[i] = (double)sum/(y.length-1);
    }
    private void calcS(int i){
        long sum = 0;
        for (int i1 = 0; i1 < y.length; i1++) {
            if(i1==i) continue;
            sum += Math.pow(y[i1] - M[i], 2);
        }
        D[i] = Math.sqrt((double)sum/(y.length-2));
    }
    private void filterY(){
        long[] yFiltered = new long[y.length];
        int j = 0;
        for (int i=0; i<y.length; i++){
            calcM(i);
            calcS(i);
            if(y[i]>M[i]-3*D[i] && y[i]<M[i]+3*D[i]){
                yFiltered[j] = y[i];
                j++;
            }
        }
        this.yFiltered = yFiltered;
    }
    double getS(long[] y){
        setY(y);
        filterY();
        double sum = 0;
        for (long aYFiltered1 : yFiltered) {
            sum += aYFiltered1;
        }
        double m = (double)sum/yFiltered.length;
        sum = 0;
        for (long aYFiltered : yFiltered) {
            sum += Math.pow(aYFiltered - m, 2);
        }
        return sum/(yFiltered.length-1);
    }

    public static void main(String[] args) {
        Education edu = new Education();
        long[] y = {120,100,100,200,200,100,200};
        System.out.format("%.9f\n",(float)edu.getS(y));
        System.out.format("%.9f\n",(float)edu.getM());

    }

}
