package db;

import static controllers.EducationController.maxTries;

/**
 * Created by Sanak Alex on 28.05.2016.
 */
public class TimeCalc {
    public static double[] getSeqOfTime(long[][] timeTries){
        long [][] arrayList = new long[maxTries][7];
        double[] M = new double[maxTries];
        double[] S = new double[maxTries];
        Education edu = new Education();
        for(int i = 0; i < maxTries; i++){
            for(int j = 0; j < timeTries[i].length-1; j ++){
                long k = timeTries[i][j + 1] - timeTries[i][j];
                arrayList[i][j] = k;
            }
            S[i] =  edu.getS(arrayList[i]);
            M[i] = edu.getM();
        }
        double sumS = 0;
        double sumM = 0;
        for(int i=0; i<maxTries;i++){
            sumS += S[i]/maxTries;
            sumM += M[i]/maxTries;
        }
        double[] answer = new double[2];
        answer[0] = sumS;
        answer[1] = sumM;
        return answer;
    }
    public static long[] getSeqForAuth(long[] timeTries){
        long[] answer = new long[timeTries.length-1];
        for(int j = 0; j < timeTries.length-1; j ++){
            long k = timeTries[j + 1] - timeTries[j];
            answer[j] = k;
        }
        return answer;
    }
}
