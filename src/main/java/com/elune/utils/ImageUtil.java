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


package com.elune.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    /**
     * 缩放图片
     *
     * @param imgPath 源图片绝对路径
     * @param disPath 目标图片绝对路径
     * @param height 目标高度
     * @param width 目标宽度
     * @param whiteFill 是否补白
     */
    public final static void scale(String imgPath, String disPath, int height, int width, boolean whiteFill) {
        try {
            double ratio = 0.0;
            File file = new File(imgPath);
            BufferedImage bufferedImage = ImageIO.read(file);
            Image tempImg = bufferedImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            // 计算比例
            if ((bufferedImage.getHeight() > height) || (bufferedImage.getWidth() > width)) {
                double ratioV = (new Integer(height).doubleValue() / bufferedImage.getHeight());
                double ratioH = (new Integer(width).doubleValue() / bufferedImage.getWidth());
                ratio = Math.max(ratioV, ratioH);
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                tempImg = op.filter(bufferedImage, null);
            }
            if (whiteFill) {
                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = img.createGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);
                if (width == tempImg.getWidth(null)) {
                    g.drawImage(tempImg, 0, (height - tempImg.getHeight(null)) / 2, tempImg.getWidth(null), tempImg.getHeight(null), Color.WHITE, null);
                } else {
                    g.drawImage(tempImg, (width - tempImg.getWidth(null)) / 2, 0, tempImg.getWidth(null), tempImg.getHeight(null), Color.WHITE, null);
                }
                g.dispose();
                tempImg = img;
            }
            ImageIO.write((BufferedImage)tempImg, "JPEG", new File(disPath));
        } catch (IOException e) {
            //
            e.printStackTrace();
        }
    }
}
