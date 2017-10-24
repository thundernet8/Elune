/**
 * Elune - Lightweight Forum Powered by Razor
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


package com.elune.controller.api;

import com.elune.model.Channel;
import com.elune.service.ChannelService;

import com.elune.service.MailService;
import com.fedepot.exception.HttpException;
import com.fedepot.ioc.annotation.FromService;
import com.fedepot.mvc.annotation.HttpGet;
import com.fedepot.mvc.annotation.Route;
import com.fedepot.mvc.annotation.RoutePrefix;
import com.fedepot.mvc.controller.APIController;

import java.util.List;

/**
 * @author Touchumind
 */
@RoutePrefix("api/v1/channels")
public class ChannelController extends APIController {

    @FromService
    private ChannelService channelService;

    @HttpGet
    @Route("")
    public void getChannels() {

        try {

            List<Channel> channels = channelService.getAllChannels();
            Succeed(channels);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpGet
    @Route("{int:id}")
    public void getChannel(int id) {

        try {

            Channel channel = channelService.getChannel(id);

            if (channel == null) {

                throw new HttpException("Specified channel is not exist", 404);
            }
            Succeed(channel);
        } catch (Exception e) {

            Fail(e);
        }
    }

    @HttpGet
    @Route("{string:slug}")
    public void getChannel(String slug) {

        try {

            Channel channel = channelService.getChannelBySlug(slug);

            if (channel == null) {

                throw new HttpException("Specified channel is not exist", 404);
            }
            Succeed(channel);
        } catch (Exception e) {

            Fail(e);
        }
    }
}
