public class Movie {
    private String title;
    private String posterPath;
    private int[] availableHalls;
    private String[] showTimes;

    public Movie(String title, String posterPath,int[] availableHalls,String[] showTimes){
        this.title = title;
        this.posterPath = posterPath;
        this.availableHalls = availableHalls;
        this.showTimes = showTimes;
    }

    public String getTitle() {
        return title;
    }

    public String[] getShowTimes() {
        return showTimes;
    }

    public int[] getAvailableHalls() {
        return availableHalls;
    }

    public String getPosterPath() {
        return posterPath;
    }


    @Override
    public String toString() {
        return title;
    }
}
