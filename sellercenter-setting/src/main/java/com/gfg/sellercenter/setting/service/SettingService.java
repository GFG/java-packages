package com.gfg.sellercenter.setting.service;

import com.gfg.sellercenter.setting.entity.Setting;
import com.gfg.sellercenter.setting.reader.HttpReader;
import lombok.AllArgsConstructor;
import org.json.JSONObject;

/**
 * This service provides you the possibility to retrieve either global or seller settings
 *
 * @author Dennis Munchausen <dennis.munchausen@global-fashion-group.com>
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
public class SettingService implements SettingServiceInterface {

    private HttpReader httpReader;

    /**
     * @param field name of the field in the database (global setting)
     * @return Setting it returns a setting object with main important values for a setting
     * @throws Exception throws an exception when the endpoint is not reachable or no setting were found
     */
    public Setting getSetting(String field, int sellerId) throws Exception
    {
        return createSettingEntity(
            httpReader.getSetting(field, sellerId)
        );
    }

    /**
     * @param field name of the field in the database (global setting)
     * @return Setting it returns a setting object with main important values for a setting
     * @throws Exception throws an exception when the endpoint is not reachable or no setting were found
     */
    public Setting getSetting(String field) throws Exception
    {
        return createSettingEntity(
            httpReader.getSetting(field)
        );
    }

    private Setting createSettingEntity(JSONObject response)
    {
        return new Setting(
            response.getInt("sellerId"),
            response.getString("field"),
            response.getString("value")
        );
    }
}
