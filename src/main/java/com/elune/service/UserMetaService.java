/**
 * Elune - Lightweight Forum Powered by Razor.
 * Copyright (C) 2017, Touchumind<chinash2010@gmail.com>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.elune.service;

import com.elune.entity.UsermetaEntity;
import com.elune.model.Pagination;
import com.elune.model.Topic;
import com.elune.model.User;

import java.util.List;

/**
 * @author Touchumind
 */
public interface UserMetaService {

    long createUsermeta(UsermetaEntity usermetaEntity);

    boolean createOrUpdateUsermeta(long uid, String metaKey, String metaValue);

    boolean deleteUsermeta(long id);

    boolean deleteUsermeta(UsermetaEntity usermetaEntity);

    List<UsermetaEntity> getUsermetas(long uid, String metaKey, int page, int pageSize);

    UsermetaEntity getSingleUsermeta(long uid, String metaKey);

    Long countUsermetas(long uid, String metaKey);

    boolean favoriteTopic(long uid, long topicId);

    boolean unfavoriteTopic(long uid, long topicId);

    Pagination<Topic> getFavorites(long uid, int page, int pageSize);

    Long countFavorites(long uid);

    List<Long> getFavoriteIds(long uid);

    List<Long> getFavoriteIds(long uid, int page, int pageSize);

    Pagination<Topic> getFollowingTopics(long uid, int page, int pageSize);

    Long countFollowingTopics(long uid);

    boolean followTopic(long uid, long topicId);

    boolean unfollowTopic(long uid, long topicId);

    Pagination<User> getFollowingUsers(long uid, int page, int pageSize);

    Long countFollowingUsers(long uid);

    boolean followUser(long uid, long followedUid);

    boolean unfollowUser(long uid, long unfollowedUid);

    boolean hasSignedToday(long uid);

    int getBalance(long uid);

    boolean changeBalance(long uid, int change);
}
