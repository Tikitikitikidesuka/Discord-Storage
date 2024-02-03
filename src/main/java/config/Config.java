package config;

public class Config {
    private SenderConfig sender;
    private DownloaderConfig downloader;

    public SenderConfig getSender() {
        return this.sender;
    }

    public void setSender(SenderConfig senderConfig) {
        this.sender = senderConfig;
    }

    public DownloaderConfig getDownloader() {
        return this.downloader;
    }

    public void setDownloader(DownloaderConfig downloaderConfig) {
        this.downloader = downloaderConfig;
    }
}