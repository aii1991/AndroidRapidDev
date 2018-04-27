package com.boildcoffee.base.network.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.boildcoffee.base.network.db.entity.ApiCacheEntity;

/**
 * @author zjh
 *         2018/3/13
 */
@Dao
public interface ApiCacheDao {
    @Query("SELECT * FROM t_api_cache WHERE `key` = :key AND create_time < cache_time")
    ApiCacheEntity findByKey(int key);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    long insert(ApiCacheEntity cacheEntity);

    @Delete
    void delete(ApiCacheEntity cacheEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(ApiCacheEntity cacheEntity);
}
