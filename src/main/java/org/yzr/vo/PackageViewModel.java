package org.yzr.vo;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.yzr.model.Package;
import org.yzr.utils.PathManager;

import java.net.URLEncoder;
import java.util.Date;


@Getter
public class PackageViewModel {
    private String downloadURL;
    private String safeDownloadURL;
    private String iconURL;
    private String installURL;
    private String previewURL;
    private String id;
    private String version;
    private String bundleID;
    private String name;
    private long createTime;
    private String buildVersion;
    private String displaySize;
    private String displayTime;
    private boolean iOS;

    public PackageViewModel(Package aPackage, PathManager pathManager) {
        this.downloadURL = pathManager.getBaseURL(false) + "p/" + aPackage.getId();
        this.safeDownloadURL = pathManager.getBaseURL(true) + "p/" + aPackage.getId();
        this.iconURL = pathManager.getPackageResourceURL(aPackage, true) + "icon.png";
        this.id = aPackage.getId();
        this.version = aPackage.getVersion();
        this.bundleID = aPackage.getBundleID();
        this.name = aPackage.getName();
        this.createTime = aPackage.getCreateTime();
        this.buildVersion = aPackage.getBuildVersion();
        this.displaySize = String.format("%.2f MB", aPackage.getSize() / (1.0F * FileUtils.ONE_MB));
        Date updateTime = new Date(this.createTime);
        String displayTime = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(updateTime);
        this.displayTime = displayTime;
        if (aPackage.getPlatform().equals("ios")) {
            this.iOS = true;
            String url = pathManager.getBaseURL(true) + "m/" + aPackage.getId();
            try {
                this.installURL = "itms-services://?action=download-manifest&url=" + URLEncoder.encode(url, "utf-8");
            } catch (Exception e){e.printStackTrace();}
        } else if (aPackage.getPlatform().equals("android")) {
            this.iOS = false;
            this.installURL = pathManager.getPackageResourceURL(aPackage, false) + aPackage.getFileName();
        }
        this.previewURL = pathManager.getBaseURL(false) + "s/" + aPackage.getApp().getShortCode() + "?id=" + aPackage.getId();
    }

}
