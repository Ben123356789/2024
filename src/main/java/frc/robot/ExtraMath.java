package frc.robot;

public final class ExtraMath {
    private ExtraMath() {}

    // https://www.desmos.com/calculator/kfyws5ap6u
    public static double clamp(double val, double start, double end) {
        double min = Math.min(start, end);
        double max = Math.max(start, end);
        if(val > max){
            val = max;
        } else if(val < min){
            val = min;
        }
        return val;
    }

    public static int clamp(int val, int start, int end) {
        int min = Math.min(start, end);
        int max = Math.max(start, end);
        if(val > max){
            val = max;
        } else if(val < min){
            val = min;
        }
        return val;
    }

    // https://stackoverflow.com/questions/5731863/mapping-a-numeric-range-onto-another
    // https://www.desmos.com/calculator/0zisstnbzz
    public static double rangeMap(double val, double low1, double high1, double low2, double high2) {
        return ((high2 - low2) / (high1 - low1)) * (val - low1) + low2;
    }
}
