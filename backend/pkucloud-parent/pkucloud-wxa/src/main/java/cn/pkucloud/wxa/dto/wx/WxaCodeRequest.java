package cn.pkucloud.wxa.dto.wx;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WxaCodeRequest {
    private String scene;
    private String page;
    private int width;
    private boolean auto_color;
    private WxaCodeLineColor line_color;
    private boolean is_hyaline;

    public WxaCodeRequest(String scene, String page) {
        this.scene = scene;
        this.page = page;
        this.width = 280;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isAuto_color() {
        return auto_color;
    }

    public void setAuto_color(boolean auto_color) {
        this.auto_color = auto_color;
    }

    public WxaCodeLineColor getLine_color() {
        return line_color;
    }

    public void setLine_color(WxaCodeLineColor line_color) {
        this.line_color = line_color;
    }

    public boolean isIs_hyaline() {
        return is_hyaline;
    }

    public void setIs_hyaline(boolean is_hyaline) {
        this.is_hyaline = is_hyaline;
    }
}
