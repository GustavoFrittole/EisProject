package it.unipd.dei.eis.UI;

public class Options {

    private String csvFilePath;
    private String confFilePath;
    private String query;
    private boolean ga;
    private boolean cf;
    private boolean sf;
    private boolean rt;

    public Options(String csvFilePath, String confFilePath, String query, boolean ga, boolean cf, boolean sf, boolean rt) {
        this.csvFilePath = csvFilePath;
        this.confFilePath = confFilePath;
        this.query = query;
        this.ga = ga;
        this.cf = cf;
        this.sf = sf;
        this.rt = rt;
    }

    public String getCsvFilePath() {
        return csvFilePath;
    }

    public void setCsvFilePath(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public String getConfFilePath() {
        return confFilePath;
    }

    public void setConfFilePath(String confFilePath) {
        this.confFilePath = confFilePath;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isGa() {
        return ga;
    }

    public void setGa(boolean ga) {
        this.ga = ga;
    }

    public boolean isCf() {
        return cf;
    }

    public void setCf(boolean cf) {
        this.cf = cf;
    }

    public boolean isSf() {
        return sf;
    }

    public void setSf(boolean sf) {
        this.sf = sf;
    }

    public boolean isRt() {
        return rt;
    }

    public void setRt(boolean rt) {
        this.rt = rt;
    }
}
