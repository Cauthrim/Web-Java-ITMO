package ru.itmo.wp.form;

import javax.validation.constraints.NotNull;

public class AvailabilitySwitch {
    @NotNull
    private long userId;

    @NotNull
    private boolean switchType;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean getSwitchType() {
        return switchType;
    }

    public void setSwitchType(boolean switchType) {
        this.switchType = switchType;
    }
}
