package com.weddingcar.user.common.dao;

import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.manager.SPController;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by inrokei on 2018/4/30.
 */

public class NotificationManager {
    private static NotificationManager mInstance;
    private NotificationDao notificationDao;
    private UserInfo userInfo;

    private NotificationManager() {
        notificationDao = GreenDaoHelper.getInstance().getDaoSession().getNotificationDao();
        userInfo = SPController.getInstance().getUserInfo();
    }

    public static NotificationManager getInstance() {
        if (mInstance == null) {
            mInstance = new NotificationManager();
        }

        return mInstance;
    }

    public long getCount() {
        return notificationDao.queryBuilder().where(NotificationDao.Properties.Account.eq(userInfo.getUserId())).buildCount().count();
    }

    public List<Notification> getNotificationList(String type) {

        QueryBuilder<Notification> queryBuilder = notificationDao.queryBuilder();
        queryBuilder.where(NotificationDao.Properties.Account.eq(userInfo.getUserId()));
        queryBuilder.where(NotificationDao.Properties.Type.eq(type));
        queryBuilder.orderDesc(NotificationDao.Properties.CreateTime);

        return queryBuilder.list();
    }

    public void saveNotification(Notification notification) {
        notificationDao.save(notification);
    }

}
