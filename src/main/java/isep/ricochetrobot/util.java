package isep.ricochetrobot;

public class util {

    public static int[][] rotate(int[][] a, int n) {

        for (int i=0; i< n-1; i++){
            for (int j=i; j<n-1-i; j++){
                int temp = a[i][j];
                a[i][j] = a[n-1-j][i];
                a[n-1-j][i] = a[n-1-i][n-1-j];
                a[n-1-i][n-1-j] = a[j][n-1-i];
                a[j][n-1-i] = temp;
            }
        }
        return a;
    }
}

