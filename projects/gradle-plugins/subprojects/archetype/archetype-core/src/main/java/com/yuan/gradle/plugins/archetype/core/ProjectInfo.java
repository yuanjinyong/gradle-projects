/**
 *
 */
package com.yuan.gradle.plugins.archetype.core;


import java.io.File;


/**
 * Gradle工程信息
 *
 * @author Yuanjy
 *
 */
public class ProjectInfo {
    /**
     * 工程目录
     */
    private File projectDir;
    /**
     * 构建脚本名称
     */
    private String buildFileName;
    /**
     * 工程名称
     */
    private String name;
    /**
     * 多工程结构时，本工程相对Root工程的路径
     */
    private String path;
    /**
     * 工程描述
     */
    private String description;
    /**
     * 项目组
     */
    private String group;
    /**
     * 输出构件名称
     */
    private String archiveBaseName;
    /**
     * 输出构件版本号
     */
    private String version;
    /**
     * 工程默认的包名
     */
    private String pkg;
    /**
     * 包路径，即把包名中的“.”替换成了“.”
     */
    private String pkgPath;
    /**
     * 多工程结构时，Root工程下多工程设置文件
     */
    private File settingsFile;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("projectDir=").append(projectDir);
        sb.append(", ").append("buildFileName=").append(buildFileName);
        sb.append(", ").append("path=").append(path);
        sb.append(", ").append("description=").append(description);
        sb.append(", ").append("group=").append(group);
        sb.append(", ").append("archiveBaseName=").append(archiveBaseName);
        sb.append(", ").append("version=").append(version);
        sb.append(", ").append("pkg=").append(pkg);
        sb.append(", ").append("pkgPath=").append(pkgPath);
        sb.append(", ").append("settingsFile=").append(settingsFile).append('}');
        return sb.toString();
    }

    public File getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(File projectDir) {
        this.projectDir = projectDir;
    }

    public String getBuildFileName() {
        return buildFileName;
    }

    public void setBuildFileName(String buildFileName) {
        this.buildFileName = buildFileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getArchiveBaseName() {
        return archiveBaseName;
    }

    public void setArchiveBaseName(String archiveBaseName) {
        this.archiveBaseName = archiveBaseName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getPkgPath() {
        return pkgPath;
    }

    public void setPkgPath(String pkgPath) {
        this.pkgPath = pkgPath;
    }

    public File getSettingsFile() {
        return settingsFile;
    }

    public void setSettingsFile(File settingsFile) {
        this.settingsFile = settingsFile;
    }
}
