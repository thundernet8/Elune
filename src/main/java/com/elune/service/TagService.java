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

import com.elune.model.*;

import java.util.List;

/**
 * @author Touchumind
 */
public interface TagService {

    Tag getTag(int id);

    int createTag(TagCreationModel tagCreationModel);

    List<Integer> createTags(List<TagCreationModel> tagCreationModels);

    boolean deleteTag(int id);

    /**
     * 更新标签的帖子数量
     *
     * @param id 标签ID
     * @param increase 变动值，为负则减少计数
     * @return 更新成功返回True
     */
    boolean updateTopicCount(int id, int increase);

    /**
     * 分页查询标签(按最后回复/发布/更新时间降序)
     *
     * @param page 分页
     * @param pageSize 分页大小
     * @param orderClause 查询排序规则
     * @return 分页的话题列表对象
     */
    Pagination<Tag> getTags(int page, int pageSize, String orderClause);

    List<Tag> getTagsByIdList(List<Integer> ids);

    List<Tag> getTopicTags(long topicId);
}
