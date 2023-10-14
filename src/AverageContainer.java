class AverageContainer {
    long sum;
    long count;

    public AverageContainer() {
        this.sum = 0;
        this.count = 0;
    }

    public void combine(AverageContainer other) {
        this.sum += other.sum;
        this.count += other.count;
    }

    public void add(int value) {
        this.sum += value;
        this.count++;
    }

    public double getAverage() {
        return count > 0 ? (double) sum / count : 0.0;
    }
}