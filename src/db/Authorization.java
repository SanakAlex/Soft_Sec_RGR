package db;


/**
 * Created by Sanak Alex on 27.05.2016.
 */
public class Authorization {
    private long[] y;
    private long[] yFiltered;

    private double[] M = new double[7];
    private double[] D = new double[7];

    private double m;
    private double s;

    public double getM() {
        return m;
    }
    public double getS() {
        return s;
    }


    private void calcM(int i){
        long sum = 0;
        for (int i1 = 0; i1 < y.length; i1++) {
            if(i1==i) continue;
            sum += y[i1];
        }
        M[i] = sum/(y.length-1);
    }
    private void calcS(int i){
        long sum = 0;
        for (int i1 = 0; i1 < y.length; i1++) {
            if(i1==i) continue;
            sum += Math.pow(y[i1] - M[i], 2);
        }
        D[i] = (long)Math.sqrt((double)sum/(y.length-2));
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

    void calcM(){
        double sum = 0;
        for (long aY : yFiltered) {
            sum += aY;
        }
        m = sum/(yFiltered.length);
    }
    void calcS(){
        double sum = 0;
        for (long aY : yFiltered) {
            sum += Math.pow(aY - m, 2);
        }
        s = sum/(yFiltered.length-1);
    }
    public boolean checkResult(long[] y, double m, double s){
        this.y = y;
        filterY();
        calcM();
        calcS();
        double d = Math.sqrt((Math.pow(s,2)+Math.pow(this.s,2))*(y.length-1) /(2*y.length-1));
        double t = (Math.abs(m+this.m))/(d*Math.sqrt((double)2/y.length));
        double studCoef = 2.3646;
        System.out.printf("%.4f"+"  ?  "+studCoef+"\n",t);
        return t <= studCoef;
    }

    public static void main(String[] args) {
        Authorization au = new Authorization();
        long[] y = {223, 280, 191, 283, 170, 198, 185};
        System.out.println(au.checkResult(y, 666.1714285714286, 26262.238095238095));
    }
}
