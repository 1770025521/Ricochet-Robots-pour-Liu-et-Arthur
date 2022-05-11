package isep.ricochetrobot;

public class util {

    public static int[][] rotateTable(int[][] table) {
        int n = table.length;

        for (int i=0; i< n-1; i++){
            for (int j=i; j<n-1-i; j++){
                int temp = table[i][j];
                table[i][j] = table[n-1-j][i];
                table[n-1-j][i] = table[n-1-i][n-1-j];
                table[n-1-i][n-1-j] = table[j][n-1-i];
                table[j][n-1-i] = temp;
            }
        }

        return table;
    }

    public static void printTable(int[][] table){
        int N = table.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(" " + table[i][j]);
            System.out.print("\n");
        }
        System.out.print("\n");
    }

}

