package com.cit.fool.ec.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cit.fool.ec.database.entity.DaoMaster;

public class ReleaseOpenHelper extends  DaoMaster.OpenHelper
{
    public ReleaseOpenHelper(Context context, String name)
    {
        super(context, name);
    }

    public ReleaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory)
    {
        super(context, name, factory);
    }
}
