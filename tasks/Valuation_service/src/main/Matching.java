package main;

class Matching {
    private int matching_id;
    private int top_priced_count;

    public Matching(int matching_id, int top_priced_count) {
        this.matching_id = matching_id;
        this.top_priced_count = top_priced_count;
    }

    public int getMatching_id() {
        return matching_id;
    }

    public int getTop_priced_count() {
        return top_priced_count;
    }
}
