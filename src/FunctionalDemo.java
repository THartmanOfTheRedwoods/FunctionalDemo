import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FunctionalDemo {
    private final static Scanner s = new Scanner(System.in);
    public static double mean(int version) {
        System.out.println("Enter a list of numbers separated by a comma.");
        String[] numbers = s.nextLine().split(",");
        int sum = 0;
        switch (version) {
            case 1 ->  sum = Arrays.stream(numbers).map(Integer::parseInt).reduce(0, Integer::sum);
            case 2 ->  sum = Arrays.stream(numbers).map(Integer::parseInt)
                    .collect(Collectors.summingInt(Integer::intValue));
            case 3 -> sum = Arrays.stream(numbers).map(Integer::parseInt).mapToInt(Integer::intValue).sum();
            case 4 -> sum = Arrays.stream(numbers).mapToInt(Integer::parseInt).sum();
            case 5 -> sum = Arrays.stream(numbers).filter((s) -> s.matches("\\d+")).mapToInt(Integer::parseInt).sum();
            case 6 -> {
                return Arrays.stream(numbers)
                        .filter((s) -> s.trim().matches("\\d+"))
                        .mapToInt((n) -> Integer.parseInt(n.trim()))
                        .collect(
                                AverageContainer::new,
                                AverageContainer::add,
                                AverageContainer::combine
                        )
                        .getAverage();
            }
            case 7 -> {
                Collector<String, double[], Double> averageCollector = Collector.of(
                        () -> new double[2], // Supplier for the accumulator: [sum, count]
                        (acc, str) -> {
                            if (str.trim().matches("\\d+")) {
                                acc[0] += Double.parseDouble(str.trim()); // Sum
                                acc[1]++; // Count
                            }
                        },
                        (acc1, acc2) -> {
                            acc1[0] += acc2[0]; // Combine sums
                            acc1[1] += acc2[1]; // Combine counts
                            return acc1;
                        },
                        acc -> acc[1] > 0 ? acc[0] / acc[1] : 0.0 // Finisher: Calculate the average
                );
                return Arrays.stream(numbers).collect(averageCollector);
            }
            case 8 -> {
                return Arrays.stream(numbers).filter((s) -> s.trim().matches("\\d+"))
                        .mapToInt((n) -> Integer.parseInt(n.trim())).average().orElse(0);
            }
        }
        return (1.0 * sum) / numbers.length;
    }

    public static void usage() {
        System.out.println(
                "You must provide a single integer argument between 1 - 6 to choose your mean calculation method.");
        System.exit(1);
    }

    public static void main(String[] args) {
        int options = 8;
        if(args.length < 1) { usage(); }
        int version = 0;
        try {
            version = Integer.parseInt(args[0]);
            if(version < 1 || version > options) {
                usage();
            }
        } catch (NumberFormatException nfe) {
            usage();
        }
        System.out.printf("The calculated mean is: %f", mean(version));
    }
}
