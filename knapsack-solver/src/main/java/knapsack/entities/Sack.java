package knapsack.entities;

public class Sack {
    private int[][] solution;
    private int level;

    public Sack(int[][] solution, int level) {
        this.solution = new int[solution.length][3];
        for (int i = 0; i < solution.length; i++){
            this.solution[i][0] = solution[i][0];
            this.solution[i][1] = solution[i][1];
            this.solution[i][2] = -1;
        }
        this.level = level;
    }

    public int[][] getSolution() {
        return solution;
    }

    public void setSolution(int[][] solution) {
        this.solution = solution;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int sumWeights(){
        int sum = 0;
        for (int row = 0; row < solution.length; row++)
            sum += solution[row][1] * solution [row][2];
        return sum;
    }

    public int eval(){
        int sum = 0;
        for (int row = 0; row < solution.length; row++)
            sum += solution[row][0] * solution [row][2];
        return sum;
    }
}
