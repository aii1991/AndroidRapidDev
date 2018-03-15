package com.boildcoffee.base;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.boildcoffee.base.network.db.dao.ApiCacheDao;
import com.boildcoffee.base.network.db.entity.ApiCacheEntity;

/**
 * @author zjh
 *         2018/3/13
 */
@Database(entities = {ApiCacheEntity.class},version = BuildConfig.dbVersion,exportSchema = false)
public abstract class BFDatabase extends RoomDatabase{

    public abstract ApiCacheDao apiCacheDao();

    public static BFDatabase create(Context context,boolean useInMemory){
        RoomDatabase.Builder<BFDatabase> databaseBuilder;
        if (useInMemory){
            databaseBuilder = Room.inMemoryDatabaseBuilder(context,BFDatabase.class);
        }else {
            databaseBuilder = Room.databaseBuilder(context,BFDatabase.class, BFConfig.INSTANCE.getConfig().getDbName());
        }
        return databaseBuilder
                .fallbackToDestructiveMigration()
                .build();
    }
}
