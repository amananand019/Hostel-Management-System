package resource.utils;

public class ModelTableHistory {
    String username, usn, sharing, bed, date;

    public ModelTableHistory(String username, String usn, String sharing, String bed, String date) {
        this.username = username;
        this.usn = usn;
        this.sharing = sharing;
        this.bed = bed;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getSharing() {
        return sharing;
    }

    public void setSharing(String sharing) {
        this.sharing = sharing;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
