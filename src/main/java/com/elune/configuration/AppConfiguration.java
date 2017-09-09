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


package com.elune.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * Configurations
 *
 * @author Touchumind
 * @since 0.0.1
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AppConfiguration {

    private Properties props = new Properties();

    public static AppConfiguration newInstance() {

        return new AppConfiguration();
    }

    public AppConfiguration set(@NonNull String key, @NonNull Object value) {

        props.put(key, value);

        return this;
    }

    public AppConfiguration setMulti(@NonNull Map<String, String> map) {

        map.forEach(props::setProperty);

        return this;
    }

    public Optional<String> get(@NonNull String key) {

        return Optional.ofNullable(props.getProperty(key));
    }

    public String get(@NonNull String key, String defaultValue) {

        return props.getProperty(key, defaultValue);
    }

    public Optional<Object> getObject(@NonNull String key) {

        return Optional.ofNullable(props.get(key));
    }

    public Optional<Integer> getInt(@NonNull String key) {

        Optional<Object> optional = getObject(key);

        return optional.isPresent() ? Optional.of(Integer.parseInt(optional.get().toString())) : Optional.empty();
    }

    public Integer getInt(@NonNull String key, int defaultValue) {

        return getInt(key).orElse(defaultValue);
    }

    public Optional<Long> getLong(@NonNull String key) {

        Optional<Object> optional = getObject(key);

        return optional.isPresent() ? Optional.of(Long.parseLong(optional.get().toString())) : Optional.empty();
    }

    public Long getLong(@NonNull String key, long defaultValue) {

        return getLong(key).orElse(defaultValue);
    }

    public Optional<Double> getDouble(@NonNull String key) {

        Optional<Object> optional = getObject(key);

        return optional.isPresent() ? Optional.of(Double.parseDouble(optional.get().toString())) : Optional.empty();
    }

    public Double getDouble(@NonNull String key, double defaultValue) {

        return getDouble(key).orElse(defaultValue);
    }

    public Optional<Boolean> getBool(@NonNull String key) {

        Optional<Object> optional = getObject(key);

        return optional.isPresent() ? Optional.of(Boolean.parseBoolean(optional.get().toString())) : Optional.empty();
    }

    public Boolean getBool(@NonNull String key, boolean defaultValue) {

        return getBool(key).orElse(defaultValue);
    }

    public Properties getProps() {

        return props;
    }
}
