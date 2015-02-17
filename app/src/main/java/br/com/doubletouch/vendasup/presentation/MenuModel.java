package br.com.doubletouch.vendasup.presentation;

import android.app.Activity;

import br.com.doubletouch.vendasup.presentation.view.activity.BaseActivity;

/**
 * Created by LADAIR on 17/02/2015.
 */
public class MenuModel {

    private Class<? extends BaseActivity> to;
    private String title;
    private String description;
    private int icon;
    private int order;


    public MenuModel(Class<? extends BaseActivity> to, String title, int icon, int order) {
        this.to = to;
        this.title = title;
        this.icon = icon;
        this.order = order;
    }

    public Class<? extends BaseActivity> getTo() {
        return to;
    }

    public void setTo(Class<? extends BaseActivity> to) {
        this.to = to;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
