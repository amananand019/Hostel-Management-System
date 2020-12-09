package resource.utils;

public class ModelTableSearch{
    String search_usn, search_name, search_sharing, search_bed, search_year, search_baseFee, search_laundry;
    int total;

    public ModelTableSearch(String search_usn, String search_name, String search_sharing, String search_bed, String search_year, String search_baseFee, String search_laundry) {
        this.search_usn = search_usn;
        this.search_name = search_name;
        this.search_sharing = search_sharing;
        this.search_bed = search_bed;
        this.search_year = search_year;
        this.search_baseFee = search_baseFee;
        this.search_laundry = search_laundry;
        total = Integer.parseInt(search_baseFee) + Integer.parseInt(search_laundry);
    }

    public String getSearch_usn() {
        return search_usn;
    }

    public void setSearch_usn(String search_usn) {
        this.search_usn = search_usn;
    }

    public String getSearch_name() {
        return search_name;
    }

    public void setSearch_name(String search_name) {
        this.search_name = search_name;
    }

    public String getSearch_sharing() {
        return search_sharing;
    }

    public void setSearch_sharing(String search_sharing) {
        this.search_sharing = search_sharing;
    }

    public String getSearch_bed() {
        return search_bed;
    }

    public void setSearch_bed(String search_bed) {
        this.search_bed = search_bed;
    }

    public String getSearch_year() {
        return search_year;
    }

    public void setSearch_year(String search_year) {
        this.search_year = search_year;
    }

    public String getSearch_baseFee() {
        return search_baseFee;
    }

    public void setSearch_baseFee(String search_baseFee) {
        this.search_baseFee = search_baseFee;
    }

    public String getSearch_laundry() {
        return search_laundry;
    }

    public void setSearch_laundry(String search_laundry) {
        this.search_laundry = search_laundry;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
