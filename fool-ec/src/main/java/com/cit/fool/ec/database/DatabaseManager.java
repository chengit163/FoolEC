package com.cit.fool.ec.database;

import android.content.Context;

import com.cit.fool.ec.database.entity.DaoMaster;
import com.cit.fool.ec.database.entity.DaoSession;
import com.cit.fool.ec.database.entity.UserDao;

import org.greenrobot.greendao.database.Database;

public class DatabaseManager
{
    private DaoSession mDaoSession;
    private UserDao mUserDao;

    private DatabaseManager()
    {
    }

    public static DatabaseManager getInstance()
    {
        return Holder.INSTANCE;
    }

    private static final class Holder
    {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public DatabaseManager init(Context context)
    {
        initDao(context);
        return this;
    }

    private void initDao(Context context)
    {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "fool_ec.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserDao = mDaoSession.getUserDao();
    }

    public UserDao getUserDao()
    {
        return mUserDao;
    }
}
