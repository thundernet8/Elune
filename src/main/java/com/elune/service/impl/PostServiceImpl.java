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


package com.elune.service.impl;

import com.elune.configuration.AppConfiguration;
import com.elune.constants.Constant;
import com.elune.dal.DBManager;
import com.elune.dao.PostMapper;
import com.elune.entity.*;
import com.elune.model.*;
import com.elune.service.*;
import com.elune.utils.DateUtil;
import com.elune.utils.DozerMapperUtil;

import com.fedepot.ioc.annotation.FromService;
import com.fedepot.ioc.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @FromService
    private DBManager dbManager;

    @FromService
    private UserService userService;

    @FromService
    private TopicService topicService;

    @FromService
    private MailMQService mailMQService;

    @FromService
    private AppConfiguration appConfiguration;

    @Override
    public Post getPost(long id) {

        PostEntity postEntity = getPostEntity(id);
        if (postEntity == null) {

            return null;
        }

        Post post = DozerMapperUtil.map(postEntity, Post.class);

        return assemblePosts(Collections.singletonList(post)).get(0);
    }

    @Override
    public PostEntity getPostEntity(long id) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            PostMapper mapper = sqlSession.getMapper(PostMapper.class);
            return mapper.selectByPrimaryKey(id);
        }
    }

    @Override
    public long createPost(UserEntity author, PostCreationModel postCreationModel) {

        Topic topic = topicService.getTopic(postCreationModel.topicId);

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            PostMapper mapper = sqlSession.getMapper(PostMapper.class);

            Byte type = postCreationModel.parentId != null && postCreationModel.parentId > 0 ? Byte.parseByte("2") : Byte.parseByte("1");
            Integer now = DateUtil.getTimeStamp();

            PostEntity postEntity = PostEntity.builder().tid(postCreationModel.topicId).pid(postCreationModel.parentId).authorName(author.getUsername()).authorId(author.getId()).topicAuthorName(topic.getAuthorName()).topicAuthorId(topic.getAuthorId()).ip(postCreationModel.ip).ua(postCreationModel.ua).content(postCreationModel.content).contentHtml(postCreationModel.contentHtml).contentRaw(postCreationModel.contentRaw).type(type).createTime(now).build();
            mapper.insertSelective(postEntity);
            sqlSession.commit();

            topicService.lastReplayTopic(postCreationModel.topicId, author);

            // 对提及的用户发送邮件通知
            Arrays.stream(postCreationModel.mentions).forEach(mention -> {
                if (mention.equals(author.getUsername())) {
                    return;
                }

                User mentionUser = userService.getUserByName(mention);
                String topicLink = appConfiguration.get(Constant.CONFIG_KEY_SITE_FRONTEND_HOME, "").concat("/topic/").concat(Long.toString(postCreationModel.topicId));
                if (mentionUser != null) {
                    mailMQService.sendMail(mentionUser.getEmail(), mentionUser.getNickname(), "Elune - ".concat(author.getNickname()).concat("在回复中提到了你"), postCreationModel.content.concat("\r\n").concat(topicLink));
                }
            });

            return postEntity.getId();
        } catch (Exception e) {

            log.error("Insert post failed", e);
            throw e;
        }
    }

    @Override
    public boolean deletePost(long id) {

        PostEntity postEntity = PostEntity.builder().id(id).status(Byte.parseByte("0")).build();
        return updatePost(postEntity);
    }

    @Override
    public boolean upvotePost(long id) {

        PostEntity postEntity = PostEntity.builder().id(id).upvotesCount(1).build();
        return increaseUpdatePost(postEntity);
    }

    @Override
    public boolean downvotePost(long id) {

        PostEntity postEntity = PostEntity.builder().id(id).downvotesCount(1).build();
        return increaseUpdatePost(postEntity);
    }

    @Override
    public Pagination<Post> getPosts(int page, int pageSize, String orderClause) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            PostMapper mapper = sqlSession.getMapper(PostMapper.class);

            PostEntityExample postEntityExample = PostEntityExample.builder().oredCriteria(new ArrayList<>()).offset((page - 1) * pageSize).limit(pageSize).orderByClause(orderClause).build();
            Byte normalStatus = 1;
            postEntityExample.or().andStatusEqualTo(normalStatus);
            List<PostEntity> postEntities = mapper.selectByExampleWithBLOBs(postEntityExample);
            List<Post> posts = assemblePosts(postEntities);

            PostEntityExample countPostEntityExample = PostEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            countPostEntityExample.or().andStatusEqualTo(normalStatus);
            long total = mapper.countByExample(countPostEntityExample);

            return new Pagination<>(total, page ,pageSize, posts);
        }
    }

    @Override
    public long countPostsByAuthor(long authorId) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            PostMapper mapper = sqlSession.getMapper(PostMapper.class);
            PostEntityExample postEntityExample = PostEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            postEntityExample.or().andAuthorIdEqualTo(authorId).andStatusEqualTo(Byte.valueOf("1"));

            return mapper.countByExample(postEntityExample);
        }
    }

    @Override
    public Pagination<Post> getTopicPosts(int page, int pageSize, long topicId, String orderClause) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            PostMapper mapper = sqlSession.getMapper(PostMapper.class);

            PostEntityExample postEntityExample = PostEntityExample.builder().oredCriteria(new ArrayList<>()).offset((page - 1) * pageSize).limit(pageSize).orderByClause(orderClause).build();
            Byte normalStatus = 1;
            postEntityExample.or().andTidEqualTo(topicId).andStatusEqualTo(normalStatus);
            List<PostEntity> postEntities = mapper.selectByExampleWithBLOBs(postEntityExample);
            List<Post> posts = assemblePosts(postEntities);

            PostEntityExample countPostEntityExample = PostEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            countPostEntityExample.or().andTidEqualTo(topicId).andStatusEqualTo(normalStatus);
            long total = mapper.countByExample(countPostEntityExample);

            return new Pagination<>(total, page ,pageSize, posts);
        }
    }

    @Override
    public Pagination<Post> getUserPosts(int page, int pageSize, long authorId, String orderClause) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            PostMapper mapper = sqlSession.getMapper(PostMapper.class);

            PostEntityExample postEntityExample = PostEntityExample.builder().oredCriteria(new ArrayList<>()).offset((page - 1) * pageSize).limit(pageSize).orderByClause(orderClause).build();
            Byte normalStatus = 1;
            postEntityExample.or().andAuthorIdEqualTo(authorId).andStatusEqualTo(normalStatus);
            List<PostEntity> postEntities = mapper.selectByExampleWithBLOBs(postEntityExample);
            List<Post> posts = assemblePosts(postEntities);

            PostEntityExample countPostEntityExample = PostEntityExample.builder().oredCriteria(new ArrayList<>()).build();
            countPostEntityExample.or().andAuthorIdEqualTo(authorId).andStatusEqualTo(normalStatus);
            long total = mapper.countByExample(countPostEntityExample);

            return new Pagination<>(total, page ,pageSize, posts);
        }
    }

    private List<Post> assemblePosts(List<PostEntity> postEntities) {

        List<Post> posts = new ArrayList<>();

        List<Long> authorIds = postEntities.stream().map(PostEntity::getAuthorId).distinct().collect(Collectors.toList());
        Map<Long, User> userMap = userService.getUsersByIdList(authorIds).stream().collect(Collectors.toMap(User::getId, Function.identity()));

        postEntities.forEach(postEntity -> {

            Post post = DozerMapperUtil.map(postEntity, Post.class);
            post.setAuthor(userMap.get(postEntity.getAuthorId()));

            posts.add(post);
        });

        return posts;
    }

    private boolean updatePost(PostEntity postEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            PostMapper mapper = sqlSession.getMapper(PostMapper.class);
            int update = mapper.updateByPrimaryKeySelective(postEntity);
            sqlSession.commit();

            return update > 0;
        }
    }

    private boolean increaseUpdatePost(PostEntity postEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            PostMapper mapper = sqlSession.getMapper(PostMapper.class);
            int update = mapper.increaseByPrimaryKeySelective(postEntity);
            sqlSession.commit();

            return update > 0;
        }
    }

    private boolean decreaseUpdatePost(PostEntity postEntity) {

        try (SqlSession sqlSession = dbManager.getSqlSession()) {

            PostMapper mapper = sqlSession.getMapper(PostMapper.class);
            int update = mapper.decreaseByPrimaryKeySelective(postEntity);
            sqlSession.commit();

            return update > 0;
        }
    }
}
