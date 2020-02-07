package info.revenberg.maintain.objects;

public class DataObject1 {
    private String filename;
    private String bundleName;
    private  String songName;	

    public DataObject1() {
    }

    public DataObject1(String filename) {
        this.filename = filename;
        this.bundleName = null;
        this.songName = null;
    }

    public DataObject1(String filename, String bundleName, String songName) {
        this.filename = filename;
        this.bundleName = bundleName;
        this.songName = songName;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBundleName() {
        return this.bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public DataObject1 filename(String filename) {
        this.filename = filename;
        return this;
    }

    public DataObject1 bundleName(String bundleName) {
        this.bundleName = bundleName;
        return this;
    }

    public DataObject1 songName(String songName) {
        this.songName = songName;
        return this;
    }
    }
