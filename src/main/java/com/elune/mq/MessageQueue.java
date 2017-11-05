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


package com.elune.mq;

public interface MessageQueue {

    /**
     * 发布一条消息至队列
     *
     * @param topic 消息所属话题分类
     * @param message 消息
     */
    void publish(String topic, String message);

    /**
     * 阅读指定话题下的当前一条消息
     *
     * @param topic 消息所属话题分类
     * @return 消息
     */
    String read(String topic);

    /**
     * 如果消息消费成功, 移除该消息并进入到下一条消息流程
     *
     * @param topic 消息所属话题分类
     */
    void next(String topic);

    /**
     * 如果消息消费失败, 移动该消息至队列末尾并进入到下一条消息流程
     *
     * @param topic 消息所属话题分类
     */
    void delay(String topic, String message);

    /**
     * 资源释放
     */
    void dispose();

    /**
     * 注册关联的Producer以便于资源管理
     *
     * @param producer
     */
    void registerProducer(Producer producer);

    /**
     * 注册关联的Consumer以便于资源管理
     *
     * @param consumer
     */
    void registerConsumer(Consumer consumer);

    void cancelProducer(Producer producer);

    void cancelConsumer(Consumer consumer);
}
