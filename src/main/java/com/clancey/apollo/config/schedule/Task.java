package com.clancey.apollo.config.schedule;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author ChenShuai
 * @date 2018/8/28 16:23
 */
@Getter
@Setter
public class Task {
    private long id;
    private String triggerName;
    private String cron;
    private State state;
    private Date nextExecute;

    public Task(long id, String triggerName, String cron, State state, Date nextExecute) {
        this.id = id;
        this.triggerName = triggerName;
        this.cron = cron;
        this.state = state;
        this.nextExecute = nextExecute;
    }

    public enum State {
        RUN, WAITING_NEXT, STOP
    }
}
