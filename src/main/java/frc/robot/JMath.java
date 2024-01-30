package frc.robot;

public final class JMath {
    private JMath() {}

    // https://www.desmos.com/calculator/kfyws5ap6u
    public static double clamp(double x, double min, double max) {
        return Math.min(max, Math.max(min, x));
    }

    // https://stackoverflow.com/questions/5731863/mapping-a-numeric-range-onto-another
    // https://www.desmos.com/calculator/0zisstnbzz
    public static double map(double x, double low1, double high1, double low2, double high2) {
        return ((high2 - low2) / (high1 - low1)) * (x - low1) + low2;
    }

    public static boolean within(double a, double b, double epsilon) {
        return Math.abs(a - b) <= epsilon;
    }

    public static boolean changedByAtLeastFactor(double a, double b, double factor) {
        return (a / b >= factor || b / a >= factor);
    }
}
