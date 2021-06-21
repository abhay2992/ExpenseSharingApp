package com.splitwise.services.settleupstrategy.user;

import com.splitwise.models.User;

public interface SettleUserStrategy {
    String settleUp(User user);
}
