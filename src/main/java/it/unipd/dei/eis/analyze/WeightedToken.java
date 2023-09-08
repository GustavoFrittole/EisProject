package it.unipd.dei.eis.analyze;

public class WeightedToken implements Comparable<WeightedToken> {
    private String token;
    private int weight;

    public WeightedToken(String token, int weight) {
        if (token == null)
            throw new IllegalArgumentException();
        this.token = token;
        this.weight = weight;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (token == null)
            throw new IllegalArgumentException();
        this.token = token;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(WeightedToken other) {
        if (other == null)
            throw new NullPointerException();

        if (other.weight != this.weight)
            return this.weight - other.weight;
        else
            return this.token.compareTo(other.token);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == this)
            return true;
        if (!(otherObject instanceof WeightedToken))
            return false;
        boolean equalWeight = this.weight == ((WeightedToken) otherObject).weight;
        boolean equalToken = this.token.equals(((WeightedToken) otherObject).token);
        return equalToken && equalWeight;
    }

    public String toString() {

        return this.token + "\t" + this.weight;
    }
}
