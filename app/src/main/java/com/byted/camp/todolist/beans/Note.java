package com.byted.camp.todolist.beans;

import android.graphics.Color;

import java.util.Date;

/**
 * Created on 2019/1/23.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class Note {

    public final long id;
    private Date date;
    private State state;
    private String content;
    private  Priority priority;

    public enum Priority {
        Higher(2, Color.RED),
        Medium(1, Color.BLUE),
        Lower(0, Color.WHITE);

        public final int intValue;
        public final int color;

        Priority(int intValue, int color) {
            this.intValue = intValue;
            this.color = color;
        }

        public static Priority from(int intValue) {
            for (Priority priority : Priority.values()) {
                if (priority.intValue == intValue) {
                    return priority;
                }
            }
            return Priority.Lower; // default
        }
    }

    public Note(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
